package ControladoresPresentacion;

import Vistas.VistaSesion;
import Vistas.VistaTemas;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import Vistas.VistaConfiguracionPerfil;
import Vistas.VistaIniciarSesion;
import Vistas.VistaJugar;
import Vistas.VistaPartidasGuardadas;
import Vistas.VistaSeleccionarTipoPartida;
import Vistas.VistaConfiguracionPartida;
import Vistas.VistaEstadisticas;

/**
 * Controlador de la vista de sesion
 * 
 * @author Jordi Estany
 * @author Juan Carlos Rubio
 */

public class ControladorVistaSesion {

	/** Vista de la sesion */
	private VistaSesion vista;

	/** Vista de configuracion de perfil */
	private VistaConfiguracionPerfil vistaConfiguracion;

	/** Controlador de presentacion */
	private ControladorPresentacio ctrlPresentacio;

	/** Vista de jugar */
	private VistaJugar vistaJugar;

	/** Vista de seleccion de tipo de partida */
	private VistaSeleccionarTipoPartida vistaSeleccionarTipo;

	/** Vista de configuracion de partida */
	private VistaConfiguracionPartida vistaConfiguracionPartida;

	/** Vista de partidas guardadas */
	private VistaPartidasGuardadas vistaPartidasGuardadas;

	/** Vista de estadisticas */
	private VistaEstadisticas vistaEstadisticas;

	/** Vista de temas */
	private VistaTemas vistaTemas;

	/**
     * Constructor de la clase
     *
     * @param pCtrlPresentacip Controlador de la clase
     */
    public ControladorVistaSesion(ControladorPresentacio pCtrlPresentacip) {
    	Color[] tema = pCtrlPresentacip.obtenTema();
        this.vista = new VistaSesion(this);
        this.vistaConfiguracion = new VistaConfiguracionPerfil(this, tema);
        this.ctrlPresentacio = pCtrlPresentacip;
        this.vistaJugar = new VistaJugar(this);
        this.vistaSeleccionarTipo = new VistaSeleccionarTipoPartida(this);
        this.vistaConfiguracionPartida = new VistaConfiguracionPartida(this);
    }

    /**
     * Haace visible la vista de sesion
     */
    public void iniciar() {
        vista.hacerVisible();
    }


/**
 * Cierra la sesion actual del usuario
 */
	public void cerrarSesion() {		
		ctrlPresentacio.cerrarSesion();
	}
	
	/**
	 * Muestra la vista de configuración de perfil
	 */
    public void mostrarVistaConfiguracion() {
        vista.setVisible(false);
        vistaConfiguracion.hacerVisible();
    }

    /**
     * Muestra la vista del manual de instrucciones
     */
	public void mostrarVistaManual() {
		vista.setVisible(false);
		ctrlPresentacio.mostrarVistaManual();	
	}
	
	/**
	 * Muestra la vista para escoger si cargar una partida o crear una nueva
	 */
    public void mostrarVistaJugar() {
        vista.setVisible(false);
        vistaJugar.hacerVisible();
    }

    /**
     * Muestra la vista del ranking global
     * 
     * En caso de que el ranking este vacio muestra un mensaje de error
     */
	public void mostrarVistaRankingGlobal() {
		try {
			ctrlPresentacio.mostrarVistaRankingGlobal();
			vista.setVisible(false);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ranking vacio");
		}		
	}
	
	/**
	 * Muestra la vista de los records
	 */
	public void mostrarVistaRecords() {
		ctrlPresentacio.mostrarVistaRecords();
		vista.setVisible(false);		
	}

	/**
	 * Muestra la vista de seleccion del tipo de partida
	 */
	public void mostrarTipoPartida() {
		vistaSeleccionarTipo.hacerVisible();		
	}
	
	/**
	 * Muestra la vista de configuracion de partida
	 * @param codemaker Indica si la partida sera de tipo codemaker o codebreaker
	 */
	public void mostrarConfigurarPartida(boolean codemaker) {
		vistaConfiguracionPartida.hacerVisible(codemaker);		
	}

