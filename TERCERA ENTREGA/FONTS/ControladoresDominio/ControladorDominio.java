package ControladoresDominio;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ControladoresPersistencia.ControladorPersistencia;
import Dominio.Estadisticas;
import Dominio.FactoriaPartida;
import Dominio.IPartida;
import Dominio.Pair;
import Dominio.Partida;
import Dominio.Perfil;
import Dominio.RankingGlobal;
import Dominio.RecordsMovimientos;


/**
 * ControladorDominio es una clase que actua como el controlador principal del dominio del sistema. 
 * Se encarga de gestionar las partidas, perfiles de usuario, ranking global y registros de movimientos.
 * Tambien se comunica con el controlador de persistencia para almacenar y recuperar datos.
 * 
 * @author Alex Pertusa
 * @author Jordi Estany
 */
public class ControladorDominio {
	
	/** Instancia estatica para hacer del controlador de dominio una clase Singleton. */
	private static ControladorDominio ctrl;
	
	/** Instancia del controlador de persistencia */
	private ControladorPersistencia ctrlPersistencia;
	
	/** Instancia del rankingGlobal */
	private RankingGlobal rankingGlobal;
	
	/** Instancia de recordsMovimientos */
	private RecordsMovimientos recordsMovimientos;

	/** Instancia de la interfaz partida. */
	private IPartida partidaI;
	
	/** Instancia del usuario activo */
	private Perfil usuarioActivo;
	
	/** Mapa con todos los usuarios registrados en el sistema */
	private HashMap<String,Perfil> Usuarios; 
		
	/** Partida que representa la partida que esta jugando el usuario en un instante */
	private Partida partidaActiva;
	
	/** ID de la ultima partida creada */
	private int idUltimaPartida;

	/**
	 * Constructor de la clase ControladorDominio.
	 * Inicializa las variables y objetos necesarios para el controlador de dominio.
	 * Ademas, realiza algunas operaciones de inicializacion de datos.
	 */
	private ControladorDominio() {
		usuarioActivo = null;
		Usuarios = new HashMap<String,Perfil>();
		ctrlPersistencia = ControladorPersistencia.obtenInstancia();
		rankingGlobal = RankingGlobal.obtenInstancia();
		recordsMovimientos = RecordsMovimientos.obtenInstancia();
		try {
			ObtenListaUsuarios();
			actualizaUltimoIdPartida();
		} catch (Exception e) {
			
		}
		
		try {
			ObtenRecordsMovimientos();
		} catch (Exception e) {
			
		}
		
		try {
			cargaPartidasRankingGlobal();
		} catch (Exception e) {
			
		}
	}

	/**
	 * Obtiene una instancia unica del ControladorDominio.
	 * Si no existe una instancia previa, crea una nueva y la retorna.
	 * Si ya existe una instancia, simplemente la retorna.
	 *
	 * @return La instancia unica del ControladorDominio.
	 */
	public static ControladorDominio obtenInstancia() {
		if (ctrl == null)
			ctrl = new ControladorDominio();
		return ctrl;
	}

	/**
	 * Crea una nueva partida utilizando la configuracion proporcionada.
	 *
	 * @param config La configuracion de la partida.
	 * @return El ID de la partida creada.
	 * @throws Exception Si el usuario no esta activo.
	 */
	public int creaPartida(List<Object> config) throws Exception {
		FactoriaPartida partidaFactory = FactoriaPartida.obtenInstancia();
		if (usuarioActivo == null) throw new Exception("ERROR: necesitas estar logeado para crear una partida");
		//partidaI = partidaFactory.creaPartida(partidas.size(), config, usuarioActivo);
		idUltimaPartida += 1;
		partidaI = partidaFactory.creaPartida(idUltimaPartida, config, usuarioActivo);
		if (partidaI != null) {
			partidaActiva = partidaI.obtenPartida();
			//partidas.add(partidaActiva);
			usuarioActivo.informaPartidas(partidaActiva);
			GuardaPerfiles();
			//Habria que alomejor escribir aqui en persistencia para guardar las partidas del perfil
		}
		return partidaActiva.obtenId();
	}

