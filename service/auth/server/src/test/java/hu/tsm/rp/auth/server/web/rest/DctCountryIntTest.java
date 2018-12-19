package org.wappli.auth.server.web.rest;

import org.wappli.auth.api.criteria.DctCountryCriteria;
import org.wappli.auth.api.dto.input.DctCountryInputDTO;
import org.wappli.auth.api.dto.output.DctCountryOutputDTO;
import org.wappli.auth.model.domain.DctCountry;
import org.wappli.auth.server.repository.DctCountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DctCountryIntTest extends AbstractQueryResourceIntTest<DctCountryOutputDTO, DctCountryInputDTO,
        DctCountryCriteria, DctCountry> {

    private static final String COUNTRY = "Hungary";
    private static final String COUNTRY_CODE = "hu";

    @Autowired
    private DctCountryRepository dctCountryRepository;

    @Override
    protected String getEntityUrl() {
        return "/countries";
    }

    @Override
    protected DctCountry createEntityForQuery() {
        final DctCountry dctCountry = new DctCountry();
        dctCountry.setCountryCode(COUNTRY_CODE);
        dctCountry.setCountryName(COUNTRY);
        return dctCountry;
    }

    @Override
    protected ResultActions assertJsonObjectContainsDefaultValues(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(jsonPath("$.name").value(COUNTRY))
                .andExpect(jsonPath("$.code").value(COUNTRY_CODE));
    }

    @Override
    protected ResultActions assertAllJsonObjectsContainDefaultValues(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(jsonPath("$[*].name").value(COUNTRY))
                .andExpect(jsonPath("$[*].code").value(COUNTRY_CODE));
    }

    @Override
    protected String[] filtersForDefaultEntityShouldBeFound(DctCountry entity) {
        return new String[]{
                "name.equals=" + COUNTRY,
                "name.in=" + COUNTRY,
                "name.specified=true",
                "code.equals=" + COUNTRY_CODE,
                "code.in=" + COUNTRY_CODE,
                "code.specified=true",
                "id.equals=" + entity.getId(),
                "id.in=" + entity.getId(),
                "id.specified=true"
        };
    }

    @Override
    protected String[] filtersForDefaultEntityShouldNotBeFound(DctCountry entity) {
        return new String[]{
                "name.equals=" + "magyarorszag",
                "name.in=" + "magyarorszag",
                "name.specified=false",
                "code.equals=" + "12",
                "code.in=" + "12",
                "code.specified=" + "false",
                "id.equals=" + Long.MAX_VALUE,
                "id.in=" + Long.MAX_VALUE,
                "id.specified=false"
        };
    }

    @Override
    JpaRepository<DctCountry, Long> getRepository() {
        return dctCountryRepository;
    }
}
