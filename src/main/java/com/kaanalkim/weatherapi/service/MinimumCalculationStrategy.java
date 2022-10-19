package com.kaanalkim.weatherapi.service;

import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;

@Component
public class MinimumCalculationStrategy implements CalculationStrategy {
    @Override
    public Map<Metric, Map<String, Integer>> calculate(Map<Metric, IntSummaryStatistics> statistics) {
        Map<Metric, Map<String, Integer>> preparedStatistic = new HashMap<>();

        statistics.forEach((metric, statistic) -> {
            preparedStatistic.put(metric, new HashMap<>() {{
                put(Statistic.MIN.name(), statistic.getMin());
            }});
        });

        return preparedStatistic;
    }

    @Override
    public Statistic getStatistic() {
        return Statistic.MIN;
    }
}
