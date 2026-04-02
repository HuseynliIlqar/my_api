package com.earthquake.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EarthquakeDto {

    private Long id;

    @NotNull(message = "Magnitude is required")
    @DecimalMin(value = "0.0", message = "Magnitude must be positive")
    private Double magnitude;

    @NotBlank(message = "Place is required")
    private String place;

    private LocalDateTime occurredAt;

    @NotNull(message = "Depth is required")
    private Double depth;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    private String type;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
