package com.kaanalkim.weatherapi.service;

import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import com.kaanalkim.weatherapi.payload.SensorRequest;
import com.kaanalkim.weatherapi.payload.SensorResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface WeatherForecastService {
    SensorResponse create(SensorRequest sensorRequest);

    Map<Metric, Double> search(Long sensor, List<Metric> metric, Statistic statistic, LocalDateTime startDate, LocalDateTime endDate);
}
