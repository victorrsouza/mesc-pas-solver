package org.optaplanner.solutions.pas.optional.domain;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.solutions.pas.domain.AllocationPercentage;

import java.util.Comparator;

public class PercentageComparator implements Comparator<AllocationPercentage> {

    public int compare(AllocationPercentage a, AllocationPercentage b) {
        return new CompareToBuilder()
                .append(a.getValue(), b.getValue())
                .toComparison();
    }
}
