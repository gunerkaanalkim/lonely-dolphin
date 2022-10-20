package com.kaanalkim.weatherapi.payload;

import com.kaanalkim.weatherapi.model.Metric;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorRequest {
    @NotNull(message = "Metric should not be incorrect")
    @Min(1L)
    private Long sensorId;

    @NotNull(message = "Metric should not be incorrect")
    private Metric metric;

    @NotNull
    @Min(value = 5, message = "Min value is -100")
    @Max(value = 1000, message = "Max value is 1000")
    private Double value;
}
