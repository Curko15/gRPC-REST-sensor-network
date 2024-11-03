package hr.fer.tel.rassus.dto;



public class SensorData {
    private double latitude;
    private double longitude;
    private String ip;
    private int port;

    public SensorData(double latitude, double longitude, String ip, int port) {
        this.latitude = latitude;
        this.port = port;
        this.ip = ip;
        this.longitude = longitude;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
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
}
