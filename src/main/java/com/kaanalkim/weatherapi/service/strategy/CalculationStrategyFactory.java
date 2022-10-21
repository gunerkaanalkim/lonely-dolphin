package com.kaanalkim.weatherapi.service.strategy;

import com.kaanalkim.weatherapi.model.Statistic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CalculationStrategyFactory {

    private final Map<Statistic, CalculationStrategy> calculateStrategies;

    public CalculationStrategy findStrategyByStatistic(Statistic statistic) {
        return calculateStrategies.get(statistic);
    }
}
