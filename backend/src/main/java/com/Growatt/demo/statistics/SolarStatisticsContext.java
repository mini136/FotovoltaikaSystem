package com.Growatt.demo.statistics;

import com.Growatt.demo.entity.SolarData;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SolarStatisticsContext {

    private final List<SolarStatStrategy> strategies;

    public SolarStatisticsContext() {
        this.strategies = List.of(
                new AverageProductionStat(),
                new AverageConsumptionStat(),
                new MaxProductionStat(),
                new MinConsumptionStat()
                // Přidej další strategie sem
        );
    }

    public Map<String, Double> calculateAll(List<SolarData> data) {
        Map<String, Double> result = new LinkedHashMap<>();
        for (SolarStatStrategy stat : strategies) {
            result.put(stat.getName(), stat.compute(data));
        }
        return result;
    }
}