	/**
	 * Ejecuta un turno en la partida activa utilizando el codigo proporcionado.
	 *
	 * @param codigo El codigo a ejecutar en el turno.
	 * @return El resultado del turno.
	 * @throws Exception Si ocurre un error durante la ejecucion del turno.
	 */
	public int ejecutarTurno(List<Integer> codigo) throws Exception {
		int resultadoTurno = partidaActiva.ejecutarTurno(codigo);
		switch (resultadoTurno) {
		case 1:
			RankingGlobal ranking = RankingGlobal.obtenInstancia();
			if(ranking.anadirPartida(partidaActiva)) guardaRankingGlobal();
			
			int longitudCodigo = partidaActiva.obtenLongitudCodigo();
			int numeroColores = partidaActiva.obtenNumeroColores();
			int turnos = partidaActiva.obtenTurnoActual();
			String usuario = partidaActiva.obtenPerfil().obtenNombreUsuario();
			if (recordsMovimientos.anadirPartida(usuario, longitudCodigo, numeroColores, turnos)) {
				GuardaNuevoRecord();
			}
			usuarioActivo.informaPartidasGanadas();
			usuarioActivo.informaPuntuacion(partidaActiva.obtenPuntuacion(), partidaActiva.obtenId());
			break;
			
		case -1:
			RankingGlobal ranking1 = RankingGlobal.obtenInstancia();
			if (ranking1.anadirPartida(partidaActiva)) guardaRankingGlobal();
			usuarioActivo.informaPartidaPerdida();
			break;
		case 0:
			break;
		default:
			throw new Exception("ERROR: ejecucion de turno incorrecta");
		}
		GuardaPerfiles();
		return resultadoTurno;
			
	}

	/**
	 * Obtiene la puntuacion de la partida creada.
	 *
	 * @return La puntuacion de la partida.
	 */
	public double obtenPuntuacion() {
		//return partidaI.calculaPuntuacion();
		return partidaActiva.calculaPuntuacion();
	}
	
	 /**
	 * Inicia sesion de un usuario.
	 *
	 * @param username El nombre de usuario.
	 * @param password La contrasena del usuario.
	 * @return 0 si la sesion se inicia correctamente, -1 si el nombre de usuario o la contrasena son incorrectos.
	 */
		public int iniciarSesion(String username, String password) {
			if (Usuarios.containsKey(username)) {
				if (Usuarios.get(username).contrasenaCorrecta(password)) {
					usuarioActivo = Usuarios.get(username);
					return 0;
				} else {
					
					return -1;
				}
			}
			else {
				return -1;
			}
		}
		
		/**
		 * Registra un nuevo usuario.
		 *
		 * @param username El nombre de usuario para el nuevo usuario.
		 * @param password La contrasena para el nuevo usuario.
		 * @return 0 si el registro se realiza correctamente, -1 si el nombre de usuario ya esta en uso.
		 */
		public int registrar(String username, String password){
			if (!Usuarios.containsKey(username)) {				
	            	Usuarios.put(username, new Perfil(username, password));
	            	usuarioActivo = Usuarios.get(username);
	            	try {
	            		GuardaPerfiles();
					} catch (Exception e) {
						e.printStackTrace();
					}
	            	return 0;
	            }
	            else return -1;		
		}
			
		/**
		 * Cierra la sesion del usuario activo.
		 */
		public void cerrarSesion() {		
				usuarioActivo.informaUltimaConexion();
				usuarioActivo = null;
		}
	
		/**
		 * Obtiene el ranking global de partidas.
		 *
		 * @return Un arreglo de pares (nombre de usuario, puntuacion) que representa el ranking global de partidas.
		 * @throws Exception Si el ranking esta vacio.
		 */
	public Pair<String, Integer>[] obtenRanking() throws Exception {
		RankingGlobal Ranking = RankingGlobal.obtenInstancia();
		Partida[] RankingPartidas = Ranking.obtenPartidas();
		Pair<String, Integer>[] result = new Pair[RankingPartidas.length];
		for (int i = 0; i < RankingPartidas.length; i++) {
			if (RankingPartidas[i] != null) {
				Integer punt = Integer.valueOf((int) RankingPartidas[i].obtenPuntuacion());
				String nombreUsuario="desconocido";
				if(RankingPartidas[i].obtenPerfil()!=null) nombreUsuario=RankingPartidas[i].obtenPerfil().obtenNombreUsuario();
				Pair<String, Integer> partialResult = new Pair(nombreUsuario, punt);
				result[i] = partialResult;			
			}
		}
		if (result[0] == null) throw new Exception("ERROR: actualmente el ranking se encuentra vacio");
		return result;
	}	
	
