package com.earthquake.api.service;

import com.earthquake.api.dto.EarthquakeDto;
import com.earthquake.api.dto.PageResponse;
import com.earthquake.api.model.Earthquake;
import com.earthquake.api.repository.EarthquakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EarthquakeService {

    private final EarthquakeRepository earthquakeRepository;

    @Cacheable(value = "earthquakes", key = "#page + '_' + #size + '_' + #minMag + '_' + #place")
    public PageResponse<EarthquakeDto> getAll(int page, int size, Double minMag, String place) {
        int pageSize = Math.min(size, 20);
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("occurredAt").descending());

        Page<Earthquake> result;
        if (minMag != null && place != null) {
            result = earthquakeRepository.findByMagnitudeGreaterThanEqualAndPlaceContainingIgnoreCase(
                    minMag, place, pageable);
        } else if (minMag != null) {
            result = earthquakeRepository.findByMagnitudeGreaterThanEqual(minMag, pageable);
        } else if (place != null) {
            result = earthquakeRepository.findByPlaceContainingIgnoreCase(place, pageable);
        } else {
            result = earthquakeRepository.findAll(pageable);
        }

        return new PageResponse<>(
                result.getContent().stream().map(this::toDto).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }

    public EarthquakeDto getById(Long id) {
        return earthquakeRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Earthquake not found with id: " + id));
    }

    @CacheEvict(value = "earthquakes", allEntries = true)
    public EarthquakeDto create(EarthquakeDto dto) {
        return toDto(earthquakeRepository.save(toEntity(dto)));
    }

    @CacheEvict(value = "earthquakes", allEntries = true)
    public EarthquakeDto update(Long id, EarthquakeDto dto) {
        Earthquake existing = earthquakeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Earthquake not found with id: " + id));

        existing.setMagnitude(dto.getMagnitude());
        existing.setPlace(dto.getPlace());
        existing.setOccurredAt(dto.getOccurredAt() != null ? dto.getOccurredAt() : existing.getOccurredAt());
        existing.setDepth(dto.getDepth());
        existing.setLatitude(dto.getLatitude());
        existing.setLongitude(dto.getLongitude());
        existing.setType(dto.getType() != null ? dto.getType() : existing.getType());
        existing.setStatus(dto.getStatus() != null ? dto.getStatus() : existing.getStatus());

        return toDto(earthquakeRepository.save(existing));
    }

    @CacheEvict(value = "earthquakes", allEntries = true)
    public void delete(Long id) {
        if (!earthquakeRepository.existsById(id)) {
            throw new IllegalArgumentException("Earthquake not found with id: " + id);
        }
        earthquakeRepository.deleteById(id);
    }

    private EarthquakeDto toDto(Earthquake e) {
        return EarthquakeDto.builder()
                .id(e.getId())
                .magnitude(e.getMagnitude())
                .place(e.getPlace())
                .occurredAt(e.getOccurredAt())
                .depth(e.getDepth())
                .latitude(e.getLatitude())
                .longitude(e.getLongitude())
                .type(e.getType())
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    private Earthquake toEntity(EarthquakeDto dto) {
        return Earthquake.builder()
                .magnitude(dto.getMagnitude())
                .place(dto.getPlace())
                .occurredAt(dto.getOccurredAt() != null ? dto.getOccurredAt() : LocalDateTime.now())
                .depth(dto.getDepth())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .type(dto.getType() != null ? dto.getType() : "earthquake")
                .status(dto.getStatus() != null ? dto.getStatus() : "automatic")
                .build();
    }
}
