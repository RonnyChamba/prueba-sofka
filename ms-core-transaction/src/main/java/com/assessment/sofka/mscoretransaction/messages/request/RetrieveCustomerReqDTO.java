package com.assessment.sofka.mscoretransaction.messages.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetrieveCustomerReqDTO implements Serializable {
    private String identification;
}
