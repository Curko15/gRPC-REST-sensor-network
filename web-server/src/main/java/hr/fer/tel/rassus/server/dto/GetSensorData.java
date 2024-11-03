package hr.fer.tel.rassus.server.dto;

import hr.fer.tel.rassus.server.beans.Sensor;

import java.util.ArrayList;
import java.util.List;

public class GetSensorData {
    private Long id;
    private double latitude;
    private double longitude;
    private String ip;
    private int port;

    public GetSensorData(Long id, double latitude, double longitude, String ip, int port) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ip = ip;
        this.port = port;
    }

    public GetSensorData() {}

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static List<GetSensorData> convertSensorList(List<Sensor> sensors) {
        List<GetSensorData> sensorDataList = new ArrayList<>();

        for (Sensor sensor : sensors) {
            GetSensorData sensorData = new GetSensorData();
            sensorData.setId(sensor.getId());
            sensorData.setLatitude(sensor.getLatitude());
            sensorData.setLongitude(sensor.getLongitude());
            sensorData.setIp(sensor.getIp());
            sensorData.setPort(sensor.getPort());

            sensorDataList.add(sensorData);
        }
        return sensorDataList;
    }
}
