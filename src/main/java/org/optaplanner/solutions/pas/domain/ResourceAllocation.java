package org.optaplanner.solutions.pas.domain;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.solutions.pas.optional.domain.ItemDifficultyComparator;

@PlanningEntity(difficultyComparatorClass = ItemDifficultyComparator.class)
@XStreamAlias("ResourceAllocation")
public class ResourceAllocation {

    private int id;
    private FinancialSource source;
    private PlanningItem item;
    private AllocationPercentage percentage;

    public ResourceAllocation(){}

    public ResourceAllocation(int id, FinancialSource source, PlanningItem item){
        this.id = id;
        this.source = source;
        this.item = item;
    }

    public int getId() {
        return this.id;
    }

    public FinancialSource getSource() {
        return source;
    }

    public void setSource(FinancialSource source) {
        this.source = source;
    }

    public PlanningItem getItem() {
        return item;
    }

    public void setItem(PlanningItem item) {
        this.item = item;
    }

    @PlanningVariable(valueRangeProviderRefs = {"percentageAllocationRange"})
    public AllocationPercentage getAllocationPercentage() {
        return percentage;
    }

    public void setAllocationPercentage(AllocationPercentage percentage) {
        this.percentage = percentage;
    }

    public double getCalculationSourceUsed() {
        BigDecimal divisor = new BigDecimal(100);
        BigDecimal multiplicand = this.percentage.getValue();
        BigDecimal value = new BigDecimal(this.item.getEstimatedResource());
        BigDecimal result = value.multiply(multiplicand).divide(divisor);
        
        return result.doubleValue();
    }

    @Override
    public String toString() {
        if (source != null && percentage != null){
            return item + ", " + source + ", " + percentage;
        }else {
            return item + ", NA";
        }
    }
}
