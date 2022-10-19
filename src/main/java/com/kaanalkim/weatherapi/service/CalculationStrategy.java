package com.kaanalkim.weatherapi.service;

import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;

import java.util.IntSummaryStatistics;
import java.util.Map;

public interface CalculationStrategy {
    Statistic getStatistic();
    Map<Metric, Map<String, Integer>> calculate(Map<Metric, IntSummaryStatistics> statistics);
}
