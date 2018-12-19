package org.wappli.auth.server.util;

import org.wappli.auth.model.domain.DctCountry;

public final class DctCountryUtil {

    private DctCountryUtil() {
    }

    public static DctCountry createEntity(String name, String code) {
        final DctCountry entity = new DctCountry();
        entity.setCountryName(name);
        entity.setCountryCode(code);
        return entity;
    }
}
