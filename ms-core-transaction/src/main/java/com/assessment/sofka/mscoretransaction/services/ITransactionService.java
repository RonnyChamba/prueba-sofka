package com.assessment.sofka.mscoretransaction.services;

import com.assessment.sofka.mscoretransaction.messages.request.TransactionReqDTO;
import com.dependency.mscore.grpc.dto.request.GenericRequestDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;

public interface ITransactionService {

    GenericResponseDTO<String> saveTransaction(GenericRequestDTO<TransactionReqDTO> request);

}
