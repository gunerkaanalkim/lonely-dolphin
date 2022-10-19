package com.kaanalkim.weatherapi.service.impl;


import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import com.kaanalkim.weatherapi.model.WeatherForecast;
import com.kaanalkim.weatherapi.payload.SensorRequest;
import com.kaanalkim.weatherapi.repository.WeatherForecastRepository;
import com.kaanalkim.weatherapi.service.CalculationStrategy;
import com.kaanalkim.weatherapi.service.CalculationStrategyFactory;
import com.kaanalkim.weatherapi.service.WeatherForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherForecastServiceImpl implements WeatherForecastService {
    private final WeatherForecastRepository weatherForecastRepository;
    private final CalculationStrategyFactory calculationStrategyFactory;

    @Override
    public WeatherForecast create(SensorRequest sensorRequest) {
        WeatherForecast wearWeatherForecast = WeatherForecast.builder()
                .sensorId(sensorRequest.getSensorId())
                .metric(sensorRequest.getMetric())
                .value(sensorRequest.getValue())
                .build();

        return this.weatherForecastRepository.save(wearWeatherForecast);
    }

    @Override
    public Map<Metric, Double> search(Long sensor, List<Metric> metric, Statistic statistic, LocalDateTime startDate, LocalDateTime endDate) {
        List<WeatherForecast> weatherForecasts = this.weatherForecastRepository
                .findBySensorIdAndMetricInAndCreatedDateBetween(sensor, metric, startDate, endDate);

        Map<Metric, List<WeatherForecast>> groupedMetrics = weatherForecasts.stream().collect(Collectors.groupingBy(WeatherForecast::getMetric));

        CalculationStrategy calculationStrategy = this.calculationStrategyFactory.findStrategyByStatistic(statistic);
        return calculationStrategy.calculate(groupedMetrics);
    }
}
