package com.kaanalkim.weatherapi.service.impl;


import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import com.kaanalkim.weatherapi.model.WeatherForecast;
import com.kaanalkim.weatherapi.payload.SensorRequest;
import com.kaanalkim.weatherapi.repository.WeatherForecastRepository;
import com.kaanalkim.weatherapi.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherForecastServiceImpl implements WeatherForecastService {
    private final WeatherForecastRepository weatherForecastRepository;

    @Autowired
    public WeatherForecastServiceImpl(WeatherForecastRepository weatherForecastRepository) {
        this.weatherForecastRepository = weatherForecastRepository;
    }

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
    public List<WeatherForecast> search(Long sensor, List<Metric> metric, Statistic statistic, Date startDate, Date endDate) {
        List<WeatherForecast> weatherForecasts = this.weatherForecastRepository
                .findBySensorIdAndMetricInAndCreatedDateBetween(sensor, metric, startDate, endDate);

        Map<Metric, List<WeatherForecast>> metricListMap = weatherForecasts.stream().collect(Collectors.groupingBy(WeatherForecast::getMetric));

        Map<Metric, IntSummaryStatistics> collect = metricListMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        metricListEntry -> metricListEntry.getValue().stream()
                                .mapToInt(WeatherForecast::getValue).summaryStatistics())
                );

        return List.of(new WeatherForecast());
    }
}
