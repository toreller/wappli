package org.wappli.auth.server.web.rest;

import org.wappli.auth.api.dto.input.InputDTO;
import org.wappli.auth.api.dto.output.OutputDTO;
import org.wappli.auth.api.util.HasId;
import org.wappli.auth.model.domain.AbstractEntity;
import org.wappli.auth.server.AuthServerApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.wappli.auth.api.constant.Constants.API_BASE_URL;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServerApp.class)
@AutoConfigureMockMvc
public abstract class AbstractQueryResourceIntTest<ODTO extends OutputDTO,
        IDTO extends InputDTO,
        CRITERIA,
        ENTITY extends AbstractEntity & HasId> extends AbstractIntTest {

    @Autowired
    protected MockMvc restMockMvc;

    @Test
    @Transactional
    public void testFindById() throws Exception {
        final ENTITY entity = getRepository().saveAndFlush(createEntityForQuery());
        final ResultActions resultActions = restMockMvc.perform(
                get(API_BASE_URL+ getEntityUrl() + "/{id}", entity.getId().intValue())
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(entity.getId().intValue()));

        assertJsonObjectContainsDefaultValues(resultActions);

    }

    @Test
    @Transactional
    public void getAllEntities() throws Exception {
        final ENTITY entity = getRepository().saveAndFlush(createEntityForQuery());
        // Get all the repositoryList
        ResultActions resultActions = restMockMvc.perform(get(API_BASE_URL+ getEntityUrl()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(entity.getId().intValue())));
        assertAllJsonObjectsContainDefaultValues(resultActions);
    }


    @Test
    @Transactional
    public void testsWithDefaultEntityShouldBeFound() throws Exception {
        final ENTITY entity = getRepository().saveAndFlush(createEntityForEntityShouldBeFound());

        for (String filter : filtersForDefaultEntityShouldBeFound(entity)) {
            defaultEntityShouldBeFound(filter, entity);
        }
    }

    protected ENTITY createEntityForEntityShouldBeFound() {
        return createEntityForQuery();
    }

    @Test
    @Transactional
    public void testsWithDefaultEntityShouldNotBeFound() throws Exception {
        final ENTITY entity = getRepository().saveAndFlush(createEntityForEntityShouldNotBeFound());

        for (String filter : filtersForDefaultEntityShouldNotBeFound(entity)) {
            defaultEntityShouldNotBeFound(filter);
        }
    }

    protected ENTITY createEntityForEntityShouldNotBeFound() {
        return createEntityForQuery();
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEntityShouldBeFound(String filter, ENTITY entity) throws Exception {
        restMockMvc.perform(get(API_BASE_URL + getEntityUrl() + "?" + filter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(entity.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEntityShouldNotBeFound(String filter) throws Exception {
        restMockMvc.perform(get(API_BASE_URL + getEntityUrl() + "?" + filter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    abstract protected String[] filtersForDefaultEntityShouldBeFound(ENTITY entity);

    abstract protected String[] filtersForDefaultEntityShouldNotBeFound(ENTITY entity);

    abstract ResultActions assertAllJsonObjectsContainDefaultValues(ResultActions resultActions) throws Exception;

    abstract String getEntityUrl();

    abstract ENTITY createEntityForQuery();

    abstract ResultActions assertJsonObjectContainsDefaultValues(ResultActions resultActions) throws Exception;

    abstract JpaRepository<ENTITY, Long> getRepository();


}
