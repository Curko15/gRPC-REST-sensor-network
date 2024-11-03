package hr.fer.tel.rassus.server.controllers;

import hr.fer.tel.rassus.server.beans.Reading;
import hr.fer.tel.rassus.server.beans.Sensor;
import hr.fer.tel.rassus.server.dto.GetReadingData;
import hr.fer.tel.rassus.server.services.ReadingRepository;
import hr.fer.tel.rassus.server.services.SensorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ReadingController {

    private final ReadingRepository readingRepository;
    private final SensorRepository sensorRepository;
    public ReadingController(ReadingRepository readingRepository, SensorRepository sensorRepository) {
        this.readingRepository = readingRepository;
        this.sensorRepository = sensorRepository;
    }

  // TODO 4.3  Spremanje očitanja pojedinog senzora
    @PostMapping("/saveReading/{id}")
    public ResponseEntity<?> saveReading(@RequestBody Reading reading, @PathVariable Long id) {
        Optional<Sensor> optionalSensor = sensorRepository.findById(id);
        if (optionalSensor.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Sensor sensor = optionalSensor.get();
        reading.setSensor(sensor);
        Reading savedReading = readingRepository.save(reading);
        savedReading.setSensor(null);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(String.format("/readings/%d",savedReading.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).body(savedReading);
    }
  // TODO 4.5  Popis očitanja pojedinog senzora
    @GetMapping("/getReadings/{id}")
    public ResponseEntity<?> getReadings(@PathVariable Long id) {
        Optional<Sensor> optionalSensor = sensorRepository.findById(id);
        if (optionalSensor.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       List<Reading> list = optionalSensor.get().getReadings();
        List<GetReadingData> data = GetReadingData.convertReadingList(list);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/saveReading/{sensorId}/readings/{id}")
    public ResponseEntity<?> getReadings(@PathVariable Long sensorId, @PathVariable Long id) {
        Reading reading = readingRepository.findById(id).orElse(null);
        if (reading == null) {
            return ResponseEntity.noContent().build();
        }
        GetReadingData getReadingData = new GetReadingData();
        getReadingData.setId(reading.getId());
        getReadingData.setTemperature(reading.getTemperature());
        getReadingData.setHumidity(reading.getHumidity());
        getReadingData.setPressure(reading.getPressure());
        getReadingData.setCo(reading.getCo());
        getReadingData.setNo2(reading.getNo2());
        getReadingData.setSo2(reading.getSo2());

        return ResponseEntity.ok(getReadingData);
    }
}