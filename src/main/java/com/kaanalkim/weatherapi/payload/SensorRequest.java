package com.kaanalkim.weatherapi.payload;

import com.kaanalkim.weatherapi.model.Metric;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorRequest {
    @NotNull
    private Long sensorId;

    @NotNull
    private Metric metric;

    @NotNull
    private Double value;
}
