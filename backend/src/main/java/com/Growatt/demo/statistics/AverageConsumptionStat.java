package com.Growatt.demo.statistics;

import com.Growatt.demo.entity.SolarData;

import java.util.List;
import java.util.OptionalDouble;

public class AverageConsumptionStat implements SolarStatStrategy {

    @Override
    public String getName() {
        return "averageConsumption";
    }

    @Override
    public double compute(List<SolarData> data) {
        return data.stream()
                .mapToDouble(d -> d.getConsumption().doubleValue()).max().orElse(0.0);    }
}
