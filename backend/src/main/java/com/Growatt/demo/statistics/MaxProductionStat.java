package com.Growatt.demo.statistics;

import com.Growatt.demo.entity.SolarData;

import java.util.List;

public class MaxProductionStat implements SolarStatStrategy {

    @Override
    public String getName() {
        return "maxProduction";
    }

    @Override
    public double compute(List<SolarData> data) {
        return data.stream().mapToDouble(d -> d.getProduction().doubleValue()).max().orElse(0.0);
    }
}
