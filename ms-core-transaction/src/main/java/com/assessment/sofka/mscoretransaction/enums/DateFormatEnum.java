package com.assessment.sofka.mscoretransaction.enums;

import lombok.Getter;

@Getter
public enum DateFormatEnum {
    DATE_TIME_FORMAT("yyyy-MM-dd hh:mm:ss"),
    DATE_FORMAT("yyyy-MM-dd"),
    TIME_FORMAT("hh:mm:ss");

    private final String format;

    private DateFormatEnum(String format) {
        this.format = format;
    }
}
