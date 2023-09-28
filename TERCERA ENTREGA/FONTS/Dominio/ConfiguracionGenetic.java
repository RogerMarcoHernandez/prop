package Dominio;

/**
 * Clase configuracion de la maquina genetic.
 * @author Roger Marco
 *
 */
public class ConfiguracionGenetic {
	/** Numero de colores de la partida */
	private static int numeroColores = 4;
	/** Longitud del codigo de la partida */
	private static int longitudCodigo = 4;
	/** Generaciones maximas */
	private static int maxGeneraciones = 1000; // maxSteps
	/** Numero de codigos de poblacion */
	private static int numeroCodigosPoblacion = 200; // 200
	/** Dificultad de la partida */
	private static int dificultad = 0;

	/**
	 * Obtenedora de la dificultad.
	 * @return Devuelve la dificultad.
	 */
	public static int obtenDificultad() {
		return dificultad;
	}

	/**
	 * Informadora de dificultad.
	 * @param dificultad Dificultad a informar.
	 */
	public static void informaDificultad(int dificultad) {
		ConfiguracionGenetic.dificultad = dificultad;
	}

	/**
	 * Informadora de numero de colores.
	 * @param numeroColores Numero de colores a informar.
	 */
	public static void informaNumeroColores(int numeroColores) {
		ConfiguracionGenetic.numeroColores = numeroColores;
	}

	/**
	 * Informadora de longitud de codigo.
	 * @param longitudCodigo Longitud de codigo a informar.
	 */
	public static void informaLongitudCodigo(int longitudCodigo) {
		ConfiguracionGenetic.longitudCodigo = longitudCodigo;
	}

	/**
	 * Informadora de generaciones maximas.
	 * @param maxGeneraciones Maximas generaciones a informar.
	 */
	public static void informaMaxGeneraciones(int maxGeneraciones) {
		ConfiguracionGenetic.maxGeneraciones = maxGeneraciones;
	}

	/**
	 * Obtenedora de generaciones maximas.
	 * @return Devuelve las generaciones maximas.
	 */
	public static int obtenMaxGeneraciones() {
		return maxGeneraciones;
	}

	/**
	 * Obtenedora de numero de codigos de poblacion.
	 * @return Devuelve el numero de codigos de poblacion.
	 */
	public static int obtenNumeroCodigosPoblacion() {
		return numeroCodigosPoblacion;
	}

	/**
	 * Obtenedora de numero de colores.
	 * @return Devuelve el numero de colores.
	 */
	public static int obtenNumeroColores() {
		return numeroColores;
	}

	/**
	 * Obtenedora de longitud de codigo.
	 * @return Devuelve la longitud de codigo.
	 */
	public static int obtenLongitudCodigo() {
		return longitudCodigo;
	}

	/**
	 * Informadora de numero de codigos de poblacion.
	 * @param numeroCodigosPoblacion Numero de codigos de poblacion a informar.
	 */
	public static void informaNumeroCodigosPoblacion(int numeroCodigosPoblacion) {
		ConfiguracionGenetic.numeroCodigosPoblacion = numeroCodigosPoblacion;
	}

}