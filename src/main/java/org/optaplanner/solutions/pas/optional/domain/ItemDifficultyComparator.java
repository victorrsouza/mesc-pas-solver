package org.optaplanner.solutions.pas.optional.domain;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.solutions.pas.domain.ResourceAllocation;

import java.util.Comparator;

public class ItemDifficultyComparator implements Comparator<ResourceAllocation> {

    public int compare(ResourceAllocation a, ResourceAllocation b) {
        return new CompareToBuilder()
                .append(a.getSource().getAvailableResource(), b.getSource().getAvailableResource())
                .toComparison();
    }
}
