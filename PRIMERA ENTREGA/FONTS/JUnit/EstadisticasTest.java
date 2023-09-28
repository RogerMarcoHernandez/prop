package JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Dominio.Estadisticas;

public class EstadisticasTest {

	Estadisticas estadistica;

	@Before
	public void InformaUp() throws Exception {
		estadistica = new Estadisticas();
	}

	@Test
	public void testEstadisticas() {
		Estadisticas e = new Estadisticas();
		assertEquals("Partidas ganadas a 0", 0, e.obtenPartidasGanadas());
		assertEquals("Partidas perdidas a 0", 0, e.obtenPartidasPerdidas());
		assertTrue(0.0 == e.obtenMejorPuntuacion());
		assertEquals("Mejor partida no existe", -1, e.obtenIdMejorPartida());
	}

	@Test
	public void testObtenPartidasJugadas4() {
		Estadisticas e = new Estadisticas();
		e.anadirPartidaGanada();
		e.anadirPartidaGanada();
		e.anadirPartidaPerdida();
		e.anadirPartidaPerdida();
		assertEquals("Partidas totales igual a 4", 4, e.obtenPartidasJugadas());
	}
	
	@Test
	public void testObtenPartidasJugadas1() {
		Estadisticas e = new Estadisticas();
		e.anadirPartidaGanada();
		assertEquals("Partidas totales igual a 1", 1, e.obtenPartidasJugadas());
	}
	
	@Test
	public void testObtenPartidasJugadas10() {
		Estadisticas e = new Estadisticas();
		e.anadirPartidaGanada();
		e.anadirPartidaGanada();
		e.anadirPartidaGanada();
		e.anadirPartidaGanada();
		e.anadirPartidaPerdida();
		e.anadirPartidaPerdida();
		e.anadirPartidaPerdida();
		e.anadirPartidaPerdida();
		e.anadirPartidaPerdida();
		e.anadirPartidaPerdida();
		assertEquals("Partidas totales igual a 10", 10, e.obtenPartidasJugadas());
	}
	
	@Test
	public void testObtenPartidasJugadas0() {
		Estadisticas e = new Estadisticas();
		assertEquals("Partidas totales igual a 0", 0, e.obtenPartidasJugadas());
	}

	@Test
	public void testObtenPartidasGanadas4() {
		Estadisticas e = new Estadisticas();
		e.anadirPartidaGanada();
		e.anadirPartidaGanada();
		e.anadirPartidaGanada();
		e.anadirPartidaGanada();
		assertEquals("Partidas ganadas igual 4", 4, e.obtenPartidasGanadas());
	}
	@Test
	public void testObtenPartidasGanadas0() {
		Estadisticas e = new Estadisticas();
		e.anadirPartidaPerdida();
		assertEquals("Partidas ganadas igual 0", 0, e.obtenPartidasGanadas());
	}

	@Test
	public void testInformaPartidasGanadas10() {
		Estadisticas e = new Estadisticas();
		e.anadirPartidaGanada();
		e.informaPartidasGanadas(10);
		assertEquals("Partidas ganadas igual 10", 10, e.obtenPartidasGanadas());
	}
	
	@Test
	public void testInformaPartidasGanadas6() {
		Estadisticas e = new Estadisticas();
		e.anadirPartidaGanada();
		e.informaPartidasGanadas(5);
		e.anadirPartidaGanada();
		assertEquals("Partidas ganadas igual 6", 6, e.obtenPartidasGanadas());
	}
	
	@Test
	public void testInformaPartidasGanadas0() {
		Estadisticas e = new Estadisticas();
		e.anadirPartidaGanada();
		e.informaPartidasGanadas(0);
		assertEquals("Partidas ganadas igual 0", 0, e.obtenPartidasGanadas());
	}
	
