import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.optaplanner.solutions.pas.domain.FinancialSource;
import org.optaplanner.solutions.pas.domain.PASPlanningSolver;
import org.optaplanner.solutions.pas.domain.PlanningItem;

public class Regra3Test extends BaseTest {

	@Test
	void AlocacaoPodeSerMenorQueCemPorCentoComCombinacao2x1() {
		//Given
		FinancialSource source1 = new FinancialSource(1, 10);
		FinancialSource source2 = new FinancialSource(2, 20);
		PlanningItem item = new PlanningItem(1, 100);
		item.addSource(source1);
		item.addSource(source2);

		this.addSource(source1);
		this.addSource(source2);
		this.addItem(item);
	
		//When
		PASPlanningSolver solved = this.buildSolver().solve(this.getProblem());

		//Then
		BigDecimal percentageValue = solved.getAllocationList().stream()
			.map(x -> x.getAllocationPercentage().getValue())
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		Double sourceUsed = solved.getAllocationList().stream()
			.map(x -> x.getCalculationSourceUsed())
			.reduce(0.0, Double::sum);

		assertThat(percentageValue.doubleValue()).isEqualTo(30);
		assertThat(sourceUsed).isEqualTo(30);
	}

	@Test
	void AlocacaoPodeSerMenorQueCemPorCentoComCombinacao2x2() {
		//Given
		FinancialSource source1 = new FinancialSource(1, 10);
		FinancialSource source2 = new FinancialSource(2, 20);
		PlanningItem item1 = new PlanningItem(1, 100);
		PlanningItem item2 = new PlanningItem(2, 100);

		item1.addSource(source1);
		item1.addSource(source2);
		item2.addSource(source1);
		item2.addSource(source2);

		this.addSource(source1);
		this.addSource(source2);
		this.addItem(item1);
		this.addItem(item2);
	
		//When
		PASPlanningSolver solved = this.buildSolver().solve(this.getProblem());

		//Then
		BigDecimal percentageValue1 = solved.getAllocationList().stream()
			.filter(x -> x.getItem().equals(item1))
			.map(x -> x.getAllocationPercentage().getValue())
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal percentageValue2 = solved.getAllocationList().stream()
			.filter(x -> x.getItem().equals(item2))
			.map(x -> x.getAllocationPercentage().getValue())
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		Double sourceUsed1 = solved.getAllocationList().stream()
			.filter(x -> x.getSource().equals(source1))
			.map(x -> x.getCalculationSourceUsed())
			.reduce(0.0, Double::sum);

		Double sourceUsed2 = solved.getAllocationList().stream()
			.filter(x -> x.getSource().equals(source2))
			.map(x -> x.getCalculationSourceUsed())
			.reduce(0.0, Double::sum);

		assertThat(percentageValue1.doubleValue()).isLessThan(100);
		assertThat(percentageValue2.doubleValue()).isLessThan(100);
		assertThat(sourceUsed1).isEqualTo(source1.getAvailableResource());
		assertThat(sourceUsed2).isEqualTo(source2.getAvailableResource());
	}

	@Test
	void AlocacaoPodeSerMenorQueCemPorCentoComCombinacao1x2() {
		//Given
		FinancialSource source = new FinancialSource(1, 10);
		PlanningItem item1 = new PlanningItem(1, 100);
		PlanningItem item2 = new PlanningItem(2, 100);
		item1.addSource(source);
		item2.addSource(source);

		this.addSource(source);
		this.addItem(item1);
		this.addItem(item2);
	
		//When
		PASPlanningSolver solved = this.buildSolver().solve(this.getProblem());

		//Then
		BigDecimal percentageValue1 = solved.getAllocationList().stream()
			.filter(x -> x.getItem().equals(item1))
			.map(x -> x.getAllocationPercentage().getValue())
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal percentageValue2 = solved.getAllocationList().stream()
			.filter(x -> x.getItem().equals(item2))
			.map(x -> x.getAllocationPercentage().getValue())
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		Double sourceUsed = solved.getAllocationList().stream()
			.filter(x -> x.getSource().equals(source))
			.map(x -> x.getCalculationSourceUsed())
			.reduce(0.0, Double::sum);

		assertThat(percentageValue1.doubleValue()).isLessThan(100);
		assertThat(percentageValue2.doubleValue()).isLessThan(100);
		assertThat(sourceUsed).isEqualTo(source.getAvailableResource());
	}
	
}
