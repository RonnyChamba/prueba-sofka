package com.assessment.sofka.mscoretransaction.communication.rest;

import com.assessment.sofka.mscoretransaction.messages.request.TransactionReqDTO;
import com.assessment.sofka.mscoretransaction.services.ITransactionService;
import com.dependency.mscore.grpc.dto.request.GenericRequestDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public GenericResponseDTO<String> createTransaction(
            @RequestBody GenericRequestDTO<TransactionReqDTO> request) {
        return transactionService.saveTransaction(request);
    }
}
