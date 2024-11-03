package hr.fer.tel.rassus.server.beans;

import hr.fer.tel.rassus.server.dto.GetSensorData;
import hr.fer.tel.rassus.server.dto.SensorData;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Sensor {
  //  TODO
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private double latitude;
  private double longitude;
  private String ip;
  private int port;

  @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reading> readings;

  public static Sensor fromSensorData(SensorData sensorData) {
    Sensor sensor = new Sensor();
    sensor.setLatitude(sensorData.getLatitude());
    sensor.setLongitude(sensorData.getLongitude());
    sensor.setIp(sensorData.getIp());
    sensor.setPort(sensorData.getPort());
    return sensor;
  }

}
