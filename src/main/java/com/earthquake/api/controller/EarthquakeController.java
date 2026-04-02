package com.earthquake.api.controller;

import com.earthquake.api.dto.ApiResponse;
import com.earthquake.api.dto.EarthquakeDto;
import com.earthquake.api.dto.PageResponse;
import com.earthquake.api.service.EarthquakeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/earthquakes")
@RequiredArgsConstructor
@Tag(name = "Earthquakes", description = "Earthquake data — GET is public, write operations require JWT")
public class EarthquakeController {

    private final EarthquakeService earthquakeService;

    @GetMapping
    @Operation(summary = "Get paginated list of earthquakes (max 20 per page)")
    public ResponseEntity<ApiResponse<PageResponse<EarthquakeDto>>> getAll(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size (max 20)") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Filter by minimum magnitude (e.g. 5.0)") @RequestParam(required = false) Double minMag,
            @Parameter(description = "Filter by place name (partial match)") @RequestParam(required = false) String place
    ) {
        return ResponseEntity.ok(ApiResponse.success(earthquakeService.getAll(page, size, minMag, place)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single earthquake by ID")
    public ResponseEntity<ApiResponse<EarthquakeDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(earthquakeService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "Create a new earthquake record", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<EarthquakeDto>> create(@Valid @RequestBody EarthquakeDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(earthquakeService.create(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an earthquake record", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<EarthquakeDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody EarthquakeDto dto
    ) {
        return ResponseEntity.ok(ApiResponse.success(earthquakeService.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an earthquake record", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        earthquakeService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
