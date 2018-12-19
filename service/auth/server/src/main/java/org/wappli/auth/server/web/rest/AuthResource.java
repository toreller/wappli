package org.wappli.auth.server.web.rest;

import org.wappli.auth.api.dto.output.UzerAuth;
import org.wappli.auth.api.interfaces.AuthInterface;
import org.wappli.auth.server.service.UzerAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.wappli.auth.api.constant.Constants.API_BASE_URL;
import static org.wappli.auth.api.interfaces.AuthInterface.AUTH_URL;

@RestController
@RequestMapping(API_BASE_URL + AUTH_URL)
public class AuthResource implements AuthInterface {
    private final UzerAuthService uzerAuthService;

    public AuthResource(UzerAuthService uzerAuthService) {
        this.uzerAuthService = uzerAuthService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<UzerAuth> getAuth(@PathVariable String email) {
        return ResponseEntity.ok(uzerAuthService.getAuth(email));
    }
}
