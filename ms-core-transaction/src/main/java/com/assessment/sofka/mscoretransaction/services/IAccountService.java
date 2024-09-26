package com.assessment.sofka.mscoretransaction.services;

import com.assessment.sofka.mscoretransaction.messages.request.AccountReqDTO;
import com.assessment.sofka.mscoretransaction.messages.request.AccountUpdateDTO;
import com.dependency.mscore.grpc.dto.request.GenericRequestDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;

public interface IAccountService {

    GenericResponseDTO<String> saveAccount(GenericRequestDTO<AccountReqDTO> requestDTO);
    GenericResponseDTO<String> updateAccount(GenericRequestDTO<AccountUpdateDTO> requestDTO);
    GenericResponseDTO<String> deleteAccount(GenericRequestDTO<String> requestDTO);
}
