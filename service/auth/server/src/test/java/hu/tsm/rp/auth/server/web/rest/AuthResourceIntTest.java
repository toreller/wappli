package org.wappli.auth.server.web.rest;

import org.wappli.auth.api.enums.UzerStatusEnum;
import org.wappli.auth.api.interfaces.AuthInterface;
import org.wappli.auth.model.domain.DctCountry;
import org.wappli.auth.model.domain.DctSettlement;
import org.wappli.auth.model.domain.Uzer;
import org.wappli.auth.server.AuthServerApp;
import org.wappli.auth.server.repository.UzerRepository;
import org.wappli.auth.server.util.UzerDataUtil;
import org.wappli.auth.server.util.UzerGdprUtil;
import org.wappli.auth.server.util.UzerUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.wappli.auth.api.constant.Constants.API_BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServerApp.class)
@AutoConfigureMockMvc
public class AuthResourceIntTest extends AbstractIntTest {

    private static final String EMAIL_OK = "rptest.ok@t-systems.hu";
    private static final String EMAIL_NOT_ACTIVATED = "rptest.notactivated@t-systems.hu";
    private static final String EMAIL_INACTIVATED = "rptest.inactivated@t-systems.hu";
    public static final String PASSWORD = "asd123";

    @Autowired
    private MockMvc restMockMvc;

    @Autowired
    private UzerRepository uzerRepository;

    private Uzer userOk;

    private Uzer userNotActivated;

    private Uzer userInactivated;

    @Before
    public void setup() {
        DctSettlement birthPlace = createSettlement("Budapest", "1000");
        DctCountry citizenShip = createCountry("Hungary", "hu");
        userOk = createUzer(EMAIL_OK, true, UzerStatusEnum.ACTIVE, birthPlace, citizenShip);
        userNotActivated = createUzer(EMAIL_NOT_ACTIVATED, false, UzerStatusEnum.ACTIVE, birthPlace, citizenShip);
        userInactivated = createUzer(EMAIL_INACTIVATED, true, UzerStatusEnum.INACTIVE, birthPlace, citizenShip);
    }

    @Test
    @Transactional
    public void testGetAuth() throws Exception {
        restMockMvc.perform(
                get(API_BASE_URL + AuthInterface.AUTH_URL + "/{email}", EMAIL_OK)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(userOk.getId()))
                .andExpect(jsonPath("$.username").value(EMAIL_OK))
                .andExpect(jsonPath("$.password").value(PASSWORD))
                .andExpect(jsonPath("$.enabled").value(true));
    }

    @Test
    @Transactional
    public void testGetAuthNotActivated() throws Exception {
        restMockMvc.perform(
                get(API_BASE_URL + AuthInterface.AUTH_URL + "/{email}", EMAIL_NOT_ACTIVATED)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(userNotActivated.getId()))
                .andExpect(jsonPath("$.username").value(EMAIL_NOT_ACTIVATED))
                .andExpect(jsonPath("$.password").value(PASSWORD))
                .andExpect(jsonPath("$.enabled").value(false));
    }

    @Test
    @Transactional
    public void testGetAuthInactivated() throws Exception {
        restMockMvc.perform(
                get(API_BASE_URL + AuthInterface.AUTH_URL + "/{email}", EMAIL_INACTIVATED)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(userInactivated.getId()))
                .andExpect(jsonPath("$.username").value(EMAIL_INACTIVATED))
                .andExpect(jsonPath("$.password").value(PASSWORD))
                .andExpect(jsonPath("$.enabled").value(false));
    }

    @Test
    @Transactional
    public void testGetAuthNonExisting() throws Exception {
        restMockMvc.perform(
                get(API_BASE_URL + AuthInterface.AUTH_URL + "/{email}", "email.nonexisting@t-systems.hu")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    private Uzer createUzer(String email, boolean activated, UzerStatusEnum status, DctSettlement birthPlace,
                                DctCountry citizenShip) {

        final Uzer uzer = UzerUtil.createEntity(
                email,
                PASSWORD,
                activated,
                Instant.now().plus(1, ChronoUnit.DAYS),
                status,
                UzerDataUtil.createDefaultEntity(birthPlace, citizenShip),
                UzerGdprUtil.createDefaultEntity());
        return uzerRepository.saveAndFlush(uzer);
    }

}
