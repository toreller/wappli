package org.wappli.auth.server.web.rest;

import org.wappli.auth.api.criteria.CompetenceCriteria;
import org.wappli.auth.api.dto.input.CompetenceInputDTO;
import org.wappli.auth.api.dto.output.CompetenceOutputDTO;
import org.wappli.auth.model.domain.Competence;
import org.wappli.auth.server.repository.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CompetenceResourceIntTest extends AbstractQueryResourceIntTest<CompetenceOutputDTO, CompetenceInputDTO,
        CompetenceCriteria, Competence> {

    private static final String ENTITY_URL = "/competence";
    private static final String COMPETENCE_NAME = "java";

    @Autowired
    private CompetenceRepository competenceRepository;

    @Override
    protected Competence createEntityForQuery() {
        final Competence competence = new Competence();
        competence.setCompetenceName(COMPETENCE_NAME);
        return competence;
    }

    @Override
    protected ResultActions assertJsonObjectContainsDefaultValues(ResultActions resultActions) throws Exception {
        return resultActions.andExpect(jsonPath("$.name").value(COMPETENCE_NAME));
    }


    @Override
    protected ResultActions assertAllJsonObjectsContainDefaultValues(ResultActions resultActions) throws Exception {
        return resultActions.andExpect(jsonPath("$[*].name").value(COMPETENCE_NAME));
    }


    @Override
    protected String[] filtersForDefaultEntityShouldNotBeFound(Competence entity) {
        return new String[]{
                "name.equals=" + "sql",
                "name.in=" + "html" + "," + "sql",
                "name.specified=false",
                "id.equals=" + Long.MAX_VALUE,
                "id.in=" + Long.MAX_VALUE,
                "id.specified=false"
        };
    }

    @Override
    protected String[] filtersForDefaultEntityShouldBeFound(Competence entity) {
        return new String[]{
                "name.equals=" + COMPETENCE_NAME,
                "name.in=" + COMPETENCE_NAME + "," + "sql",
                "name.specified=true",
                "id.equals=" + entity.getId(),
                "id.in=" + entity.getId(),
                "id.specified=true"
        };
    }

    @Override
    protected String getEntityUrl() {
        return ENTITY_URL;
    }

    @Override
    JpaRepository<Competence, Long> getRepository() {
        return competenceRepository;
    }
}
