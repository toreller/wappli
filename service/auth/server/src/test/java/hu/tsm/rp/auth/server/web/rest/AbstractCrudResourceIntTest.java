package org.wappli.auth.server.web.rest;

import org.wappli.auth.api.dto.input.InputDTO;
import org.wappli.auth.api.dto.output.OutputDTO;
import org.wappli.auth.api.util.HasId;
import org.wappli.auth.model.domain.AbstractEntity;
import org.wappli.auth.server.AuthServerApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static org.wappli.auth.api.constant.Constants.API_BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServerApp.class)
@AutoConfigureMockMvc
public abstract class AbstractCrudResourceIntTest<ODTO extends OutputDTO,
        IDTO extends InputDTO,
        CRITERIA,
        ENTITY extends AbstractEntity & HasId> extends AbstractQueryResourceIntTest<ODTO, IDTO, CRITERIA, ENTITY> {
    static protected Locale LOCALE = Locale.ENGLISH;
    
    public static final String ACCEPT_LANGUAGE = "Accept-Language";

    @Test
    @Transactional
    public void testUpdateEntity() throws Exception {

        ENTITY entityToUpdate = createEntityForUpdate();

        entityToUpdate = getRepository().saveAndFlush(entityToUpdate);

        long countBeforeUpdate = getRepository().count();

        final IDTO iDTO = createTestUpdateInput(entityToUpdate);

        final Long id = entityToUpdate.getId();
        ResultActions resultActions = restMockMvc.perform(
                put(API_BASE_URL + getEntityUrl() + "/{id}", id)
                        .header(ACCEPT_LANGUAGE, LOCALE.getLanguage())
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(iDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        long countAfterUpdate = getRepository().count();
        assertThat(countAfterUpdate).isEqualTo(countBeforeUpdate);

        resultActions = verifyUpdatedEntity(getRepository().getOne(id), resultActions);
        verifyUpdateResponse(resultActions);
    }

    @Test
    @Transactional
    public void testCreateEntity() throws Exception {

        long countBeforeUpdate = getRepository().count();

        final IDTO iDTO = createTestCreateInput();

        ResultActions resultActions = restMockMvc.perform(
                post(API_BASE_URL + getEntityUrl() + getCreateUrl())
                        .header(ACCEPT_LANGUAGE, LOCALE.getLanguage())
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(iDTO)))
                .andExpect(status().isOk());

        long countAfterUpdate = getRepository().count();
        assertThat(countAfterUpdate).isEqualTo(countBeforeUpdate + 1);

        verifyBeanInteractionsForCreate();

        verifyUpdateResponse(resultActions);
    }

    protected void verifyBeanInteractionsForCreate() {
    }

    protected String getCreateUrl() {
        return "";
    }

    protected abstract IDTO createTestCreateInput();

    abstract ResultActions verifyCreateResponse(ResultActions resultActions) throws Exception;

    abstract ResultActions verifyUpdateResponse(ResultActions resultActions) throws Exception;

    protected ResultActions verifyUpdatedEntity(ENTITY entity, ResultActions resultActions) {
        return resultActions;
    }

    abstract IDTO createTestUpdateInput(ENTITY entity);

    abstract ENTITY createEntityForUpdate();

}
