package com.assessment.sofka.mscoretransaction.services.impl;

import com.assessment.sofka.mscoretransaction.entity.Account;
import com.assessment.sofka.mscoretransaction.entity.Transaction;
import com.assessment.sofka.mscoretransaction.enums.DateFormatEnum;
import com.assessment.sofka.mscoretransaction.messages.request.RetrieveCustomerReqDTO;
import com.assessment.sofka.mscoretransaction.messages.response.CustomerRespDTO;
import com.assessment.sofka.mscoretransaction.messages.response.ReportRespDTO;
import com.assessment.sofka.mscoretransaction.repository.IAccountRepository;
import com.assessment.sofka.mscoretransaction.repository.ITransactionRepository;
import com.assessment.sofka.mscoretransaction.services.IReportService;
import com.assessment.sofka.mscoretransaction.services.IServiceCustomer;
import com.assessment.sofka.mscoretransaction.util.DateUtil;
import com.dependency.mscore.grpc.dto.request.GenericRequestDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements IReportService {

    private final IAccountRepository accountRepository;

    private final ITransactionRepository transactionRepository;

    private final IServiceCustomer serviceCustomer;

    public static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Override
    public GenericResponseDTO<List<ReportRespDTO>> generateReport(String identificationCustomer, String dateStart, String dateEnd) {

        try {

            GenericResponseDTO<CustomerRespDTO> grpcResponse = serviceCustomer.retrieveCustomer(
                    GenericRequestDTO.<RetrieveCustomerReqDTO>builder()
                            .payload(RetrieveCustomerReqDTO
                                    .builder()
                                    .identification(identificationCustomer)
                                    .build())
                            .build()
            );
            LOG.info("Response from grpc: {}", grpcResponse);

            List<Account> accounts = accountRepository.findByCustomerIdAndStatusRecord(grpcResponse.getPayload().getPersonId(), "Active");

            List<ReportRespDTO> reportRespDTOS = new ArrayList<>();

            for (Account account : accounts) {

                ReportRespDTO reportRespDTO = new ReportRespDTO();
                reportRespDTO.setAccountNumber(account.getAccountNumber());
                reportRespDTO.setFullNameCustomer(grpcResponse.getPayload().getFullName());
                reportRespDTO.setType(account.getAccountType());
                reportRespDTO.setBalance(account.getBalance());

                List<Transaction> transactions = transactionRepository.findByCreatedAtBetween(
                        DateUtil.convertToDate(dateStart, DateFormatEnum.DATE_FORMAT),
                        DateUtil.convertToDate(dateEnd, DateFormatEnum.DATE_FORMAT),
                        account.getAccountId());

                for (Transaction transaction : transactions) {
                    ReportRespDTO.MovementRespDTO movementRespDTO = ReportRespDTO.MovementRespDTO.builder().build();
                    movementRespDTO.setAmount(transaction.getValue());
                    movementRespDTO.setMovementType(transaction.getMovementType());
                    movementRespDTO.setDescription(transaction.getDescription());
                    movementRespDTO.setInitialBalance(transaction.getBalance());
                    movementRespDTO.setCreateAt(DateUtil.convertDateToString(transaction.getCreatedAt(), DateFormatEnum.DATE_TIME_FORMAT));
                    movementRespDTO.setAvailableBalance(transaction.getAvailableBalance());

                    reportRespDTO.getMovements().add(movementRespDTO);
                }

                reportRespDTOS.add(reportRespDTO);
            }

            return GenericResponseDTO.<List<ReportRespDTO>>builder()
                    .message("Report generated successfully")
                    .status("OK")
                    .code(200)
                    .payload(reportRespDTOS)
                    .build();
        } catch (Exception e) {
            LOG.error("Error generating report: {}", e.getMessage());
            return GenericResponseDTO.<List<ReportRespDTO>>builder()
                    .message(e.getMessage())
                    .status("ERROR")
                    .code(500)
                    .build();
        }


    }
}
