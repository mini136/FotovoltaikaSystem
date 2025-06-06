package com.Growatt.demo.repository;

import com.Growatt.demo.entity.SolarData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SolarDataRepository extends JpaRepository<SolarData, LocalDateTime> {
    List<SolarData> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end);
    SolarData findTopByOrderByTimestampDesc();
}
