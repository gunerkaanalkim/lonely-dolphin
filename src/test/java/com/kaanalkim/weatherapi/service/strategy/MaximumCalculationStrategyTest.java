package com.kaanalkim.weatherapi.service.strategy;

import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.WeatherForecastEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MaximumCalculationStrategyTest {
    @InjectMocks
    MaximumCalculationStrategy maximumCalculationStrategy;

    @Test
    void calculate() {
        Metric metric = Metric.TEMPERATURE;

        List<WeatherForecastEntity> entities = getEntities(LocalDateTime.now());
        Map<Metric, List<WeatherForecastEntity>> metricListMap = new HashMap<>() {{
            put(metric, entities);
        }};

        Map<Metric, Double> calculate = maximumCalculationStrategy.calculate(metricListMap);

        assertEquals(11.0, calculate.get(metric));
    }

    public static List<WeatherForecastEntity> getEntities(LocalDateTime now) {
        Long sensorId = 1L;

        return List.of(WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.TEMPERATURE).value(10.0).build(), WeatherForecastEntity.builder().createdDate(now).sensorId(sensorId).metric(Metric.TEMPERATURE).value(11.0).build());
    }

}