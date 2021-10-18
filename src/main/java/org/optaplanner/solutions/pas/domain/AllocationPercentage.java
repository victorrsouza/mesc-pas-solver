package org.optaplanner.solutions.pas.domain;

import java.math.BigDecimal;

public class AllocationPercentage {

    private BigDecimal value;

    public AllocationPercentage(){}

    public AllocationPercentage(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public double getDoubleValue() {
        return this.value.doubleValue();
    }

    @Override
    public String toString() {
        return String.format("%.2f", this.value)  + "%";
    }
}
