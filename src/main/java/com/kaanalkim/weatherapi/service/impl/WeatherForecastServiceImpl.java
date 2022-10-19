package com.kaanalkim.weatherapi.service.impl;


import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.Statistic;
import com.kaanalkim.weatherapi.model.WeatherForecast;
import com.kaanalkim.weatherapi.payload.SensorRequest;
import com.kaanalkim.weatherapi.repository.WeatherForecastRepository;
import com.kaanalkim.weatherapi.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherForecastServiceImpl implements WeatherForecastService {
    private final WeatherForecastRepository weatherForecastRepository;

    @Autowired
    public WeatherForecastServiceImpl(WeatherForecastRepository weatherForecastRepository) {
        this.weatherForecastRepository = weatherForecastRepository;
    }

    @Override
    public WeatherForecast create(SensorRequest sensorRequest) {
        WeatherForecast wearWeatherForecast = WeatherForecast.builder()
                .sensorId(sensorRequest.getSensorId())
                .metric(sensorRequest.getMetric())
                .value(sensorRequest.getValue())
                .build();

        return this.weatherForecastRepository.save(wearWeatherForecast);
    }

    @Override
    public Map<Metric, Map<String, Integer>> search(Long sensor, List<Metric> metric, Statistic statistic, Date startDate, Date endDate) {
        List<WeatherForecast> weatherForecasts = this.weatherForecastRepository
                .findBySensorIdAndMetricInAndCreatedDateBetween(sensor, metric, startDate, endDate);

        Map<Metric, List<WeatherForecast>> groupedMetrics = weatherForecasts.stream().collect(Collectors.groupingBy(WeatherForecast::getMetric));

        Map<Metric, IntSummaryStatistics> statistics = getAllStatistics(groupedMetrics);

        Map<Metric, Map<String, Integer>> specificStatistic = getSpecificStatistic(statistic, statistics);

        return specificStatistic;
    }

    private static Map<Metric, IntSummaryStatistics> getAllStatistics(Map<Metric, List<WeatherForecast>> metricListMap) {
        Map<Metric, IntSummaryStatistics> statistics = metricListMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        metricListEntry -> metricListEntry.getValue().stream()
                                .mapToInt(WeatherForecast::getValue).summaryStatistics())
                );
        return statistics;
    }

    private static Map<Metric, Map<String, Integer>> getSpecificStatistic(Statistic statistic, Map<Metric, IntSummaryStatistics> statistics) {
        Map<Metric, Map<String, Integer>> preparedStatistic = new HashMap<>();

        statistics.forEach((metric1, statistics1) -> {
            if (Statistic.AVERAGE.name().equals(statistic.name())) {
                preparedStatistic.put(metric1, new HashMap<>() {{
                    put(statistic.name(), (int) statistics1.getAverage());
                }});
            } else if (Statistic.MIN.name().equals(statistic.name())) {
                preparedStatistic.put(metric1, new HashMap<>() {{
                    put(statistic.name(), statistics1.getMin());
                }});
            } else if (Statistic.MAX.name().equals(statistic.name())) {
                preparedStatistic.put(metric1, new HashMap<>() {{
                    put(statistic.name(), statistics1.getMax());
                }});
            } else {
                preparedStatistic.put(metric1, new HashMap<>() {{
                    put(statistic.name(), (int) statistics1.getAverage());
                }});
            }
        });
        return preparedStatistic;
    }
}