	/**
	 * Obtiene las estadisticas del perfil activo.
	 *
	 * @return Un vector de enteros que representa las estadisticas del perfil activo.
	 *         El vector contiene en la posicion 0 el numero de partidas jugadas,
	 *         en la posicion 1 el numero de partidas ganadas y
	 *         en la posicion 2 el numero de partidas perdidas.
	 * @throws Exception Si no se ha iniciado sesion en un perfil activo.
	 */
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
	
	/**
	 * Informa al perfil activo sobre el tema configurado.
	 *
	 * @param pTema El tema escogido.
	 */
	public void informaTema(int pTema) {
		usuarioActivo.informaTema(pTema);
	}

	
	/**
	 * Obtiene las partidas sin acabar del perfil activo.
	 *
	 * @return Una lista de enteros que representa los identificadores de las partidas sin acabar del perfil activo.
	 * @throws Exception Si no se ha iniciado sesion en un perfil activo.
	 */
	public ArrayList<Integer> obtenPartidasPerfilPorAcabar() throws Exception{
		if (usuarioActivo == null) throw new Exception("ERROR: debes logearte para poder consultar tus partidas inacabadas");
		return usuarioActivo.obtenIdsPartidasSinAcabar();
	}
	
	/**
	 * Obtiene las partidas acabadas del perfil activo.
	 *
	 * @return Una lista de enteros que representa los identificadores de las partidas acabadas del perfil activo.
	 * @throws Exception Si no se ha iniciado sesion en un perfil activo.
	 */
	public ArrayList<Integer> obtenPartidasPerfilAcabadas() throws Exception{	
		if (usuarioActivo == null) throw new Exception("ERROR: debes logearte para poder consultar tus partidas acabadas");
		return usuarioActivo.obtenIdsPartidasAcabadas();
	}
	
	/**
	 * Carga la partida con el identificador proporcionado en la partida activa.
	 *
	 * @param idPartida El identificador de la partida a cargar.
	 * @throws Exception Si la partida con el identificador no se encuentra en la lista de partidas sin acabar del perfil activo.
	 */
	public void cargarPartida(int idPartida) throws Exception {
		ArrayList<Integer> partidasSinAcabar = usuarioActivo.obtenIdsPartidasSinAcabar();
		boolean trobat = false;
		for (Integer idP : partidasSinAcabar) {
			if (idP == idPartida)  {
				//partidaActiva = partidas.get(idPartida);
				partidaActiva = usuarioActivo.ObtenPartida(idPartida);
				trobat = true;
			}
		}
		if (!trobat) throw new Exception("ERROR: la partida introducida no se encuentra en la lista de partidas inacabadas");
		
	}
	
	/**
	 * Obtiene un record concreto especificado por la longitud del codigo y el numero de colores.
	 *
	 * @param longitudCodigo La longitud del codigo.
	 * @param numeroColores  El numero de colores.
	 * @return Un par (numero de movimientos, nombre de usuario) que representa el record concreto.
	 * @throws Exception Si no se encuentra un record con los parametros especificados.
	 */
	public Pair<Integer, String> obtenRecordConcreto(int longitudCodigo, int numeroColores) throws Exception {
		//RecordsMovimientos recordsMovimientos = RecordsMovimientos.obtenInstancia();
		return recordsMovimientos.obtenRecord(longitudCodigo, numeroColores);		
	}
	
	/**
	 * Obtiene todos los registros de records.
	 *
	 * @return Una matriz bidimensional de pares (numero de movimientos, nombre de usuario) que representa todos los registros de records.
	 */
	public Pair<Integer, String>[][] obtenRecords() {
		//RecordsMovimientos recordsMovimientos = RecordsMovimientos.obtenInstancia();
		return recordsMovimientos.obtenRecords();		
	}
	
	/**
	 * Obtiene el perfil activo.
	 *
	 * @return El perfil activo.
	 */
	public Perfil obtenUsuarioActivo() {
		return this.usuarioActivo;
	}

