import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.optaplanner.solutions.pas.domain.FinancialSource;
import org.optaplanner.solutions.pas.domain.PASPlanningSolver;
import org.optaplanner.solutions.pas.domain.PlanningItem;

public class Regra2Test extends BaseTest {

	@Test
	void AlocacaoNaoDeveExtrapolarCemPorCentoComCombinacao2x1() {
		//Given
		FinancialSource source1 = new FinancialSource(1, 200);
		FinancialSource source2 = new FinancialSource(2, 200);
		PlanningItem item = new PlanningItem(1, 200);
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

		double sourceUsed = solved.getAllocationList().stream()
			.map(x -> x.getCalculationSourceUsed())
			.reduce(0.0, Double::sum);

		assertThat(percentageValue.doubleValue()).isEqualTo(100);
		assertThat(sourceUsed).isEqualTo(item.getEstimatedResource());
	}

	@Test
	void AlocacaoNaoDeveExtrapolarCemPorCentoComCombinacao2x2() {
		//Given
		FinancialSource source1 = new FinancialSource(1, 500);
		FinancialSource source2 = new FinancialSource(2, 500);
		PlanningItem item1 = new PlanningItem(1, 200);
		PlanningItem item2 = new PlanningItem(2, 200);

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

		double sourceUsed1 = solved.getAllocationList().stream()
			.filter(x -> x.getItem().equals(item1))
			.map(x -> x.getCalculationSourceUsed())
			.reduce(0.0, Double::sum);

		assertThat(percentageValue1.doubleValue()).isEqualTo(100);
		assertThat(sourceUsed1).isEqualTo(item1.getEstimatedResource());

		BigDecimal percentageValue2 = solved.getAllocationList().stream()
			.filter(x -> x.getItem().equals(item2))
			.map(x -> x.getAllocationPercentage().getValue())
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		double sourceUsed2 = solved.getAllocationList().stream()
			.filter(x -> x.getItem().equals(item2))
			.map(x -> x.getCalculationSourceUsed())
			.reduce(0.0, Double::sum);

		assertThat(percentageValue2.doubleValue()).isEqualTo(100);
		assertThat(sourceUsed2).isEqualTo(item2.getEstimatedResource());
	}

	@Test
	void AlocacaoNaoDeveExtrapolarCemPorCentoComCombinacao1x2() {
		//Given
		FinancialSource source = new FinancialSource(1, 400);
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

		double sourceUsed1 = solved.getAllocationList().stream()
			.filter(x -> x.getItem().equals(item1))
			.map(x -> x.getCalculationSourceUsed())
			.reduce(0.0, Double::sum);

		assertThat(percentageValue1.doubleValue()).isEqualTo(100);
		assertThat(sourceUsed1).isEqualTo(item1.getEstimatedResource());

		BigDecimal percentageValue2 = solved.getAllocationList().stream()
			.filter(x -> x.getItem().equals(item2))
			.map(x -> x.getAllocationPercentage().getValue())
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		double sourceUsed2 = solved.getAllocationList().stream()
			.filter(x -> x.getItem().equals(item2))
			.map(x -> x.getCalculationSourceUsed())
			.reduce(0.0, Double::sum);

		assertThat(percentageValue2.doubleValue()).isEqualTo(100);
		assertThat(sourceUsed2).isEqualTo(item2.getEstimatedResource());
	}
	
}