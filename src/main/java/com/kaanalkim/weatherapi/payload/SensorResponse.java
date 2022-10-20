package com.kaanalkim.weatherapi.payload;

import com.kaanalkim.weatherapi.model.Metric;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorResponse {
    private Long sensorId;
    private Metric metric;
    private Double value;
}
