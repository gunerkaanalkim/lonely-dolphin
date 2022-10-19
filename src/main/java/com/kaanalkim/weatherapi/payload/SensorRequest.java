package com.kaanalkim.weatherapi.payload;

import com.kaanalkim.weatherapi.model.Metric;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorRequest {
    private Long sensorId;
    private Metric metric;
    private Double value;
}
