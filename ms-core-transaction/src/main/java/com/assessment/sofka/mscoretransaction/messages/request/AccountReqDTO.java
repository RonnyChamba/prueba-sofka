package com.assessment.sofka.mscoretransaction.messages.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountReqDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String identificationCustomer;
}
