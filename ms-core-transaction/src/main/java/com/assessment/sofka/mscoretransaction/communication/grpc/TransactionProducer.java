package com.assessment.sofka.mscoretransaction.communication.grpc;


import com.assessment.sofka.mscoretransaction.config.MainConfig;
import com.dependency.mscore.grpc.communication.MessageGrpcProducer;
import com.dependency.mscore.grpc.config.GenericServiceGrpc;
import com.dependency.mscore.grpc.dto.model.GenericProducerModel;
import com.dependency.mscore.grpc.dto.request.InternalRequestDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;
import com.dependency.mscore.grpc.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TransactionProducer {

    private final MainConfig mainConfig;

    private final MessageGrpcProducer messageGrpcProducer;

    public <P, R> R sendMessage(GenericProducerModel<P> requestDTO) {

        GenericServiceGrpc.GenericServiceBlockingStub stub =
                CommonUtil.getBlockingStub(mainConfig.getGrpcServerMap().get(requestDTO.getService()),
                        mainConfig.getGrpcIdleTimeout(),
                        mainConfig.getGrpcDeadlineTimeout());
        GenericResponseDTO response;

        response = messageGrpcProducer.sendMessage(InternalRequestDTO.<P>builder()
                .transactionId(requestDTO.getTransactionId())
                .option(requestDTO.getOption())
                .origin(requestDTO.getOrigin())
                .payload(requestDTO.getPayload())
                .blockingStub(stub)
                .userRequest(requestDTO.getUserRequest())
                .build());
        return (R) response;
    }
}
