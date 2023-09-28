package Dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Codigo {

	// Datos
	private static int longitudCodigo = 4; // LONGITUD DEL CODIGO CAMBIADO AL EJECUTAR EL METODO "SOLVE" DE LA
											// MAQUINA GENETIC DE ACUERDO A LA CONFIGURACION DE LA PARTIDA
	private Vector<Color> colores;

	// Para aumentar eficiencia
	private double fitness = 0;

	// Constructor
	public Codigo() {
		colores = new Vector<Color>();
		for (int indice = 0; indice < longitudCodigo; indice++) {
			Color col = new Color();
			colores.add(col);
		}
	}

	// Numero de colores
	public int longitudCodigo() {
		return colores.size();
	}

	// Obtener el color de una posicion concreta
	public Color obtenColor(int indice) {
		return colores.get(indice);
	}

	// Informar el color de una posicion concreta
	public void informaColor(int indice, Color valor) {
		colores.set(indice, valor);
		fitness = 0;

	}

	// Informar el color de una posicion concreta con un valor random
	public void informaColorRandom(int indice) {
		Color gen = new Color();
		gen.informaValor(gen.colorRandom());
		informaColor(indice, gen);
	}

	// Inicializar codigo con colores random
	public void inicializaCodigo() {
		for (int i = 0; i < longitudCodigo(); i++) {
			informaColorRandom(i);
		}
	}

	public List<Integer> toListInteger() {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < longitudCodigo(); i++) {
			Integer valor = (Integer) obtenColor(i).obtenValor();
			list.add(valor);
		}
		return list;
	}

	public static void informaLongitudCodigo(int numGenesPorDefecto) {
		Codigo.longitudCodigo = numGenesPorDefecto;
	}

	// Fitness
	public double fitnessCodigo() {
		if (fitness == 0) {
			fitness = CalculoFitness.fitnessCodigo(this);
		}
		return fitness;
	}

}