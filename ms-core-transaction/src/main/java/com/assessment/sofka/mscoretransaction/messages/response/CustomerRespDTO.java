package com.assessment.sofka.mscoretransaction.messages.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRespDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fullName;
    private Integer age;
    private String gender;
    private String identification;
    private String address;
    private String phone;
    private String status;
    private Integer personId;
}
