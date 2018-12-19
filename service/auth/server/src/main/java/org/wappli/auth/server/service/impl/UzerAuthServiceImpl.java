package org.wappli.auth.server.service.impl;

import org.wappli.auth.api.dto.output.UzerAuth;
import org.wappli.auth.server.domain.Uzer;
import org.wappli.auth.server.service.UzerAuthService;
import org.wappli.auth.server.service.UzerService;
import org.wappli.auth.server.service.mapper.UzerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UzerAuthServiceImpl implements UzerAuthService {

    private final UzerService uzerService;

    private final UzerMapper uzerMapper;

    public UzerAuthServiceImpl(UzerService uzerService,
                                 UzerMapper uzerMapper) {
        this.uzerService = uzerService;
        this.uzerMapper = uzerMapper;
    }

    @Override
    public UzerAuth getAuth(String email) {
        final Uzer uzer = uzerService.findByEmail(email);
        return uzerMapper.toAuthDTO(uzer);
    }
}
