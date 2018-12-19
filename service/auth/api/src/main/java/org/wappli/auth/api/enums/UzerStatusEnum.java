package org.wappli.auth.api.enums;

import lombok.Getter;

public enum UzerStatusEnum {

    INACTIVE(0), ACTIVE(1);

    @Getter
    private int value;

    UzerStatusEnum(int value) {
        this.value = value;
    }

    public static UzerStatusEnum fromValue(int value) {
        for (UzerStatusEnum b : UzerStatusEnum.values()) {
            if (b.getValue() == value) {
                return b;
            }
        }
        return null;
    }
}
