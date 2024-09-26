package com.assessment.sofka.mscorepersona.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UpdateCustomerVO implements Serializable {

    private String fullName;
    private Integer age;
    private String gender;
    private String address;
    private String phone;
    private String password;
    private String status;

}
