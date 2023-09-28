package JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Dominio.Partida;
import Dominio.PartidaCodebreaker;
import Dominio.PartidaCodemaker;
import Excepciones.atributoConfiguracionInvalidoException;
import Excepciones.coloresCodigoInvalidosException;
import Excepciones.configuracionConValoresNullException;
import Excepciones.longitudCodigoInvalidaException;

public class PartidaTest {

	private List<Object> config;
	private Partida partida;

	@Before
	public void setUp() throws Exception {
		config = List.of(4, 0, 0, 4, 4, true);
		partida = new PartidaCodemaker(1, config, null);
	}

	@Test
	public void testCreacionPartidaCorrecta() throws Exception {
		config = List.of(4, 0, 0, 4, 4, true);
		partida = new PartidaCodemaker(1, config, null);
		partida.informaPuntuacion(0);
		partida.informaPerfil(null);
		assertTrue(!partida.partidaAcabada() && partida.obtenId() == 1 && partida.obtenNivelAyuda() == 0
				&& partida.obtenDificultad() == 0 && partida.obtenPistasDisponibles() == 1
				&& partida.obtenPuntuacion() == 0 && partida.obtenPerfil() == null && partida.esCodemaker());
	}

	@Test
	public void testGeneraResultadoColoreDiferentes() {
		List<Integer> guess = List.of(1, 2, 3, 0);
		List<Integer> resultado = List.of(2, 2, 2, 2);
		List<Integer> codigoSecreto = List.of(1, 2, 3, 0);
		partida.informaCodigoSecreto(codigoSecreto);

		assertEquals(resultado.toString(), partida.generaResultado(guess).toString());
	}

	@Test
	public void testGeneraResultadoDosColoresRepetidos() {
		List<Integer> guess = List.of(0, 0, 1, 1);
		List<Integer> resultado = List.of(1, 1, 1, 1);
		List<Integer> codigoSecreto = List.of(1, 1, 0, 0);
		partida.informaCodigoSecreto(codigoSecreto);

		assertEquals(resultado.toString(), partida.generaResultado(guess).toString());
	}

	@Test
	public void testGeneraResultadoUnColorRepetido() {
		List<Integer> guess = List.of(1, 2, 2, 0);
		List<Integer> resultado = List.of(0, 0, 0, 0);
		List<Integer> codigoSecreto = List.of(3, 3, 3, 3);
		partida.informaCodigoSecreto(codigoSecreto);

		assertEquals(resultado.toString(), partida.generaResultado(guess).toString());
	}

	@Test(expected = coloresCodigoInvalidosException.class)
	public void testInformaAdivinanzaConColoresInvalidosNegativos() throws Exception {
		config = List.of(4, 0, 0, 4, 4, false);
		partida = new PartidaCodebreaker(1, config, null);
		partida.ejecutarTurno(List.of(-1, 0, 0, 0));
	}

	@Test(expected = coloresCodigoInvalidosException.class)
	public void testInformaAdivinanzaConColoresInvalidosSuperioresAlMaximo() throws Exception {
		config = List.of(4, 0, 0, 4, 4, false);
		partida = new PartidaCodebreaker(1, config, null);
		partida.ejecutarTurno(List.of(5, 0, 0, 0));
	}

	@Test(expected = longitudCodigoInvalidaException.class)
	public void testInformaAdivinanzaConLongitudCodigoInvalida() throws Exception {
		config = List.of(4, 0, 0, 4, 4, false);
		partida = new PartidaCodebreaker(1, config, null);
		partida.ejecutarTurno(List.of(3, 0, 0, 0, 0));
	}

	@Test(expected = coloresCodigoInvalidosException.class)
	public void testInformaResultadoConColoresInvalidosNegativos() throws Exception {
		config = List.of(4, 0, 0, 4, 4, true);
		partida = new PartidaCodemaker(1, config, null);
		partida.ejecutarTurno(List.of(3, 0, 0, 0));
		partida.ejecutarTurno(null);
		List<Integer> resultado = partida.obtenPista();
		resultado.set(0, -1);
		partida.informaResultado(resultado);
	}

	@Test(expected = coloresCodigoInvalidosException.class)
	public void testInformaResultadoConColoresInvalidosSuperioresAlMaximo() throws Exception {
		config = List.of(4, 0, 0, 4, 4, true);
		partida = new PartidaCodemaker(1, config, null);
		partida.ejecutarTurno(List.of(3, 0, 0, 0));
		partida.ejecutarTurno(null);
		List<Integer> resultado = partida.obtenPista();
		resultado.set(0, 3);
		partida.informaResultado(resultado);
	}

	@Test(expected = longitudCodigoInvalidaException.class)
	public void testInformaResultadoConLongitudCodigoInvalida() throws Exception {
		config = List.of(4, 0, 0, 4, 4, true);
		partida = new PartidaCodemaker(1, config, null);
		partida.ejecutarTurno(List.of(3, 0, 0, 0));
		partida.ejecutarTurno(null);
		List<Integer> resultado = partida.obtenPista();
		resultado.add(1);
		partida.informaResultado(resultado);
	}

	@Test(expected = NullPointerException.class)
	public void testPartidaConfiguracionConValoresNull() throws Exception {
		config = List.of(4, null, 0, 10, 5, false);
		partida = new PartidaCodemaker(1, config, null);
	}

	@Test(expected = configuracionConValoresNullException.class)
	public void testPartidaConfiguracionNull() throws Exception {
		config = null;
		partida = new PartidaCodemaker(1, config, null);
	}

	@Test(expected = atributoConfiguracionInvalidoException.class)
	public void testPartidaDificultadInvalido() throws Exception {
		config = List.of(4, 10, 0, 5, 5, false);
		partida = new PartidaCodemaker(1, config, null);
	}

	@Test(expected = atributoConfiguracionInvalidoException.class)
	public void testPartidaLongitudCodigoInvalido() throws Exception {
		config = List.of(4, 0, 0, 5, 10, false);
		partida = new PartidaCodemaker(1, config, null);
	}

	@Test(expected = atributoConfiguracionInvalidoException.class)
	public void testPartidaNivelAyudaInvalido() throws Exception {
		config = List.of(10, 2, 3, 5, 5, false);
		partida = new PartidaCodemaker(1, config, null);
	}

	@Test(expected = atributoConfiguracionInvalidoException.class)
	public void testPartidaNumeroColoresInvalido() throws Exception {
		config = List.of(4, 0, 0, 10, 5, false);
		partida = new PartidaCodemaker(1, config, null);
	}

	@Test(expected = atributoConfiguracionInvalidoException.class)
	public void testPartidaNumeroTurnosInvalido() throws Exception {
		config = List.of(11, 2, 0, 5, 5, false);
		partida = new PartidaCodemaker(1, config, null);
	}
}
