package com.assessment.sofka.mscoretransaction.services.impl;

import com.assessment.sofka.mscoretransaction.entity.Account;
import com.assessment.sofka.mscoretransaction.entity.Transaction;
import com.assessment.sofka.mscoretransaction.exeption.GenericException;
import com.assessment.sofka.mscoretransaction.messages.request.TransactionReqDTO;
import com.assessment.sofka.mscoretransaction.repository.IAccountRepository;
import com.assessment.sofka.mscoretransaction.repository.ITransactionRepository;
import com.assessment.sofka.mscoretransaction.services.ITransactionService;
import com.assessment.sofka.mscoretransaction.util.CommonValidator;
import com.assessment.sofka.mscoretransaction.util.ConstantsTransaction;
import com.dependency.mscore.grpc.dto.request.GenericRequestDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository transactionRepository;

    private final IAccountRepository accountRepository;
    public static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);


    @Override
    @Transactional
    public GenericResponseDTO<String> saveTransaction(GenericRequestDTO<TransactionReqDTO> requestDTO) {
        try {
            LOG.info("Request to save transaction: {}", requestDTO);

            CommonValidator.validateFieldObjectFromTemplate(requestDTO, List.of("payload"));
            CommonValidator.validateFieldObjectFromTemplate(requestDTO.getPayload(),
                    List.of("accountNumber", "value", "movementType"));

            validMovementType(requestDTO.getPayload());

            TransactionReqDTO transactionReqDTO = requestDTO.getPayload();

            Account accountFrom = accountRepository.findByAccountNumberAndStatusRecord(transactionReqDTO.getAccountNumber(), "Active")
                    .orElseThrow(() -> new GenericException("Account not found with account number: " + transactionReqDTO.getAccountNumber() + " with status Active"));

            BigDecimal initialBalance = accountFrom.getBalance();

            switch (transactionReqDTO.getMovementType()) {
                case "DEPOSIT" -> accountFrom.setBalance(accountFrom.getBalance().add(transactionReqDTO.getValue()));
                case "WITHDRAW" -> {

                    if (accountFrom.getBalance().compareTo(transactionReqDTO.getValue()) < 0) {
                        throw new GenericException("Insufficient balance");
                    }
                    accountFrom.setBalance(accountFrom.getBalance().subtract(transactionReqDTO.getValue()));
                }
            }

            Account accountUpdate = accountRepository.save(accountFrom);
            LOG.info("Account updated: {}", accountUpdate.getAccountId());

            Transaction transaction = CommonValidator.objectMapping(transactionReqDTO, Transaction.class);
            transaction.setAccount(accountUpdate);
            transaction.setBalance(initialBalance);
            transaction.setAvailableBalance(accountUpdate.getBalance());
            transaction.setDescription(String.format("%s of %s", transactionReqDTO.getMovementType().toLowerCase(), transaction.getValue()));
            transaction.setStatusRecord("Active");

            Transaction transactionSave = transactionRepository.save(transaction);

            LOG.info("Transaction saved: {}", transactionSave.getTransactionId());

            return GenericResponseDTO.<String>builder()
                    .message("Transaction saved successfully")
                    .status("OK")
                    .code(200)
                    .payload(transactionSave.getTransactionId() + "")
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

    private void validMovementType(TransactionReqDTO transactionReqDTO) {

        if (!ConstantsTransaction.OPERATION_TYPE.containsKey(transactionReqDTO.getMovementType())) {

            String typeOperationString = String.join(", ", ConstantsTransaction.OPERATION_TYPE.keySet());

            throw new GenericException(String.format("Type operation %s not found, valid types are: %s",
                    transactionReqDTO.getMovementType(),
                    typeOperationString));
        }

        if (transactionReqDTO.getValue().compareTo(new BigDecimal("0")) <= 0) {
            throw new GenericException("Value must be greater than 0");
        }


    }
}
