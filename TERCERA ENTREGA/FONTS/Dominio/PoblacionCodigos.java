package Dominio;

/**
 * Clase representadora de las poblaciones de codigos.
 * @author Roger Marco
 *
 */
public class PoblacionCodigos {

	/**
	 *  Codigos de la poblacion.
	 */
	private Codigo[] codigos;

	/**
	 *  Constructor
	 * @param tamanyo Tamanyo de la poblacion.
	 * @param inicializa Opcion de inicializar.
	 */
	public PoblacionCodigos(int tamanyo, boolean inicializa) {
		codigos = new Codigo[tamanyo];
		// Inicializa la poblacion
		if (inicializa) {
			for (int i = 0; i < numeroCodigos(); i++) {
				// NOTA: Si se quiere almacenar otra cosa, hay que indicarlo aqui
				Codigo newCodigo = new Codigo();
				newCodigo.inicializaCodigo();
				informaCodigo(i, newCodigo);
			}
		}
	}

	/**
	 *  Numero de codigos
	 * @return Devuelve el numero de codigos.
	 */
	public int numeroCodigos() {
		return codigos.length;
	}

	/**
	 *  Obtener el codigo de una posicion concreta
	 * @param indice Indice del codigo a obtener.
	 * @return Devuelve el codigo del indice.
	 */
	public Codigo obtenCodigo(int indice) {
		return codigos[indice];
	}

	/**
	 *  Informar el codigo de una posicion concreta
	 * @param indice Indice del codigo a informar.
	 * @param codigo Codigo a informar en el indice indicado.
	 */
	public void informaCodigo(int indice, Codigo codigo) {
		codigos[indice] = codigo;
	}

	/**
	 *  Codigo mas apto
	 * @return Devuelve el codigo con fitness mas elevado.
	 */
	public Codigo mejorCodigo() {
		Codigo fittest = codigos[0];
		for (int i = 0; i < numeroCodigos(); i++) {
			if (fittest.fitnessCodigo() <= obtenCodigo(i).fitnessCodigo()) {
				fittest = obtenCodigo(i);
			}
		}
		return fittest;
	}

}