package hr.fer.tel.rassus;

import com.opencsv.exceptions.CsvValidationException;
import hr.fer.tel.rassus.dto.GetSensorData;
import hr.fer.tel.rassus.dto.ReadingData;
import hr.fer.tel.rassus.dto.SensorData;
import hr.fer.tel.rassus.httpClient.RetrofitImpl;
import hr.fer.tel.rassus.util.ParserCsv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Sensor {

    private static final Logger logger = Logger.getLogger(Sensor.class.getName());
    private static final double MIN_LONGITUDE = 15.87;
    private static final double MAX_LONGITUDE = 16.0;
    private static final double MIN_LATITUDE = 45.75;
    private static final double MAX_LATITUDE = 45.85;

    private double latitude;
    private double longitude;
    private int port;
    private final String ip = "127.0.0.1";
    private final RestInterface restInterface;
    private List<ReadingData> readingDataList = new ArrayList<>();
    private long startTime;
    private long sensorId;

    public Sensor(RestInterface restInterface) throws IOException {
        this.restInterface = restInterface;
        initSensor();
    }

    private void initSensor() throws IOException {
        Random random = new Random();
        this.latitude = MIN_LATITUDE + (MAX_LATITUDE - MIN_LATITUDE) * random.nextDouble();
        this.longitude = MIN_LONGITUDE + (MAX_LONGITUDE - MIN_LONGITUDE) * random.nextDouble();
    }

    private void startSensor() throws IOException, InterruptedException {

        this.startTime = System.currentTimeMillis() / 1000;
        RPCServer server = new RPCServer(new SensorProtoRpcService(this));
        server.start();
        port = server.getPort();
        sensorId = restInterface.registerSensor(new SensorData(latitude, longitude, ip, port)); //registracija na poslužitelj
        logger.info("Sensor registered!");
        while (true) {
            ReadingData current_reading = getReadingBasedOnTime();
            //nađi najbližeg
            GetSensorData neighbourSensor = restInterface.getNeighbourSensor(sensorId);
            if (neighbourSensor != null) {
                logger.info("Closest neighbour is sensor with id:" + neighbourSensor.getId());
                try {
                    RPCClient rpcClient = new RPCClient(neighbourSensor.getIp(), neighbourSensor.getPort());
                    Reading reading = rpcClient.requestReading();
                    ReadingData neighbourReading = new ReadingData(reading.getTemperature(), reading.getPressure(), reading.getHumidity(), reading.getCo(), reading.getNo2(), reading.getSo2());
                    rpcClient.stop();
                    ReadingData calibrated_reading = calibrate(current_reading, neighbourReading); //kalibriranje ocitanaja
                    Integer integer = restInterface.registerReading(calibrated_reading, sensorId);
                    logger.info("Saved calibrated reading:" + calibrated_reading);
                    Thread.sleep(5000);
                } catch (Exception e) {
                    logger.info("Failed to connect to neighbour sensor");
                    Integer integer = restInterface.registerReading(current_reading, sensorId);
                    logger.info("Saved reading:" + current_reading);
                    Thread.sleep(5000);
                }
            } else {
                Integer integer = restInterface.registerReading(current_reading, sensorId);
                logger.info("Saved reading:" + current_reading);
                Thread.sleep(5000);
            }
        }

    }

    public static ReadingData calibrate(ReadingData ownReading, ReadingData neighborReading) {
        double temp = calculateAverage(ownReading.getTemperature(), neighborReading.getTemperature());
        double pressure = calculateAverage(ownReading.getPressure(), neighborReading.getPressure());
        double humidity = calculateAverage(ownReading.getHumidity(), neighborReading.getHumidity());

        Double co = calculateAverage(ownReading.getCo(), neighborReading.getCo());
        Double no2 = calculateAverage(ownReading.getNo2(), neighborReading.getNo2());
        Double so2 = calculateAverage(ownReading.getSo2(), neighborReading.getSo2());

        return new ReadingData(temp, pressure, humidity, co, no2, so2);
    }

    private static double calculateAverage(double ownValue, double neighborValue) {
        if (ownValue == 0 && neighborValue == 0) {
            return 0;
        }
        if (ownValue == 0) {
            return neighborValue;
        }
        if (neighborValue == 0) {
            return ownValue;
        }
        return (ownValue + neighborValue) / 2.0;
    }

    private static Double calculateAverage(Double ownValue, Double neighborValue) {
        if (ownValue == null && neighborValue == null) {
            return null;
        }
        if (ownValue == null || ownValue == 0) {
            return neighborValue;
        }
        if (neighborValue == null || neighborValue == 0) {
            return ownValue;
        }
        return (ownValue + neighborValue) / 2.0;
    }

    public ReadingData getReadingBasedOnTime() {
        long brojAktivnihSekundi = (System.currentTimeMillis() / 1000) - startTime;

        int red = (int) ((brojAktivnihSekundi % 100) + 1);

        if (red < readingDataList.size()) {
            return readingDataList.get(red);
        } else {
            System.out.println("Nema očitanja za navedeni indeks.");
            return null;
        }
    }

    private void prepareCSV() {
        ParserCsv parser = new ParserCsv();
        try {
            List<ReadingData> readings = parser.parseCsv("readings.csv");
            readingDataList.addAll(readings);
        } catch (IOException |  CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Sensor sensor = new Sensor(new RetrofitImpl("http://localhost:8090"));
        sensor.prepareCSV();
        sensor.startSensor();
    }




}