	/**
	 * Obtiene el tablero de adivinanzas de la partida activa.
	 *
	 * @return Una lista de listas de enteros que representa el tablero de adivinanzas de la partida activa.
	 */
	public List<List<Integer>> obtenTableroAdivinanzas() {
		return partidaActiva.obtenTableroAdivinanzas();
	}

	/**
	 * Obtiene el tablero de resultados de la partida activa.
	 *
	 * @return Una lista de listas de enteros que representa el tablero de resultados de la partida activa.
	 */
	public List<List<Integer>> obtenTableroResultados() {
		return partidaActiva.obtenTableroResultados();
	}

	/**
	 * Informa el resultado del turno actual de la partida activa.
	 *
	 * @param resultado Una lista de enteros que representa el resultado del turno actual.
	 * @throws Exception Si la lista de resultado no cumple con las condiciones requeridas.
	 */
	public void informaResultado(List<Integer> resultado) throws Exception {
		 partidaActiva.informaResultado(resultado);
	}

	/**
	 * Obtiene una pista correspondiente a la partida activa.
	 *
	 * @return Una lista de enteros que representa la pista obtenida.
	 * @throws Exception Si no se puede obtener una pista para la partida activa.
	 */
	public List<Integer> obtenPista() throws Exception {		
		return partidaActiva.obtenPista();
	}
	
	/**
	 * Obtiene el numero de pistas disponibles de la partida activa.
	 *
	 * @return El numero de pistas disponibles.
	 */
	public int obtenPistasDisponibles() {		
		return partidaActiva.obtenPistasDisponibles();
	}

	/**
	 * Obtiene el porcentaje de victorias del perfil activo.
	 *
	 * @return El porcentaje de victorias del perfil activo.
	 */
	public double obtenPorcentajeDeVictorias() {
		return usuarioActivo.obtenPorcentajeVictoria();
	}

	/**
	 * Obtiene el ID de la mejor partida del perfil activo.
	 *
	 * @return El ID de la mejor partida del perfil activo.
	 */
	public int obtenIdMejorPartida() {
		return usuarioActivo.obtenIdMejorPartida();
	}

	/**
	 * Obtiene la mejor puntuacion del perfil activo.
	 *
	 * @return La mejor puntuacion del perfil activo.
	 */
	public double obtenMejorPuntuacion() {
		return usuarioActivo.obtenMejorPuntuacion();
	}

	/**
	 * Obtiene los datos de la partida activa.
	 *
	 * @return Un vector de enteros que representa los datos de la partida activa.
	 *         El vector contiene en la posicion 0 el numero de turnos,
	 *         en la posicion 1 la dificultad,
	 *         en la posicion 2 el nivel de ayuda,
	 *         en la posicion 3 el numero de colores,
	 *         en la posicion 4 la longitud del codigo,
	 *         en la posicion 5 un valor binario que indica si el jugador es el codemaker (1 si es el codemaker, 0 si no lo es) y
	 *         en la posicion 6 el turno actual.
	 */
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
	
	/**
	 * Consulta si el perfil activo tiene el rol de "Codemaker" en la partida con el ID proporcionado.
	 *
	 * @param pIdPartida El ID de la partida a consultar.
	 * @return true si el perfil activo tiene el rol de "Codemaker" en la partida, false de lo contrario.
	 */
	public boolean esCodeMaker(int pIdPartida) {
		return usuarioActivo.ObtenPartida(pIdPartida).esCodemaker();
		//return partidas.get(pIdPartida).esCodemaker();
	}	

	/**
	 * Obtiene el codigo secreto de la partida activa.
	 *
	 * @return Una lista de enteros que representa el codigo secreto de la partida activa.
	 */
	public List<Integer> obtenCodigoOriginal() {
		return partidaActiva.obtenCodigoSecreto();
	}
		
	/**
	 * Vacia la lista de partidas del perfil activo.
	 *
	 * @throws Exception Si ocurre un error al vaciar las partidas del perfil activo.
	 */
	public void vaciarPartidasPerfilActivo() throws Exception {
		usuarioActivo.asignaPartidas(null);
		GuardaPerfiles();
	}
	
