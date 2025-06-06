package com.Growatt.demo.statistics;

import com.Growatt.demo.entity.SolarData;

import java.util.List;

public interface SolarStatStrategy {
    String getName(); // například "averageProduction"
    double compute(List<SolarData> data);
}