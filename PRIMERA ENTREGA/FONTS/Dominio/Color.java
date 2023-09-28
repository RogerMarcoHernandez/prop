package Dominio;

public class Color {

	// Datos
	protected Object valor;
	protected static int valorMaximoColor = 4; // Maximo valor que puede tomar un Gen - Cambiado en PartidaCodemaker y en
											// Maquina Genetic de acuerdo a la configuracion de la partida.

	// Obtener el valor
	public Object obtenValor() {
		return valor;
	}

	// Informar el valor
	public void informaValor(Object v) {
		valor = v;
	}

	public static void informaMaxColorValue(int naxColorValue) {
		Color.valorMaximoColor = naxColorValue;
	}

	// Genera un color random
	public Integer colorRandom() {
		return ((int) Math.round(Math.random() * (valorMaximoColor - 1)));
	}

}