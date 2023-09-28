package JUnit;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Dominio.ConfiguracionPerfil;

public class ConfiguracionPerfilTest {

	@Test
	public void testConfiguracionPerfil() {
		ConfiguracionPerfil cp = new ConfiguracionPerfil(1); 
		
		assertEquals("Comprovar  tema", 1, cp.obtenTema());
	}

}
