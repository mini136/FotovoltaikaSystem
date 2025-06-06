package com.Growatt.demo.statistics;

import com.Growatt.demo.entity.SolarData;

import java.util.List;
import java.util.OptionalDouble;

public class AverageProductionStat implements SolarStatStrategy {

    @Override
    public String getName() {
        return "averageProduction";
    }

    @Override
    public double compute(List<SolarData> data) {
        return data.stream()
                .mapToDouble(d -> d.getProduction().doubleValue())
                .average()
                .orElse(0.0);
    }
}
