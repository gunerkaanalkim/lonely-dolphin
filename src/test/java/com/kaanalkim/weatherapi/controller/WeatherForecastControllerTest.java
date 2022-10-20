package com.kaanalkim.weatherapi.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaanalkim.weatherapi.WeatherApiApplicationTests;
import com.kaanalkim.weatherapi.exception.InvalidDateException;
import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import com.kaanalkim.weatherapi.model.WeatherForecastEntity;
import com.kaanalkim.weatherapi.payload.SensorRequest;
import com.kaanalkim.weatherapi.payload.SensorResponse;
import com.kaanalkim.weatherapi.repository.WeatherForecastRepository;
import com.kaanalkim.weatherapi.service.calculate.CalculationStrategy;
import com.kaanalkim.weatherapi.service.calculate.CalculationStrategyFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(OrderAnnotation.class)
class WeatherForecastControllerTest extends WeatherApiApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WeatherForecastRepository weatherForecastRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    CalculationStrategyFactory calculationStrategyFactory;

    @Test
    @Order(1)
    @SneakyThrows
    public void sensorRequest_create_sensorResponse() {
        //Given
        SensorRequest sensorRequest = SensorRequest.builder()
                .sensorId(1L)
                .value(35.0)
                .metric(Metric.TEMPERATURE)
                .build();

        //When
        ResultActions resultActions = this.mockMvc.perform(post("/weather-forecast")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(sensorRequest))
        ).andDo(print());

        WeatherForecastEntity weatherForecast = weatherForecastRepository.findAll().iterator().next();

        SensorResponse sensorResponse = SensorResponse.builder()
                .sensorId(1L)
                .value(35.0)
                .metric(Metric.TEMPERATURE)
                .build();

        // Then
        assertEquals(sensorRequest.getSensorId(), weatherForecast.getSensorId());
        assertEquals(sensorRequest.getValue(), weatherForecast.getValue());
        assertEquals(sensorRequest.getMetric(), weatherForecast.getMetric());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sensorId").value(sensorResponse.getSensorId()))
                .andExpect(jsonPath("$.metric").value(sensorResponse.getMetric().name()))
                .andExpect(jsonPath("$.value").value(sensorResponse.getValue()));
    }

    @Test
    @Order(2)
    @SneakyThrows
    public void statisticTemperatureAndHumidity_read_metricValueMapByMax() {
        //Given
        Long sensorId = 1L;
        Metric temperatureMetric = Metric.TEMPERATURE;
        Metric humidityMetric = Metric.HUMIDITY;
        Statistic statistic = Statistic.MAX;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(2);
        LocalDateTime endDate = now.plusMinutes(10);
        String LOCAL_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

        //When
        weatherForecastRepository.saveAll(getEntities(now));

        String url = String.format("/weather-forecast?sensor=%s&metrics=%s&metrics=%s&statistic=%s&from=%s&to=%s",
                sensorId,
                temperatureMetric,
                humidityMetric,
                statistic,
                startDate.format(DateTimeFormatter.ofPattern(LOCAL_DATETIME_FORMAT)),
                endDate.format(DateTimeFormatter.ofPattern(LOCAL_DATETIME_FORMAT)));

        ResultActions resultActions = this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());

        Iterable<WeatherForecastEntity> weatherForecasts = weatherForecastRepository
                .findBySensorIdAndMetricInAndCreatedDateBetween(sensorId, List.of(temperatureMetric, humidityMetric), startDate, endDate);

        Map<Metric, List<WeatherForecastEntity>> groupedMetrics = StreamSupport.stream(weatherForecasts.spliterator(), true)
                .collect(groupingBy(WeatherForecastEntity::getMetric));

        CalculationStrategy strategyByStatistic = this.calculationStrategyFactory.findStrategyByStatistic(statistic);
        Map<Metric, Double> calculatedMetric = strategyByStatistic.calculate(groupedMetrics);

        //Then

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.TEMPERATURE").value(calculatedMetric.get(temperatureMetric)));
    }

    @Test
    @Order(3)
    @SneakyThrows
    public void statisticTemperatureAndHumidity_read_metricValueMapByMin() {
        //Given
        Long sensorId = 1L;
        Metric temperatureMetric = Metric.TEMPERATURE;
        Metric humidityMetric = Metric.HUMIDITY;
        Statistic statistic = Statistic.MIN;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(2);
        LocalDateTime endDate = now.plusMinutes(10);
        String LOCAL_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

        //When
        weatherForecastRepository.saveAll(getEntities(now));

        String url = String.format("/weather-forecast?sensor=%s&metrics=%s&metrics=%s&statistic=%s&from=%s&to=%s",
                sensorId,
                temperatureMetric,
                humidityMetric,
                statistic,
                startDate.format(DateTimeFormatter.ofPattern(LOCAL_DATETIME_FORMAT)),
                endDate.format(DateTimeFormatter.ofPattern(LOCAL_DATETIME_FORMAT)));

        ResultActions resultActions = this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());

        Iterable<WeatherForecastEntity> weatherForecasts = weatherForecastRepository
                .findBySensorIdAndMetricInAndCreatedDateBetween(sensorId, List.of(temperatureMetric, humidityMetric), startDate, endDate);

        Map<Metric, List<WeatherForecastEntity>> groupedMetrics = StreamSupport.stream(weatherForecasts.spliterator(), true)
                .collect(groupingBy(WeatherForecastEntity::getMetric));

        CalculationStrategy strategyByStatistic = this.calculationStrategyFactory.findStrategyByStatistic(statistic);
        Map<Metric, Double> calculatedMetric = strategyByStatistic.calculate(groupedMetrics);

        //Then

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.TEMPERATURE").value(calculatedMetric.get(temperatureMetric)));
    }

    @Test
    @Order(4)
    @SneakyThrows
    public void statisticTemperatureAndHumidity_read_metricValueMapByAvg() {
        //Given
        Long sensorId = 1L;
        Metric temperatureMetric = Metric.TEMPERATURE;
        Metric humidityMetric = Metric.HUMIDITY;
        Statistic statistic = Statistic.AVERAGE;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(2);
        LocalDateTime endDate = now.plusMinutes(10);
        String LOCAL_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

        //When
        weatherForecastRepository.saveAll(getEntities(now));

        String url = String.format("/weather-forecast?sensor=%s&metrics=%s&metrics=%s&statistic=%s&from=%s&to=%s",
                sensorId,
                temperatureMetric,
                humidityMetric,
                statistic,
                startDate.format(DateTimeFormatter.ofPattern(LOCAL_DATETIME_FORMAT)),
                endDate.format(DateTimeFormatter.ofPattern(LOCAL_DATETIME_FORMAT)));

        ResultActions resultActions = this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());

        Iterable<WeatherForecastEntity> weatherForecasts = weatherForecastRepository
                .findBySensorIdAndMetricInAndCreatedDateBetween(sensorId, List.of(temperatureMetric, humidityMetric), startDate, endDate);

        Map<Metric, List<WeatherForecastEntity>> groupedMetrics = StreamSupport.stream(weatherForecasts.spliterator(), true)
                .collect(groupingBy(WeatherForecastEntity::getMetric));

        CalculationStrategy strategyByStatistic = this.calculationStrategyFactory.findStrategyByStatistic(statistic);
        Map<Metric, Double> calculatedMetric = strategyByStatistic.calculate(groupedMetrics);

        //Then

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.TEMPERATURE").value(calculatedMetric.get(temperatureMetric)));
    }

    @Test
    @Order(5)
    @SneakyThrows
    public void statisticTemperatureAndHumidity_read_metricValueMapBySum() {
        //Given
        Long sensorId = 1L;
        Metric temperatureMetric = Metric.TEMPERATURE;
        Metric humidityMetric = Metric.HUMIDITY;
        Statistic statistic = Statistic.SUM;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(2);
        LocalDateTime endDate = now.plusMinutes(10);
        String LOCAL_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

        //When
        weatherForecastRepository.saveAll(getEntities(now));

        String url = String.format("/weather-forecast?sensor=%s&metrics=%s&metrics=%s&statistic=%s&from=%s&to=%s",
                sensorId,
                temperatureMetric,
                humidityMetric,
                statistic,
                startDate.format(DateTimeFormatter.ofPattern(LOCAL_DATETIME_FORMAT)),
                endDate.format(DateTimeFormatter.ofPattern(LOCAL_DATETIME_FORMAT)));

        ResultActions resultActions = this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());

        Iterable<WeatherForecastEntity> weatherForecasts = weatherForecastRepository
                .findBySensorIdAndMetricInAndCreatedDateBetween(sensorId, List.of(temperatureMetric, humidityMetric), startDate, endDate);

        Map<Metric, List<WeatherForecastEntity>> groupedMetrics = StreamSupport.stream(weatherForecasts.spliterator(), true)
                .collect(groupingBy(WeatherForecastEntity::getMetric));

        CalculationStrategy strategyByStatistic = this.calculationStrategyFactory.findStrategyByStatistic(statistic);
        Map<Metric, Double> calculatedMetric = strategyByStatistic.calculate(groupedMetrics);

        //Then

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.TEMPERATURE").value(calculatedMetric.get(temperatureMetric)));
    }


    @Test
    @Order(6)
    public void givenInvalidDate_whenGetInvalidDateException_thenBadRequest() throws Exception {

        //Given
        Long sensorId = 1L;
        Metric temperatureMetric = Metric.TEMPERATURE;
        Metric humidityMetric = Metric.HUMIDITY;
        Statistic statistic = Statistic.SUM;
        LocalDateTime sameDay = LocalDateTime.now();
        String LOCAL_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
        String exceptionMessage = "Date difference or date order is incorrect.";

        //When
        weatherForecastRepository.saveAll(getEntities(sameDay));

        String url = String.format("/weather-forecast?sensor=%s&metrics=%s&metrics=%s&statistic=%s&from=%s&to=%s",
                sensorId,
                temperatureMetric,
                humidityMetric,
                statistic,
                sameDay.format(DateTimeFormatter.ofPattern(LOCAL_DATETIME_FORMAT)),
                sameDay.format(DateTimeFormatter.ofPattern(LOCAL_DATETIME_FORMAT)));

        ResultActions resultActions = this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidDateException))
                .andExpect(result -> assertEquals(exceptionMessage, Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    public static List<WeatherForecastEntity> getEntities(LocalDateTime now) {
        Long sensorId = 1L;

        return List.of(
                WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.TEMPERATURE).value(10.0).build(),
                WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.TEMPERATURE).value(11.0).build(),
                WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.TEMPERATURE).value(12.0).build(),
                WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.TEMPERATURE).value(13.0).build(),
                WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.TEMPERATURE).value(15.0).build(),
                WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.HUMIDITY).value(10.0).build(),
                WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.HUMIDITY).value(11.0).build(),
                WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.HUMIDITY).value(12.0).build(),
                WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.HUMIDITY).value(13.0).build(),
                WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.HUMIDITY).value(15.0).build());
    }
}