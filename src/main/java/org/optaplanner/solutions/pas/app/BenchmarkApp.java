package org.optaplanner.solutions.pas.app;

import org.optaplanner.benchmark.api.PlannerBenchmark;
import org.optaplanner.benchmark.api.PlannerBenchmarkFactory;
import org.optaplanner.solutions.pas.domain.AllocationPercentage;
import org.optaplanner.solutions.pas.domain.PASPlanningSolver;
import org.optaplanner.solutions.pas.domain.PlanningItem;
import org.optaplanner.solutions.pas.domain.ResourceAllocation;
import org.optaplanner.solutions.pas.domain.FinancialSource;
import org.optaplanner.solutions.pas.shared.DataReader;
import org.optaplanner.solutions.pas.shared.ProblemContent;

import java.util.*;


public class BenchmarkApp {
    public static void main(String[] args) {

        // Build the Solver
        System.out.println("Starting Solver\n");
        PlannerBenchmarkFactory benchmarkFactory = PlannerBenchmarkFactory.createFromXmlResource(
                "org/optaplanner/solutions/pas/config/benchmarkConfig.xml");

        // Create Dataset
        System.out.println("Creating Dataset\n");
        DataReader dataGenerator = new DataReader();
        ProblemContent dataset = dataGenerator.readFromTxt("base_pas");

        List<FinancialSource> sources = dataset.getSourceList();
        List<PlanningItem> items = dataset.getPlanningItemList();
        List<ResourceAllocation> allocations = dataset.getResourceAllocationList();
        List<AllocationPercentage> percentages = dataset.getAllocationPercentageList();

        PASPlanningSolver unsolved = new PASPlanningSolver(sources, items, allocations, percentages);

        List<PASPlanningSolver> binpackingDataset = new ArrayList<PASPlanningSolver>();
        binpackingDataset.add(unsolved);

        // Build the Solver
        PlannerBenchmark plannerBenchmark = benchmarkFactory.buildPlannerBenchmark(binpackingDataset);
        plannerBenchmark.benchmarkAndShowReportInBrowser();
    }
}