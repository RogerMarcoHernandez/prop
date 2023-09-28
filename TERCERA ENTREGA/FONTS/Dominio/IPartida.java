package Dominio;

import java.util.List;

/**
 * Clase interfaz de partida.
 * @author Roger Marco
 *
 */
public interface IPartida {
	/**
	 * Ejecutadora de turno.
	 * @param codigo Codigo del turno.
	 * @return Devuelve el estado de la partida -1/0/1.
	 * @throws Exception En caso de codigo o ejecucion de turno invalida.
	 */
	public int ejecutarTurno(List<Integer> codigo) throws Exception;
	
	/**
	 * Calculadora de puntuacion.
	 * @return Devuelve la puntuacion calculada.
	 */
	public double calculaPuntuacion();
	
	/**
	 * Obtenedora de partida.
	 * @return Devuelve la partida.
	 */
	public Partida obtenPartida();
}