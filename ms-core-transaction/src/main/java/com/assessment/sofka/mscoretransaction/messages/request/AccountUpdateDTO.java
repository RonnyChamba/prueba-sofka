package com.assessment.sofka.mscoretransaction.messages.request;

import com.assessment.sofka.mscoretransaction.vo.AccountUpdateVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateDTO {

    private String accountNumber;
    private AccountUpdateVO dataToUpdate;
}
