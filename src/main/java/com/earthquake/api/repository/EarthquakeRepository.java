package com.earthquake.api.repository;

import com.earthquake.api.model.Earthquake;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EarthquakeRepository extends JpaRepository<Earthquake, Long> {

    Page<Earthquake> findByMagnitudeGreaterThanEqual(Double magnitude, Pageable pageable);

    Page<Earthquake> findByPlaceContainingIgnoreCase(String place, Pageable pageable);

    Page<Earthquake> findByMagnitudeGreaterThanEqualAndPlaceContainingIgnoreCase(
            Double magnitude, String place, Pageable pageable);
}
