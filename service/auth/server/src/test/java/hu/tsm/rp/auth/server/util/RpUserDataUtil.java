package org.wappli.auth.server.util;

import org.wappli.auth.model.domain.DctCountry;
import org.wappli.auth.model.domain.DctSettlement;
import org.wappli.auth.model.domain.UzerData;

import java.time.LocalDate;

public final class UzerDataUtil {

    public static final String DEFAULT_TSM_UNIVAZ_CODE = "tsm1234";

    private UzerDataUtil() {
    }

    public static UzerData createDefaultEntity(DctSettlement birthPlace, DctCountry citizenship) {
        final UzerData entity = new UzerData();
        entity.setTitle("title");
        entity.setFirstname("firstname");
        entity.setSurname("surname");
        entity.setMotherName("motherName");
        entity.setBirthPlace(birthPlace);
        entity.setBirthDate(LocalDate.now());
        entity.setCitizenship(citizenship);
        entity.setTelNumber("01234567890");
        entity.setTsmUnivazCode(DEFAULT_TSM_UNIVAZ_CODE);
        return entity;
    }
}
