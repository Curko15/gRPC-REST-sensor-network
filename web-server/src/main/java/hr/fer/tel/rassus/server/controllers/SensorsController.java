package hr.fer.tel.rassus.server.controllers;


import hr.fer.tel.rassus.server.beans.Sensor;
import hr.fer.tel.rassus.server.dto.GetSensorData;
import hr.fer.tel.rassus.server.dto.SensorData;
import hr.fer.tel.rassus.server.services.SensorRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class SensorsController {

    private final SensorRepository sensorRepository;

    public SensorsController(SensorRepository sensorRepository1) {
        this.sensorRepository = sensorRepository1;
    }
    //  TODO 4.1  Registracija
    @PostMapping("/registerSensor")
    public ResponseEntity<?> register(@RequestBody SensorData sensorData) {
        try {
            Sensor sensor = Sensor.fromSensorData(sensorData);
        Sensor createdSensor = sensorRepository.save(sensor);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSensor.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException("Sensor registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/registerSensor/{id}")
    public ResponseEntity<GetSensorData> getSensor(@PathVariable Long id) {
        Sensor sensor = sensorRepository.findById(id).orElse(null);
        GetSensorData getSensorData = new GetSensorData();
        getSensorData.setId(sensor.getId());
        getSensorData.setLatitude(sensor.getLatitude());
        getSensorData.setLongitude(sensor.getLongitude());
        getSensorData.setIp(sensor.getIp());
        getSensorData.setPort(sensor.getPort());
        return new ResponseEntity<>(getSensorData, HttpStatus.OK);
    }

    //  TODO 4.4  Popis senzora
    @GetMapping("/getSensors")
    public ResponseEntity<List<GetSensorData>> getSensors() {
        List<Sensor> sensors = sensorRepository.findAll();
        List<GetSensorData> sensorDataList = GetSensorData.convertSensorList(sensors);

        return new ResponseEntity<>(sensorDataList, HttpStatus.OK);
    }

    //  TODO 4.2  Najbliži susjed
    @GetMapping("/closestNeighbour/{id}")
    public ResponseEntity<GetSensorData> getNearestNeighbour(@PathVariable Long id) {

        Optional<Sensor> optionalSensor = sensorRepository.findById(id);
        if (optionalSensor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Sensor sensor = optionalSensor.get();
        List<Sensor> allSensors = sensorRepository.findAll();

        Sensor nearestNeighbour = null;
        double minimumDistance = Double.MAX_VALUE;

        for (Sensor otherSensor : allSensors) {
            if (!otherSensor.getId().equals(sensor.getId())) {
                double distance = calculateHaversine(
                        sensor.getLatitude(), sensor.getLongitude(),
                        otherSensor.getLatitude(), otherSensor.getLongitude()
                );

                if (distance < minimumDistance) {
                    minimumDistance = distance;
                    nearestNeighbour = otherSensor;
                }
            }
        }

        if (nearestNeighbour == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new GetSensorData(nearestNeighbour.getId(), nearestNeighbour.getLatitude(), nearestNeighbour.getLongitude(), nearestNeighbour.getIp(), nearestNeighbour.getPort()));
    }

    private double calculateHaversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

}
