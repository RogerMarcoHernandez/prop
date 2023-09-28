package Dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Clase representadora de codigo.
 * @author Roger Marco
 *
 */
public class Codigo {

	/** Longitud del codigo */
	private static int longitudCodigo = 4;
	/** Colores del codigo */
	private Vector<Color> colores;

	/** Variable contenedora del fitness del codigo */
	private double fitness = 0;

	/**
	 * Constructora de codigo.
	 */
	public Codigo() {
		colores = new Vector<Color>();
		for (int indice = 0; indice < longitudCodigo; indice++) {
			Color col = new Color();
			colores.add(col);
		}
	}

	/**
	 * Obtenedora de la longitud del codigo.
	 * @return Devuelve la longitud del codigo.
	 */
	public int longitudCodigo() {
		return colores.size();
	}

	/**
	 *  Obtener el color de una posicion concreta
	 * @param indice Indice del color a obtener.
	 * @return Devuelve el color del indice indicado.
	 */
	public Color obtenColor(int indice) {
		return colores.get(indice);
	}

	/**
	 *  Informar el color de una posicion concreta
	 * @param indice Indice donde se informara el color.
	 * @param valor Color a informar.
	 */
	public void informaColor(int indice, Color valor) {
		colores.set(indice, valor);
		fitness = 0;

	}

	/**
	 *  Informar el color de una posicion concreta con un valor random
	 * @param indice Indice de la posicion a informar el color random.
	 */
	public void informaColorRandom(int indice) {
		Color gen = new Color();
		gen.informaValor(gen.colorRandom());
		informaColor(indice, gen);
	}

	/**
	 *  Inicializar codigo con colores random
	 */
	public void inicializaCodigo() {
		for (int i = 0; i < longitudCodigo(); i++) {
			informaColorRandom(i);
		}
	}

	/**
	 * Transformador de codigo a lista de enteros.
	 * @return Devuelve una lista de enteros conteniendo los valores de los colores del codigo.
	 */
	public List<Integer> toListInteger() {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < longitudCodigo(); i++) {
			Integer valor = (Integer) obtenColor(i).obtenValor();
			list.add(valor);
		}
		return list;
	}

	/**
	 * Informadora de la longitud del codigo.
	 * @param numGenesPorDefecto Longitud del codigo a informar.
	 */
	public static void informaLongitudCodigo(int numGenesPorDefecto) {
		Codigo.longitudCodigo = numGenesPorDefecto;
	}

	/**
	 * Fitness
	 * @return Devuelve el fitness del codigo.
	 */
	public double fitnessCodigo() {
		if (fitness == 0) {
			fitness = CalculoFitness.fitnessCodigo(this);
		}
		return fitness;
	}

}