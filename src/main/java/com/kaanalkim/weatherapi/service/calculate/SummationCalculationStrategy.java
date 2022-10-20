package com.kaanalkim.weatherapi.service.calculate;

import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import com.kaanalkim.weatherapi.model.WeatherForecastEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SummationCalculationStrategy implements CalculationStrategy {
    @Override
    public Map<Metric, Double> calculate(Map<Metric, List<WeatherForecastEntity>> groupedMetrics) {
        return groupedMetrics.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        metricListEntry -> metricListEntry.getValue().stream()
                                .mapToDouble(WeatherForecastEntity::getValue).sum())
                );
    }

    @Override
    public Statistic getStatistic() {
        return Statistic.SUM;
    }
}