	/**
	 * Guarda las partidas del ranking global.
	 *
	 * @throws Exception Si ocurre un error al guardar las partidas del ranking global.
	 */
	public void guardaRankingGlobal() throws Exception {
		Partida[] partidasRankingGlobal = rankingGlobal.obtenPartidas();
		List<List<Object>> informacionPartidasRankingGlobal = new ArrayList<>();
		for(Partida p: partidasRankingGlobal) if(p!=null) informacionPartidasRankingGlobal.add(obtenInformacionPartida(p));
		ctrlPersistencia.escribeRankingGlobal(informacionPartidasRankingGlobal);
		
	}
	
	/**
	 * Carga las partidas del ranking global.
	 *
	 * @return Un arreglo de Partida con las partidas cargadas del ranking global.
	 * @throws Exception Si ocurre un error al cargar las partidas del ranking global.
	 */
	public Partida[] cargaPartidasRankingGlobal() throws Exception {
		Partida[] partidasRankingGlobal = new Partida[10];
		List<List<Object>> informacionPartidasPerfilActivo = ctrlPersistencia.leeRankingGlobal();
		int i=0;
		for(List<Object> p: informacionPartidasPerfilActivo) {
			Partida partida = creaPartidaConInformacionPartida(p);
			partidasRankingGlobal[i]=partida;
			rankingGlobal = RankingGlobal.obtenInstancia();
			rankingGlobal.informaPartidas(partidasRankingGlobal);
			i++;
		}		
		return partidasRankingGlobal;
	}
	
	/**
	 * Vacia el ranking global.
	 *
	 * @throws Exception Si ocurre un error al vaciar el ranking global.
	 */
	public void vaciarRankingGlobal() throws Exception {
		rankingGlobal = RankingGlobal.obtenInstancia();
		rankingGlobal.informaPartidas(new Partida[10]);
		guardaRankingGlobal();
	}
	
	/**
	 * Guarda los perfiles de usuarios.
	 *
	 * @throws Exception Si ocurre un error al guardar los perfiles de usuarios.
	 */
	public void GuardaPerfiles() throws Exception{
		List<Object> listaPerfiles = new ArrayList<>();
		for(String usuario: Usuarios.keySet()) {
			Perfil perfil = Usuarios.get(usuario);
			listaPerfiles.add(perfil);
		}
		ctrlPersistencia.escribePerfiles(listaPerfiles);
	}
	
	/**
	 * Obtiene la lista de usuarios y los agrega al mapa de usuarios.
	 *
	 * @throws Exception Si ocurre un error al obtener la lista de usuarios.
	 */
	public void ObtenListaUsuarios() throws Exception{
		List<Object> listaObjetos = new ArrayList<>();
		listaObjetos = ctrlPersistencia.leePerfiles();
		List<Perfil> listaPerfiles = new ArrayList<>();
		for(Object objeto:listaObjetos) {
			try {
				Perfil perfil = (Perfil) objeto;
				listaPerfiles.add(perfil);
			} catch (Exception e) {
				 
			}
		}
		
	    for (Perfil perfil : listaPerfiles) {
	        Usuarios.put(perfil.obtenNombreUsuario(), perfil);
	    }
	}
	
	/**
	 * Guarda el nuevo record de movimientos.
	 *
	 * @throws Exception Si ocurre un error al guardar el nuevo record de movimientos.
	 */
	public void GuardaNuevoRecord() throws Exception{
		Object recordMovimientosAux = recordsMovimientos;
		ctrlPersistencia.escribeRecordsMovimientos(recordMovimientosAux);
	}
	
	/**
	 * Obtiene los records de movimientos y los asigna al objeto `recordsMovimientos`.
	 *
	 * @throws Exception Si ocurre un error al obtener los records de movimientos.
	 */
	public void ObtenRecordsMovimientos() throws Exception{
		RecordsMovimientos recordsMovimientosAux = (RecordsMovimientos) ctrlPersistencia.leeRecordsMovimientos();
		recordsMovimientos.informaRecords(recordsMovimientosAux.obtenRecords());
	}
		
