package JUnit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Dominio.PartidaCodemaker;
import Excepciones.pistasAgotadasException;
import Excepciones.resultadoInformadoIncorrectoException;

public class PartidaCodemakerTest {

	private List<Integer> codigo;
	private List<Object> config;
	private PartidaCodemaker partida;

	@Before
	public void setUp() throws Exception {
		int longitudCodigo = 2;
		int numeroTurnos = 5;
		int dificultad = 1;
		int nivelAyuda = 0;
		int numeroColores = 3;

		config = List.of(numeroTurnos, dificultad, nivelAyuda, numeroColores, longitudCodigo, false);

		partida = new PartidaCodemaker(1, config, null);

		codigo = generarCodigoRandom();
	}

	@Test
	public void testCalculaPuntuacion() throws Exception {
		int longitudCodigo = 4;
		int numeroTurnos = 4;
		int dificultad = 1;
		int nivelAyuda = 2;
		int numeroColores = 4;

		config = List.of(numeroTurnos, dificultad, nivelAyuda, numeroColores, longitudCodigo, true);

		partida = new PartidaCodemaker(1, config, null);

		codigo = generarCodigoRandom();

		partida.ejecutarTurno(codigo);
		partida.ejecutarTurno(null);
		partida.informaResultado(partida.obtenPista());
		partida.ejecutarTurno(null);

		double puntuacionEsperada = ((4 * 4 * 1000) / 4);
		puntuacionEsperada = partida.obtenGanada() ? puntuacionEsperada * 2
				: partida.partidaAcabada() ? 0 : puntuacionEsperada * 1;
		assertTrue(puntuacionEsperada == partida.calculaPuntuacion());

	}

	@Test
	public void testObtenPista() throws Exception {
		partida.ejecutarTurno(codigo);
		partida.ejecutarTurno(null);
		assertArrayEquals(partida.obtenPista().toArray(), partida.generaResultado(partida.obtenAdivinanza()).toArray());
	}

	@Test(expected = pistasAgotadasException.class)
	public void testObtenPistaConPistasAgotadas() throws Exception {
		partida.ejecutarTurno(codigo);
		partida.ejecutarTurno(null);
		partida.obtenPista();
		partida.obtenPista();
	}

	@Test
	public void testPartidaCompleta() throws Exception {
		int estadoPartida = 0;
		List<List<Integer>> resultados = new ArrayList<>();

		List<List<Integer>> partidaResultados = new ArrayList<>();

		// EL CODIGO ES USADO COMO CODIGO SECRETO EN EL PRIMER TURNO
		estadoPartida = partida.ejecutarTurno(codigo);
		while (estadoPartida == 0) {
			// A PARTIR DEL SEGUNDO TURNO NO HACE FALTA INSERTAR EL CODIGO COMO PARAMETRO
			// PORQUE ES CODEMAKER
			estadoPartida = partida.ejecutarTurno(null);

			List<Integer> adivinanzaMaquina = partida.obtenAdivinanza();
			List<Integer> resultadoGeneradoAutomaticamente = partida.generaResultado(adivinanzaMaquina);
			partida.informaResultado(resultadoGeneradoAutomaticamente);

			estadoPartida = partida.ejecutarTurno(null);

			resultados.add(resultadoGeneradoAutomaticamente);

			partidaResultados.add(resultadoGeneradoAutomaticamente);
		}

		assertArrayEquals(resultados.toArray(), partidaResultados.toArray());

	}

	@Test
	public void testPartidaCompletaDificil() throws Exception {
		int longitudCodigo = 2;
		int numeroTurnos = 5;
		int dificultad = 2;
		int nivelAyuda = 0;
		int numeroColores = 3;

		config = List.of(numeroTurnos, dificultad, nivelAyuda, numeroColores, longitudCodigo, true);

		partida = new PartidaCodemaker(1, config, null);

		codigo = generarCodigoRandom();
		int estadoPartida = 0;
		List<List<Integer>> resultados = new ArrayList<>();

		List<List<Integer>> partidaResultados = new ArrayList<>();

		// EL CODIGO ES USADO COMO CODIGO SECRETO EN EL PRIMER TURNO
		estadoPartida = partida.ejecutarTurno(codigo);
		while (estadoPartida == 0) {
			// A PARTIR DEL SEGUNDO TURNO NO HACE FALTA INSERTAR EL CODIGO COMO PARAMETRO
			// PORQUE ES CODEMAKER
			estadoPartida = partida.ejecutarTurno(null);

			List<Integer> adivinanzaMaquina = partida.obtenAdivinanza();
			List<Integer> resultadoGeneradoAutomaticamente = partida.generaResultado(adivinanzaMaquina);
			partida.informaResultado(resultadoGeneradoAutomaticamente);

			estadoPartida = partida.ejecutarTurno(null);

			resultados.add(resultadoGeneradoAutomaticamente);

			partidaResultados.add(resultadoGeneradoAutomaticamente);
		}

		assertArrayEquals(resultados.toArray(), partidaResultados.toArray());

	}

	@Test(expected = Exception.class)
	public void testPrimerTurnoSinCodigoSecreto() throws Exception {
		// EL CODIGO ES USADO COMO CODIGO SECRETO EN EL PRIMER TURNO
		partida.ejecutarTurno(null);
	}

	@Test(expected = resultadoInformadoIncorrectoException.class)
	public void testResultadoInformadoIncorrecto() throws Exception {
		partida.ejecutarTurno(codigo);
		partida.ejecutarTurno(null);
		List<Integer> resultado = partida.generaResultado(partida.obtenAdivinanza());
		resultado.set(0, resultado.get(0) == 1 ? 0 : 2 - resultado.get(0));
		partida.informaResultado(resultado);
	}

	@Test
	public void testTurnoInicial() throws Exception {
		partida.ejecutarTurno(codigo);

		partida.ejecutarTurno(null);
		List<Integer> adivinanzaMaquina = partida.obtenAdivinanza();

		List<Integer> resultado = partida.generaResultado(adivinanzaMaquina);
		partida.informaResultado(resultado);
		partida.ejecutarTurno(null);

		assertTrue(Arrays.equals(partida.obtenCodigoSecreto().toArray(), codigo.toArray()) && adivinanzaMaquina != null
				&& Arrays.equals(resultado.toArray(), partida.generaResultado(adivinanzaMaquina).toArray()));
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
