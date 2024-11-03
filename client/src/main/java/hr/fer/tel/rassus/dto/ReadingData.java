package hr.fer.tel.rassus.dto;


public class ReadingData {
    private double temperature;
    private double pressure;
    private double humidity;
    private Double co;
    private Double no2;
    private Double so2;

    public ReadingData(double temp, double pressure, double humidity, Double co, Double no2, Double so2) {
        this.temperature = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.co = co;
        this.no2 = no2;
        this.so2 = so2;
    }

    public ReadingData(){

    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public Double getCo() {
        return co;
    }

    public Double getNo2() {
        return no2;
    }

    public Double getSo2() {
        return so2;
    }

    @Override
    public String toString() {
        return "ReadingData{" +
                "temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", co=" + co +
                ", no2=" + no2 +
                ", so2=" + so2 +
                '}';
    }
}
