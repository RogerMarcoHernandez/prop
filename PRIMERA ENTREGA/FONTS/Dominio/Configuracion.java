package Dominio;

import java.util.List;

import Excepciones.atributoConfiguracionInvalidoException;
import Excepciones.configuracionConValoresNullException;

public class Configuracion {
	// Nivel de dificultad.
	private int dificultad = 0; // IMPLEMENTADO AL ALGORISMO GENETICO MEDIANTE LA FUNCION
	// Booleano que indica si el jugador es Codemaker.
	private boolean esCodemaker = false; // CONTROLADO IMPLICITAMENTE EN LAS CONTRUCTORAS DE PARTIDA
	// Longitud de los codigos.
	private int longitudCodigo = 4; // CONTROLADO POR EXCEPCIONS EN PARTIDA
	// convertDifficultyToMaxGenerations() en Partida Codemaker.
	// Nivel de ayuda.
	private int nivelAyuda = 0;
	// Numero maximo de colores.
	private int numeroColores = 4; // CONTROLADO POR EXCEPCIONS EN PARTIDA
	// Numero maximo de turnos.
	private int numeroTurnos = 4; // CONTROLADO IMPLICITAMENTE EN LAS CONSTRUCTORAS DE PARTIDA

	// Constructor mediante un parametro lista que contiene los valores de la
	// configuracion.
	public Configuracion(List<Object> config) throws Exception {
		if (config == null)
			throw new configuracionConValoresNullException();
		numeroTurnos = (Integer) config.get(0);
		if (numeroTurnos <= 0 || numeroTurnos > 10)
			throw new atributoConfiguracionInvalidoException("numeroTurnos", numeroTurnos);

		dificultad = (Integer) config.get(1);
		if (dificultad < 0 || dificultad > 2)
			throw new atributoConfiguracionInvalidoException("dificultad", dificultad);

		nivelAyuda = (Integer) config.get(2);
		if (nivelAyuda < 0 || nivelAyuda > 2)
			throw new atributoConfiguracionInvalidoException("nivelAyuda", nivelAyuda);

		numeroColores = (Integer) config.get(3);
		if (numeroColores <= 1 || numeroColores > 5)
			throw new atributoConfiguracionInvalidoException("numeroColores", numeroColores);

		longitudCodigo = (Integer) config.get(4);
		if (longitudCodigo <= 0 || longitudCodigo > 5)
			throw new atributoConfiguracionInvalidoException("longitudCodigo", longitudCodigo);

		esCodemaker = (Boolean) config.get(5);
	}

	public int obtenDificultad() {
		return dificultad;
	}

	public boolean obtenEsCodemaker() {
		return esCodemaker;
	}

	public int obtenLongitudCodigo() {
		return longitudCodigo;
	}

	public int obtenNivelAyuda() {
		return nivelAyuda;
	}

	public int obtenNumeroColores() {
		return numeroColores;
	}

	public int obtenNumeroTurnos() {
		return numeroTurnos;
	}

}