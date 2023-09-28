package JUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = { PartidaTest.class, PartidaCodemakerTest.class, PartidaCodebreakerTest.class,
		ConfiguracionPerfilTest.class, EstadisticasTest.class, LogroPerfilTest.class, LogroTest.class,
		MaquinaGeneticTest.class, RankingGlobalTest.class, PerfilTest.class })
public class AllTest {

	@Test
	public void testAll() {
		// fail("Not yet implemented");
	}

}
