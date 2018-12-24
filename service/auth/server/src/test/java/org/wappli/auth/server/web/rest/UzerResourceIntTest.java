package org.wappli.auth.server.web.rest;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.wappli.auth.api.dto.input.*;
import org.wappli.auth.api.enums.UzerStatusEnum;
import org.wappli.auth.server.AuthServerApp;
import org.wappli.auth.server.domain.Uzer;
import org.wappli.auth.server.repository.UzerRepository;
import org.wappli.auth.server.service.impl.UserEmailService;
import org.wappli.auth.server.util.UzerUtil;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import org.wappli.common.server.web.rest.TestUtil;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.UUID;

import static org.wappli.auth.api.constant.Constants.API_BASE_URL;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServerApp.class)
@AutoConfigureMockMvc
public class UzerResourceIntTest {

    static protected Locale LOCALE = Locale.ENGLISH;
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    private static final String ENTITY_URL = "/users";
    private static final String USERNAME = "username";
    private static final String EMAIL = "rpemail@t-systems.hu";
    private static final String PWD = "asd123";
    private static final String TSM_UNIVAZ_CODE = "123456789A";
    private static final String EMAIL_QUERY = "rpuserqueryemail@t-systems.hu";
    private static final String BIRTHDATE_STR = "1970-01-01";

    @MockBean
    private UserEmailService userEmailService;

    @Autowired
    private UzerRepository uzerRepository;

    @Autowired
    protected MockMvc restMockMvc;

    @Test
    @Transactional
    public void testConfirm() throws Exception {
        final Uzer uzer1 = UzerUtil.createEntity(USERNAME, EMAIL, PWD);

        final Uzer uzer = uzer1;

        final String hash = UUID.randomUUID().toString();
        uzer.setActivationHash(hash);
        uzer.setActivated(false);

        final Uzer userRegNotConfirmed = uzerRepository.saveAndFlush(uzer);

        restMockMvc.perform(get(API_BASE_URL + getEntityUrl() + "/confirm/{hash}", hash)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());

        final Uzer rpRegConfirmed = uzerRepository.getOne(userRegNotConfirmed.getId());
        Assert.assertTrue(rpRegConfirmed.isActivated());
    }

    @Test
    @Transactional
    public void testInactivate() throws Exception {
        Uzer uzer = UzerUtil.createEntity(USERNAME, EMAIL, PWD);
        uzer.setUserStatus(UzerStatusEnum.ACTIVE);

        uzer = uzerRepository.saveAndFlush(uzer);

        restMockMvc.perform(delete(API_BASE_URL + getEntityUrl() + "/{id}", uzer.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        final Uzer actualUser = uzerRepository.getOne(uzer.getId());
        Assert.assertEquals(UzerStatusEnum.INACTIVE, actualUser.getUserStatus());
    }

    @Test
    @Transactional
    public void testChangePassword() throws Exception {
        final Uzer uzer1 = UzerUtil.createEntity(USERNAME, EMAIL, PWD);

        Uzer uzer = uzer1;
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
        Assert.assertEquals(newPassword, actualUser.getPsw());
    }

    @Test
    @Transactional
    public void testForgottenPassword() throws Exception {
        final String activationHash = UUID.randomUUID().toString();
        final Instant activateDeadline = Instant.now().minus(2, ChronoUnit.DAYS);

        final Uzer uzer1 = UzerUtil.createEntity(USERNAME, EMAIL, PWD);

        Uzer uzer = uzer1;
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
        Assert.assertNotEquals(activationHash, actualUser.getActivationHash());
        Assert.assertNotEquals(activateDeadline, actualUser.getActivateDeadline());

        ArgumentCaptor<Uzer> argCaptor = ArgumentCaptor.forClass(Uzer.class);

        Mockito.verify(userEmailService).sendForgottenPasswordEmail(argCaptor.capture(), eq(LOCALE));
        Assert.assertEquals(argCaptor.getValue().getId(), actualUser.getId());
    }

    @Test
    @Transactional
    public void testNewPassword() throws Exception {
        final String activationHash = UUID.randomUUID().toString();

        final Uzer uzer1 = UzerUtil.createEntity(USERNAME, EMAIL, PWD);

        Uzer uzer = uzer1;
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
        Assert.assertEquals(newPassword, actualUser.getPsw());
    }

    String getEntityUrl() {
        return ENTITY_URL;
    }
}
