import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.solutions.pas.domain.FinancialSource;
import org.optaplanner.solutions.pas.domain.PASPlanningSolver;
import org.optaplanner.solutions.pas.domain.PlanningItem;

public class BaseTest {

	private List<FinancialSource> sources;
	private List<PlanningItem> items;
	private double percentageScale;

	private SolverFactory<PASPlanningSolver> solverFactory;
	
	public BaseTest() {

		this.sources = new ArrayList<FinancialSource>();
		this.items = new ArrayList<PlanningItem>();
		this.percentageScale = 1L;

		String xmlPath = "org/optaplanner/solutions/pas/config/solverConfig.xml";
		this.solverFactory = SolverFactory.createFromXmlResource(xmlPath);
	}

	protected void addItem(PlanningItem item) {
		this.items.add(item);
	}

	protected void addSource(FinancialSource source) {
		this.sources.add(source);
	}

	protected PASPlanningSolver getProblem() {
		return new PASPlanningSolver(this.sources, this.items, this.percentageScale);
	}

	protected Solver<PASPlanningSolver> buildSolver() {
		return this.solverFactory.buildSolver();
	}
}
