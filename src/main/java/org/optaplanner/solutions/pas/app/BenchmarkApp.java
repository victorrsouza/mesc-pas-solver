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

        List<String> datasets = Arrays.asList("dataset2");
        List<PASPlanningSolver> problems = new ArrayList<PASPlanningSolver>();

        for (String file : datasets) {
            // Create Dataset
            System.out.println("Creating Dataset '" + file + "' \n");
            DataReader dataReader = new DataReader();
            ProblemContent dataset = dataReader.readFromJson(
                "org/optaplanner/solutions/pas/data/" + file + ".json"
            );
            
            // Get Problem Entities
            List<FinancialSource> sources = dataset.getSourceList();
            List<PlanningItem> items = dataset.getPlanningItemList();
            List<ResourceAllocation> allocations = dataset.getResourceAllocationList();
            List<AllocationPercentage> percentages = dataset.getAllocationPercentageList();

            // Create problem
            PASPlanningSolver problem =
                new PASPlanningSolver(sources, items, allocations, percentages);
            
            problems.add(problem);
        }

        // Build the Solver
        System.out.println("Starting Solver\n");
        PlannerBenchmarkFactory benchmarkFactory = 
            PlannerBenchmarkFactory.createFromXmlResource(
                "org/optaplanner/solutions/pas/config/benchmarkConfig.xml"
            );

        // Build the Benchmark
        PlannerBenchmark plannerBenchmark = benchmarkFactory.buildPlannerBenchmark(problems);
        plannerBenchmark.benchmarkAndShowReportInBrowser();
    }
}