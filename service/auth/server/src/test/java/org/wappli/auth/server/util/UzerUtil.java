package org.wappli.auth.server.util;

import org.wappli.auth.api.enums.UzerStatusEnum;
import org.wappli.auth.server.domain.Uzer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class UzerUtil {

    private UzerUtil() {
    }

    public static Uzer createEntity(String userName, String email, String psw) {
        final Uzer uzer = new Uzer();
        uzer.setUsername(userName);
        uzer.setEmail(email);
        uzer.setPsw(psw);
        uzer.setActivated(true);
        uzer.setActivateDeadline(Instant.now().plus(1, ChronoUnit.DAYS));
        uzer.setUserStatus(UzerStatusEnum.ACTIVE);
        return uzer;
    }

    public static Uzer createEntity(String userName, String email, String psw, boolean activated, Instant activateDeadline,
                                    UzerStatusEnum status) {
        final Uzer uzer = new Uzer();
        uzer.setUsername(userName);
        uzer.setEmail(email);
        uzer.setPsw(psw);
        uzer.setActivated(activated);
        uzer.setActivateDeadline(activateDeadline);
        uzer.setUserStatus(status);
        return uzer;
    }
}
