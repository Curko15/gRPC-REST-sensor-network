package hr.fer.tel.rassus.server.beans;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Reading {
  //  TODO
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
    private double temperature;
    private double pressure;
    private double humidity;
    private Double co;
    private Double no2;
    private Double so2;

  @ManyToOne
  @JoinColumn(name = "sensor_id", nullable = false)
  private Sensor sensor;
}

