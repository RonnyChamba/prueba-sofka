package com.assessment.sofka.mscorepersona.service;

import com.assessment.sofka.mscorepersona.messages.request.CustomerReqDTO;
import com.assessment.sofka.mscorepersona.messages.request.CustomerUpdateReqDTO;
import com.assessment.sofka.mscorepersona.messages.response.CustomerRespDTO;
import com.dependency.mscore.grpc.dto.request.GenericRequestDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;

import java.util.List;

public interface ICustomerService {

    GenericResponseDTO<String> saveCustomer(GenericRequestDTO<CustomerReqDTO> requestDTO);

    GenericResponseDTO<String> deleteCustomer(GenericRequestDTO<String> requestDTO);

    GenericResponseDTO<String> updateCustomer(GenericRequestDTO<CustomerUpdateReqDTO> requestDTO);

    GenericResponseDTO<List<CustomerRespDTO>> findAllCustomer();

    CustomerRespDTO findByAIdentification(GenericRequestDTO<String> requestDTO);

}
