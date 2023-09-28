package Dominio;

public class ConfiguracionGenetic {
	private static int numeroColores = 4;
	private static int longitudCodigo = 4;
	private static int maxGeneraciones = 1000; // maxSteps
	private static int numeroCodigosPoblacion = 200; // 200
	private static int dificultad = 0;

	public static int obtenDificultad() {
		return dificultad;
	}

	public static void informaDificultad(int dificultad) {
		ConfiguracionGenetic.dificultad = dificultad;
	}

	public static void informaNumeroColores(int numeroColores) {
		ConfiguracionGenetic.numeroColores = numeroColores;
	}

	public static void informaLongitudCodigo(int longitudCodigo) {
		ConfiguracionGenetic.longitudCodigo = longitudCodigo;
	}

	public static void informaMaxGeneraciones(int maxGeneraciones) {
		ConfiguracionGenetic.maxGeneraciones = maxGeneraciones;
	}

	public static int obtenMaxGeneraciones() {
		return maxGeneraciones;
	}

	public static int obtenNumeroCodigosPoblacion() {
		return numeroCodigosPoblacion;
	}

	public static int obtenNumeroColores() {
		return numeroColores;
	}

	public static int obtenLongitudCodigo() {
		return longitudCodigo;
	}

	public static void informaNumeroCodigosPoblacion(int numeroCodigosPoblacion) {
		ConfiguracionGenetic.numeroCodigosPoblacion = numeroCodigosPoblacion;
	}

}