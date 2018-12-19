package org.wappli.auth.server.util;

import org.wappli.auth.api.enums.UzerStatusEnum;
import org.wappli.auth.model.domain.Uzer;
import org.wappli.auth.model.domain.UzerData;
import org.wappli.auth.model.domain.UzerGdpr;
import org.assertj.core.util.Lists;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public final class UzerUtil {

    public static final String COMPETENCE_JAVA = "java";
    public static final String REFERENCE_TEXT = "This is a reference.";
    public static final String BIRTHPLACE_NAME = "Budapest";
    public static final String ZIP = "1200";
    public static final String TITLE = "Mr";
    public static final String FIRSTNAME = "John";
    public static final String SURNAME = "Smith";
    public static final String MOTHER_NAME = "Mother";
    public static final String TEL_NUMBER = "+361123456789";
    public static final LocalDate BIRTH_DATE = LocalDate.of(1970, 1, 1);

    private UzerUtil() {
    }

    public static Uzer createEntity(String email, String psw) {
        final Uzer uzer = new Uzer();
        uzer.setEmail(email);
        uzer.setPsw(psw);
        uzer.setActivated(true);
        uzer.setActivateDeadline(Instant.now().plus(1, ChronoUnit.DAYS));
        uzer.setUserStatus(UzerStatusEnum.ACTIVE);
        return uzer;
    }

    public static Uzer createEntity(String email, String psw, boolean activated, Instant activateDeadline,
                                      UzerStatusEnum status) {
        final Uzer uzer = new Uzer();
        uzer.setEmail(email);
        uzer.setPsw(psw);
        uzer.setActivated(activated);
        uzer.setActivateDeadline(activateDeadline);
        uzer.setUserStatus(status);
        return uzer;
    }

    public static Uzer createEntity(String email, String psw, boolean activated, Instant activateDeadline,
                                      UzerStatusEnum status, UzerData uzerData, UzerGdpr uzerGdpr) {
        final Uzer uzer = createEntity(email, psw, activated, activateDeadline, status);
        uzer.setUserData(uzerData);
        uzer.setUzerGdpr(uzerGdpr);
        return uzer;
    }

    public static UzerInputDTO setInputData(UzerInputDTO uzerInputDTO, Long citizenshipId, String tsmUnivazCode) {
        //Competence mapping
        final ArrayList<CompetenceInputDTO> competenceList = Lists.newArrayList();
        final CompetenceInputDTO competenceInputDTO = new CompetenceInputDTO();
        competenceInputDTO.setName(COMPETENCE_JAVA);
        competenceList.add(competenceInputDTO);
        uzerInputDTO.setCompetence(competenceList);

        //GDPR mapping
        final UzerGdprInputDTO gdprInputDTO = new UzerGdprInputDTO();
        gdprInputDTO.setDataProtectionManagement(true);
        gdprInputDTO.setLegalStatement(true);
        gdprInputDTO.setPrivacyStatement(true);
        gdprInputDTO.setTac(true);
        uzerInputDTO.setGdpr(gdprInputDTO);

        //Reference mapping
        final ArrayList<ReferenceInputDTO> references = Lists.newArrayList();
        final ReferenceInputDTO referenceInputDTO = new ReferenceInputDTO();
        referenceInputDTO.setText(REFERENCE_TEXT);
        references.add(referenceInputDTO);
        uzerInputDTO.setReferences(references);

        //UserData mapping
        final UzerDataInputDTO userData = new UzerDataInputDTO();
        userData.setBirthDate(BIRTH_DATE);
        userData.setTitle(TITLE);
        userData.setFirstname(FIRSTNAME);
        userData.setSurname(SURNAME);
        userData.setMotherName(MOTHER_NAME);
        userData.setTsmUnivazCode(tsmUnivazCode);
        userData.setTelNumber(TEL_NUMBER);

        //BirthPlace mapping
        final DctSettlementInputDTO birthPlace = new DctSettlementInputDTO();
        birthPlace.setName(BIRTHPLACE_NAME);
        birthPlace.setZip(ZIP);
        userData.setBirthPlace(birthPlace);

        //Citizenship
        userData.setCitizenshipId(citizenshipId);

        uzerInputDTO.setUserData(userData);

        return uzerInputDTO;
    }
}