	/**
	 * Crea una partida con la informacion proporcionada en una lista.
	 *
	 * @param informacionPartida La lista con la informacion de la partida.
	 * @return La partida creada.
	 * @throws Exception Si ocurre un error al crear la partida.
	 */
	private Partida creaPartidaConInformacionPartida(List<Object> informacionPartida) throws Exception {
		FactoriaPartida factoriaPartida = FactoriaPartida.obtenInstancia();
		List<Object> config = (List<Object>) informacionPartida.get(0);
		List<Integer> codigoSecreto = (List<Integer>) informacionPartida.get(1);
		Integer pistasDisponibles = (Integer) informacionPartida.get(2);
		boolean ganada = (boolean) informacionPartida.get(3);
		boolean acabada = (boolean) informacionPartida.get(4);
		Integer turnoActual = (Integer) informacionPartida.get(5);
		List<List<Integer>> tableroAdivinanzas = (List<List<Integer>>) informacionPartida.get(6);
		List<List<Integer>> tableroResultados = (List<List<Integer>>) informacionPartida.get(7);
		Integer id = (Integer) informacionPartida.get(8);
		
		boolean esCodemaker = (boolean) config.get(5);
		
		String nombreUsuario = null;
		if(informacionPartida.size()==10)nombreUsuario=(String) informacionPartida.get(9);
		Perfil perfilPartida = new Perfil("desconocido","c");
		if(nombreUsuario!=null)perfilPartida = Usuarios.get(nombreUsuario);
		
		IPartida interfazPartida = factoriaPartida.creaPartida(id, config, perfilPartida);
		Partida partida = null;
		if (interfazPartida != null) {
			partida = interfazPartida.obtenPartida();
			partida.informaCodigoSecreto(codigoSecreto);
			partida.informaPistasDisponibles(pistasDisponibles);
			partida.informaGanada(ganada);
			partida.informaAcabada(acabada);
			partida.informaTurnoActual(turnoActual);
			partida.informaTableroAdivinanzas(tableroAdivinanzas);
			partida.informaTableroResultados(tableroResultados);
		}
		return partida;
	}
	
	/**
	 * Obtiene la informacion de la partida especificada en forma de lista.
	 *
	 * @param p La partida de la cual se desea obtener la informacion.
	 * @return La lista con la informacion de la partida.
	 */
	private List<Object> obtenInformacionPartida(Partida p){
		if(p.obtenPerfil()==null) {
			return List.of(
					p.obtenConfiguracion(),p.obtenCodigoSecreto(),p.obtenPistasDisponibles(),
					p.obtenGanada(),p.partidaAcabada(),p.obtenTurnoActual(),p.obtenTableroAdivinanzas(),
					p.obtenTableroResultados(),p.obtenId());
		}
		return List.of(
				p.obtenConfiguracion(),p.obtenCodigoSecreto(),p.obtenPistasDisponibles(),
				p.obtenGanada(),p.partidaAcabada(),p.obtenTurnoActual(),p.obtenTableroAdivinanzas(),
				p.obtenTableroResultados(),p.obtenId(),p.obtenPerfil().obtenNombreUsuario());
	}

	/**
	 * Obtiene el tema del perfil activo y lo convierte en un arreglo de objetos de tipo Color.
	 *
	 * @return Arreglo de objetos Color que representa el tema del perfil activo.
	 */
	public Color[] obtenTema() {
		return convertTema(usuarioActivo.obtenTema());
	}
	
	/**
	 * Obtiene la lista de temas disponibles, donde cada tema se representa como un arreglo de objetos de tipo Color.
	 *
	 * @return Lista de temas, donde cada tema es un arreglo de objetos Color.
	 */
	public List<Color[]> obtenTemas() {
		List <Color[]> temas = new ArrayList<>();
		for (int i = 1; i < 4; ++i) {
			temas.add(convertTema(i));
		}
		return temas;
	}
	
	/**
	 * Convierte un numero de tema en un arreglo de objetos de tipo Color que representa los colores del tema.
	 *
	 * @param numeroTema Numero del tema a convertir.
	 * @return Arreglo de objetos Color que representa los colores del tema.
	 */
	private Color[] convertTema(int numeroTema) {
		Color[] colores = {
	            Color.RED,
	            Color.BLUE,
	            Color.GREEN,
	            Color.YELLOW,
	            Color.DARK_GRAY
	        };
		if (numeroTema == 2) {
			colores[0] = Color.PINK;
			colores[1] = Color.ORANGE;
			colores[2] = Color.CYAN;
			colores[3] = Color.BLACK;
			colores[4] = Color.BLUE;
			
		}
		if (numeroTema == 3) {
			colores[0] = Color.BLUE;
			colores[1] = Color.RED;
			colores[2] = Color.BLUE;
			colores[3] = Color.BLACK;
			colores[4] = Color.WHITE;
			
		}
		return colores;
	}

