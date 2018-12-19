package org.wappli.auth.server.web.rest;

import org.apache.commons.lang.LocaleUtils;
import org.springframework.data.domain.PageRequest;
import org.wappli.auth.api.constant.Constants;
import org.wappli.auth.api.criteria.UzerCriteria;
import org.wappli.auth.api.dto.entities.UzerDTO;
import org.wappli.auth.api.dto.input.*;
import org.wappli.auth.api.interfaces.UzerInterface;
import org.wappli.auth.server.domain.Uzer;
import org.wappli.auth.server.service.UzerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wappli.auth.server.service.impl.UzerQueryService;
import org.wappli.auth.server.service.mapper.UzerMapper;
import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;
import org.wappli.common.server.logging.MessageLogger;
import org.wappli.common.server.web.rest.ResourceWithControllerLogic;
import org.wappli.common.server.web.util.ResponseUtil;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Constants.API_BASE_URL + UzerInterface.ENTITY_URL)
public class UzerResource extends ResourceWithControllerLogic<Uzer,
        UzerDTO,
        UzerCriteria,
        UzerService,
        UzerQueryService,
        UzerMapper> implements UzerInterface {

    private static final String ENTITY_NAME = "Uzer";

    private final UzerService uzerService;

    public UzerResource(UzerService uzerService, UzerQueryService uzerQueryService, UzerMapper uzerMapper, MessageLogger log) {
        super(createControllerLogic(uzerService, uzerQueryService, uzerMapper, log, ENTITY_NAME, ENTITY_URL));

        this.uzerService = uzerService;
    }


    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EntityWithIdOutputDTO<UzerDTO>> getUser(@PathVariable Long id) {
        return controllerLogic.get(id);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<EntityWithIdOutputDTO<UzerDTO>>> getAllUsers(UzerCriteria criteria, PageableDTO pageableDTO) {
        return controllerLogic.getAll(criteria, PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize(), getSort(pageableDTO.getSort())));
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<EntityWithIdOutputDTO<UzerDTO>> register(@Valid @RequestBody UzerRegInputDTO idto, @RequestHeader("Accept-Language") String lang) {
        return ResponseEntity.ok(uzerService.register(idto, LocaleUtils.toLocale(lang)));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<IdDTO> delete(@PathVariable Long id) {
        return controllerLogic.delete(id);
    }

    @Override
    @GetMapping("/confirm/{hash}")
    public ResponseEntity<Void> confirm(@PathVariable String hash) {
        final boolean success = uzerService.confirm(hash);

        if (success) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    @PostMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordInputDTO idto) {
        uzerService.changePassword(idto.getNewPsw(), idto.getEmail());

        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/forgottenPassword")
    public ResponseEntity<Void> forgottenPassword(@Valid @RequestBody ForgottenPasswordInputDTO idto, @RequestHeader("Accept-Language") String lang) {
        uzerService.forgottenPassword(idto.getEmail(), LocaleUtils.toLocale(lang));

        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/newPassword")
    public ResponseEntity<Void> newPassword(@Valid @RequestBody NewPasswordInputDTO idto) {
        uzerService.newPassword(idto.getNewPsw(), idto.getActivationHash());

        return ResponseEntity.ok().build();
    }
}
