package ControladoresPresentacion;

import Vistas.VistaPantallaPrincipal;
import Vistas.VistaRegistrarse;

import javax.swing.JOptionPane;

import Vistas.VistaIniciarSesion;

/**
 * Controlador de la vista pantalla principal
 * 
 * @author Jordi Estany
 * @author Juan Carlos Rubio
 */

public class ControladorVistaPantallaPrincipal {
		
	/** Vista de la pantalla principal	 */
	private VistaPantallaPrincipal vista;

	/** Vista de registro de usuario	 */
	private VistaRegistrarse vistaRegistro;

	/** Vista de inicio de sesión	 */
	private VistaIniciarSesion vistaIniciarSesion;

	/** Controlador de la capa de presentación	 */
	private ControladorPresentacio ctrlPresentacio;
	
	/**
	 * Constructor de la clase
	 *
	 * @param pCtrlPresentacip Controlador de la clase
	 */
    public ControladorVistaPantallaPrincipal(ControladorPresentacio pCtrlPresentacip) {
        this.vista = new VistaPantallaPrincipal(this);
        this.vistaRegistro = new VistaRegistrarse(this);
        this.vistaIniciarSesion = new VistaIniciarSesion(this);
        this.ctrlPresentacio = pCtrlPresentacip;
    }

    /**
     * Metodo para iniciar la vista de la pantalla principal
     */
    public void iniciar() {
        vista.hacerVisible();
    }

    /**
     * Metodo para mostrar la vista de registro de usuario
     */
    public void mostrarVistaRegistro() {
        vista.setVisible(false);
        vistaRegistro.hacerVisible();
    }
    
    /**
     * Metodo para mostrar la vista de inicio de sesion
     */
    public void mostrarVistaIniciarSesion() {
        vista.setVisible(false);
        vistaIniciarSesion.hacerVisible();
    }

    /**
     * Metodo para registrar un usuario
     *
     * @param usuario    Nombre de usuario
     * @param contrasena Contrasena del usuario
     * @return Resultado del registro
     */
	public int registrarUsuario(String usuario, String contrasena) {
		return ctrlPresentacio.registrarUsuario(usuario, contrasena);		
	}

	/**
	 * Metodo para iniciar sesion de un usuario
	 *
	 * @param usuario    Nombre de usuario
	 * @param contrasena Contrasena del usuario
	 * @return Resultado del inicio de sesion
	 */
	public int iniciarSesionUsuario(String usuario, String contrasena) {
		return ctrlPresentacio.iniciarSesion(usuario, contrasena);
	}

	/**
	 * Metodo para mostrar la vista del manual
	 */
	public void mostrarVistaManual() {
		vista.setVisible(false);
		ctrlPresentacio.mostrarVistaManual();		
	}

	/**
	 * Metodo para mostrar la vista del ranking global
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
	 * Metodo para mostrar la vista de los records
	 */
	public void mostrarVistaRecords() {
		ctrlPresentacio.mostrarVistaRecords();
		vista.setVisible(false);		
	}	
	
}
