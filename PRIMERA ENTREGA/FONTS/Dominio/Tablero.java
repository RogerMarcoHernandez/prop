package Dominio;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
	// Numero del turno en el que se encuentra la partida.
	private int numeroTurnoActual = 0;
	// Matriz correspondiente al tablero donde se informan las adivinanzas.
	private List<List<Integer>> tablero;
	// Matriz correspondiente al tablero donde se informan los resultados relativos
	// a las adivinanzas.
	private List<List<Integer>> tableroResultados;

	// Constructor de tablero e inicializador, tanto del tablero de adivinanzas como
	// el de resultados.
	public Tablero(int longitudCodigo, int numeroTurnos) {
		tablero = new ArrayList<>(numeroTurnos);
		tableroResultados = new ArrayList<>(numeroTurnos);

		for (int i = 0; i < numeroTurnos; i++) {

			tablero.add(new ArrayList<>(longitudCodigo));
			tableroResultados.add(new ArrayList<>(longitudCodigo));

		}
	}

	// Incrementa el numero de turno actual en uno.
	public void avanzarTurno() {
		numeroTurnoActual++;
	}

	// Informa la adivinanza en el tablero en el turno actual
	public void informaAdivinanza(List<Integer> adivinanza) {
		this.tablero.set(numeroTurnoActual, adivinanza);
	}

	// Informa el resultado en el tablero de resultados en el turno actual
	public void informaResultado(List<Integer> resultado) {
		this.tableroResultados.set(numeroTurnoActual, resultado);
	}

	// Obten la adivinanza del turno actual
	public List<Integer> obtenAdivinanza() {
		return tablero.get(numeroTurnoActual);
	}

	// Obten la adivinanza del turno anterior
	public List<Integer> obtenAdivinanzaAnterior() {
		return tablero.get(numeroTurnoActual - 1);
	}

	// Obten el resultado del turno actual
	public List<Integer> obtenResultado() {
		return tableroResultados.get(numeroTurnoActual);
	}

	// Obten el resultado del turno anterior
	public List<Integer> obtenResultadoAnterior() {
		return tableroResultados.get(numeroTurnoActual - 1);
	}

	// Obten el tablero completo de adivinanzas
	public List<List<Integer>> obtenTableroAdivinanzas() {
		return tablero;
	}

	// Obten el tablero completo de resultados
	public List<List<Integer>> obtenTableroResultados() {
		return tableroResultados;
	}

	// Obten el numero de turno actual
	public int obtenTurnoActual() {
		return numeroTurnoActual;
	}

}