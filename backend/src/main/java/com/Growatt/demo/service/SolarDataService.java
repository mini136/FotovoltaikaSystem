package com.Growatt.demo.service;

import com.Growatt.demo.entity.SolarData;
import com.Growatt.demo.repository.SolarDataRepository;
import com.Growatt.demo.statistics.SolarStatisticsContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SolarDataService {

    private final SolarStatisticsContext statisticsContext;

    private final SolarDataRepository repository;

    public List<SolarData> getAll() {
        return repository.findAll();
    }

    public SolarData getLatest() {
        return repository.findTopByOrderByTimestampDesc();
    }

    public List<SolarData> getBetween(LocalDateTime start, LocalDateTime end) {
        return repository.findAllByTimestampBetween(start, end);
    }

    public SolarData save(SolarData data) {
        return repository.save(data);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public Map<String, Double> getStatistics() {
        return statisticsContext.calculateAll(repository.findAll());
    }
}
