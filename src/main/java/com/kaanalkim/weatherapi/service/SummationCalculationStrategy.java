package com.kaanalkim.weatherapi.service;

import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import com.kaanalkim.weatherapi.model.WeatherForecast;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SummationCalculationStrategy implements CalculationStrategy {
    @Override
    public Map<Metric, Double> calculate(Map<Metric, List<WeatherForecast>> groupedMetrics) {
        return groupedMetrics.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        metricListEntry -> metricListEntry.getValue().stream()
                                .mapToDouble(WeatherForecast::getValue).sum())
                );
    }

    @Override
    public Statistic getStatistic() {
        return Statistic.SUM;
    }
}
