package com.Growatt.demo.statistics;

import com.Growatt.demo.entity.SolarData;

import java.util.List;

public class MinConsumptionStat implements SolarStatStrategy {

    @Override
    public String getName() {
        return "minConsumption";
    }

    @Override
    public double compute(List<SolarData> data) {
        return data.stream().mapToDouble(d -> d.getProduction().doubleValue()).min().orElse(0.0);
    }
}
