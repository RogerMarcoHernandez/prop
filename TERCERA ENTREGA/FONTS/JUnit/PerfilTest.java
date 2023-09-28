package JUnit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Dominio.Estadisticas;
import Dominio.Partida;
import Dominio.PartidaCodebreaker;
import Dominio.PartidaCodemaker;
import Dominio.Perfil;


public class PerfilTest {
	
	private List<Object> config;
	private Partida partida;
	
	@Before
	public void setUp() throws Exception {
		config = List.of(4, 0, 0, 4, 4, false);
	}
	
	@Test
	public void testCreacionPerfil() {
		Perfil perfil = new Perfil("usuario","contrasenya");
		perfil.informaNombreUsuario("nombreEjemplo");
		perfil.informaContrasena("contrasenyaEjemplo");
		assertTrue(perfil.obtenContrasena().equals("contrasenyaEjemplo") && perfil.obtenNombreUsuario().equals("nombreEjemplo") 
				&& perfil.obtenEstadisticas()!=null && perfil.obtenPartidasGanadas()==0 && perfil.obtenPartidasJugadas()==0 && perfil.obtenPartidasPerdidas()==0);
	}
	
	@Test
	public void testPerfilPartidasAnadidas() throws Exception {
		Perfil perfil = new Perfil("usuario","contrasenya");
		Partida[] listaPartidas = new Partida[10];
		for (int i = 0; i < 10; i++) {
			partida = new PartidaCodebreaker(i, config, perfil);
			perfil.informaPartidas(partida);
			listaPartidas[i]=partida;
		}
		assertArrayEquals(listaPartidas, perfil.obtenPartidas());
	}
	
	@Test
	public void testMejorPuntuacion() throws Exception {
		Perfil perfil = new Perfil("usuario","contrasenya");
		for (int i = 0; i < 10; i++) {
			partida = new PartidaCodebreaker(i, config, perfil);
			partida.informaAcabada(true);
			perfil.informaPartidas(partida);
			perfil.informaPuntuacion(i*1000, partida.obtenId());
		}
		assertTrue(9000==perfil.obtenMejorPuntuacion());
	}
	
	@Test
	public void testPerfilPartidasAcabadas() throws Exception {
		Perfil perfil = new Perfil("usuario","contrasenya");
		ArrayList<Integer> listaPartidas = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			partida = new PartidaCodebreaker(i, config, perfil);
			partida.informaAcabada(true);
			perfil.informaPartidas(partida);
			listaPartidas.add(partida.obtenId());
		}
		assertEquals(listaPartidas, perfil.obtenIdsPartidasAcabadas());
	}
	
	@Test
	public void testComprobarContrasenaTrue() {
		Perfil perfil = new Perfil("usuario1","password");
		assertEquals(true, perfil.contrasenaCorrecta("password"));
	}
	
	@Test
	public void testComprobarContrasenaFalse() {
		Perfil perfil = new Perfil("usuario1","password");
		assertEquals(false, perfil.contrasenaCorrecta("incorrecto"));
	}
	
	
	@Test
	public void testObtenIdsPartidasSinAcabar() throws Exception {
		Perfil perfil = new Perfil("usuario1","password");
		for (int i = 0; i < 10; i++) {
			partida = new PartidaCodemaker(i, config, perfil);
			partida.informaAcabada(false);
			perfil.informaPartidas(partida);
		}
		for (int i = 10; i < 15; i++) {
			partida = new PartidaCodemaker(i, config, perfil);
			partida.informaAcabada(true);
			perfil.informaPartidas(partida);
		}
		ArrayList<Integer> listaPartidasSinAcabar = new ArrayList<>();
		for (int i = 0; i < 10; i++) listaPartidasSinAcabar.add(i);
		assertEquals(listaPartidasSinAcabar, perfil.obtenIdsPartidasSinAcabar());
	}
	
	@Test
	public void testObtenIdsPartidasAcabadas() throws Exception {
		Perfil perfil = new Perfil("usuario1","password");
		for (int i = 0; i < 10; i++) {
			partida = new PartidaCodemaker(i, config, perfil);
			partida.informaAcabada(false);
			perfil.informaPartidas(partida);
		}
		for (int i = 10; i < 15; i++) {
			partida = new PartidaCodemaker(i, config, perfil);
			partida.informaAcabada(true);
			perfil.informaPartidas(partida);
		}
		ArrayList<Integer> listaPartidasAcabadas = new ArrayList<>();
		for (int i = 10; i < 15; i++) listaPartidasAcabadas.add(i);
		assertEquals(listaPartidasAcabadas, perfil.obtenIdsPartidasAcabadas());
	}
	
	@Test
	public void testObtenPorcentageVictoria() throws Exception {
		Perfil perfil = new Perfil("usuario1","password");
		perfil.informaPartidasGanadas();
		perfil.informaPartidaPerdida();
		assertTrue(perfil.obtenPorcentajeVictoria() == 50.0);
	}
	
	@Test
	public void testObtenPartidasJugadas() throws Exception {
		Perfil perfil = new Perfil("usuario1","password");
		for (int i = 0; i < 10; i++) {
			partida = new PartidaCodemaker(i, config, perfil);
			partida.informaAcabada(false);
			perfil.informaPartidas(partida);
		}
		for (int i = 10; i < 15; i++) {
			partida = new PartidaCodemaker(i, config, perfil);
			partida.informaAcabada(true);
			perfil.informaPartidas(partida);
		}
		assertTrue(perfil.obtenPartidasJugadas() == 15);
	}
}
