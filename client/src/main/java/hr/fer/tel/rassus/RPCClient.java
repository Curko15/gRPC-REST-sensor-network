package hr.fer.tel.rassus;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RPCClient {
    private static final Logger logger = Logger.getLogger(RPCClient.class.getName());



    private final ManagedChannel channel;
    private final NeighbourReadingGrpc.NeighbourReadingBlockingStub neighbourReadingBlockingStub;

    public RPCClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        neighbourReadingBlockingStub = NeighbourReadingGrpc.newBlockingStub(channel);
    }

    public void stop() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public Reading requestReading() {
        logger.info("Requesting reading with grpc channel...");
        return neighbourReadingBlockingStub.requestReading(Message.newBuilder().build());
    }

}
