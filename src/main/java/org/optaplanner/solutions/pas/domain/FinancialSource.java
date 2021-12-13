package org.optaplanner.solutions.pas.domain;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ResourceSource")
public class FinancialSource {

    private int code;
    private double availableResource;

    public FinancialSource(){}

    public FinancialSource(int code, BigDecimal availableResource){
        this.code = code;
        this.availableResource = availableResource.doubleValue();
    }

    public FinancialSource(int code, double availableResource){
        this.code = code;
        this.availableResource = availableResource;
    }

    public int getCode() {
        return this.code;
    }

    public double getAvailableResource() {
        return availableResource;
    }

    public void setAvailableResource(double availableResource) {
        this.availableResource = availableResource;
    }

    @Override
    public String toString() {
        return "Fonte: " + getCode();
    }    
}
