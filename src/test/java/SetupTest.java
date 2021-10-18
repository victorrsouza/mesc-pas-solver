import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.optaplanner.solutions.pas.domain.FinancialSource;
import org.optaplanner.solutions.pas.domain.PASPlanningSolver;
import org.optaplanner.solutions.pas.domain.PlanningItem;

public class SetupTest extends BaseTest {

	@Test
	void AlocacaoNaoDeveExistirSeNaoHouverItem() {
		//Given		
		FinancialSource source = new FinancialSource(1, 100);
		this.addSource(source);
	
		//When
		PASPlanningSolver solved = this.buildSolver().solve(this.getProblem());

		//Then		
		assertThat(solved.getAllocationList()).isEmpty();
	}

	@Test
	void AlocacaoNaoDeveExistirSeNaoHouverFonte() {
		//Given		
		PlanningItem item = new PlanningItem(1, 100);		
		this.addItem(item);
	
		//When
		PASPlanningSolver solved = this.buildSolver().solve(this.getProblem());

		//Then		
		assertThat(solved.getAllocationList()).isEmpty();
	}

	@Test
	void AlocacaoNaoDeveExistirSeNaoHouverAssociacaoEntreFonteEItem() {
		//Given
		FinancialSource source = new FinancialSource(1, 100);
		PlanningItem item = new PlanningItem(1, 100);

		this.addSource(source);
		this.addItem(item);
	
		//When
		PASPlanningSolver solved = this.buildSolver().solve(this.getProblem());

		//Then		
		assertThat(solved.getAllocationList()).isEmpty();
	}
}
