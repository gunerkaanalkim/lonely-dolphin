package com.kaanalkim.weatherapi.controller;


import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import com.kaanalkim.weatherapi.model.WeatherForecast;
import com.kaanalkim.weatherapi.payload.SensorRequest;
import com.kaanalkim.weatherapi.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("weather-forecast")
public class WeatherForecastController {
    private final WeatherForecastService weatherForecastService;

    @Autowired
    public WeatherForecastController(WeatherForecastService weatherForecastService) {
        this.weatherForecastService = weatherForecastService;
    }

    @PostMapping
    public ResponseEntity<WeatherForecast> create(@RequestBody SensorRequest sensorRequest) {
        WeatherForecast weatherForecast = this.weatherForecastService.create(sensorRequest);

        return ResponseEntity.ok().body(weatherForecast);
    }

    //TODO Configure datetime format in .yml file or in application level
    @GetMapping
    public ResponseEntity< Map<Metric, Map<String, Integer>>> read(
            @RequestParam Long sensor,
            @RequestParam List<Metric> metrics,
            @RequestParam Statistic statistic,
            @RequestParam("from")  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")  Date startDate,
            @RequestParam("to")  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")  Date endDate) {
        Map<Metric, Map<String, Integer>> statistics = this.weatherForecastService.search(sensor, metrics, statistic, startDate, endDate);

        return ResponseEntity.ok().body(statistics);
    }

}
