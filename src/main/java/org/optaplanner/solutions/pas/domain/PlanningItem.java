package org.optaplanner.solutions.pas.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("PlanningItem")
public class PlanningItem {

    private int id;
    private double estimatedResource;
    private List<Integer> acceptableCodes;

    public PlanningItem(){}

    public PlanningItem(int id, double estimatedResource, List<Integer> acceptableCodes) {
        this.id = id;
        this.estimatedResource = estimatedResource;
        this.acceptableCodes = acceptableCodes;
    }

    public PlanningItem(int id, double estimatedResource) {
        this.id = id;
        this.estimatedResource = estimatedResource;
        this.acceptableCodes = new ArrayList<Integer>();
    }

    public int getId() {
        return this.id;
    }

    public List<Integer> getAcceptableCodes() {
        return acceptableCodes;
    }

    public void setAcceptableCodes(List<Integer> acceptableCodes) {
        this.acceptableCodes = acceptableCodes;
    }

    public double getEstimatedResource() {
        return estimatedResource;
    }

    public void setEstimatedResource(double estimatedResource) {
        this.estimatedResource = estimatedResource;
    }

    public void addSource(FinancialSource source) {
        this.acceptableCodes.add(source.getCode());
    }

    @Override
    public String toString() {
        return "Meta: " + getId() + ", Valor: " + getEstimatedResource();
    }
}