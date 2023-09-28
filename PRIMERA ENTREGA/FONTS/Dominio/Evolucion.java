package Dominio;

import java.util.Collections;
import java.util.List;

public class Evolucion {
	// Numero de codigos de poblacion escogidos en la seleccion fitness
	private static final int numTorneos = 5; // 5

	// Umbral para que se de la recombinacion en los codigos.
	private static final double umbralRecombinacion = 0.5; // 0.5

	// Tasa para que se aplica la mutacion.
	private static final double tasaMutacion = 0.03; // 0.03

	// Umbral para que se de la permutacion en los codigos.
	private static final double umbralPermutacion = 0.03; // 0.03

	// Umbral para que se de la inversion en los codigos.
	private static final double umbralInversion = 0.02; // 0.02

	// Evolucion de una poblacion
	public PoblacionCodigos evolucionaPoblacionCodigos(PoblacionCodigos poblacion) {
		
		PoblacionCodigos nuevaPoblacion = new PoblacionCodigos(poblacion.numeroCodigos(), false);

		// Guarda el mejor codigo
		nuevaPoblacion.informaCodigo(0, poblacion.mejorCodigo());
		
		double diferenciaFitnessTantoPorUno;
		// Recombinacion
		for (int i = 1; i < poblacion.numeroCodigos(); i++) {
			Codigo codigo1 = seleccionaCodigo(poblacion, numTorneos);
			Codigo codigo2 = seleccionaCodigo(poblacion, numTorneos);
			Codigo newCodigo = recombinaCodigos(codigo1, codigo2, umbralRecombinacion);
			newCodigo = mutaCodigo(newCodigo, tasaMutacion, 1);
			diferenciaFitnessTantoPorUno = (CalculoFitness.maxFitness() - newCodigo.fitnessCodigo())/ CalculoFitness.maxFitness();
			if (Math.random() <= (umbralPermutacion * diferenciaFitnessTantoPorUno)) {
				newCodigo = permutaCodigo(newCodigo);
			}
			diferenciaFitnessTantoPorUno = (CalculoFitness.maxFitness() - newCodigo.fitnessCodigo())/ CalculoFitness.maxFitness();
			if (Math.random() <= ((umbralInversion * diferenciaFitnessTantoPorUno))) {
				newCodigo = invierteCodigo(newCodigo);
			}
			nuevaPoblacion.informaCodigo(i, newCodigo);
		}
		
		return nuevaPoblacion;
	}
	
	
	

	// Seleccion de un codigo en funcion del fitness
	private Codigo seleccionaCodigo(PoblacionCodigos pobl, int numTorneos) {
		PoblacionCodigos torneo = new PoblacionCodigos(numTorneos, false);
		
		// Seleccion random de numTorneos individuos
		for (int i = 0; i < numTorneos; i++) {
			int randomId = (int) (Math.random() * pobl.numeroCodigos());
			torneo.informaCodigo(i, pobl.obtenCodigo(randomId));
		}
		// Obtiene el codigo mas apto de los seleccionados
		return torneo.mejorCodigo();
	}

	// Recombinacion de un codigo
	private Codigo recombinaCodigos(Codigo codigo1, Codigo codigo2, double umbralRecombinacion) {
		Codigo nuevoCodigo = new Codigo();

		double fitness1 = codigo1.fitnessCodigo();
		double fitness2 = codigo2.fitnessCodigo();
		double factorFitness = 1;
		if (fitness1 > fitness2)
			factorFitness = 1.5;
		else
			factorFitness = 0.5;

		for (int i = 0; i < nuevoCodigo.longitudCodigo(); i++) {
			if (Math.random() <= umbralRecombinacion * factorFitness) {
				nuevoCodigo.informaColor(i, codigo1.obtenColor(i));
			} else {
				nuevoCodigo.informaColor(i, codigo2.obtenColor(i));
			}
		}
		return nuevoCodigo;
	}

	// Mutacion de un codigo en funcion del fitness
	private Codigo mutaCodigo(Codigo codigo, double tasaMutacion, double diferenciaFitnessTantoPorUno) {
		Codigo newCodigo = new Codigo();
		for (int i = 0; i < newCodigo.longitudCodigo(); i++)
			newCodigo.informaColor(i, codigo.obtenColor(i));
		for (int i = 0; i < codigo.longitudCodigo(); i++) {
			if (Math.random() <= tasaMutacion * diferenciaFitnessTantoPorUno) {
				// Crear gen al azar
				newCodigo.informaColorRandom(i);
			}
		}
		return newCodigo;
	}

	// Permutacion de dos posiciones random del codigo introducido como parametro.
	private Codigo permutaCodigo(Codigo codigo) {
		Codigo newCodigo = new Codigo();
		for (int i = 0; i < newCodigo.longitudCodigo(); i++)
			newCodigo.informaColor(i, codigo.obtenColor(i));

		int iniRand = (int) (Math.random() * (codigo.longitudCodigo() - 1));
		int finRand = (int) (Math.random() * (codigo.longitudCodigo() - 1));

		if (iniRand != finRand) {
			Color aux = codigo.obtenColor(iniRand);
			newCodigo.informaColor(iniRand, codigo.obtenColor(finRand));
			newCodigo.informaColor(finRand, aux);
		}
		return newCodigo;
	}

	// Inversion entre dos posiciones random del codigo introducido como parametro.
	private Codigo invierteCodigo(Codigo codigo) {
		int iniRand = (int) (Math.random() * (codigo.longitudCodigo() - 1));
		int finRand = (int) (Math.random() * (codigo.longitudCodigo() - 1));

		if (iniRand > finRand) {
			int temp = iniRand;
			iniRand = finRand;
			finRand = temp;
		}

		Codigo newCodigo = new Codigo();
		for (int i = 0; i < newCodigo.longitudCodigo(); i++)
			newCodigo.informaColor(i, codigo.obtenColor(i));

		if (iniRand != finRand) {
			List<Integer> newCodigoList = newCodigo.toListInteger().subList(iniRand, finRand + 1);
			Collections.reverse(newCodigoList);

			int numInversions = finRand - iniRand;
			for (int i = 0; i <= numInversions; i++) {
				Color col = new Color();

				col.informaValor(newCodigoList.get(i));

				newCodigo.informaColor(i + iniRand, col);
			}
		}

		return newCodigo;
	}

}