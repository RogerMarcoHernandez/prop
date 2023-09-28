package Dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase contenedora del estado de la partida en forma de tablero.
 * @author Roger Marco
 *
 */
public class Tablero implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 *  Numero del turno en el que se encuentra la partida.
	 */
	private int numeroTurnoActual = 0;
	/**
	 *  Matriz correspondiente al tablero donde se informan las adivinanzas.
	 */
	private List<List<Integer>> tablero;
	/**
	 *  Matriz correspondiente al tablero donde se informan los resultados relativos a las adivinanzas.
	 */
	private List<List<Integer>> tableroResultados;

	/**
	 *  Constructor de tablero e inicializador, tanto del tablero de adivinanzas como el de resultados.
	 * @param longitudCodigo Longitud de los codigos del tablero.
	 * @param numeroTurnos Numero de turnos de la partida.
	 */
	public Tablero(int longitudCodigo, int numeroTurnos) {
		tablero = new ArrayList<>(numeroTurnos);
		tableroResultados = new ArrayList<>(numeroTurnos);

		for (int i = 0; i < numeroTurnos; i++) {

			tablero.add(new ArrayList<>(longitudCodigo));
			tableroResultados.add(new ArrayList<>(longitudCodigo));

		}
	}

	/**
	 *  Incrementa el numero de turno actual en uno.
	 */
	public void avanzarTurno() {
		numeroTurnoActual++;
	}

	/**
	 *  Informa la adivinanza en el tablero en el turno actual
	 * @param adivinanza Adivinanza a informar.
	 */
	public void informaAdivinanza(List<Integer> adivinanza) {
		this.tablero.set(numeroTurnoActual, adivinanza);
	}

	/**
	 *  Informa el resultado en el tablero de resultados en el turno actual
	 * @param resultado Resultado a informar.
	 */
	public void informaResultado(List<Integer> resultado) {
		this.tableroResultados.set(numeroTurnoActual, resultado);
	}

	/**
	 *  Obten la adivinanza del turno actual
	 * @return Devuelve la adivinanza del turno actual.
	 */
	public List<Integer> obtenAdivinanza() {
		return tablero.get(numeroTurnoActual);
	}

	/**
	 *  Obten la adivinanza del turno anterior
	 * @return Devuelve la adivinanza del turno anterior.
	 */
	public List<Integer> obtenAdivinanzaAnterior() {
		return tablero.get(numeroTurnoActual - 1);
	}

	/**
	 *  Obten el resultado del turno actual
	 * @return Devuelve el resultado del turno actual.
	 */
	public List<Integer> obtenResultado() {
		return tableroResultados.get(numeroTurnoActual);
	}

	/**
	 *  Obten el resultado del turno anterior
	 * @return Devuelve el resultado del turno anterior.
	 */
	public List<Integer> obtenResultadoAnterior() {
		return tableroResultados.get(numeroTurnoActual - 1);
	}

	/**
	 *  Obten el tablero completo de adivinanzas
	 * @return Devuelve el tablero de adivinanzas.
	 */
	public List<List<Integer>> obtenTableroAdivinanzas() {
		return tablero;
	}

	/**
	 *  Obten el tablero completo de resultados
	 * @return Devuelve el tablero de resultados.
	 */
	public List<List<Integer>> obtenTableroResultados() {
		return tableroResultados;
	}

	/**
	 *  Obten el numero de turno actual
	 * @return Devuelve el turno actual.
	 */
	public int obtenTurnoActual() {
		return numeroTurnoActual;
	}
	
	/**
	 *  Informa el turno actual indicado
	 * @param turnoActual Turno actual a informar.
	 */
	public void informaTurnoActual(Integer turnoActual) {
		this.numeroTurnoActual=turnoActual;
	}

	
	@Override
	/**
	 *  Convierte a string el conjunto de atributos de la clase
	 */
	public String toString() {
		return "Tablero [numeroTurnoActual=" + numeroTurnoActual + ", tablero=" + tablero.toString() + ", tableroResultados="
				+ tableroResultados.toString() + "]";
	}

	/**
	 *  Informa el tablero de adivinanzas indicado
	 * @param tableroAdivinanzas Tablero de adivinanzas a informar.
	 */
	public void informaTableroAdivinanzas(List<List<Integer>> tableroAdivinanzas) {
		this.tablero = tableroAdivinanzas;
	}

	/**
	 *  Informa el tablero de resultados indicado
	 * @param tableroResultados Tablero de resultados a informar.
	 */
	public void informaTableroResultados(List<List<Integer>> tableroResultados) {
		this.tableroResultados = tableroResultados;
	}
	
	

}