package hr.fer.tel.rassus;

import hr.fer.tel.rassus.dto.GetSensorData;
import hr.fer.tel.rassus.dto.ReadingData;
import hr.fer.tel.rassus.dto.SensorData;

import java.io.IOException;

public interface RestInterface {

    Integer registerSensor(SensorData sensor) throws IOException;

    GetSensorData getNeighbourSensor(Long sensorId) throws IOException;

    Integer registerReading(ReadingData reading, Long id) throws IOException;

}
