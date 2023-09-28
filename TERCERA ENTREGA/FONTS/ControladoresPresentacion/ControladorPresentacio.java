package ControladoresPresentacion;

import java.awt.Color;
import java.util.*;

import Vistas.*;
import ControladoresDominio.*;

/**
 * Controlador de Persistencia
 * 
 * @author Jordi Estany
 * @author Juan Carlos Rubio
 */

public class ControladorPresentacio {
	
	/** Instancia del controlador de dominio */
	private ControladorDominio ctrlDominio;
	
	/** Vista de la pantalla principal */
	private ControladorVistaPantallaPrincipal ctrlVistaPantallaPrincipal;
	
	/** Vista de la pantalla inicial de un usuario con sesion iniciada */
	private ControladorVistaSesion ctrlVistaSesion;
	
	/** Vista de la pantalla del manual */
	private VistaManual vistaManual;
	
	/** Vista del ranking global */
	private VistaRankingGlobal vistaRankingGlobal;
	
	/** Vista de records */
	private VistaRecords vistaRecords;
	
	/** Vista de una partida de timpo codeBreaker */
	private VistaPartidaCodeBreaker vistaPartidaCodeBreaker;
	
	/** Vista de una partida de timpo codeMaker */
	private VistaPartidaCodeMaker vistaCodeMaker;
	
	/** Vista para introducir el codigo a batir en una partida de tipo codeMaker */
	private VistaIntroducirCodigo vistaCodigoInicial;
	
	/** Vista para poder guardar la partida */
	private VistaGuardarPartida vistaGuardarPartida;
	
	/**
	 * Constructor de la clase ControladorPresentacio
	 * 
	 * Inicializa las instancias de los controladores de dominio y de las vistas
	 */
	public ControladorPresentacio() {
		ctrlDominio = ControladorDominio.obtenInstancia();
		ctrlVistaPantallaPrincipal = new ControladorVistaPantallaPrincipal(this);		
        this.vistaManual = new VistaManual(this);
        this.vistaRankingGlobal = new VistaRankingGlobal(this);
        this.vistaRecords = new VistaRecords(this);       
        }
	
	/**
	 * Inicializa la presentacion del juego, mostrando la pantalla principal
	 */
	public void inicializarPresentacion() {
		ctrlVistaPantallaPrincipal.iniciar();
	}
	
	/**
	 * Muestra la vista de seleccion del tipo de partida
	 */
	public void mostrarVistaTipoPartida() {
		ctrlVistaSesion.mostrarTipoPartida();
	
	}
	
	/**
	 * Muestra la vista de las partidas guardadas
	 */
	public void mostrarVistaPartidasGuardadas() {
		ctrlVistaSesion.mostrarPartidasGuardadas();
	
	}

	/**
	 * Registra a un usuario con el nombre de usuario y contrasena proporcionados
	 *
	 * @param usuario    Nombre de usuario a registrar
	 * @param contrasena Contrasena del usuario a registrar
	 * @return Codigo de resultado del registro (0 = exito, 1 = nombre de usuario ya existente, 2 = error de registro)
	 */
	public int registrarUsuario(String usuario, String contrasena) {		
		int resultadoRegistro = ctrlDominio.registrar(usuario, contrasena);
		if (resultadoRegistro == 0) {
			ctrlVistaSesion = new ControladorVistaSesion(this);
			ctrlVistaSesion.iniciar();
		}
		return resultadoRegistro;
	}

	/**
	 * Inicia sesion con el nombre de usuario y contrasena proporcionados
	 *
	 * @param usuario    Nombre de usuario para iniciar sesion
	 * @param contrasena Contrasena del usuario para iniciar sesion
	 * @return Codigo de resultado de inicio de sesion (0 = exito, -1 = error de inicio de sesion)
	 */
	public int iniciarSesion(String usuario, String contrasena) {
		int resultadoRegistro = ctrlDominio.iniciarSesion(usuario, contrasena);
		if (resultadoRegistro == 0) {
			ctrlVistaSesion = new ControladorVistaSesion(this);
			ctrlVistaSesion.iniciar();
		}
		return resultadoRegistro;
	}

