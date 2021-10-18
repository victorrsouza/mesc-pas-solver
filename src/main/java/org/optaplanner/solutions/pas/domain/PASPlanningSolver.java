package org.optaplanner.solutions.pas.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.persistence.xstream.api.score.buildin.hardsoft.HardSoftScoreXStreamConverter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@PlanningSolution
@XStreamAlias("PASPlanningSolver")
public class PASPlanningSolver {

    private List<FinancialSource> sourceList;
    private List<PlanningItem> itemList;
    private List<ResourceAllocation> allocationList;
    private List<AllocationPercentage> percentageList;

    @XStreamConverter(HardSoftScoreXStreamConverter.class)
    private HardSoftScore score;

    public PASPlanningSolver() {}

    public PASPlanningSolver(
            List<FinancialSource> sourceList,
            List<PlanningItem> itemList,
            List<ResourceAllocation> allocationList,
            List<AllocationPercentage> percentageList) {
        this.sourceList = sourceList;
        this.itemList = itemList;
        this.allocationList = allocationList;
        this.percentageList = percentageList;
    }

    public PASPlanningSolver(
            List<FinancialSource> sourceList,
            List<PlanningItem> itemList,
            double percentageScale) {
        this.sourceList = sourceList;
        this.itemList = itemList;
        this.allocationList = this.buildResourceAllocations(this.itemList, this.sourceList);
        this.percentageList = this.buildAllocationPercentages(percentageScale);    
    }

    @PlanningEntityCollectionProperty
    public List<ResourceAllocation> getAllocationList() { return allocationList; }

    public void setAllocationList(List<ResourceAllocation> allocationList) { this.allocationList = allocationList; }

    @ProblemFactCollectionProperty
    public List<FinancialSource> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<FinancialSource> sourceList) { this.sourceList = sourceList; }

    @ProblemFactCollectionProperty
    public List<PlanningItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<PlanningItem> itemList) {
        this.itemList = itemList;
    }

    @ValueRangeProvider(id = "percentageAllocationRange")
    @ProblemFactCollectionProperty
    public List<AllocationPercentage> getPercentageList() { return percentageList; }

    public void setPercentageList(List<AllocationPercentage> percentageList) { this.percentageList = percentageList; }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }


    private List<AllocationPercentage> buildAllocationPercentages(double percentageScale){
        
        List<AllocationPercentage> result = new ArrayList<AllocationPercentage>();
        for (double i = 0; i <= 100; i = i + percentageScale) {
            AllocationPercentage percentage = new AllocationPercentage(new BigDecimal(i));
            result.add(percentage);
        }
        return result;
    }

    private List<ResourceAllocation> buildResourceAllocations(List<PlanningItem> items, List<FinancialSource> sources){
        
        List<ResourceAllocation> result = new ArrayList<ResourceAllocation>();
        int i = 1;

        for (PlanningItem item : items) {            
            for (Integer sourceCode : item.getAcceptableCodes()) {
                FinancialSource source = this.getSourceByCode(sources, sourceCode.toString());
                ResourceAllocation resourceAllocation = new ResourceAllocation(i, source, item);
                result.add(resourceAllocation);
                i++;
            }
        }

        return result;
    }

    private FinancialSource getSourceByCode(List<FinancialSource> sources, String code) {
        for (FinancialSource s : sources) {
            if (Integer.parseInt(code.trim()) == s.getCode()) {
                return s;
            }
        }
        return null;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

}