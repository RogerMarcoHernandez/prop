package Dominio;

import java.io.Serializable;

/**
 * La clase RankingGlobal representa el Ranking de jugadores en el sistema.
 * @author Jordi Estany
 */

public class RankingGlobal implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** Instancia del ranking */
	transient private static RankingGlobal ranking;

	/** Lista de las partidas del ranking */
	private Partida[] partidas;

	/**
	 * Inicializa el conjunto de partidas con un conjunto vacio
	 */
	private RankingGlobal() {
		partidas = new Partida[10];
	}
	
	/**
	* Funcion para obtener la instancia del RankingGlobal.
	* Si la instancia no existe, se crea una nueva instancia y se devuelve.
	* @return la instancia del RankingGlobal.
	*/
	public static RankingGlobal obtenInstancia() {
		if (ranking == null)
			ranking = new RankingGlobal();
		return ranking;
	}
	
	/**
	 * Funcion para anadir una partida al RankingGlobal.
	 *
	 * @param partida la partida a anadir.
	 * @return true si la partida se anadio correctamente, false de lo contrario.
	 */
	public boolean anadirPartida(Partida partida) {
		int size = 0;
		for (int i = 0; i < 10; i++) {
			if (partidas[i] != null) size++;
		}	
		
		int pos = 0;
		for (int j = 0; j < size; j++) {
			if(partida.obtenPuntuacion() <= partidas[j].obtenPuntuacion()) pos++;
		}
		int v = size-1;
		if (v > 8) v = 8;
		for(int x = v; x >= 0; x--) {
			partidas[x + 1] = partidas[x];
		}
		if(pos < 10 && partida.obtenPuntuacion() > 0) {
			partidas[pos] = partida;
			return true;
		}
		return false;
	}
	
	/**
	 * Funcion para obtener todas las partidas del RankingGlobal.
	 *
	 * @return un array de tipo Partida que contiene todas las partidas del RankingGlobal.
	 */
	public Partida[] obtenPartidas() {
		return partidas;
	}

	/**
	 * Metodo para informar al RankingGlobal sobre las partidas.
	 *
	 * @param partidas el array de tipo Partida que se utilizara para actualizar las partidas del RankingGlobal.
	 */
	public void informaPartidas(Partida[] partidas) {
		this.partidas = partidas;		
	}
	
	/**
	 * Metodo para restablecer el RankingGlobal, eliminando todas las partidas almacenadas.
	 * Se inicializa el conjunto de partidas con un nuevo conjunto de tamano 10 vacio.
	 */
	public void reset() {
		this.partidas = new Partida[10];
	}
	

}
