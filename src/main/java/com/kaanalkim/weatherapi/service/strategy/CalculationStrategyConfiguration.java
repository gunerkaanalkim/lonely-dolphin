package com.kaanalkim.weatherapi.service.strategy;

import com.kaanalkim.weatherapi.model.Statistic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

@Configuration
public class CalculationStrategyConfiguration {

    @Bean
    public Map<Statistic, CalculationStrategy> calculateStrategies(Set<CalculationStrategy> calculationStrategies) {
        return calculationStrategies.stream()
                .collect(toMap(CalculationStrategy::getStatistic, strategy -> strategy));
    }
}
