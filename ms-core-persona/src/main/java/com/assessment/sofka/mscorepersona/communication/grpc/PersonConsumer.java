package com.assessment.sofka.mscorepersona.communication.grpc;

import com.dependency.mscore.dispacher.interfaces.ICompleteDispatcher;
import com.dependency.mscore.grpc.communication.MessageGrpcConsumer;
import com.dependency.mscore.grpc.config.GenericRequest;
import com.dependency.mscore.grpc.config.GenericResponse;
import com.dependency.mscore.grpc.config.GenericServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class PersonConsumer extends GenericServiceGrpc.GenericServiceImplBase {

    @Autowired
    private MessageGrpcConsumer messageGrpcConsumer;

    private ICompleteDispatcher dispatcher;

    @Autowired
    private BeanFactory beanFactory;

    @Override
    public void call(GenericRequest request, StreamObserver<GenericResponse> responseObserver) {

        dispatcher = (ICompleteDispatcher) beanFactory.getBean("DispatcherComponent", ICompleteDispatcher.class);
        messageGrpcConsumer.consumeMessage(request, responseObserver, dispatcher);
    }
}
