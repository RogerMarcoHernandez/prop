package JUnit;

import static org.junit.Assert.assertArrayEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Dominio.Partida;
import Dominio.RankingGlobal;

public class RankingGlobalTest {

	@Before
	public void setUp() {
		RankingGlobal.obtenInstancia().reset();
	}	

	@Test
	public void testRankingGlobal() {
		RankingGlobal rg = RankingGlobal.obtenInstancia();
		Partida[] x = new PartidaStub[10];
		assertArrayEquals("Test partidas ranking vacio ", x, rg.obtenPartidas());
	}
			
	@Test
	public void testObtenVectorPartidas1() throws Exception {
		RankingGlobal rg = RankingGlobal.obtenInstancia();
		
		List<Object> config = List.of(4, 0, 0, 4, 4, false);
		Partida p = new PartidaStub(1, config);
		rg.anadirPartida(p);
		
		Partida[] expected = {p, null,null,null,null,null,null,null,null,null};
		assertArrayEquals("Test de un ranking con 1 elemento ", expected, rg.obtenPartidas());
	}
	
	@Test
	public void testObtenVectorPartidas2() throws Exception {
		RankingGlobal rg = RankingGlobal.obtenInstancia();
		
		List<Object> config = List.of(4, 0, 0, 4, 4, false);
		
		int numPartidas = 10;
		Partida[] partidas = new PartidaStub[numPartidas];
		for (int i = 0; i < numPartidas/2; i++) {
			partidas[i] = new PartidaStub(i+1, config);
			
			rg.anadirPartida(partidas[i]);
		}
		Partida[] x = {partidas[4],partidas[3],partidas[2],partidas[1],partidas[0],null,null,null,null,null};
		assertArrayEquals("Test de un ranking con 5 elemento ", x, rg.obtenPartidas());
	}
	
	@Test
	public void testObtenVectorPartidas3() throws Exception {
		RankingGlobal rg = RankingGlobal.obtenInstancia();
		
		List<Object> config = List.of(4, 0, 0, 4, 4, false);
		
		int numPartidas = 10;
		Partida[] partidas = new PartidaStub[numPartidas];
		
		for (int i = 0; i < numPartidas; i++) {
			partidas[i] = new PartidaStub(i+1, config);
			
			rg.anadirPartida(partidas[i]);
		}
		Partida[] x = {partidas[9],partidas[8],partidas[7],partidas[6],partidas[5],partidas[4],partidas[3],partidas[2],partidas[1],partidas[0]};;
		assertArrayEquals("Test de un ranking con 10 elemento ", x, rg.obtenPartidas());
	}
	
	
	@Test
	public void testObtenVectorPartidas4() throws Exception {
		RankingGlobal rg = RankingGlobal.obtenInstancia();
		
		List<Object> config = List.of(4, 0, 0, 4, 4, false);
		
		int numPartidas = 100;
		
		Partida[] partidas = new PartidaStub[numPartidas];
		for (int i = 0; i < numPartidas; i++) {
			partidas[i] = new PartidaStub(i+1, config);
			
			rg.anadirPartida(partidas[i]);
		}
		Partida[] expected = {partidas[99], partidas[98],partidas[97],partidas[96],partidas[95],partidas[94],partidas[93],partidas[92],partidas[91],partidas[90]};
		assertArrayEquals("Test ranking de 10 elementos despues de insertar 100 ", expected, rg.obtenPartidas());
	}

}
