package hr.fer.tel.rassus.httpClient;


import hr.fer.tel.rassus.dto.GetSensorData;
import hr.fer.tel.rassus.dto.SensorData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SensorApi {
    @POST("/registerSensor")
    Call<Void> registerSensor(@Body SensorData sensorData);

    @GET("/closestNeighbour/{id}")
    Call<GetSensorData> closestNeighbour(@Path("id") Long id);
}
