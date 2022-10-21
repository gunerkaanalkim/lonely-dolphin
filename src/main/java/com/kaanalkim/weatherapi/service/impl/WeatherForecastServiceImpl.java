package com.kaanalkim.weatherapi.service.impl;


import com.kaanalkim.weatherapi.exception.InvalidDateException;
import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import com.kaanalkim.weatherapi.model.WeatherForecastEntity;
import com.kaanalkim.weatherapi.payload.SensorRequest;
import com.kaanalkim.weatherapi.payload.SensorResponse;
import com.kaanalkim.weatherapi.repository.WeatherForecastRepository;
import com.kaanalkim.weatherapi.service.WeatherForecastService;
import com.kaanalkim.weatherapi.service.calculate.CalculationStrategy;
import com.kaanalkim.weatherapi.service.calculate.CalculationStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeatherForecastServiceImpl implements WeatherForecastService {
    private final WeatherForecastRepository weatherForecastRepository;
    private final CalculationStrategyFactory calculationStrategyFactory;

    @Transactional
    @Override
    public SensorResponse create(SensorRequest sensorRequest) {
        WeatherForecastEntity wearWeatherForecast = WeatherForecastEntity.builder()
                .sensorId(sensorRequest.getSensorId())
                .metric(sensorRequest.getMetric())
                .value(sensorRequest.getValue())
                .build();

        WeatherForecastEntity save = this.weatherForecastRepository.save(wearWeatherForecast);

        return SensorResponse.builder()
                .sensorId(save.getSensorId())
                .metric(save.getMetric())
                .value(save.getValue())
                .build();
    }

    @Override
    public Map<Metric, Double> search(Long sensor, List<Metric> metrics, Statistic statistic, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            endDate = LocalDateTime.now();
            startDate = endDate.minusDays(1);
        }

        long dayDifferences = ChronoUnit.DAYS.between(startDate, endDate);
        long monthDifferences = ChronoUnit.MONTHS.between(startDate, endDate);

        if (dayDifferences < 1 || monthDifferences > 1 || endDate.isBefore(startDate)) {
            throw new InvalidDateException();
        }

        List<WeatherForecastEntity> weatherForecasts = this.weatherForecastRepository
                .findBySensorIdAndMetricInAndCreatedDateBetween(sensor, metrics, startDate, endDate);

        Map<Metric, List<WeatherForecastEntity>> groupedMetrics = weatherForecasts.stream().collect(groupingBy(WeatherForecastEntity::getMetric));

        CalculationStrategy calculationStrategy = this.calculationStrategyFactory.findStrategyByStatistic(statistic);
        return calculationStrategy.calculate(groupedMetrics);
    }
}
