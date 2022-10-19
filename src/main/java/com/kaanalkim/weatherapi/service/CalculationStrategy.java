package com.kaanalkim.weatherapi.service;

import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import com.kaanalkim.weatherapi.model.WeatherForecast;

import java.util.List;
import java.util.Map;

public interface CalculationStrategy {
    Statistic getStatistic();
    Map<Metric, Double> calculate(Map<Metric, List<WeatherForecast>> groupedMetrics);
}
