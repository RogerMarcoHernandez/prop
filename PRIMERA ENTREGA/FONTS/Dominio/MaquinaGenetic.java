package Dominio;

import java.util.ArrayList;
import java.util.List;

public class MaquinaGenetic implements Maquina {
	// Constructora de la MaquinaGenetic, donde se calcula los maxSteps i/o
	// maxGeneraciones a partir de la dificultad de la partida y se establece la
	// configuracion del algorismo genetic de acuerdo a los parametros recibidos.
	public MaquinaGenetic(int numeroColores, int maxSteps, int longitudCodigo) {
		ConfiguracionGenetic.informaMaxGeneraciones(maxSteps);
		ConfiguracionGenetic.informaNumeroColores(numeroColores);
		ConfiguracionGenetic.informaLongitudCodigo(longitudCodigo);

		Color.informaMaxColorValue(numeroColores);

		Codigo.informaLongitudCodigo(longitudCodigo);
	}

	// Metodo para resolver el codigo secreto introducido.
	@Override
	public List<List<Integer>> solve(List<Integer> codigoSecreto) throws Exception {
		// Se lanza una excepcion en caso que el codigo secreto introducido como
		// parametro no este de acuerdo a la configuracion establecida.
		if (codigoSecreto.size() != ConfiguracionGenetic.obtenLongitudCodigo())
			throw new IllegalArgumentException(
					"ERROR: El codigo secreto introducido no cumple con los parametros de la partida!");
		for (Integer val : codigoSecreto)
			if (val > (ConfiguracionGenetic.obtenNumeroColores() - 1) || val < 0)
				throw new IllegalArgumentException(
						"ERROR: El codigo secreto introducido no cumple con los parametros de la partida!");

		// Lista de codigos obtenidos en el total de steps(generaciones)
		List<List<Integer>> codigos = new ArrayList<>();

		// Evolucion
		Evolucion evolucion = new Evolucion();

		// Solucion --> CodigoSecreto
		CalculoFitness.informaCodigoSecretoGenetic(codigoSecreto);

		// Numero maximo de codigos por poblacion.
		int numeroCodigosPoblacion = ConfiguracionGenetic.obtenNumeroCodigosPoblacion();

		// Crear poblacion inicial
		PoblacionCodigos poblacion = new PoblacionCodigos(numeroCodigosPoblacion, true);

		// Numero maximo de generaciones i/o steps.
		int maxGeneraciones = ConfiguracionGenetic.obtenMaxGeneraciones();

		// Evolucionar la poblacion hasta conseguir encontrar el mejor codigo i/o llegar
		// al limite de generaciones i/o maxSteps.
		int generacion = 0;
		while (generacion < maxGeneraciones && poblacion.mejorCodigo().fitnessCodigo() < CalculoFitness.maxFitness()) {
			poblacion = evolucion.evolucionaPoblacionCodigos(poblacion);

			// Anado el mejor codigo del step (generacion) a la lista a devolver.
			codigos.add(poblacion.mejorCodigo().toListInteger());
			generacion++;
		}

		// Se anade el codigo a la poblacion inicial en caso que la maquina lo adivine
		// (poco probable) a la primera.
		if (generacion == 0 && poblacion.mejorCodigo().fitnessCodigo() == CalculoFitness.maxFitness())
			codigos.add(poblacion.mejorCodigo().toListInteger());

		return codigos;
	}
}