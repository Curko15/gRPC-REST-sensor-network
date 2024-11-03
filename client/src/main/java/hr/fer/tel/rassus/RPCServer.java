package hr.fer.tel.rassus;

import io.grpc.Server;
import io.grpc.ServerBuilder;


import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class RPCServer {
    private static final Logger logger = Logger.getLogger(RPCServer.class.getName());

    private Server server;
    private final SensorProtoRpcService service;
    private int port;


    public RPCServer(SensorProtoRpcService service) {
        this.service = service;
    }

    public void start() throws IOException {
        server = ServerBuilder.forPort(0)
                .addService(this.service)
                .build()
                .start();
        port = server.getPort();
        logger.info("Server started on " + port);


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server since JVM is shutting down");
            try {
                RPCServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("Server shut down");
        }));
    }

    public int getPort() {
        return port;
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }


}
