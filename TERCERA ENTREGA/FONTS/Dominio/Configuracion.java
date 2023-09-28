package Dominio;

import java.io.Serializable;
import java.util.List;

import Excepciones.atributoConfiguracionInvalidoException;
import Excepciones.configuracionConValoresNullException;

/**
 * Clase configuracion de partidas.
 * @author Roger Marco
 *
 */
public class Configuracion implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 *  Nivel de dificultad.
	 */
	private int dificultad = 0; // IMPLEMENTADO AL ALGORISMO GENETICO MEDIANTE LA FUNCION
	/**
	 *  Booleano que indica si el jugador es Codemaker.
	 */
	private boolean esCodemaker = false; // CONTROLADO IMPLICITAMENTE EN LAS CONTRUCTORAS DE PARTIDA
	/**
	 *  Longitud de los codigos.
	 */
	private int longitudCodigo = 4; // CONTROLADO POR EXCEPCIONS EN PARTIDA
	// convertDifficultyToMaxGenerations() en Partida Codemaker.
	// Nivel de ayuda.
	private int nivelAyuda = 0;
	/**
	 *  Numero maximo de colores.
	 */
	private int numeroColores = 4; // CONTROLADO POR EXCEPCIONS EN PARTIDA
	/**
	 *  Numero maximo de turnos.
	 */
	private int numeroTurnos = 4; // CONTROLADO IMPLICITAMENTE EN LAS CONSTRUCTORAS DE PARTIDA

	/** Constructor mediante un parametro lista que contiene los valores de la configuracion.
	 *  @param config Configuracion
	 *  @throws Exception Si la configuracion es erronea salta error
	 */
	public Configuracion(List<Object> config) throws Exception {
		if (config == null)
			throw new configuracionConValoresNullException();
		numeroTurnos = (Integer) config.get(0);
		if (numeroTurnos <= 0 || numeroTurnos > 10)
			throw new atributoConfiguracionInvalidoException("numeroTurnos", numeroTurnos);

		dificultad = (Integer) config.get(1);
		if (dificultad < 0 || dificultad > 3)
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

		esCodemaker = (boolean) config.get(5);
	}

	/**
	 *  Obten la dificultad
	 * @return Devuelve la dificultad.
	 */
	public int obtenDificultad() {
		return dificultad;
	}

	/**
	 *  Obten si es Codemaker
	 * @return Devuelve si es codemaker.
	 */
	public boolean obtenEsCodemaker() {
		return esCodemaker;
	}

	/**
	 *  Obten la longitud del codigo
	 * @return Devuelve la longitud del codigo.
	 */
	public int obtenLongitudCodigo() {
		return longitudCodigo;
	}

	/**
	 *  Obten el nivel de ayuda
	 * @return Devuelve el nivel de ayuda.
	 */
	public int obtenNivelAyuda() {
		return nivelAyuda;
	}

	/**
	 *  Obten el numero de colores
	 * @return Devuelve el numero de colores.
	 */
	public int obtenNumeroColores() {
		return numeroColores;
	}

	/**
	 *  Obten el numero de turnos
	 * @return Devuelve el numero de turnos.
	 */
	public int obtenNumeroTurnos() {
		return numeroTurnos;
	}
	
	/**
	 *  Convierte a lista el conjunto de atributos de la clase
	 * @return Devuelve la configuracion en forma de lista de objetos.
	 */
	public List<Object> toList(){
		return List.of(numeroTurnos,dificultad,nivelAyuda,numeroColores,longitudCodigo,esCodemaker);
	}

	
	@Override
	/**
	 *  Convierte a string los atributos de la clase
	 *  @return Devuelve la string correspondiente al conjunto de atributos.
	 */
	public String toString() {
		return "Configuracion [dificultad=" + dificultad + ", esCodemaker=" + esCodemaker + ", longitudCodigo="
				+ longitudCodigo + ", nivelAyuda=" + nivelAyuda + ", numeroColores=" + numeroColores + ", numeroTurnos="
				+ numeroTurnos + "]";
	}
	
	

}