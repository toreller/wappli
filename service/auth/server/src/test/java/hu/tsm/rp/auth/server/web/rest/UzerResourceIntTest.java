package org.wappli.auth.server.web.rest;

import org.wappli.auth.api.criteria.UzerCriteria;
import org.wappli.auth.api.dto.input.*;
import org.wappli.auth.api.dto.output.UzerOutputDTO;
import org.wappli.auth.api.enums.UzerStatusEnum;
import org.wappli.auth.model.domain.DctCountry;
import org.wappli.auth.model.domain.Uzer;
import org.wappli.auth.model.domain.UzerData;
import org.wappli.auth.server.repository.UzerRepository;
import org.wappli.auth.server.service.impl.UserEmailService;
import org.wappli.auth.server.util.UzerDataUtil;
import org.wappli.auth.server.util.UzerGdprUtil;
import org.wappli.auth.server.util.UzerUtil;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.wappli.auth.api.constant.Constants.API_BASE_URL;
import static org.wappli.auth.server.util.UzerDataUtil.DEFAULT_TSM_UNIVAZ_CODE;
import static org.wappli.auth.server.util.UzerUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UzerResourceIntTest extends AbstractCrudResourceIntTest<UzerOutputDTO, UzerRegInputDTO, UzerCriteria, Uzer> {

    private static final String ENTITY_URL = "/users";
    private static final String EMAIL = "rpemail@t-systems.hu";
    private static final String PWD = "asd123";
    private static final String TSM_UNIVAZ_CODE = "123456789A";
    private static final String EMAIL_QUERY = "rpuserqueryemail@t-systems.hu";
    private static final String BIRTHDATE_STR = "1970-01-01";

    @MockBean
    private UserEmailService userEmailService;

    @Autowired
    private UzerRepository uzerRepository;

    @Test
    @Transactional
    public void testConfirm() throws Exception {
        final Uzer uzer = createUzer(EMAIL, PWD);

        final String hash = UUID.randomUUID().toString();
        uzer.setActivationHash(hash);
        uzer.setActivated(false);

        final Uzer userRegNotConfirmed = uzerRepository.saveAndFlush(uzer);

        restMockMvc.perform(get(API_BASE_URL + getEntityUrl() + "/confirm/{hash}", hash)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());

        final Uzer rpRegConfirmed = uzerRepository.getOne(userRegNotConfirmed.getId());
        assertThat(rpRegConfirmed.isActivated()).isEqualTo(true);
    }

    @Test
    @Transactional
    public void testInactivate() throws Exception {
        Uzer uzer = createUzer(EMAIL, PWD);
        uzer.setUserStatus(UzerStatusEnum.ACTIVE);
        uzer = uzerRepository.saveAndFlush(uzer);

        restMockMvc.perform(delete(API_BASE_URL + getEntityUrl() + "/{id}", uzer.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        final Uzer actualUser = uzerRepository.getOne(uzer.getId());
        assertThat(actualUser.getUserStatus()).isEqualTo(UzerStatusEnum.INACTIVE);
    }

    @Test
    @Transactional
    public void testChangePassword() throws Exception {
        Uzer uzer = createUzer(EMAIL, PWD);
        uzer = uzerRepository.saveAndFlush(uzer);

        final String newPassword = "newPassword";
        final ChangePasswordInputDTO iDTO = new ChangePasswordInputDTO();
        iDTO.setEmail(EMAIL);
        iDTO.setNewPsw(newPassword);

        restMockMvc.perform(post(API_BASE_URL + getEntityUrl() + "/changePassword")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(iDTO)))
                .andExpect(status().isOk());

        final Uzer actualUser = uzerRepository.getOne(uzer.getId());
        assertThat(actualUser.getPsw()).isEqualTo(newPassword);
    }

    @Test
    @Transactional
    public void testForgottenPassword() throws Exception {
        final String activationHash = UUID.randomUUID().toString();
        final Instant activateDeadline = Instant.now().minus(2, ChronoUnit.DAYS);

        Uzer uzer = createUzer(EMAIL, PWD);
        uzer.setActivationHash(activationHash);
        uzer.setActivateDeadline(activateDeadline);
        uzer = uzerRepository.saveAndFlush(uzer);


        final ForgottenPasswordInputDTO iDTO = new ForgottenPasswordInputDTO();
        iDTO.setEmail(EMAIL);

        restMockMvc.perform(post(API_BASE_URL + getEntityUrl() + "/forgottenPassword")
                .header(ACCEPT_LANGUAGE, LOCALE.getLanguage())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(iDTO)))
                .andExpect(status().isOk());

        final Uzer actualUser = uzerRepository.getOne(uzer.getId());
        assertThat(actualUser.getActivationHash()).isNotEqualTo(activationHash);
        assertThat(actualUser.getActivateDeadline()).isNotEqualTo(activateDeadline);

        ArgumentCaptor<Uzer> argCaptor = ArgumentCaptor.forClass(Uzer.class);

        Mockito.verify(userEmailService).sendForgottenPasswordEmail(argCaptor.capture(), eq(LOCALE));
        assertThat(actualUser.getId()).isEqualTo(argCaptor.getValue().getId());
    }

    @Test
    @Transactional
    public void testNewPassword() throws Exception {
        final String activationHash = UUID.randomUUID().toString();

        Uzer uzer = createUzer(EMAIL, PWD);
        uzer.setActivationHash(activationHash);
        uzer = uzerRepository.saveAndFlush(uzer);

        final String newPassword = "newPassword";
        final NewPasswordInputDTO iDTO = new NewPasswordInputDTO();
        iDTO.setActivationHash(activationHash);
        iDTO.setNewPsw(newPassword);

        restMockMvc.perform(post(API_BASE_URL + getEntityUrl() + "/newPassword")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(iDTO)))
                .andExpect(status().isOk());

        final Uzer actualUser = uzerRepository.getOne(uzer.getId());
        assertThat(actualUser.getPsw()).isEqualTo(newPassword);
    }

    @Override
    UzerRegInputDTO createTestUpdateInput(Uzer entity) {
        return UzerUtil.setInputData(new UzerRegInputDTO(), entity.getUserData().getCitizenship().getId(), TSM_UNIVAZ_CODE);
    }

    @Override
    Uzer createEntityForUpdate() {
        return createUzer(EMAIL, PWD);
    }

    private Uzer createUzer(String email, String pwd) {
        final Uzer uzer = UzerUtil.createEntity(email, pwd);
        final UzerData uzerData = UzerDataUtil.createDefaultEntity(createDefaultSettlement(), createDefaultCountry());
        uzer.setUserData(uzerData);
        uzer.setUzerGdpr(UzerGdprUtil.createDefaultEntity());
        return uzer;
    }

    @Override
    ResultActions verifyUpdateResponse(ResultActions resultActions) throws Exception {
        return verifyCreateUpdateResponse(resultActions);
    }

    private ResultActions verifyCreateUpdateResponse(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.userData.title").value(TITLE))
                .andExpect(jsonPath("$.userData.firstname").value(FIRSTNAME))
                .andExpect(jsonPath("$.userData.surname").value(SURNAME))
                .andExpect(jsonPath("$.userData.motherName").value(MOTHER_NAME))
                .andExpect(jsonPath("$.userData.tsmUnivazCode").value(DEFAULT_TSM_UNIVAZ_CODE))
                .andExpect(jsonPath("$.userData.telNumber").value(TEL_NUMBER))
                .andExpect(jsonPath("$.userData.birthPlace.name").value(BIRTHPLACE_NAME))
                .andExpect(jsonPath("$.userData.birthPlace.zip").value(ZIP))
                .andExpect(jsonPath("$.userData.birthDate").value(BIRTHDATE_STR))
                .andExpect(jsonPath("$.competence[0].name").value(COMPETENCE_JAVA))
                .andExpect(jsonPath("$.references[0].text").value(REFERENCE_TEXT))
                .andExpect(jsonPath("$.gdpr.dataProtectionManagement").value(true))
                .andExpect(jsonPath("$.gdpr.privacyStatement").value(true))
                .andExpect(jsonPath("$.gdpr.legalStatement").value(true))
                .andExpect(jsonPath("$.gdpr.tac").value(true));
    }

    @Override
    Uzer createEntityForQuery() {
        final Uzer uzer = createUzer(EMAIL_QUERY, "asd123");
        return uzer;
    }

    @Override
    ResultActions assertJsonObjectContainsDefaultValues(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(jsonPath("$.email").value(EMAIL_QUERY));
    }

    @Override
    ResultActions assertAllJsonObjectsContainDefaultValues(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(jsonPath("$[*].email").value(EMAIL_QUERY));
    }

    @Override
    String getEntityUrl() {
        return ENTITY_URL;
    }

    @Override
    protected String getCreateUrl() {
        return "/register";
    }

    @Override
    JpaRepository<Uzer, Long> getRepository() {
        return uzerRepository;
    }

    @Override
    protected UzerRegInputDTO createTestCreateInput() {
        final DctCountry defaultCountry = createDefaultCountry();
        final UzerRegInputDTO uzerRegInputDTO = new UzerRegInputDTO();
        uzerRegInputDTO.setEmail(EMAIL);
        uzerRegInputDTO.setPsw(PWD);
        return UzerUtil.setInputData(uzerRegInputDTO, defaultCountry.getId(), DEFAULT_TSM_UNIVAZ_CODE);
    }

    @Override
    protected void verifyBeanInteractionsForCreate() {
        ArgumentCaptor<Uzer> argCaptor = ArgumentCaptor.forClass(Uzer.class);
        Mockito.verify(userEmailService).sendRegistrationConfirmEmail(argCaptor.capture(), eq(LOCALE));
        final Uzer uzer = argCaptor.getValue();
        assertThat(uzer.getEmail()).isEqualTo(EMAIL);
    }

    @Override
    ResultActions verifyCreateResponse(ResultActions resultActions) throws Exception {
        return verifyCreateUpdateResponse(resultActions);
    }


    @Override
    protected Uzer createEntityForEntityShouldBeFound() {
        final Uzer entity = super.createEntityForEntityShouldBeFound();
        entity.setUserStatus(UzerStatusEnum.ACTIVE);
        return entity;
    }

    @Override
    protected String[] filtersForDefaultEntityShouldBeFound(Uzer entity) {
        return new String[]{
                "title.equals=" + "title",
                "title.in=" + "title",
                "title.specified=true",

                "firstname.equals=" + "firstname",
                "firstname.in=" + "firstname",
                "firstname.specified=true",

                "surname.equals=" + "surname",
                "surname.in=" + "surname",
                "surname.specified=true",

                "motherName.equals=" + "motherName",
                "motherName.in=" + "motherName",
                "motherName.specified=true",

                "email.equals=" + EMAIL_QUERY,
                "email.in=" + EMAIL_QUERY,
                "email.specified=true",

                "tsmUnivazCode.equals=" + DEFAULT_TSM_UNIVAZ_CODE,
                "tsmUnivazCode.in=" + DEFAULT_TSM_UNIVAZ_CODE,
                "tsmUnivazCode.specified=true",

                "status.equals=" + UzerStatusEnum.ACTIVE,
                "status.in=" + UzerStatusEnum.ACTIVE,
                "status.specified=true",

                "id.equals=" + entity.getId(),
                "id.in=" + entity.getId(),
                "id.specified=true"
        };
    }

    @Override
    protected String[] filtersForDefaultEntityShouldNotBeFound(Uzer entity) {
        return new String[]{
                "title.equals=" + "Mr",
                "title.in=" + "Mr",
                "title.specified=false",

                "firstname.equals=" + "Jim",
                "firstname.in=" + "Jim",
                "firstname.specified=false",

                "surname.equals=" + "Lastname",
                "surname.in=" + "Lastname",
                "surname.specified=false",

                "motherName.equals=" + "mother name",
                "motherName.in=" + "mother name",
                "motherName.specified=false",

                "email.equals=" + "nonexisting@t-systems.hu",
                "email.in=" + "nonexisting@t-systems.hu",
                "email.specified=false",

                "tsmUnivazCode.equals=" + "567",
                "tsmUnivazCode.in=" + "567",
                "tsmUnivazCode.specified=false",

                "status.equals=" + UzerStatusEnum.INACTIVE,
                "status.in=" + UzerStatusEnum.INACTIVE,
                "status.specified=false",

                "id.equals=" + Long.MAX_VALUE,
                "id.in=" + Long.MAX_VALUE,
                "id.specified=false"
        };
    }

}
