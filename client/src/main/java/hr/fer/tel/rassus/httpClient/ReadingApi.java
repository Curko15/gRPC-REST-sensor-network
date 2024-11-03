package hr.fer.tel.rassus.httpClient;

import hr.fer.tel.rassus.dto.ReadingData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReadingApi {
    @POST("/saveReading/{id}")
    Call<Void> registerSensor(@Body ReadingData sensorData, @Path("id") Long id);

}

