package JUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Test;

import Dominio.Logro;
import Dominio.LogroPerfil;

public class LogroPerfilTest {

	@Test
	public void testLogroPerfil() {
		Logro l = new Logro(1, "resolver en un movimiento", 1000);
		LogroPerfil lp = new LogroPerfil(l);		
		assertEquals("Bool igual", false, lp.esLogrado());
		assertEquals("Logro igual", l.obtenId(), lp.obtenIdLogro());
	}
	
	@Test
	public void testInformaLogrado() {
		Logro l = new Logro(1, "resolver en un movimiento", 1000);
		LogroPerfil lp = new LogroPerfil(l);	
		lp.informaLogrado();
		assertEquals("Bool igual", true, lp.esLogrado());
		assertEquals("Fecha igual", LocalDate.now(), lp.obtenFechaLogrado());
	}
	
	
	@Test
	public void testFechaSinLograr() {
		Logro l = new Logro(1, "resolver en un movimiento", 1000);
		LogroPerfil lp = new LogroPerfil(l);	
		assertNull("Fecha igual", lp.obtenFechaLogrado());
	}
	

}
