package com.litblc.shiro.enums;

import lombok.Getter;

@Getter
public enum Weekday {
    MON(1),
    TUE(2),
    WED(3),
    THU(4),
    FRI(5),
    SAT(6),
    SUN(0);

    private final int dayValue;
    Weekday(int dayValue) {
        this.dayValue = dayValue;
    }
}

@Getter
enum Gender {
    MALE(1),
    FEMALE(2);

    private final int val;
    Gender(int val) {
        this.val = val;
    }
}
