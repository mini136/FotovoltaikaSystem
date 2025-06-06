package com.Growatt.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "SolarData")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolarData {

    @Id
    @Column(name = "Timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "Production", precision = 10, scale = 2)
    private BigDecimal production;

    @Column(name = "Consumption", precision = 10, scale = 2)
    private BigDecimal consumption;

    @Column(name = "BatteryLevel", precision = 5, scale = 2)
    private BigDecimal batteryLevel;

    @Column(name = "GridImport", precision = 10, scale = 2)
    private BigDecimal gridImport;

    //region get a set

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getProduction() {
        return production;
    }

    public void setProduction(BigDecimal production) {
        this.production = production;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    public BigDecimal getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(BigDecimal batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public BigDecimal getGridImport() {
        return gridImport;
    }

    public void setGridImport(BigDecimal gridImport) {
        this.gridImport = gridImport;
    }

    //endregion
}
