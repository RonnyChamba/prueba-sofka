package com.assessment.sofka.mscoretransaction.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Data
@Configuration
public class MainConfig {

    @Value("#{${grpc.client-server.map}}")
    private HashMap<String, String> grpcServerMap;
    @Value("${grpc.client.timeout.idle:0}")
    private int grpcIdleTimeout;
    @Value("${grpc.client.timeout.deadline-after:0}")
    private int grpcDeadlineTimeout;
}
