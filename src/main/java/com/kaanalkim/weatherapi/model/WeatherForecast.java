package com.kaanalkim.weatherapi.model;

import com.kaanalkim.weatherapi.model.base.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "weatherforecast")
@SuperBuilder
public class WeatherForecast extends AbstractEntity {

    @Column(name = "sensorId")
    private Long sensorId;

    @Enumerated(EnumType.STRING)
    private Metric metric;

    @Column(name = "value")
    private Double value;
}