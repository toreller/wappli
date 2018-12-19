package org.wappli.auth.api.interfaces;

import org.wappli.auth.api.dto.output.UzerAuth;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface AuthInterface {

    String AUTH_URL = "/auth";

    @GetMapping(AUTH_URL + "/{username}")
    ResponseEntity<UzerAuth> getAuth(@PathVariable("username") String username);
}
