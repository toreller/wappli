package org.wappli.auth.server.web.rest;

import org.wappli.auth.api.criteria.DctSettlementCriteria;
import org.wappli.auth.api.dto.input.DctSettlementInputDTO;
import org.wappli.auth.api.dto.output.DctSettlementOutputDTO;
import org.wappli.auth.model.domain.DctSettlement;
import org.wappli.auth.server.repository.DctSettlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DctSettlementIntTest extends AbstractQueryResourceIntTest<DctSettlementOutputDTO, DctSettlementInputDTO,
        DctSettlementCriteria, DctSettlement> {


    private static final String SETTLEMENT_NAME = "Budapest";
    private static final String ZIP = "1100";

    @Autowired
    private DctSettlementRepository dctSettlementRepository;

    @Override
    protected String getEntityUrl() {
        return "/settlements";
    }

    @Override
    protected DctSettlement createEntityForQuery() {
        final DctSettlement dctSettlement = new DctSettlement();
        dctSettlement.setSettlementName(SETTLEMENT_NAME);
        dctSettlement.setZip(ZIP);
        return dctSettlement;
    }

    @Override
    protected ResultActions assertJsonObjectContainsDefaultValues(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(jsonPath("$.name").value(SETTLEMENT_NAME))
                .andExpect(jsonPath("$.zip").value(ZIP));
    }

    @Override
    protected ResultActions assertAllJsonObjectsContainDefaultValues(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(jsonPath("$[*].name").value(SETTLEMENT_NAME))
                .andExpect(jsonPath("$[*].zip").value(ZIP));
    }

    @Override
    protected String[] filtersForDefaultEntityShouldBeFound(DctSettlement entity) {
        return new String[]{
                "name.equals=" + SETTLEMENT_NAME,
                "name.in=" + SETTLEMENT_NAME,
                "name.specified=true",
                "zip.equals=" + ZIP,
                "zip.in=" + ZIP,
                "zip.specified=true",
                "id.equals=" + entity.getId(),
                "id.in=" + entity.getId(),
                "id.specified=true"
        };
    }

    @Override
    protected String[] filtersForDefaultEntityShouldNotBeFound(DctSettlement entity) {
        return new String[]{
                "name.equals=" + "Szeged",
                "name.in=" + "Szeged",
                "name.specified=false",
                "zip.equals=" + "1234",
                "zip.in=" + "1234",
                "zip.specified=false",
                "id.equals=" + Long.MAX_VALUE,
                "id.in=" + Long.MAX_VALUE,
                "id.specified=false"
        };
    }


    @Override
    JpaRepository<DctSettlement, Long> getRepository() {
        return dctSettlementRepository;
    }
}
