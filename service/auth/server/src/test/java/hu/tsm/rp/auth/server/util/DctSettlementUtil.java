package org.wappli.auth.server.util;

import org.wappli.auth.model.domain.DctSettlement;

public final class DctSettlementUtil {

    private DctSettlementUtil() {
    }

    public static DctSettlement createEntity(String name, String zip) {
        final DctSettlement entity = new DctSettlement();
        entity.setSettlementName(name);
        entity.setZip(zip);
        return entity;
    }
}
