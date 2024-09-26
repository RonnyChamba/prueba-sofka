package com.assessment.sofka.mscorepersona.communication.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GrpcServerConfiguration {
    private Server server;

    @Value("${grpc.server.port}")
    private int port;

    @Value("${grpc.server.inbound.message.size:5242881}")
    private int maxInboundSize;

    @Autowired
    private PersonConsumer grpcConsumer;

    public static final org.slf4j.Logger LOG = LoggerFactory.getLogger(GrpcServerConfiguration.class);

    public void start() {

        try {
            server = ServerBuilder.forPort(port)
                    .maxInboundMessageSize(maxInboundSize)
                    .addService(grpcConsumer)
                    .build()
                    .start();

            LOG.info("gRPC server started on port: " + port);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {

                LOG.info("Received Shutdown Request");
                stop();
            }));
        } catch (IOException e) {
            LOG.info("Error starting gRPC server: " + e.getMessage());
        }
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() {
        try {
            if (server != null) {
                server.awaitTermination();
            }
        } catch (InterruptedException e) {
            LOG.info("Error stopping gRPC server: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
