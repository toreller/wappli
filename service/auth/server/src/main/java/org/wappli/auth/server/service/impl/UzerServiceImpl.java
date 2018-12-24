package org.wappli.auth.server.service.impl;

import org.wappli.auth.api.dto.entities.UzerDTO;
import org.wappli.auth.api.dto.input.UzerRegInputDTO;
import org.wappli.auth.api.enums.UzerStatusEnum;
import org.wappli.auth.server.config.ApplicationConfig;
import org.wappli.auth.server.domain.Role;
import org.wappli.auth.server.domain.UzerRoles;
import org.wappli.auth.server.domain.Uzer;
import org.wappli.auth.server.repository.RoleRepository;
import org.wappli.auth.server.repository.UzerRepository;
import org.wappli.auth.server.service.UzerService;
import org.wappli.auth.server.service.mapper.UzerMapper;
import org.wappli.auth.server.web.rest.errors.UzerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.server.service.AbstractCrudService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class UzerServiceImpl extends AbstractCrudService<Uzer, UzerRepository> implements UzerService {
    private static Logger LOG = LoggerFactory.getLogger(UzerServiceImpl.class);

    private final ApplicationConfig applicationConfig;
    private final UserEmailService emailService;
    private final UzerMapper uzerMapper;
    private final RoleRepository roleRepository;

    public UzerServiceImpl(ApplicationConfig applicationConfig,
                           UserEmailService emailService,
                           UzerRepository uzerRepository,
                           UzerMapper uzerMapper,
                           RoleRepository roleRepository) {
        super(uzerRepository);
        this.applicationConfig = applicationConfig;
        this.emailService = emailService;
        this.uzerMapper = uzerMapper;
        this.roleRepository = roleRepository;
    }


    public EntityWithIdOutputDTO<UzerDTO> register(UzerRegInputDTO uzerRegInputDTO, Locale locale) {
        Optional<Role> userRole = roleRepository.findByKeyIgnoreCase(UzerRoles.USER);

        Uzer uzer = uzerMapper.toEntity(uzerRegInputDTO, createActivationHash(), calculateAccountActivationDeadline(),
                Instant.now(), Instant.now(), Instant.now(), userRole.map(Arrays::asList).orElse(Collections.emptyList()));

        uzer = repository.save(uzer);
        emailService.sendRegistrationConfirmEmail(uzer, locale);

        return uzerMapper.toDtoWithId(uzer);
    }

    @Override
    public String createActivationHash() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean confirm(String activationHash) {
        final Uzer uzer = repository.findByActivationHashAndActivateDeadlineGreaterThan(activationHash, repository.currentTime());

        if (uzer == null) {
            LOG.debug("Could not activate account! Activation hash[{}]", activationHash);
            return false;
        }

        uzer.setActivated(true);

        repository.save(uzer);

        return true;
    }

    @Override
    public Uzer findByEmail(String email) {
        Uzer uzer = repository.findByEmail(email);

        if (uzer == null) {
            throw new UzerNotFoundException();
        }

        return uzer;
    }

    @Override
    public Instant calculateAccountActivationDeadline() {
        final Instant currentTime = repository.currentTime();

        return currentTime.plus(applicationConfig.getAccountActivationPeriod(), ChronoUnit.DAYS);
    }

    @Override
    public void changePassword(String newPsw, String email) {
        Uzer uzer = repository.findByEmail(email);
        if (uzer == null) {
            throw new UzerNotFoundException();
        }

        uzer.setPsw(newPsw);
        repository.save(uzer);
    }

    @Override
    public void newPassword(String newPsw, String activationHash) {
        final Uzer uzer = repository.findByActivationHashAndActivateDeadlineGreaterThan(activationHash, repository.currentTime());

        if (uzer == null) {
            throw new UzerNotFoundException();
        }

        uzer.setPsw(newPsw);
        repository.save(uzer);
    }

    @Override
    public void forgottenPassword(String email, Locale locale) {
        Uzer uzer = findByEmail(email);
        if (uzer == null) {
            throw new UzerNotFoundException();
        }

        uzer.setActivationHash(createActivationHash());
        uzer.setActivateDeadline(calculateAccountActivationDeadline());

        uzer = repository.save(uzer);

        emailService.sendForgottenPasswordEmail(uzer, locale);
    }

    @Override
    public void delete(Uzer entity) {
        final Optional<Uzer> uzerOptional = repository.findById(entity.getId());

        uzerOptional.ifPresent(u -> {
            u.setUserStatus(UzerStatusEnum.INACTIVE);
            repository.save(u);
        });
    }
}
