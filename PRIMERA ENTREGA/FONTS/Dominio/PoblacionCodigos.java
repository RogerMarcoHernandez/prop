package Dominio;

public class PoblacionCodigos {

	// Datos
	private Codigo[] codigos;

	// Constructor
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

	// Numero de codigos
	public int numeroCodigos() {
		return codigos.length;
	}

	// Obtener el codigo de una posicion concreta
	public Codigo obtenCodigo(int indice) {
		return codigos[indice];
	}

	// Informar el codigo de una posicion concreta
	public void informaCodigo(int indice, Codigo codigo) {
		codigos[indice] = codigo;
	}

	// Codigo mas apto
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