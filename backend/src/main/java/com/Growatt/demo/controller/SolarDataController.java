package com.Growatt.demo.controller;
import com.Growatt.demo.entity.SolarData;
import com.Growatt.demo.service.SolarDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/solar-data")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SolarDataController {

    private final SolarDataService service;

    @GetMapping
    public List<SolarData> getAll() {
        return service.getAll();
    }

    @GetMapping("/latest")
    public SolarData getLatest() {
        return service.getLatest();
    }

    @GetMapping("/range")
    public List<SolarData> getBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return service.getBetween(start, end);
    }

    @GetMapping("/statistics")
    public Map<String, Double> getStatistics() {
        return service.getStatistics();
    }

    @PostMapping
    public SolarData insert(@RequestBody SolarData data) {
        return service.save(data);
    }

    @DeleteMapping
    public void deleteAll() {
        service.deleteAll();
    }
}
