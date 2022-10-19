package com.kaanalkim.weatherapi.service;

import com.kaanalkim.weatherapi.model.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class CalculationStrategyFactory {
    private Map<Statistic, CalculationStrategy> strategies;

    @Autowired
    public CalculationStrategyFactory(Set<CalculationStrategy> calculationStrategySet) {
        this.createStrategy(calculationStrategySet);
    }

    public CalculationStrategy findStrategyByStatistic(Statistic statistic) {
        return strategies.get(statistic);
    }

    private void createStrategy(Set<CalculationStrategy> calculationStrategySet) {
        strategies = new HashMap<>();
        calculationStrategySet.forEach(strategy -> strategies.put(strategy.getStatistic(), strategy));
    }
}
