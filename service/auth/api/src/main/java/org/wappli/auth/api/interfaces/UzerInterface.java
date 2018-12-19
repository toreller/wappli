package org.wappli.auth.api.interfaces;

import org.wappli.auth.api.criteria.UzerCriteria;
import org.wappli.auth.api.dto.entities.UzerDTO;
import org.wappli.auth.api.dto.input.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;

import javax.validation.Valid;
import java.util.List;

public interface UzerInterface {

    String ENTITY_URL = "/users";

    @GetMapping(ENTITY_URL)
    ResponseEntity<List<EntityWithIdOutputDTO<UzerDTO>>> getAllUsers(UzerCriteria criteria, PageableDTO pageable);

    @GetMapping(ENTITY_URL + "/{id}")
    ResponseEntity<EntityWithIdOutputDTO<UzerDTO>> getUser(@PathVariable("id") Long id);

    @PostMapping(ENTITY_URL + "/register")
    ResponseEntity<EntityWithIdOutputDTO<UzerDTO>> register(@Valid @RequestBody UzerRegInputDTO idto, @RequestHeader("Accept-Language") String lang);

    @DeleteMapping(ENTITY_URL + "/{id}")
    ResponseEntity<IdDTO> delete(@PathVariable("id") Long id);

    @GetMapping(ENTITY_URL + "/confirm/{hash}")
    ResponseEntity<Void> confirm(@PathVariable("hash") String hash);

    @PostMapping(ENTITY_URL + "/changePassword")
    ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordInputDTO idto);

    @PostMapping(ENTITY_URL + "/forgottenPassword")
    ResponseEntity<Void> forgottenPassword(@Valid @RequestBody ForgottenPasswordInputDTO idto, @RequestHeader("Accept-Language") String lang);

    @PostMapping(ENTITY_URL + "/newPassword")
    ResponseEntity<Void> newPassword(@Valid @RequestBody NewPasswordInputDTO idto);
}