	/**
	 * Cierra la sesion actual
	 */
	public void cerrarSesion() {
		ctrlDominio.cerrarSesion();
		ctrlVistaPantallaPrincipal.iniciar();	
		try {
			ctrlDominio.guardaRankingGlobal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Muestra la vista del manual de juego
	 */
	public void mostrarVistaManual() {
		vistaManual.hacerVisible();		
	}

	/**
	 * Vuelve a la vista anterior. Si no hay usuario activo, vuelve a la pantalla principal; de lo contrario, vuelve a la vista de sesion
	 */
	public void volver() {
		if (ctrlDominio.obtenUsuarioActivo() == null) ctrlVistaPantallaPrincipal.iniciar();	
		else ctrlVistaSesion.iniciar();		
	}

	/**
	 * Muestra la vista del ranking global
	 *
	 * @throws Exception Si ocurre un error al obtener el ranking
	 */
	public void mostrarVistaRankingGlobal() throws Exception {
		vistaRankingGlobal.hacerVisible(ctrlDominio.obtenRanking());		
	}
	
	/**
	 * Muestra la vista de los records del jugador activo
	 */
	public void mostrarVistaRecords() {
		vistaRecords.hacerVisible(ctrlDominio.obtenRecords());		
	}	
	
	/**
	 * Ejecuta un turno de juego con el codigo proporcionado
	 *
	 * @param codigo Codigo de colores para ejecutar el turno
	 * @return Resultado del turno (-1 = has perdido, 0 = continua, 1 = has ganado)
 */
	public int ejecutarTurno(List<Integer> codigo) {
		try {
			return ctrlDominio.ejecutarTurno(codigo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;		
	}

	/**
	 * Obtiene el tablero de resultados de la partida actual
	 *
	 * @return Tablero de resultados de la partida
	 */
	public List<List<Integer>> obtenTableroResultados() {
		return ctrlDominio.obtenTableroResultados();
	}

	/**
	 * Obtiene una pista para la partida actual
	 *
	 * @return Pista generada
	 */
	public List<Integer> obtenPista() {
		try {
			return ctrlDominio.obtenPista();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Obtiene la puntuacion de la partida actual
	 *
	 * @return Puntuacion de la partida
	 */
	public double obtenPuntuacion() {
		return ctrlDominio.obtenPuntuacion();
	}

	/**
	 * Obtiene la lista de partidas guardadas del perfil activo
	 *
	 * @return Lista de partidas guardadas
	 * @throws Exception Si ocurre un error al obtener las partidas guardadas
	 */
	public ArrayList<Integer> obtenPartidasGuardas() throws Exception {
		return ctrlDominio.obtenPartidasPerfilPorAcabar();
	}

	/**
	 * Carga una partida guardada con el ID proporcionado
	 *
	 * @param idPartida ID de la partida a cargar
	 */
	public void cargarPartida(int idPartida) {
		try {
			ctrlDominio.cargarPartida(idPartida);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int[] datos = ctrlDominio.obtenDatosPartida();
		cargarVistaPartida(datos[4], datos[3], datos[0], datos[2], datos[6], datos[5]);
		
	}
	
	/**
	 * Carga la vista de partida segun el rol del jugador
	 *
	 * @param pLongitudCodigo Longitud del codigo
	 * @param pNumeroColores Numero de colores disponibles
	 * @param pNumeroTurnos Numero de turnos de la partida
	 * @param pNivelAyuda Nivel de ayuda de la partida
	 * @param pTurnoActual Numero del turno actual
	 * @param codemaker Indicador de si la partida es de tipo codemaker (0 = codebreaker, 1 = codemaker)
	 */
	public void cargarVistaPartida(int pLongitudCodigo, int pNumeroColores,
    		int pNumeroTurnos, int pNivelAyuda ,int pTurnoActual, int codemaker) {
		Color[] tema = ctrlDominio.obtenTema();
		if(codemaker==0) {
			this.vistaPartidaCodeBreaker = new VistaPartidaCodeBreaker(this, pLongitudCodigo, pNumeroColores, pNumeroTurnos, pNivelAyuda,pTurnoActual+1, tema);		
			vistaPartidaCodeBreaker.hacerVisible();
			vistaPartidaCodeBreaker.cargar(ctrlDominio.obtenTableroAdivinanzas(), ctrlDominio.obtenTableroResultados());
		}
		else {
			this.vistaCodeMaker = new VistaPartidaCodeMaker(this, pLongitudCodigo, pNumeroColores, pNumeroTurnos, pNivelAyuda,pTurnoActual+1, tema);		
			vistaCodeMaker.hacerVisible(ctrlDominio.obtenCodigoOriginal(), ctrlDominio.obtenTableroAdivinanzas(), ctrlDominio.obtenTableroResultados());
			vistaCodeMaker.cargar(ctrlDominio.obtenTableroAdivinanzas(), ctrlDominio.obtenTableroResultados());
		}		
		
	}
	
	/**
	 * Obtiene el tema actual utilizado en el juego
	 *
	 * @return Tema actual
	 */
	public Color[] obtenTema() {
		return ctrlDominio.obtenTema();		
	}
	
	/**
	 * Actualiza el tema utilizado en el juego
	 *
	 * @param tema Numero del tema a utilizar
	 */
	public void actualizaTema(int tema) {
		try {
			ctrlDominio.informaTema(tema);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene la lista de temas disponibles
	 *
	 * @return Lista de temas
	 */
	public List<Color[]> obtenTemas() {
		return ctrlDominio.obtenTemas();
	}

	/**
	 * Obtiene el codigo inicial para la partida y carga la vista de introduccion de codigo
	 *
	 * @param pLongitudCodigo Longitud del codigo
	 * @param pNumeroColores Numero de colores disponibles
	 * @param pNumeroTurnos Numero de turnos de la partida
	 * @param pNivelAyuda Nivel de ayuda de la partida
	 * @param pTurnoActual Numero del turno actual
	 * @param nivelDificultad Nivel de dificultad de la partida
	 */
	public void obtenCodigoInicial(int pLongitudCodigo, int pNumeroColores,
    		int pNumeroTurnos, int pNivelAyuda ,int pTurnoActual, int nivelDificultad) {
		Color[] tema = ctrlDominio.obtenTema();
		this.vistaCodigoInicial = new VistaIntroducirCodigo(this, tema, pNumeroColores, pLongitudCodigo);  
		vistaCodigoInicial.hacerVisible();
		List<Object> listaObjetos = new ArrayList<Object>();
		listaObjetos.add(pNumeroTurnos);
		listaObjetos.add(nivelDificultad);
		listaObjetos.add(pNivelAyuda);
		listaObjetos.add(pNumeroColores);		
		listaObjetos.add(pLongitudCodigo);
		listaObjetos.add(true);
		try {
			ctrlDominio.creaPartida(listaObjetos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.vistaCodeMaker = new VistaPartidaCodeMaker(this, pLongitudCodigo, pNumeroColores, pNumeroTurnos, pNivelAyuda,pTurnoActual,tema);
	}
	
	/**
	 * Inicializa la partida en modo Codebreaker
	 *
	 * @param pLongitudCodigo Longitud del codigo
	 * @param pNumeroColores Numero de colores disponibles
	 * @param pNumeroTurnos Numero de turnos de la partida
	 * @param pNivelAyuda Nivel de ayuda de la partida
	 * @param pTurnoActual Numero del turno actual
	 */	
	public void inicializarPartidaCodeBreaker(int pLongitudCodigo, int pNumeroColores,
    		int pNumeroTurnos, int pNivelAyuda ,int pTurnoActual) {
		Color[] tema = ctrlDominio.obtenTema();
		List<Object> listaObjetos = new ArrayList<Object>();
		listaObjetos.add(pNumeroTurnos);
		listaObjetos.add(0);
		listaObjetos.add(pNivelAyuda);
		listaObjetos.add(pNumeroColores);		
		listaObjetos.add(pLongitudCodigo);
		listaObjetos.add(false);
		try {
			ctrlDominio.creaPartida(listaObjetos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.vistaPartidaCodeBreaker = new VistaPartidaCodeBreaker(this, pLongitudCodigo, pNumeroColores, pNumeroTurnos, pNivelAyuda,pTurnoActual, tema);
		vistaPartidaCodeBreaker.hacerVisible();
		
	}

	/**
	 * Metodo para mostrar la vista de la partida
	 */
	public void muestraPartida() {
		vistaCodeMaker.hacerVisible(ctrlDominio.obtenCodigoOriginal(), ctrlDominio.obtenTableroAdivinanzas(), ctrlDominio.obtenTableroAdivinanzas());		
	}
	
	/**
	 * Método para informar el codigo secreto ingresado por el jugador
	 *
	 * @param codigo Codigo secreto ingresado por el jugador
	 */
	public void informaCodigo(List<Integer> codigo) {
		try {
			ctrlDominio.ejecutarTurno(codigo);
			ctrlDominio.ejecutarTurno(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para enviar la correccion al controlador de dominio
	 *
	 * @param correccion Correccion de la jugada realizada
	 * @throws Exception Si ocurre un error al informar la correccion
	 */
	public void enviarCorreccion(List<Integer> correccion) throws Exception {
		ctrlDominio.informaResultado(correccion);
		
	}

	/**
	 * Metodo para obtener la respuesta de la maquina para el turno actual
	 *
	 * @param turnoActual Turno actual de la partida
	 * @return Lista de enteros que representa la respuesta de la maquina
	 */
	public List<Integer> obtenRespuestaMaquina(int turnoActual) {
		try {			
			return ctrlDominio.obtenTableroAdivinanzas().get(turnoActual);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo para procesar el turno actual de la partida
	 *
	 * @return Codigo de resultado del turno
	 * @throws Exception Si ocurre un error al procesar el turno
	 */
	public int procesar() throws Exception {
		return ctrlDominio.ejecutarTurno(null);		
	}

	/**
	 * Metodo para obtener los datos de la partida
	 *
	 * @param id Identificador de la partida
	 * @return Vector de enteros que contiene los datos de la partida
	 */
	public int[] obtenDatosPartida(Integer id) {
		return ctrlDominio.obtenInfoPartida(id);
	}

	/**
	 * Metodo para obtener las estadísticas del jugador
	 *
	 * @return Vector de enteros que contiene las estadisticas del jugador
	 */
	public int[] obtenEstadisticas() {
		try {
			return ctrlDominio.obtenEstadisticas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Metodo para obtener el porcentaje de victorias del jugador
	 *
	 * @return Porcentaje de victorias del jugador
	 */
	public double obtenPorcentajeDeVictorias() {
		return ctrlDominio.obtenPorcentajeDeVictorias();
	}

	/**
	 * Metodo para guardar las partidas
	 */
	public void guardarPartidas() {
		try {
			//ctrlDominio.guardaPartidasPerfilActivo();
			ctrlDominio.GuardaPerfiles();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Metodo para mostrar la vista para guardar la partida
	 */
	public void mostrarVistaGuardarPartida() {
		this.vistaGuardarPartida = new VistaGuardarPartida(this);
		vistaGuardarPartida.hacerVisible();
		
	}

	/**
	 * Metodo para eliminar la partida sin guardar
	 */
	public void eliminarPartida() {
		ctrlDominio.eliminarPartidaSinGuardar();		
	}

	/**
	 * Metodo para eliminar una partida especifica
	 *
	 * @param idPartida Identificador de la partida a eliminar
	 */
	public void eliminarPartida(int idPartida) {
		ctrlDominio.eliminaPartidaPerfil(idPartida);
		
	}

}
