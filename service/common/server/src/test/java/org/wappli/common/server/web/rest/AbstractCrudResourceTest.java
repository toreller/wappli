package org.wappli.common.server.web.rest;

import org.junit.After;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.wappli.common.api.rest.dto.entities.EntityDTO;
import org.wappli.common.api.rest.util.HasId;
import org.wappli.common.server.domain.AbstractEntity;
import org.wappli.common.server.service.AbstractSinglesDb;
import org.wappli.common.server.service.CrudService;
import org.wappli.common.server.service.mapper.IOEntityMapper;
import org.wappli.common.server.service.query.QueryService;
import org.wappli.common.server.web.resolver.PageableDTOResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.wappli.common.server.config.Constants.BE_API_V;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.wappli.common.server.web.rest.TestUtil.createFormattingConversionService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public abstract class AbstractCrudResourceTest<ENTITY extends AbstractEntity & HasId,
        EDTO extends EntityDTO,
        CRITERIA,
        REPOSITORY extends JpaRepository<ENTITY, Long>,
        SERVICE extends CrudService<ENTITY>,
        QUERY_SERVICE extends QueryService<ENTITY, CRITERIA>,
        MAPPER extends IOEntityMapper<EDTO, ENTITY>,
        RESOURCE extends ResourceWithControllerLogic<ENTITY, EDTO, CRITERIA, SERVICE, QUERY_SERVICE, MAPPER>> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected REPOSITORY repository;
    protected MAPPER mapper;
    protected SERVICE service;
    protected QUERY_SERVICE queryService;
    protected AbstractSinglesDb singlesDb;

    @Autowired
    protected EntityManager em;

    @Autowired
    protected MockMvc restMockMvc;

    @Before
    public void setup() {
        setServices();
        singlesDb.create();
    }

    protected abstract String getEntityURL();

    abstract protected void setServices();

    protected abstract EDTO createAnotherEntityDTO();

    @After
    public void purgeDb() {
        singlesDb.truncateAll();
    }

    @Test
    @Transactional
    public void createEntity() throws Exception {
        int databaseSizeBeforeCreate = repository.findAll().size();

        EDTO entityDTO = createAnotherEntityDTO();
        String contentAsString = restMockMvc.perform(post(BE_API_V + getEntityURL())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(entityDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        List<ENTITY> entityList = repository.findAll();
        assertThat(entityList).hasSize(databaseSizeBeforeCreate + 1);
        ENTITY testEntity = entityList.get(entityList.size() - 1);
        assertFieldsEqualRequired(testEntity);
        JSONAssert.assertEquals("{id: " + testEntity.getId() + "}", contentAsString, JSONCompareMode.STRICT);
    }

    protected abstract void assertFieldsEqualRequired(ENTITY testEntity);

    @Test
    @Transactional
    public void checkFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = repository.findAll().size();

        EDTO entityDTO = createAnotherEntityDTO();
        entityDTO = setARequiredFieldToNull(entityDTO);

        restMockMvc.perform(post(BE_API_V + getEntityURL())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityDTO)))
            .andExpect(status().isBadRequest());

        List<ENTITY> entityList = repository.findAll();
        assertThat(entityList).hasSize(databaseSizeBeforeTest);
    }

    protected abstract EDTO setARequiredFieldToNull(EDTO entitiy);

    @Test
    @Transactional
    public void getAllEntities() throws Exception {
        // Get all the repositoryList
        assertAllJsonObjectsContainDefaultValues(restMockMvc.perform(get(BE_API_V + getEntityURL() + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(getIntEntityId()))));
    }

    private int getIntEntityId() {
        return (int) getEntityId();
    }

    abstract protected long getEntityId();

    abstract protected ResultActions assertAllJsonObjectsContainDefaultValues(ResultActions resultActions) throws Exception;

    @Test
    @Transactional
    public void getEntity() throws Exception {

        // Get the entity
        ResultActions resultActions = restMockMvc.perform(get(BE_API_V + getEntityURL() + "/{id}", getEntityId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(getIntEntityId()));
        assertJsonObjectContainsDefaultValues(resultActions);
    }

    abstract protected ResultActions assertJsonObjectContainsDefaultValues(ResultActions resultActions) throws Exception;

    @Test
    @Transactional
    public void testsWithDefaultEntityShouldBeFound() throws Exception {
        for (String filter : filtersForDefaultEntityShouldBeFound()) {
            log.debug("testing defaultEntityShouldBeFound with filter <" + filter + ">");
            defaultEntityShouldBeFound(filter);
        }
    }

    @Test
    @Transactional
    public void testsWithDefaultEntityShouldNotBeFound() throws Exception {
        for (String filter : filtersForDefaultEntityShouldNotBeFound()) {
            log.debug("testing defaultEntityShouldNotBeFound with filter <" + filter + ">");
            defaultEntityShouldNotBeFound(filter);
        }
    }

    abstract protected String[] filtersForDefaultEntityShouldBeFound();
    abstract protected String[] filtersForDefaultEntityShouldNotBeFound();

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEntityShouldBeFound(String filter) throws Exception {
        ResultActions resultActions = restMockMvc.perform(get(BE_API_V + getEntityURL() + "?sort=id,desc&" + filter))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[*].id").value(hasItem(getIntEntityId())));
        defaultEntityShouldBeFoundAttributeCheck(resultActions);
    }

    abstract protected ResultActions defaultEntityShouldBeFoundAttributeCheck(ResultActions resultActions) throws Exception;

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEntityShouldNotBeFound(String filter) throws Exception {
        restMockMvc.perform(get(BE_API_V + getEntityURL() + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingEntity() throws Exception {
        restMockMvc.perform(get(BE_API_V + getEntityURL() + "/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntity() throws Exception {
        int databaseSizeBeforeUpdate = repository.findAll().size();

        EDTO entityDTO = createAnotherEntityDTO();
        setFieldsToUpdated(entityDTO);

        restMockMvc.perform(put(BE_API_V + getEntityURL() + "/" + getEntityId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityDTO)))
            .andExpect(status().isOk());

        // Validate the ENTITY in the database
        List<ENTITY> repositoryList = repository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeUpdate);
        ENTITY testEntity = repository.findById(getEntityId()).orElse(null);
        assertFieldsEqualUpdated(testEntity);
    }

    protected abstract void setFieldsToUpdated(EDTO updatedEntityDTO);
    protected abstract void assertFieldsEqualUpdated(ENTITY testEntity);

    @Test
    @Transactional
    public void deleteEntity() throws Exception {
        int databaseSizeBeforeDelete = repository.findAll().size();

        // Get the entity
        restMockMvc.perform(delete(BE_API_V + getEntityURL() + "/{id}", getEntityId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ENTITY> repositoryList = repository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