	@Test
	public void testObtenPartidasPerdidas4() {
		Estadisticas e = new Estadisticas();
		e.anadirPartidaPerdida();
		e.anadirPartidaPerdida();
		e.anadirPartidaPerdida();
		e.anadirPartidaPerdida();
		assertEquals("Partidas perdidas igual 4", 4, e.obtenPartidasPerdidas());
	}
	
	@Test
	public void testObtenPartidasPerdidas0() {
		Estadisticas e = new Estadisticas();
		e.anadirPartidaGanada();
		assertEquals("Partidas perdidas igual 0", 0, e.obtenPartidasPerdidas());
	}

	@Test
	public void testInformaPartidasPerdidas10() {
		Estadisticas e = new Estadisticas();
		e.anadirPartidaPerdida();
		e.informaPartidasPerdidas(10);
		assertEquals("Partidas perdidas igual 10", 10, e.obtenPartidasPerdidas());
	}
	
	@Test
	public void testInformaPartidasPerdidas6() {
		Estadisticas e = new Estadisticas();
		e.anadirPartidaPerdida();
		e.informaPartidasPerdidas(5);
		e.anadirPartidaPerdida();
		assertEquals("Partidas perdidas igual 6", 6, e.obtenPartidasPerdidas());
	}
	@Test
	public void testInformaPartidasPerdidas0() {
		Estadisticas e = new Estadisticas();
		e.anadirPartidaPerdida();
		e.informaPartidasPerdidas(0);
		assertEquals("Partidas perdidas igual 0", 0, e.obtenPartidasPerdidas());
	}

	@Test
	public void testObtenMejorPuntuacion() {
		estadistica = new Estadisticas();
		estadistica.informaPuntuacion(5.3, 0);
		estadistica.informaPuntuacion(5.8, 1);

		assertTrue(estadistica.obtenMejorPuntuacion() == 5.8);
		assertTrue(estadistica.obtenIdMejorPartida() == 1);
	}

	@Test
	public void testInformaPuntuacion() {
		estadistica = new Estadisticas();
		estadistica.informaPuntuacion(5.3, 0);
		estadistica.informaPuntuacion(5.8, 1);
	}

	@Test
	public void testObtenWinRatePorcentage0() {
		estadistica = new Estadisticas();
		estadistica.anadirPartidaGanada();
		estadistica.anadirPartidaPerdida();
		// assertSame(50,50);
		assertTrue(estadistica.obtenPorcentajeVictoria() == 50.0);
		// assertSame(tablero.obtenerTurno(numeroTurno),tablero.ObtenTablero()[numeroTurno-1])
	}
	
	@Test
	public void testObtenWinRatePorcentage1() {
		estadistica = new Estadisticas();
		estadistica.anadirPartidaGanada();
		estadistica.anadirPartidaGanada();
		estadistica.anadirPartidaGanada();
		estadistica.anadirPartidaPerdida();
		// assertSame(50,50);
		assertTrue(estadistica.obtenPorcentajeVictoria() == 75.0);
		// assertSame(tablero.obtenerTurno(numeroTurno),tablero.ObtenTablero()[numeroTurno-1])
	}
	
	@Test
	public void testObtenWinRatePorcentage2() {
		estadistica = new Estadisticas();
		estadistica.anadirPartidaGanada();
		estadistica.anadirPartidaGanada();
		estadistica.anadirPartidaGanada();
		// assertSame(50,50);
		assertTrue(estadistica.obtenPorcentajeVictoria() == 100.0);
		// assertSame(tablero.obtenerTurno(numeroTurno),tablero.ObtenTablero()[numeroTurno-1])
	}
	
	@Test
	public void testObtenWinRatePorcentage3() {
		estadistica = new Estadisticas();
		estadistica.anadirPartidaPerdida();
		// assertSame(50,50);
		assertTrue(estadistica.obtenPorcentajeVictoria() == 0.0);
		// assertSame(tablero.obtenerTurno(numeroTurno),tablero.ObtenTablero()[numeroTurno-1])
	}		

}
