package com.assessment.sofka.mscoretransaction.communication.rest;

import com.assessment.sofka.mscoretransaction.messages.request.AccountReqDTO;
import com.assessment.sofka.mscoretransaction.messages.request.AccountUpdateDTO;
import com.assessment.sofka.mscoretransaction.services.IAccountService;
import com.dependency.mscore.grpc.dto.request.GenericRequestDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public GenericResponseDTO<String> createAccount(
            @RequestBody GenericRequestDTO<AccountReqDTO> request) {
        return accountService.saveAccount(request);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public GenericResponseDTO<String> updateAccount(
            @RequestBody GenericRequestDTO<AccountUpdateDTO> request) {
        return accountService.updateAccount(request);
    }

    @PostMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public GenericResponseDTO<String> deleteAccount(
            @RequestBody GenericRequestDTO<String> request) {
        return accountService.deleteAccount(request);
    }
}
