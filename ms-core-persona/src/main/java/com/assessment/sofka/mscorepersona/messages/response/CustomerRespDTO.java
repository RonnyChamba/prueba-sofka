package com.assessment.sofka.mscorepersona.messages.response;

import com.assessment.sofka.mscorepersona.messages.request.PersonReqDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRespDTO extends PersonReqDTO {

    private String status;
    private Integer personId;
}
