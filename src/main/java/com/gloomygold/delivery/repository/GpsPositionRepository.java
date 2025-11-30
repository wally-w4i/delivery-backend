package com.gloomygold.delivery.repository;

import com.gloomygold.delivery.model.GpsPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpsPositionRepository extends JpaRepository<GpsPosition, Long> {
}
