package org.wappli.auth.server.service;


import org.wappli.auth.api.dto.entities.UzerDTO;
import org.wappli.auth.api.dto.input.UzerRegInputDTO;
import org.wappli.auth.server.domain.Uzer;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.server.service.CrudService;

import java.time.Instant;
import java.util.Locale;

public interface UzerService extends CrudService<Uzer> {
    EntityWithIdOutputDTO<UzerDTO> register(UzerRegInputDTO uzerRegInputDTO, Locale locale);
    String createActivationHash();
    boolean confirm(String verificationHash);
    Uzer findByEmail(String email);
    Instant calculateAccountActivationDeadline();
    void changePassword(String newPsw, String email);
    void newPassword(String newPsw, String activationHash);
    void forgottenPassword(String email, Locale locale);
}
