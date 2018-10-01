package org.wappli.common.server.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;

import static org.wappli.common.server.config.Constants.BE_API_V;

@RequestMapping(value = BE_API_V)
public abstract class AbstractResource {

    protected abstract String getEntityURL();

}
