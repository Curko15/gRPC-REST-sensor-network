package hr.fer.tel.rassus.httpClient;

import hr.fer.tel.rassus.RestInterface;
import hr.fer.tel.rassus.dto.GetSensorData;
import hr.fer.tel.rassus.dto.ReadingData;
import hr.fer.tel.rassus.dto.SensorData;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class RetrofitImpl implements RestInterface {

    private String url;
    private SensorApi sensorApi;
    private ReadingApi readingApi;

    public RetrofitImpl(String url) {
        this.url = url;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(JacksonConverterFactory.create()).build();
        sensorApi = retrofit.create(SensorApi.class);
        readingApi = retrofit.create(ReadingApi.class);
    }

    @Override
    public Integer registerSensor(SensorData sensorData) {
        try {
            Response<Void> response = sensorApi.registerSensor(sensorData).execute();

                String location = response.headers().get("Location");
            return Integer.parseInt(location.substring(location.lastIndexOf("/") + 1));
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
        }
        return null;
    }

    @Override
    public GetSensorData getNeighbourSensor(Long sensorId) throws IOException {

        Response<GetSensorData> response = sensorApi.closestNeighbour(Long.parseLong(String.valueOf(sensorId))).execute();
        return response.body();

    }

    @Override
    public Integer registerReading(ReadingData reading, Long id) throws IOException {
        Response<Void> response = readingApi.registerSensor(reading,id).execute();
        String location = response.headers().get("Location");
        return Integer.parseInt(location.substring(location.lastIndexOf("/") + 1));
    }
}
