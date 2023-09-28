package JUnit;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Dominio.PartidaCodebreaker;
import Excepciones.codigoTotalmenteReveladoException;
import Excepciones.pistasAgotadasException;
import Excepciones.turnoSinAdivinanzaException;

public class PartidaCodebreakerTest {

	private List<Integer> codigo;
	private List<Object> config;
	private PartidaCodebreaker partida;

	@Before
	public void setUp() throws Exception {
		int longitudCodigo = 4;
		int numeroTurnos = 4;
		int dificultad = 1;
		int nivelAyuda = 2;
		int numeroColores = 4;

		config = List.of(numeroTurnos, dificultad, nivelAyuda, numeroColores, longitudCodigo, false);

		partida = new PartidaCodebreaker(1, config, null);

		codigo = generarCodigoRandom();
	}

	@Test
	public void testCalculaPuntuacion() throws Exception {
		int longitudCodigo = 4;
		int numeroTurnos = 4;
		int dificultad = 1;
		int nivelAyuda = 2;
		int numeroColores = 4;

		config = List.of(numeroTurnos, dificultad, nivelAyuda, numeroColores, longitudCodigo, false);

		partida = new PartidaCodebreaker(1, config, null);

		codigo = generarCodigoRandom();

		partida.ejecutarTurno(codigo);

		double puntuacionEsperada = ((4 * 4 * 1000) / 4);
		puntuacionEsperada = partida.obtenGanada() ? puntuacionEsperada * 4
				: partida.partidaAcabada() ? 0 : puntuacionEsperada * 3;
		assertTrue(puntuacionEsperada == partida.calculaPuntuacion());

	}

	@Test
	public void testObtenPista() throws Exception {
		partida.ejecutarTurno(codigo);
		List<Integer> pista = partida.obtenPista();
		int pos = pista.get(0);
		int valor = pista.get(1);
		assertSame(valor, partida.obtenCodigoSecreto().get(pos));
	}

	@Test(expected = codigoTotalmenteReveladoException.class)
	public void testObtenPistaCodigoTotalmenteRevelado() throws Exception {
		int longitudCodigo = 1;
		int numeroTurnos = (int) (Math.random() * 9) + 1;
		int dificultad = (int) (Math.random() * 2);
		int nivelAyuda = 2;
		int numeroColores = (int) (Math.random() * 3) + 2;

		config = List.of(numeroTurnos, dificultad, nivelAyuda, numeroColores, longitudCodigo, false);

		partida = new PartidaCodebreaker(1, config, null);

		codigo = generarCodigoRandom();
		partida.ejecutarTurno(codigo);
		partida.obtenPista();
		partida.obtenPista();
	}

	@Test(expected = pistasAgotadasException.class)
	public void testObtenPistaConPistasAgotadas() throws Exception {
		int longitudCodigo = 4;
		int numeroTurnos = (int) (Math.random() * 9) + 1;
		int dificultad = (int) (Math.random() * 2);
		int nivelAyuda = (int) (Math.random() * 2);
		int numeroColores = (int) (Math.random() * 3) + 2;

		config = List.of(numeroTurnos, dificultad, nivelAyuda, numeroColores, longitudCodigo, false);

		partida = new PartidaCodebreaker(1, config, null);

		codigo = generarCodigoRandom();
		partida.ejecutarTurno(codigo);
		partida.obtenPista();
		partida.obtenPista();
		partida.obtenPista();
		partida.obtenPista();
	}

	@Test
	public void testPartidaCompleta() throws Exception {
		int estadoPartida = 0;
		List<List<Integer>> guesses = new ArrayList<>();
		List<List<Integer>> resultados = new ArrayList<>();

		List<List<Integer>> partidaGuesses = new ArrayList<>();
		List<List<Integer>> partidaResultados = new ArrayList<>();
		while (estadoPartida == 0) {
			estadoPartida = partida.ejecutarTurno(codigo);

			guesses.add(codigo);
			resultados.add(partida.generaResultado(codigo));

			partidaGuesses.add(partida.obtenAdivinanzaAnterior());
			partidaResultados.add(partida.obtenResultadoAnterior());

			codigo = generarCodigoRandom();
		}

		assertTrue(Arrays.equals(guesses.toArray(), partidaGuesses.toArray())
				&& Arrays.equals(resultados.toArray(), partidaResultados.toArray()));

	}

	@Test
	public void testTurno() throws Exception {
		partida.ejecutarTurno(codigo);

		assertTrue(Arrays.equals(partida.obtenAdivinanzaAnterior().toArray(), codigo.toArray()) && Arrays
				.equals(partida.obtenResultadoAnterior().toArray(), partida.generaResultado(codigo).toArray()));
	}

	@Test(expected = turnoSinAdivinanzaException.class)
	public void testTurnoSinInformarAdivinanza() throws Exception {
		partida.ejecutarTurno(null);
	}

	//
	// METODOS PRIVADOS
	//

	private List<Integer> generarCodigoRandom() {
		List<Integer> codigoRandom = new ArrayList<>();

		int longitudCodigo = (int) config.get(4);
		int numeroColores = (int) config.get(3);

		for (int i = 0; i < longitudCodigo; i++)
			codigoRandom.add((int) (Math.random() * (numeroColores)));

		return codigoRandom;
	}

}
