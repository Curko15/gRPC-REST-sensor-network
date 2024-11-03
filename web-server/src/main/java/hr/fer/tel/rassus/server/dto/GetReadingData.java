package hr.fer.tel.rassus.server.dto;

import hr.fer.tel.rassus.server.beans.Reading;

import java.util.ArrayList;
import java.util.List;

public class GetReadingData {
    private Long id;
    private double temperature;
    private double pressure;
    private double humidity;
    private Double co;
    private Double no2;
    private Double so2;

    public GetReadingData(Long id, double temperature, double pressure, double humidity, Double co, Double no2, Double so2) {
        this.id = id;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.co = co;
        this.no2 = no2;
        this.so2 = so2;
    }

    public GetReadingData() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public Double getCo() {
        return co;
    }

    public void setCo(Double co) {
        this.co = co;
    }

    public Double getNo2() {
        return no2;
    }

    public void setNo2(Double no2) {
        this.no2 = no2;
    }

    public Double getSo2() {
        return so2;
    }

    public void setSo2(Double so2) {
        this.so2 = so2;
    }

    public static  List<GetReadingData> convertReadingList(List<Reading> readings) {
        List<GetReadingData> readingDataList = new ArrayList<>();

        for (Reading reading : readings) {
            GetReadingData readingData = new GetReadingData();
            readingData.setId(reading.getId());
            readingData.setTemperature(reading.getTemperature());
            readingData.setPressure(reading.getPressure());
            readingData.setHumidity(reading.getHumidity());
            readingData.setCo(reading.getCo());
            readingData.setNo2(reading.getNo2());
            readingData.setSo2(reading.getSo2());

            readingDataList.add(readingData);
        }

        return readingDataList;
    }
}
