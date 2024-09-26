package com.assessment.sofka.mscoretransaction.services;

import com.assessment.sofka.mscoretransaction.exeption.GenericException;
import com.assessment.sofka.mscoretransaction.messages.request.RetrieveCustomerReqDTO;
import com.assessment.sofka.mscoretransaction.messages.response.CustomerRespDTO;
import com.dependency.mscore.grpc.dto.request.GenericRequestDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;

public interface IServiceCustomer {
    GenericResponseDTO<CustomerRespDTO> retrieveCustomer(GenericRequestDTO<RetrieveCustomerReqDTO> requestDTO) throws Exception;

}
