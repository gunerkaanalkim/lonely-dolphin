package com.kaanalkim.weatherapi.repository;

import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.WeatherForecast;
import com.kaanalkim.weatherapi.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface WeatherForecastRepository extends BaseRepository<WeatherForecast, Long> {
    List<WeatherForecast> findBySensorIdAndMetricInAndCreatedDateBetween(Long sensorId, Collection<Metric> metric, Date from, Date to);
}
