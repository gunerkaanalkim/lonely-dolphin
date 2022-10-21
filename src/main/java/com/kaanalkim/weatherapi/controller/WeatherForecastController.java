package com.kaanalkim.weatherapi.controller;


import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import com.kaanalkim.weatherapi.payload.SensorRequest;
import com.kaanalkim.weatherapi.payload.SensorResponse;
import com.kaanalkim.weatherapi.service.WeatherForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("weather-forecast")
public class WeatherForecastController {

    private final WeatherForecastService weatherForecastService;

    @PostMapping
    public ResponseEntity<SensorResponse> create(@Valid @RequestBody SensorRequest sensorRequest) {
        SensorResponse sensorResponse = this.weatherForecastService.create(sensorRequest);
        return ResponseEntity.ok().body(sensorResponse);
    }

    @GetMapping
    public ResponseEntity<Map<Metric, Double>> read(
            @RequestParam Long sensor,
            @RequestParam List<Metric> metrics,
            @RequestParam Statistic statistic,
            @RequestParam(required = false, name = "from") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
            @RequestParam(required = false, name = "to") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate) {
        Map<Metric, Double> statistics = this.weatherForecastService.search(sensor, metrics, statistic, startDate, endDate);
        return ResponseEntity.ok(statistics);
    }
}
