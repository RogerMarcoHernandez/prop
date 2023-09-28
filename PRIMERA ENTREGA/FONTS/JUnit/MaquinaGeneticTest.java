package JUnit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Dominio.CalculoFitness;
import Dominio.ConfiguracionGenetic;
import Dominio.Maquina;
import Dominio.MaquinaGenetic;

public class MaquinaGeneticTest {
	private int longitudCodigo;
	private int maxSteps;
	private int numeroColores;
	Maquina maquina;

	@Before
	public void setUp() {
		maxSteps = 1000;
		longitudCodigo = 10;
		numeroColores = 8;
		maquina = new MaquinaGenetic(numeroColores, maxSteps, longitudCodigo);
		CalculoFitness.informaCodigoSecretoGenetic(generarCodigoRandom());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGeneticAlgorithmCodigoSecretoInvalidoPorColoresMayoresAlMaximo() throws Exception {
		maxSteps = 1000;
		ConfiguracionGenetic.informaNumeroCodigosPoblacion(200);

		numeroColores = 4;
		longitudCodigo = 4;
		maquina = new MaquinaGenetic(numeroColores, maxSteps, longitudCodigo);

		List<Integer> codigoSecreto = List.of(0, 2, 1, 5);
		maquina.solve(codigoSecreto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGeneticAlgorithmCodigoSecretoInvalidoPorColoresNegativos() throws Exception {
		maxSteps = 1000;
		ConfiguracionGenetic.informaNumeroCodigosPoblacion(200);

		numeroColores = 4;
		longitudCodigo = 4;
		maquina = new MaquinaGenetic(numeroColores, maxSteps, longitudCodigo);

		List<Integer> codigoSecreto = List.of(0, 2, 1, -1);
		maquina.solve(codigoSecreto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGeneticAlgorithmCodigoSecretoInvalidoPorLongitud() throws Exception {
		maxSteps = 1000;
		ConfiguracionGenetic.informaNumeroCodigosPoblacion(200);

		numeroColores = 4;
		longitudCodigo = 4;
		maquina = new MaquinaGenetic(numeroColores, maxSteps, longitudCodigo);

		List<Integer> codigoSecreto = List.of(0, 2, 1, 3, 5);
		maquina.solve(codigoSecreto);

	}

	@Test
	public void testGeneticAlgorithmSolucionaCodigoSecreto() throws Exception {
		maxSteps = 1000;
		ConfiguracionGenetic.informaNumeroCodigosPoblacion(200);

		numeroColores = 5;
		longitudCodigo = 5;
		maquina = new MaquinaGenetic(numeroColores, maxSteps, longitudCodigo);

		List<Integer> codigoSecreto = generarCodigoRandom();
		CalculoFitness.informaCodigoSecretoGenetic(codigoSecreto);

		List<List<Integer>> generationalCodes;
		generationalCodes = maquina.solve(codigoSecreto);

		List<Integer> adivinanzaFinal = generationalCodes.get(generationalCodes.size() - 1);
		assertEquals(codigoSecreto.toString(), adivinanzaFinal.toString());
	}

	//
	// METODOS PRIVADOS
	//

	private List<Integer> generarCodigoRandom() {
		List<Integer> codigoRandom = new ArrayList<>();

		for (int i = 0; i < longitudCodigo; i++)
			codigoRandom.add((int) (Math.random() * (numeroColores)));

		return codigoRandom;
	}
}
