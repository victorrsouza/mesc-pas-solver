import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.optaplanner.solutions.pas.domain.FinancialSource;
import org.optaplanner.solutions.pas.domain.PASPlanningSolver;
import org.optaplanner.solutions.pas.domain.PlanningItem;
import org.optaplanner.solutions.pas.domain.ResourceAllocation;

public class Regra1Test extends BaseTest {

	@Test
	void RecursoAlocadoNaoDeveUltrapassarLimiteDaFonte() {
		//Given
		FinancialSource source = new FinancialSource(1, 100);
		PlanningItem item = new PlanningItem(1, 200);
		item.addSource(source);

		this.addSource(source);
		this.addItem(item);
 
		//When
		PASPlanningSolver solved = this.buildSolver().solve(this.getProblem());

		//Then
		ResourceAllocation allocation = solved.getAllocationList().get(0);
		double percentageValue = allocation.getAllocationPercentage().getDoubleValue();
		double sourceUsed = allocation.getCalculationSourceUsed();
		
		assertThat(percentageValue).isEqualTo(50);
		assertThat(sourceUsed).isEqualTo(source.getAvailableResource());
	}

	@Test
	void RecursoAlocadoDeveSerIgualAoLimiteDaFonte() {
		//Given
		FinancialSource source = new FinancialSource(1, 100);
		PlanningItem item = new PlanningItem(1, 100);
		item.addSource(source);

		this.addSource(source);
		this.addItem(item);
 
		//When
		PASPlanningSolver solved = this.buildSolver().solve(this.getProblem());

		//Then
		ResourceAllocation allocation = solved.getAllocationList().get(0);
		assertThat(allocation.getAllocationPercentage().getDoubleValue()).isEqualTo(100);
		assertThat(allocation.getCalculationSourceUsed()).isEqualTo(source.getAvailableResource());
	}

	@Test
	void RecursoAlocadoDeveSerInferiorAoLimiteDaFonte() {
		//Given
		FinancialSource source = new FinancialSource(1, 200);
		PlanningItem item = new PlanningItem(1, 100);
		item.addSource(source);

		this.addSource(source);
		this.addItem(item);
	
		//When
		PASPlanningSolver solved = this.buildSolver().solve(this.getProblem());

		//Then
		ResourceAllocation allocation = solved.getAllocationList().get(0);
		assertThat(allocation.getAllocationPercentage().getDoubleValue()).isEqualTo(100);
		assertThat(allocation.getCalculationSourceUsed()).isLessThan(source.getAvailableResource());  
	}

}
