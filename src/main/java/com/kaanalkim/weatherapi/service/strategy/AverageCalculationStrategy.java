package com.kaanalkim.weatherapi.service.strategy;

import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import com.kaanalkim.weatherapi.model.WeatherForecastEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AverageCalculationStrategy implements CalculationStrategy {
    @Override
    public Map<Metric, Double> calculate(Map<Metric, List<WeatherForecastEntity>> groupedMetrics) {
        return groupedMetrics.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        metricListEntry -> metricListEntry.getValue().stream()
                                .mapToDouble(WeatherForecastEntity::getValue).average().orElseThrow(IllegalStateException::new))
                );
    }

    @Override
    public Statistic getStatistic() {
        return Statistic.AVERAGE;
    }
}
