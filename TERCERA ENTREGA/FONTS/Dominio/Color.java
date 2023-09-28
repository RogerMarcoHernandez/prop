package Dominio;

/**
 * Clase representadora del color del codigo.
 * @author Roger Marco
 *
 */
public class Color {

	/**
	 *  Valor del color.
	 */
	protected Object valor;
	/** Maximo valor que puede tener el color. */
	protected static int valorMaximoColor = 4;

	/**
	 *  Obtener el valor
	 * @return Devuelve el valor del color.
	 */
	public Object obtenValor() {
		return valor;
	}

	/**
	 *  Informar el valor
	 * @param v Informa el valor del color.
	 */
	public void informaValor(Object v) {
		valor = v;
	}

	/**
	 * Informa el maximo color posible.
	 * @param naxColorValue Maximo color posible a informar.
	 */
	public static void informaMaxColorValue(int naxColorValue) {
		Color.valorMaximoColor = naxColorValue;
	}

	/**
	 *  Genera un color random
	 * @return Devuelve un color random.
	 */
	public Integer colorRandom() {
		return ((int) Math.round(Math.random() * (valorMaximoColor - 1)));
	}

}