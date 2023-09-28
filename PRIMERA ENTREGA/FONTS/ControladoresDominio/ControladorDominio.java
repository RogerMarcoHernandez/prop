package ControladoresDominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Dominio.Estadisticas;
import Dominio.FactoriaPartida;
import Dominio.IPartida;
import Dominio.Pair;
import Dominio.Partida;
import Dominio.Perfil;
import Dominio.RankingGlobal;
import Dominio.RecordsMovimientos;

public class ControladorDominio {
	// Instancia estatica para hacer del controlador de dominio una clase Singleton.
	private static ControladorDominio ctrl;

	// Instancia de la interfaz partida.
	private IPartida partidaI;
	
	// Instancia del usuario activo
	private Perfil usuarioActivo;
	
	// Mapa con todos los usuarios registrados en el sistema
	private HashMap<String,Perfil> Usuarios;
	
	// Mapa con todas las partidas creadas en el sistema
	private ArrayList<Partida> partidas;
	private Partida partidaActiva;

	// Constructor privado del controlador.
	private ControladorDominio() {
		usuarioActivo = null;
		Usuarios = new HashMap<String,Perfil>();
		partidas = new ArrayList<>();
	}

	// Metodo para obtener la instancia del singleton.
	public static ControladorDominio obtenInstancia() {
		if (ctrl == null)
			ctrl = new ControladorDominio();
		return ctrl;
	}

	// Metodo para crear una partida, mediante la obtencion de la instancia
	// singleton de la factoria de partidas y su llamada a la creacion de la
	// partida.
	public int creaPartida(List<Object> config) throws Exception {
		FactoriaPartida partidaFactory = FactoriaPartida.obtenInstancia();
		if (usuarioActivo == null) throw new Exception("ERROR: necesitas estar logeado para crear una partida");
		partidaI = partidaFactory.creaPartida(partidas.size(), config, usuarioActivo);
		if (partidaI != null) {
			partidaActiva = partidaI.obtenPartida();
			partidas.add(partidaActiva);
			usuarioActivo.informaPartidas(partidaActiva);
		}
		return partidaActiva.obtenId();
	}

	// Metodo para ejecutar un turno de la partida creada.
	public int ejecutarTurno(List<Integer> codigo) throws Exception {
		int resultadoTurno = partidaActiva.ejecutarTurno(codigo);
		switch (resultadoTurno) {
		case 1:
			RankingGlobal ranking = RankingGlobal.obtenInstancia();
			ranking.anadirPartida(partidaActiva);
			
			int longitudCodigo = partidaActiva.obtenLongitudCodigo();
			int numeroColores = partidaActiva.obtenNumeroColores();
			int turnos = partidaActiva.obtenNumeroTurnos();
			String usuario = partidaActiva.obtenPerfil().obtenNombreUsuario();
			RecordsMovimientos recordsMovimientos = RecordsMovimientos.obtenInstancia();
			recordsMovimientos.anadirPartida(usuario, longitudCodigo, numeroColores, turnos);
			usuarioActivo.informaPartidasGanadas();
			usuarioActivo.informaPuntuacion(partidaActiva.obtenPuntuacion(), partidaActiva.obtenId());
			break;
			
		case -1:
			RankingGlobal ranking1 = RankingGlobal.obtenInstancia();
			ranking1.anadirPartida(partidaActiva);
			usuarioActivo.informaPartidaPerdida();
			break;
		case 0:
			break;
		default:
			throw new Exception("ERROR: ejecucion de turno incorrecta");
		}
		return resultadoTurno;
			
	}

	// Metodo para obtener la puntuacion de la partida creada.
	public double obtenPuntuacion() {
		return partidaI.calculaPuntuacion();
	}

	// Metodo para iniciar sesion
	public void iniciarSesion(String username, String password) throws Exception {
		if (usuarioActivo != null) throw new Exception("ERROR: debes cerrar sesion antes de logearte de nuevo");
		if (Usuarios.containsKey(username)) {
			if (Usuarios.get(username).contrasenaCorrecta(password)) {
				usuarioActivo = Usuarios.get(username);
			} else {
				
				throw new Exception("ERROR: contraseña incorrecta");
			}
		}
		else {
			throw new Exception("ERROR: usuario incorrecto");
		}
	}
	
	// Metodo para registrar un usuario
	public void registrar(String username, String password) throws Exception{
		if (!Usuarios.containsKey(username)) {
			boolean mayuscula = false;
			boolean numero = false;
			boolean simbolo = false;
			if (username.length() > 15) throw new Exception("ERROR: el usuario es demasiado largo, debe ocupar maximo 15 caracteres");	
			if (password.length() > 15) throw new Exception("ERROR: la contrasena es demasiado larga, debe ocupar maximo 15 caracteres");	
			for (int i = 0; i < password.length() && (!mayuscula || !numero || !simbolo); i++) {
	            char caracter = password.charAt(i);
	            if (Character.isUpperCase(caracter)) {
	            	mayuscula = true;
	            } else if (Character.isDigit(caracter)) {
	            	numero = true;
	            } else if (!Character.isLetterOrDigit(caracter)) {
	            	simbolo = true;
	            }	            
	        }
			if (mayuscula && numero && simbolo) {
            	Usuarios.put(username, new Perfil(username, password));
            }
            else throw new Exception("ERROR: la contrasena no contiene un numero, una mayuscula y un simbolo");			
		}
		else throw new Exception("ERROR: el usuario ya existe");
	}
	
