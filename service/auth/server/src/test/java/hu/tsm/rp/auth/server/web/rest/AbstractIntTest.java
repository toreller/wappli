package org.wappli.auth.server.web.rest;

import org.wappli.auth.model.domain.DctCountry;
import org.wappli.auth.model.domain.DctSettlement;
import org.wappli.auth.server.repository.DctCountryRepository;
import org.wappli.auth.server.repository.DctSettlementRepository;
import org.wappli.auth.server.repository.UzerRepository;
import org.wappli.auth.server.util.DctCountryUtil;
import org.wappli.auth.server.util.DctSettlementUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractIntTest {

    @Autowired
    protected DctSettlementRepository dctSettlementRepository;

    @Autowired
    protected DctCountryRepository dctCountryRepository;

    @Autowired
    protected UzerRepository uzerRepository;

    protected DctSettlement createSettlement(String name, String zip) {
        return dctSettlementRepository.saveAndFlush(DctSettlementUtil.createEntity(name, zip));
    }

    protected DctCountry createCountry(String name, String code) {
        return dctCountryRepository.saveAndFlush(DctCountryUtil.createEntity(name, code));
    }

    protected DctCountry createDefaultCountry() {
        return createCountry("DEFAULT_COUNTRY", "-");
    }

    protected DctSettlement createDefaultSettlement() {
        return createSettlement("DEFAULT_SETTLEMENT_NAME", "DEFAULT_ZIP");
    }
}
