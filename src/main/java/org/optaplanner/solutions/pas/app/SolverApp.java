package org.optaplanner.solutions.pas.app;

import java.math.BigDecimal;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.solutions.pas.domain.*;
import org.optaplanner.solutions.pas.shared.DataReader;
import org.optaplanner.solutions.pas.shared.ProblemContent;

import java.util.*;


public class SolverApp {
    public static void main(String[] args) {        

        // Build the Solver
        System.out.println("Starting Solver\n");
        String xmlPath = "org/optaplanner/solutions/pas/config/solverConfig.xml";
        SolverFactory<PASPlanningSolver> solverFactory = SolverFactory.createFromXmlResource(xmlPath);

        // Create Dataset
        System.out.println("Creating Dataset\n");
        DataReader dataReader = new DataReader();
        ProblemContent dataset = dataReader.readFromJson("teste_pas");

        List<FinancialSource> sources = dataset.getSourceList();
        List<PlanningItem> items = dataset.getPlanningItemList();
        List<ResourceAllocation> allocations = dataset.getResourceAllocationList();
        List<AllocationPercentage> percentages = dataset.getAllocationPercentageList();

        PASPlanningSolver unsolved = new PASPlanningSolver(sources, items, allocations, percentages);

        System.out.println("Build Solver\n");
        Solver<PASPlanningSolver> solver = solverFactory.buildSolver();

        // Solve the problem
        System.out.println("Solving :D\n");
        PASPlanningSolver solved = solver.solve(unsolved);

        // Display the result
        System.out.println("\nSolved PAS with " + sources.size() + " Sources and " + items.size() + " Planning Items:\n");
        
        for (ResourceAllocation allocation : solved.getAllocationList()) {
            if (allocation.getAllocationPercentage().getValue().compareTo(BigDecimal.ZERO) > 0) {
                System.out.println(allocation);
            }
        }
    }
}