	// Metodo para salir de la sesion del usuario
	public void cerrarSesion() throws Exception {
		if (usuarioActivo == null) throw new Exception("ERROR: no hay ninguna cuenta iniciada");
		else {
			usuarioActivo.informaUltimaConexion();
			usuarioActivo = null;
		}
		
	}
	
	// Metodo para consultar el ranking global
	public Pair<String, Integer>[] obtenRanking() throws Exception {
		RankingGlobal Ranking = RankingGlobal.obtenInstancia();
		Partida[] RankingPartidas = Ranking.obtenPartidas();
		
		Pair<String, Integer>[] result = new Pair[RankingPartidas.length];
		for (int i = 0; i < RankingPartidas.length; i++) {
			if (RankingPartidas[i] != null) {
				Pair<String, Integer> partialResult = new Pair(RankingPartidas[i].obtenPerfil().obtenNombreUsuario(), RankingPartidas[i].obtenPuntuacion());
				result[i] = partialResult;			
			}
		}
		if (result[0] == null) throw new Exception("ERROR: actualmente el ranking se encuentra vacio");
		return result;
	}
	
	//Metodo para consultar las estadisticas del perfil activo
	public int[] obtenEstadisticas() throws Exception
	{
		if (usuarioActivo == null) throw new Exception("ERROR: debes logearte para poder consultar tus estadisticas");
		Estadisticas estadisticas = usuarioActivo.obtenEstadisticas();
		int[] result = new int[3];
		result[0] = estadisticas.obtenPartidasJugadas();
		result[1] = estadisticas.obtenPartidasGanadas();
		result[2] = estadisticas.obtenPartidasPerdidas();
		return result;
				
	}
	
	//Metodo para configurar el tema del perfil
	public void informaTema(int pTema) {
		usuarioActivo.informaTema(pTema);
	}
	
	//Metodo para configurar el volumen del perfil
	public void informaVolumen(int pVolumen) throws Exception {
		usuarioActivo.informaVolumen(pVolumen);
	}
	
	public ArrayList<Integer> obtenPartidasPerfilPorAcabar() throws Exception{
		if (usuarioActivo == null) throw new Exception("ERROR: debes logearte para poder consultar tus partidas inacabadas");
		return usuarioActivo.obtenIdsPartidasSinAcabar();
	}
	
	public ArrayList<Integer> obtenPartidasPerfilAcabadas() throws Exception{	
		if (usuarioActivo == null) throw new Exception("ERROR: debes logearte para poder consultar tus partidas acabadas");
		return usuarioActivo.obtenIdsPartidasAcabadas();
	}
	
	public void cargarPartida(int idPartida) throws Exception {
		ArrayList<Integer> partidasSinAcabar = usuarioActivo.obtenIdsPartidasSinAcabar();
		boolean trobat = false;
		for (Integer idP : partidasSinAcabar) {
			if (idP == idPartida)  {
				partidaActiva = partidas.get(idPartida);
				trobat = true;
			}
		}
		if (!trobat) throw new Exception("ERROR: la partida introducida no se encuentra en la lista de partidas inacabadas");
		
	}
	
	public Pair<Integer, String> obtenRecordConcreto(int longitudCodigo, int numeroColores) throws Exception {
		RecordsMovimientos recordsMovimientos = RecordsMovimientos.obtenInstancia();
		return recordsMovimientos.obtenRecord(longitudCodigo, numeroColores);		
	}
	
	public Pair<Integer, String>[][] obtenRecords() {
		RecordsMovimientos recordsMovimientos = RecordsMovimientos.obtenInstancia();
		return recordsMovimientos.obtenRecords();		
	}
	
	public Perfil obtenUsuarioActivo() {
		return this.usuarioActivo;
	}

	public List<List<Integer>> obtenTableroAdivinanzas() {
		return partidaActiva.obtenTableroAdivinanzas();
	}

	public List<List<Integer>> obtenTableroResultados() {
		return partidaActiva.obtenTableroResultados();
	}

	public void informaResultado(List<Integer> resultado) throws Exception {
		 partidaActiva.informaResultado(resultado);
	}

	public List<Integer> obtenPista() throws Exception {		
		return partidaActiva.obtenPista();
	}
	
	public int obtenPistasDisponibles() {		
		return partidaActiva.obtenPistasDisponibles();
	}

	public double obtenPorcentajeVictorias() {
		return usuarioActivo.obtenPorcentajeVictoria();
	}

	public int obtenIdMejorPartida() {
		return usuarioActivo.obtenIdMejorPartida();
	}

	public double obtenMejorPuntuacion() {
		return usuarioActivo.obtenMejorPuntuacion();
	}

	public int[] obtenDatosPartida() {
		int[] result = new int[7];
		result[0] = partidaActiva.obtenNumeroTurnos();
		result[1] = partidaActiva.obtenDificultad();
		result[2] = partidaActiva.obtenNivelAyuda();
		result[3] = partidaActiva.obtenNumeroColores();
		result[4] = partidaActiva.obtenLongitudCodigo();
		if (partidaActiva.esCodemaker()) result[5] = 1;
		else result[5] = 0;
		result[6] = partidaActiva.obtenTurnoActual();
		return result;
	}
	
	public boolean esCodemMaker(int pIdPartida) {
		return partidas.get(pIdPartida).esCodemaker();
	}	

	public List<Integer> obtenCodigoOriginal() {
		return partidaActiva.obtenCodigoSecreto();
	}	
	
	
}
