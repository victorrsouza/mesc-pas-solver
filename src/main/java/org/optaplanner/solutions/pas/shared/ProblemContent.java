package org.optaplanner.solutions.pas.shared;

import java.util.ArrayList;
import java.util.List;

import org.optaplanner.solutions.pas.domain.AllocationPercentage;
import org.optaplanner.solutions.pas.domain.PlanningItem;
import org.optaplanner.solutions.pas.domain.ResourceAllocation;
import org.optaplanner.solutions.pas.domain.FinancialSource;

public class ProblemContent {
    private List<FinancialSource> sourceList;
    private List<PlanningItem> planningItemList;
    private List<ResourceAllocation> allocationList;
    private List<AllocationPercentage> percentageList;

    public ProblemContent() {
        this.sourceList = new ArrayList<FinancialSource>();
        this.planningItemList = new ArrayList<PlanningItem>();
        this.allocationList = new ArrayList<ResourceAllocation>();
        this.percentageList = new ArrayList<AllocationPercentage>();
    }

    public void addPlanningItem(PlanningItem planningItem) { this.planningItemList.add(planningItem); }

    public void addSource(FinancialSource source){
        this.sourceList.add(source);
    }

    public void addResourceAllocation(ResourceAllocation allocation) { this.allocationList.add(allocation); }

    public void addPercentage(AllocationPercentage percentage) { this.percentageList.add(percentage); }

    public List<ResourceAllocation> getResourceAllocationList() { return allocationList; }

    public void setAllocationList(List<ResourceAllocation> allocationList) { this.allocationList = allocationList; }

    public List<FinancialSource> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<FinancialSource> sourceList) {
        this.sourceList = sourceList;
    }

    public List<PlanningItem> getPlanningItemList() {
        return planningItemList;
    }

    public void setPlanningItemList(List<PlanningItem> planningItemList) {
        this.planningItemList = planningItemList;
    }

    public List<AllocationPercentage> getAllocationPercentageList() { return this.percentageList; }

    public void setAllocationPercentage(List<AllocationPercentage> percentageList) { this.percentageList = percentageList; }
}
