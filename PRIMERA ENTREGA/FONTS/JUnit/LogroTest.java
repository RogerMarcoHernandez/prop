package JUnit;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Dominio.Logro;

public class LogroTest {

	@Test
	public void testLogro() {
		Logro l = new Logro(1, "resolver en un movimiento", 1000);
		assertEquals("ID igual", 1, l.obtenId());
		assertEquals("Descripcion igual", "resolver en un movimiento", l.obtenDescripcion());
		assertEquals("Puntuacion igual", 1000, l.obtenPuntuacion());
	}
}
