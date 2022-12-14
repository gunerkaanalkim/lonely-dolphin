package com.kaanalkim.weatherapi.repository;

import com.kaanalkim.weatherapi.model.Metric;
import com.kaanalkim.weatherapi.model.WeatherForecastEntity;
import com.kaanalkim.weatherapi.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface WeatherForecastRepository extends BaseRepository<WeatherForecastEntity, Long> {
    List<WeatherForecastEntity> findBySensorIdAndMetricInAndCreatedDateBetween(Long sensorId, Collection<Metric> metric, LocalDateTime from, LocalDateTime to);
}
