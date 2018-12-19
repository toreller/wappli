package org.wappli.auth.api.client;

import org.wappli.auth.api.interfaces.AuthInterface;
import org.springframework.cloud.openfeign.FeignClient;

import static org.wappli.auth.api.constant.Constants.API_BASE_URL;

@FeignClient(name = "auth", url = "${application.rest.auth}", path = API_BASE_URL)
public interface AuthClient extends AuthInterface {

}
