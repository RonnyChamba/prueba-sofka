package com.assessment.sofka.mscoretransaction.services.impl;

import com.assessment.sofka.mscoretransaction.communication.grpc.TransactionProducer;
import com.assessment.sofka.mscoretransaction.entity.Account;
import com.assessment.sofka.mscoretransaction.exeption.GenericException;
import com.assessment.sofka.mscoretransaction.messages.request.AccountReqDTO;
import com.assessment.sofka.mscoretransaction.messages.request.AccountUpdateDTO;
import com.assessment.sofka.mscoretransaction.messages.request.RetrieveCustomerReqDTO;
import com.assessment.sofka.mscoretransaction.messages.response.CustomerRespDTO;
import com.assessment.sofka.mscoretransaction.repository.IAccountRepository;
import com.assessment.sofka.mscoretransaction.services.IAccountService;
import com.assessment.sofka.mscoretransaction.services.IServiceCustomer;
import com.assessment.sofka.mscoretransaction.util.CommonValidator;
import com.dependency.mscore.grpc.dto.model.GenericProducerModel;
import com.dependency.mscore.grpc.dto.request.GenericRequestDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;
import com.dependency.mscore.grpc.util.UtilFormat;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final IAccountRepository accountRepository;

    private final IServiceCustomer serviceCustomer;
    public static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);


    @Override
    @Transactional
    public GenericResponseDTO<String> saveAccount(GenericRequestDTO<AccountReqDTO> requestDTO) {


        try {
            LOG.info("Request to save account: {}", requestDTO);
            CommonValidator.validateFieldObjectFromTemplate(requestDTO, List.of("payload"));
            CommonValidator.validateFieldObjectFromTemplate(requestDTO.getPayload(),
                    List.of("accountNumber", "customerId", "balance", "accountType"));

            if (accountRepository.existsByAccountNumberAndStatusRecord(requestDTO.getPayload().getAccountNumber(), "Active")) {
                throw new GenericException("Account already exists with the account number: " + requestDTO.getPayload().getAccountNumber());
            }

            Account account = CommonValidator.objectMapping(requestDTO.getPayload(), Account.class);
            account.setStatusRecord("Active");


            GenericResponseDTO<CustomerRespDTO> grpcResponse = serviceCustomer.retrieveCustomer(
                    GenericRequestDTO.<RetrieveCustomerReqDTO>builder()
                            .payload(RetrieveCustomerReqDTO
                                    .builder()
                                    .identification(requestDTO.getPayload().getIdentificationCustomer())
                                    .build())
                            .build()
            );
            LOG.info("Response from grpc: {}", grpcResponse);
            account.setCustomerId(grpcResponse.getPayload().getPersonId());


            account = accountRepository.save(account);

            LOG.info("Account saved: {}", account.getAccountId());

            return GenericResponseDTO.<String>builder()
                    .message("Account saved successfully")
                    .status("OK")
                    .code(200)
                    .payload(account.getAccountId() + "")
                    .build();

        } catch (Exception e) {

            String message = e.getMessage();

            if (e instanceof GenericException genericException) {
                message = genericException.getUserMessage();
            }
            return GenericResponseDTO.<String>builder()
                    .message(message)
                    .status("ERROR")
                    .code(500)
                    .build();
        }
    }

    @Override
    @Transactional
    public GenericResponseDTO<String> updateAccount(GenericRequestDTO<AccountUpdateDTO> requestDTO) {
        try {
            LOG.info("Request to update account: {}", requestDTO);

            CommonValidator.validateFieldObjectFromTemplate(requestDTO, List.of("payload"));

            CommonValidator.validateFieldObjectFromTemplate(requestDTO.getPayload(),
                    List.of("accountNumber", "dataToUpdate"));

            CommonValidator.validateFieldObjectFromTemplate(requestDTO.getPayload().getDataToUpdate(),
                    List.of("accountType"));


            Account account = accountRepository.findByAccountNumberAndStatusRecord(requestDTO.getPayload().getAccountNumber(), "Active")
                    .orElseThrow(() -> new GenericException("Account not found with account number: " + requestDTO.getPayload().getAccountNumber() + " with status Active"));


            account.setAccountType(requestDTO.getPayload().getDataToUpdate().getAccountType());

            account = accountRepository.save(account);

            LOG.info("Account updated: {}", account.getAccountId());

            return GenericResponseDTO.<String>builder()
                    .message("Account updated successfully")
                    .status("OK")
                    .code(200)
                    .payload(account.getAccountId() + "")
                    .build();

        } catch (Exception e) {

            String message = e.getMessage();

            if (e instanceof GenericException genericException) {
                message = genericException.getUserMessage();
            }
            return GenericResponseDTO.<String>builder()
                    .message(message)
                    .status("ERROR")
                    .code(500)
                    .build();
        }
    }

    @Override
    @Transactional
    public GenericResponseDTO<String> deleteAccount(GenericRequestDTO<String> requestDTO) {
        try {
            LOG.info("Request to delete account: {}", requestDTO);

            CommonValidator.validateFieldObjectFromTemplate(requestDTO, List.of("payload"));

            Account account = accountRepository.findByAccountNumberAndStatusRecord(requestDTO.getPayload(), "Active")
                    .orElseThrow(() -> new GenericException("Account not found with account number: " + requestDTO.getPayload() + " with status Active"));
            account.setStatusRecord("Deleted");
            account.setRemovedAt(new Date());

            account = accountRepository.save(account);

            LOG.info("Account deleted: {}", account.getAccountId());

            return GenericResponseDTO.<String>builder()
                    .message("Account deleted successfully")
                    .status("OK")
                    .code(200)
                    .payload(account.getAccountId() + "")
                    .build();

        } catch (Exception e) {

            String message = e.getMessage();

            if (e instanceof GenericException genericException) {
                message = genericException.getUserMessage();
            }
            return GenericResponseDTO.<String>builder()
                    .message(message)
                    .status("ERROR")
                    .code(500)
                    .build();
        }
    }
}
