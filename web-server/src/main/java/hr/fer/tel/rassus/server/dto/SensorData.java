package hr.fer.tel.rassus.server.dto;

import lombok.Data;

@Data
public class SensorData {
    private double latitude;
    private double longitude;
    private String ip;
    private int port;
}
