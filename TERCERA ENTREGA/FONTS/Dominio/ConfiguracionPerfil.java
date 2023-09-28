package Dominio;

import java.io.Serializable;

/**
 * Clase configuracion del perfil
 * @author Alex Pertusa
 *
 */

public class ConfiguracionPerfil implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 *  Identificador del tema
	 */
	private int tema;
	
	/**
	 *  Classe creadora de una configuracion de perfil
	 * @param pTema Tema de la nueva instancia.
	 */
	public ConfiguracionPerfil(int pTema) {
		this.tema = pTema;
	}

	/**
	 *  Funcion para obtener el tema
	 * @return Devuelve el tema de la configuracion.
	 */
	public int obtenTema() {
		return tema;
	}	

	/**
	 *  Funcion para modificar el tema
	 * @param pTema Tema a informar.
	 */
	public void informaTema(int pTema) {		
		this.tema = pTema;
	}

}