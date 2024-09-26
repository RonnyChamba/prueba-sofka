package com.assessment.sofka.mscorepersona;

import com.assessment.sofka.mscorepersona.communication.grpc.GrpcServerConfiguration;
import com.assessment.sofka.mscorepersona.service.ICustomerService;
import com.dependency.mscore.dispacher.interfaces.ICompleteDispatcher;
import com.dependency.mscore.grpc.dto.request.GenericRequestDTO;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dependency.mscore", "com.assessment.sofka"})
public class MsCorePersonaApplication implements ApplicationRunner {


    @Autowired
    private GrpcServerConfiguration grpcServerConfiguration;
    private ICompleteDispatcher dispatcher;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private BeanFactory beanFactory;

    public static void main(String[] args) {

        SpringApplication.run(MsCorePersonaApplication.class, args)
                .getBean(MsCorePersonaApplication.class)
                .runGrpcServer();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        registerHandlers();
    }

    private void registerHandlers() {

        dispatcher = (ICompleteDispatcher) beanFactory.getBean("DispatcherComponent");
        dispatcher.registerSimpleResultHandler(GenericRequestDTO.class, customerService::findByAIdentification, "retrievePersonById");

    }

    public void runGrpcServer() {
        grpcServerConfiguration.start();
        grpcServerConfiguration.blockUntilShutdown();
    }

}
