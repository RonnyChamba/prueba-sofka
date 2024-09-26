package com.assessment.sofka.mscoretransaction.messages.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReqDTO {
    private String accountNumber;
    private BigDecimal value;
    private String movementType;
}