	/**
	 * Empieza una partida con los parametros especificados
	 * 
	 * @param pLongitudCodigo Longitud del codigo
	 * @param pNumeroColores Numero de colores disponibles
	 * @param pNumeroTurnos Numero de turnos permitidos
	 * @param pNivelAyuda Nivel de ayuda deseado
	 * @param codeMaker Indica si la partida sera de tipo codemaker o codebreaker
	 * @param nivelDificultad Nivel de dificultad de la partida
	 */
	public void empezarPartida(int pLongitudCodigo, int pNumeroColores,
			int pNumeroTurnos, int pNivelAyuda, boolean codeMaker, int nivelDificultad) {
		if(!codeMaker) ctrlPresentacio.inicializarPartidaCodeBreaker(pLongitudCodigo, pNumeroColores, pNumeroTurnos,pNivelAyuda, 1);
		else ctrlPresentacio.obtenCodigoInicial(pLongitudCodigo, pNumeroColores, pNumeroTurnos,pNivelAyuda, 1, nivelDificultad);
		
	}
	
	/**
	 * Muestra la vista de las partidas guardadas.
	 */
	public void mostrarPartidasGuardadas() {
        try {
			this.vistaPartidasGuardadas = new VistaPartidasGuardadas(this);
			vistaPartidasGuardadas.hacerVisible();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No hay partidas sin acabar");
			iniciar();
		}				
	}

	/**
	 * Obtiene las partidas guardadas
	 * 
	 * @return Lista de identificadores de las partidas guardadas
	 * @throws Exception Si no hay partidas guardadas
	 */
	public ArrayList<Integer> obtenPartidasGuardadas() throws Exception {
		return ctrlPresentacio.obtenPartidasGuardas();		
	}

	/**
	 * Carga la partida con el identificador especificado
	 * 
	 * @param idPartida Identificador de la partida a cargar
	 */
	public void cargarPartida(int idPartida) {
		ctrlPresentacio.cargarPartida(idPartida);		
	}
	

	/**
	 * Muestra la vista de temas.
	 */
	public void mostrarVistaTemas() {
		List<Color[]> temas = ctrlPresentacio.obtenTemas();
		this.vistaTemas = new VistaTemas(temas, this);
		vistaTemas.hacerVisible();
	}

	/**
	 * Actualiza el tema de la aplicacion
	 * 
	 * @param pTema Indice del tema seleccionado
	 */
	public void actualizaTema(int pTema) {	
		ctrlPresentacio.actualizaTema(pTema);
		Color[] tema = ctrlPresentacio.obtenTema();
		vistaConfiguracion.actualizar(tema);
	}

	/**
	 * Obtiene los datos de una partida especificada por su identificador
	 * 
	 * @param id Identificador de la partida.
	 * @return Vector con los datos de la partida.
	 */
	public int[] obtenDatosPartida(Integer id) {
		return ctrlPresentacio.obtenDatosPartida(id);
	}
	
	/**
	 * Obtiene las estadisticas del usuario actual
	 * @return Vector con las estadisticas del usuario
	 */
	public int[] obtenEstadisticas() {
		return ctrlPresentacio.obtenEstadisticas();
	}

	/**
	 * Muestra la vista de estadisticas
	 */
	public void mostrarVistaEstadisticas() {
		this.vistaEstadisticas = new VistaEstadisticas(this);
		vistaEstadisticas.hacerVisible();		
	}

	/**
	 * Obtiene el porcentaje de victorias del usuario actual
	 * 
	 * @return Porcentaje de victorias
	 */
	public double obtenWr() {
		return ctrlPresentacio.obtenPorcentajeDeVictorias();
	}

	/**
	 * Elimina una partida especificada por su identificador
	 * 
	 * @param idPartida Identificador de la partida a eliminar
	 */
	public void eliminarPartida(int idPartida) {
		ctrlPresentacio.eliminarPartida(idPartida);		
	}
	
}
