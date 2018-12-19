package org.wappli.auth.server.service;

import org.wappli.auth.api.dto.output.UzerAuth;

public interface UzerAuthService {

    UzerAuth getAuth(String email);
}
