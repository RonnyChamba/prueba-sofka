package com.assessment.sofka.mscorepersona.messages.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public abstract class PersonReqDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String fullName;
    private Integer age;
    private String gender;
    private String identification;
    private String address;
    private String phone;

    public PersonReqDTO() {

    }
}