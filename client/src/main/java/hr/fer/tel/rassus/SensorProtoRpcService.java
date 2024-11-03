package hr.fer.tel.rassus;

import hr.fer.tel.rassus.dto.ReadingData;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

public class SensorProtoRpcService extends NeighbourReadingGrpc.NeighbourReadingImplBase{
    private static final Logger logger = Logger.getLogger(SensorProtoRpcService.class.getName());

    Sensor sensor;

    public SensorProtoRpcService(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public void requestReading(Message request, StreamObserver<Reading> responseObserver) {
        logger.info("Responding to reading request!");
       ReadingData readingData = sensor.getReadingBasedOnTime();
        Reading.Builder build = Reading.newBuilder();

        build.setTemperature(readingData.getTemperature());
        build.setPressure(readingData.getPressure());
        build.setHumidity(readingData.getHumidity());

        if (readingData.getCo() != null) {
            build.setCo(readingData.getCo());
        }
        if (readingData.getNo2() != null) {
            build.setNo2(readingData.getNo2());
        }
        if (readingData.getSo2() != null) {
            build.setSo2(readingData.getSo2());
        }

        Reading response = build.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
