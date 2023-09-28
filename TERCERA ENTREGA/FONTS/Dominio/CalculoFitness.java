package Dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase encargado del calculo del fitness.
 * @author Roger Marco
 *
 */
public class CalculoFitness {

	/** Codigo secreto de la maquina */
	private static Codigo codigoSecretoGenetic;

	/** 
	 * Se informa el codigo secreto mediante una lista de enteros, convirtiendola a
	 * una instancia de tipo Codigo.
	 * @param codigoSecreto Codigo secreto a informar.
	 */
	public static void informaCodigoSecretoGenetic(List<Integer> codigoSecreto) {
		codigoSecretoGenetic = new Codigo();
		for (int i = 0; i < codigoSecreto.size(); i++) {
			Integer valor = codigoSecreto.get(i);
			Color col = new Color();
			col.informaValor(valor);
			codigoSecretoGenetic.informaColor(i, col);
		}
	}

	/** 
	 * Maxima fitness posible
	 * @return Devuelve el fitness maximo posible.
	 */
	public static double maxFitness() {
		return codigoSecretoGenetic.longitudCodigo();
	}

	/**Calculo de la fitness para un codigo - Los aciertos totales y parciales
	 * repercuten de la misma forma (1 punto fitness).
	 * @param codigo Codigo del qual se calcula el fitness.
	 * @return Devuelve el fitness del codigo.
	 */
	public static double fitnessCodigo(Codigo codigo) {
		double fitness = 0;
		// Obtenion del resultado correspondiente al codigo introducido como parametro.
		List<Integer> resultado = obtenResultado(codigo);

		// Recorrido para puntuar los aciertos totales y parciales del resultado.
		Integer col = 0;
		for (int i = 0; i < codigo.longitudCodigo() && i < codigoSecretoGenetic.longitudCodigo(); i++) {
			col = resultado.get(i);
			if (col == 2)
				fitness++;
			else if (col == 1)
				fitness += 0.2;
		}
		return fitness;
	}

	/**
	 * Metodo para obtener el resultado mediante el codigo introducido.
	 * @param codigo Codigo del qual obtener el resultado.
	 * @return Devuelve el resultado obtenido del codigo comparado con el codigo secreto.
	 */
	private static List<Integer> obtenResultado(Codigo codigo) {
		List<Integer> guess = codigo.toListInteger();
		List<Integer> codigoSecreto = codigoSecretoGenetic.toListInteger();
		List<Integer> resultado = new ArrayList<>(Collections.nCopies(codigoSecreto.size(), 0));
		List<Integer> codigoSecretoAux = new ArrayList<>(codigoSecreto);
		// PRIMER RECORREGUT PER TROBAR ACERTS TOTALS
		for (int i = 0; i < codigoSecreto.size(); i++) {
			// ACERT TOTAL
			int iFound = codigoSecretoAux.indexOf(guess.get(i));
			if (i == iFound) {
				resultado.set(i, 2);
				codigoSecretoAux.set(iFound, -1);
			} else if (guess.get(i).equals(codigoSecretoAux.get(i))) {
				resultado.set(i, 2);
				codigoSecretoAux.set(i, -1);
			}
		}

		// SEGON RECORREGUT PER TROBAR ACERTS PARCIALS
		for (int i = 0; i < codigoSecreto.size(); i++) {
			// ACERT PARCIAL
			int iFound = codigoSecretoAux.indexOf(guess.get(i));
			if (iFound != -1 && resultado.get(i) != 2 && codigoSecretoAux.get(iFound) != -1) {
				resultado.set(i, 1);
				codigoSecretoAux.set(iFound, -1);
			}
		}
		return resultado;
	}
}