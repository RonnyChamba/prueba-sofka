package com.assessment.sofka.mscoretransaction.enums;

import lombok.Getter;

@Getter
public enum GprcServicesEnum {


    SERVICE_PERSONA("servicePerson");
    private final String value;

    GprcServicesEnum(String value) {
        this.value = value;
    }


}
