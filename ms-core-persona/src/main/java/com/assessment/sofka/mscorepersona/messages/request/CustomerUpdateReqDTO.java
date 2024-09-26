package com.assessment.sofka.mscorepersona.messages.request;

import com.assessment.sofka.mscorepersona.vo.UpdateCustomerVO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerUpdateReqDTO {
    private String identification;
    private UpdateCustomerVO dataToUpdate;
}
