package com.assessment.sofka.mscoretransaction.messages.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fullNameCustomer;
    private String accountNumber;
    private String type;
    private BigDecimal balance;
    private List<MovementRespDTO> movements = new ArrayList<>();

    @Data
    @Builder
    public static class MovementRespDTO {
        private String movementType;
        private BigDecimal initialBalance;
        private BigDecimal amount;
        private String description;
        private String createAt;
        private BigDecimal availableBalance;
    }
}