	public void inicializarCtrlDominio() {
		/*try {
			cargaPartidasRankingGlobal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

	/**
	 * Obtiene la informacion de una partida especifica identificada por su ID.
	 *
	 * @param id ID de la partida a consultar.
	 * @return Arreglo de enteros que contiene la informacion de la partida en el siguiente orden:
	 *         [0] - Numero de turnos de la partida
	 *         [1] - Dificultad de la partida
	 *         [2] - Nivel de ayuda de la partida
	 *         [3] - Numero de colores de la partida
	 *         [4] - Longitud del codigo de la partida
	 *         [5] - Valor binario que indica si el rol del jugador es "Codemaker" (1) o "Codebreaker" (0)
	 *         [6] - Turno actual de la partida
	 */
	public int[] obtenInfoPartida(int id) {
		Partida p = usuarioActivo.ObtenPartida(id);
		//Partida p = partidas.get(id);
		int[] result = new int[7];
		result[0] = p.obtenNumeroTurnos();
		result[1] = p.obtenDificultad();
		result[2] = p.obtenNivelAyuda();
		result[3] = p.obtenNumeroColores();
		result[4] = p.obtenLongitudCodigo();
		if (p.esCodemaker()) result[5] = 1;
		else result[5] = 0;
		result[6] = p.obtenTurnoActual();
		return result;
	}
	
	/**
	 * Reinicia los records de movimientos a sus valores iniciales.
	 * Tambien se encarga de guardar los records reiniciados en la persistencia.
	 */
	public void reiniciarRecordsMovimientos() {
		recordsMovimientos.reset();
		try {
			ctrlPersistencia.escribeRecordsMovimientos(recordsMovimientos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Elimina el perfil del usuario activo.
	 * Este metodo requiere que el usuario este previamente autenticado.
	 * Elimina el perfil del usuario activo de la lista de perfiles y establece el usuario activo como nulo.
	 * Tambien se encarga de guardar los perfiles actualizados en la persistencia.
	 */
	public void eliminaPerfil() {
		Usuarios.remove(usuarioActivo.obtenNombreUsuario());
		usuarioActivo = null;
		try {
			GuardaPerfiles();
			//guardaPartidasPerfilActivo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Elimina todos los perfiles del sistema.
	 * Elimina todos los perfiles de la lista de usuarios y establece el usuario activo como nulo.
	 * Tambien se encarga de guardar los perfiles actualizados en la persistencia.
	 */
	public void eliminaTodosPerfiles() {
		Usuarios = new HashMap<String,Perfil>();
		usuarioActivo = null;
		try {
			GuardaPerfiles();
			//guardaPartidasPerfilActivo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Elimina una partida del perfil del usuario activo.
	 *
	 * @param idPartida ID de la partida a eliminar.
	 */
	public void eliminaPartidaPerfil(int idPartida) {
		usuarioActivo.eliminarPartida(idPartida);
		try {
			GuardaPerfiles();
			//guardaPartidasPerfilActivo();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * Elimina la partida activa del perfil del usuario activo sin guardarla.
	 * Establece la partida activa como nula y actualiza los perfiles en la persistencia.
	 */
	public void eliminarPartidaSinGuardar() {
		usuarioActivo.eliminarPartida(partidaActiva.obtenId());
		partidaActiva = null;
		try {
			GuardaPerfiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Actualiza el valor del ultimo ID de partida en base a los perfiles de usuario existentes.
	 * Recorre todos los perfiles de usuario y actualiza el valor de 'idUltimaPartida' si encuentra un ID mayor.
	 */
	public void actualizaUltimoIdPartida() {
		for(String usuario: Usuarios.keySet()) {
			if (idUltimaPartida < Usuarios.get(usuario).obtenIdUltimaPartida()) {
				idUltimaPartida = Usuarios.get(usuario).obtenIdUltimaPartida();
			}
		}		
	}
		
}
