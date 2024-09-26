package com.assessment.sofka.mscorepersona.messages.request;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
public class CustomerReqDTO extends PersonReqDTO {

    public CustomerReqDTO(String fullName, Integer age, String gender, String identification, String address, String phone, String password, String status) {
        super(fullName, age, gender, identification, address, phone);
        this.password = password;
        this.status = status;

    }
    private final String password;
    private final String status;
}
