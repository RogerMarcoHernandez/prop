package Dominio;

import java.io.Serializable;


/**
 * Clase que representa las estadisticas de un perfil
 * @author Alex Pertusa
 */

public class Estadisticas implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Numero de partidas ganadas.
	 */
	private int partidasGanadas;
	
	/**
	 * Numero de partidas perdidas.
	 */
	private int partidasPerdidas;
	
	/**
	 * Puntuacion de la mejor partida.
	 */
	private double mejorPuntuacion;
	
	/**
	 * Id de la mejor partida.
	 */
	private int idMejorPartida;

	/**
	 * Constructor de la clase Estadisticas
	 * Inicializa todas las estadisticas a cero
	 */
	public Estadisticas() {
		this.partidasGanadas = 0;
		this.partidasPerdidas = 0;
		this.mejorPuntuacion = 0;
		this.idMejorPartida = -1;
	}

	/**
	 * Metodo para obtener el numero de partidas jugadas
	 * @return Numero de partidas jugadas
	 */
	public int obtenPartidasJugadas() {
		return partidasGanadas + partidasPerdidas;
	}

	/**
	 * Metodo que obtiene el numero de partidas ganadas
	 * @return Numero de partidas ganadas
	 */
	public int obtenPartidasGanadas() {
		return partidasGanadas;
	}

	/**
	 * Metodo que modifica el numero de partidas ganadas
	 * @param pPartidasGanadas Nuevo numero de partidas ganadas
	 */
	public void informaPartidasGanadas(int pPartidasGanadas) {
		this.partidasGanadas = pPartidasGanadas;
	}
	
	/**
	 * Metodo que obtiene el id de la mejor partida
	 * @return Id de la mejor partida
	 */
	public int obtenIdMejorPartida() {
		return idMejorPartida;
	}

	/**
	 * Metodo que obtiene el numero de partidas perdidas
	 * @return Numero de partidas perdidas
	 */
	public int obtenPartidasPerdidas() {
		return partidasPerdidas;
	}

	/**
	 * Metodo que modifica el numero de partidas perdidas
	 * @param pPartidasPerdidas Nuevo numero de partidas perdidas
	 */
	public void informaPartidasPerdidas(int pPartidasPerdidas) {
		this.partidasPerdidas = pPartidasPerdidas;
	}

	/**
	 * Metodo que obtiene la mejor puntuacion
	 * @return Mejor puntuacion
	 */
	public double obtenMejorPuntuacion() {
		return mejorPuntuacion;
	}

	/**
	 * Metodo que comprueba y modifica la puntuacion y su correspondiente partida si es mayor a la mejor puntuacion actual
	 * @param pPuntuacion Nueva puntuacion
	 * @param pIdPartida Id de la partida correspondiente a la nueva puntuacion
	 */
	public void informaPuntuacion(double pPuntuacion, int pIdPartida) {
		if (this.mejorPuntuacion < pPuntuacion) {
			this.mejorPuntuacion = pPuntuacion;
			this.idMejorPartida = pIdPartida;
		}
	}
	
	/**
	 * Metodo que anade una partida perdida
	 */
	public void anadirPartidaPerdida() {
		this.partidasPerdidas += 1;
	}

	/**
	 * Metodo que anade una partida ganada
	 */
	public void anadirPartidaGanada() {
		this.partidasGanadas += 1;
	}

	/**
	 * Metodo que obtiene el porcentaje de victorias
	 * @return Porcentaje de victorias
	 */
	public double obtenPorcentajeVictoria() {
		if (obtenPartidasJugadas() == 0) return 0.0;
		return ((double) this.partidasGanadas / (this.partidasGanadas + this.partidasPerdidas)) * 100;
	}

}
