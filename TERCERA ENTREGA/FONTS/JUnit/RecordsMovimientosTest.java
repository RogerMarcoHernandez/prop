package JUnit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Dominio.Partida;
import Dominio.Perfil;
import Dominio.RecordsMovimientos;
import Dominio.Pair;

public class RecordsMovimientosTest {

	@Before
	public void setUp() throws Exception {
		RecordsMovimientos.obtenInstancia().reset();
	}

	@Test
	public void testRecordMovimientos() {
		RecordsMovimientos rm = RecordsMovimientos.obtenInstancia();
		Pair[][] test = new Pair[5][4];
		assertArrayEquals("Test records movimientos vacio ", test, rm.obtenRecords());
	}

	@Test
	public void testObtenRecord1() throws Exception {
		RecordsMovimientos rm = RecordsMovimientos.obtenInstancia();
		rm.anadirPartida("Usuario1",3,4,7);
		
		Pair b = rm.obtenRecord(3, 4);
		
		assertEquals(7, b.obtenPrimero());
		assertEquals("Usuario1", b.obtenSegundo());
		
	}
	
	@Test
	public void testObtenRecord2() throws Exception {
		RecordsMovimientos rm = RecordsMovimientos.obtenInstancia();
		rm.anadirPartida("Usuario1",3,4,7);
		rm.anadirPartida("Usuario2",3,4,4);
		rm.anadirPartida("Usuario1",3,4,4);
		
		Pair b = rm.obtenRecord(3, 4);
		
		assertEquals(4, b.obtenPrimero());
		assertEquals("Usuario2", b.obtenSegundo());
		
	}
	
	@Test
	public void testObtenRecord3() throws Exception {
		RecordsMovimientos rm = RecordsMovimientos.obtenInstancia();
		rm.anadirPartida("Usuario1",3,4,7);
		rm.anadirPartida("Usuario2",3,4,4);
		rm.reset();
		rm.anadirPartida("Usuario1",3,4,9);
		
		Pair b = rm.obtenRecord(3, 4);
		
		assertEquals(9, b.obtenPrimero());
		assertEquals("Usuario1", b.obtenSegundo());
		
	}
}
