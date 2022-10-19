package com.kaanalkim.weatherapi.service.impl;


import com.kaanalkim.weatherapi.model.*;
import com.kaanalkim.weatherapi.payload.SensorRequest;
import com.kaanalkim.weatherapi.repository.WeatherForecastRepository;
import com.kaanalkim.weatherapi.service.CalculationStrategy;
import com.kaanalkim.weatherapi.service.CalculationStrategyFactory;
import com.kaanalkim.weatherapi.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherForecastServiceImpl implements WeatherForecastService {
    private final WeatherForecastRepository weatherForecastRepository;
    private final CalculationStrategyFactory calculationStrategyFactory;

    @Autowired
    public WeatherForecastServiceImpl(WeatherForecastRepository weatherForecastRepository, CalculationStrategyFactory calculationStrategyFactory) {
        this.weatherForecastRepository = weatherForecastRepository;
        this.calculationStrategyFactory = calculationStrategyFactory;
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
    public Map<Metric, Map<String, Integer>> search(Long sensor, List<Metric> metric, Statistic statistic, Date startDate, Date endDate) {
        List<WeatherForecast> weatherForecasts = this.weatherForecastRepository
                .findBySensorIdAndMetricInAndCreatedDateBetween(sensor, metric, startDate, endDate);

        Map<Metric, List<WeatherForecast>> groupedMetrics = weatherForecasts.stream().collect(Collectors.groupingBy(WeatherForecast::getMetric));

        Map<Metric, IntSummaryStatistics> statistics = getAllStatistics(groupedMetrics);

        return getSpecificStatistic(statistic, statistics);
    }

    private static Map<Metric, IntSummaryStatistics> getAllStatistics(Map<Metric, List<WeatherForecast>> metricListMap) {
        return metricListMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        metricListEntry -> metricListEntry.getValue().stream()
                                .mapToInt(WeatherForecast::getValue).summaryStatistics())
                );
    }

    private Map<Metric, Map<String, Integer>> getSpecificStatistic(Statistic statistic, Map<Metric, IntSummaryStatistics> statistics) {
        CalculationStrategy calculationStrategy = this.calculationStrategyFactory.findStrategy(statistic);
        return calculationStrategy.calculate(statistics);
    }
}
