package com.assessment.sofka.mscoretransaction.services.impl;

import com.assessment.sofka.mscoretransaction.communication.grpc.TransactionProducer;
import com.assessment.sofka.mscoretransaction.enums.GprcServicesEnum;
import com.assessment.sofka.mscoretransaction.exeption.GenericException;
import com.assessment.sofka.mscoretransaction.messages.request.RetrieveCustomerReqDTO;
import com.assessment.sofka.mscoretransaction.messages.response.CustomerRespDTO;
import com.assessment.sofka.mscoretransaction.services.IServiceCustomer;
import com.dependency.mscore.grpc.dto.model.GenericProducerModel;
import com.dependency.mscore.grpc.dto.request.GenericRequestDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;
import com.dependency.mscore.grpc.util.UtilFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceCustomerImpl implements IServiceCustomer {

    private final TransactionProducer transactionProducer;

    @Override
    public GenericResponseDTO<CustomerRespDTO> retrieveCustomer(GenericRequestDTO<RetrieveCustomerReqDTO> requestDTO) throws Exception {

        GenericProducerModel<String> requestGrpcDTO = GenericProducerModel.<String>builder()
                .service(GprcServicesEnum.SERVICE_PERSONA.getValue())
                .option("retrievePersonById")
                .payload(requestDTO
                        .getPayload().getIdentification())
                .origin("origin")
                .userRequest("userRequest")
                .requestTimeoutSeconds(10)
                .build();

        GenericResponseDTO<Object> grpcResponse = transactionProducer.sendMessage(requestGrpcDTO);
        if (grpcResponse == null || grpcResponse.getStatus().equals("ERROR")) {

            String message = grpcResponse != null ? grpcResponse.getMessage() : "Error in grpc response";
            throw new GenericException(message);
        }

        CustomerRespDTO respDTO = UtilFormat.objectMapping(grpcResponse.getPayload(), CustomerRespDTO.class);

        return GenericResponseDTO.<CustomerRespDTO>builder()
                .message("Customer retrieved successfully")
                .status("OK")
                .code(200)
                .payload(respDTO)
                .build();

    }
}
