package com.kaanalkim.weatherapi.service.impl;

import com.kaanalkim.weatherapi.WeatherApiApplicationTests;
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
import com.kaanalkim.weatherapi.service.calculate.MaximumCalculationStrategy;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class WeatherForecastServiceImplTest extends WeatherApiApplicationTests {
    @Mock
    WeatherForecastRepository weatherForecastRepository;

    @Mock
    WeatherForecastService weatherForecastService;

    @Mock
    CalculationStrategyFactory calculationStrategyFactory;

    @Test
    public void testCreateWeatherForecast() {
        //Given
        SensorRequest sensorRequest = SensorRequest.builder().sensorId(1L).metric(Metric.TEMPERATURE).value(30.00).build();

        SensorResponse sensorResponse = SensorResponse.builder().sensorId(1L).metric(Metric.TEMPERATURE).value(30.00).build();

        WeatherForecastEntity wearWeatherForecast = WeatherForecastEntity.builder().sensorId(sensorRequest.getSensorId()).metric(sensorRequest.getMetric()).value(sensorRequest.getValue()).build();

        //When
        when(weatherForecastRepository.save(wearWeatherForecast)).thenReturn(wearWeatherForecast);

        when(weatherForecastService.create(sensorRequest)).thenReturn(sensorResponse);

        SensorResponse createdSensorResponse = this.weatherForecastService.create(sensorRequest);

        //Then
        assertEquals(sensorRequest.getSensorId(), createdSensorResponse.getSensorId());
        assertEquals(sensorRequest.getMetric(), createdSensorResponse.getMetric());
        assertEquals(sensorRequest.getValue(), createdSensorResponse.getValue());
    }

    @Test
    public void testSearchWeatherForecast() {
        //Given
        Long sensorId = 1L;
        List<Metric> metricList = List.of(Metric.TEMPERATURE, Metric.HUMIDITY);
        Statistic statistic = Statistic.MAX;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusMinutes(10);
        LocalDateTime endDate = now.plusMinutes(10);
        Map<Metric, Double> givenCalculation = new HashMap<>() {{
            put(Metric.TEMPERATURE, 11.00);
            put(Metric.HUMIDITY, 11.00);
        }};

        CalculationStrategy calculationStrategy = new MaximumCalculationStrategy();

        List<WeatherForecastEntity> weatherForecastEntities = getEntities(now);

        //When
        when(this.weatherForecastRepository.findBySensorIdAndMetricInAndCreatedDateBetween(sensorId, metricList, startDate, endDate)).thenReturn(weatherForecastEntities);

        when(this.calculationStrategyFactory.findStrategyByStatistic(statistic)).thenReturn(calculationStrategy);

        when(this.weatherForecastService.search(sensorId, metricList, statistic, startDate, endDate)).thenReturn(givenCalculation);

        Map<Metric, Double> searchedCalculation = this.weatherForecastService.search(sensorId, metricList, statistic, startDate, endDate);

        //Then
        assertTrue(searchedCalculation.containsKey(Metric.TEMPERATURE));
        assertTrue(searchedCalculation.containsKey(Metric.HUMIDITY));
    }

    public static List<WeatherForecastEntity> getEntities(LocalDateTime now) {
        Long sensorId = 1L;

        return List.of(WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.TEMPERATURE).value(10.0).build(), WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.TEMPERATURE).value(11.0).build(), WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.HUMIDITY).value(10.0).build(), WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.HUMIDITY).value(11.0).build());
    }

}