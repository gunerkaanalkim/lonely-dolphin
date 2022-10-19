package com.kaanalkim.weatherapi.service;

import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import com.kaanalkim.weatherapi.model.WeatherForecast;
import com.kaanalkim.weatherapi.payload.SensorRequest;

import java.util.Date;
import java.util.List;

public interface WeatherForecastService {
    WeatherForecast create(SensorRequest sensorRequest);

    List<WeatherForecast> search(Long sensor, List<Metric> metric, Statistic statistic, Date startDate, Date endDate);
}
