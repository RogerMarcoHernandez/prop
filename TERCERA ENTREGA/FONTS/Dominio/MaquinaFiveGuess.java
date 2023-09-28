package Dominio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Clase de la maquina con el algoritmo del five guess.
 * Implementa la interfaz Maquina.
 * 
 * Esta clase se encarga de resolver un codigo secreto utilizando el algoritmo Five Guess.
 * El algoritmo realiza una serie de intentos y correcciones para adivinar el codigo secreto.
 * 
 * Para utilizar esta clase, se debe instanciar un objeto de la clase MaquinaFiveGuess y llamar al metodo solve() pasando el codigo secreto a adivinar.
 * El metodo solve() devuelve una lista de codigos que representa los intentos realizados por la maquina.
 * 
 * Esta implementacion del algoritmo Five Guess utiliza una lista de codigos posibles y aplica la estrategia de minimax para determinar el siguiente codigo a enviar.
 * 
 * @author Jordi Estany
 * @author Alex Pertusa
 *
 */

public class MaquinaFiveGuess implements Maquina{
	
	private int longitudCodigo;
	private int numeroColores;
	private int pasosMaximos;
	
	private static List<List<Integer>> codigosPosibles = new ArrayList<>();
	
	/**
	 * Constructor de la clase MaquinaFiveGuess
	 * 
	 * @param pNumeroColores El numero de colores posibles para los codigos
	 * @param pPasosMaximos El numero maximo de pasos permitidos para adivinar el codigo
	 * @param pLongitudCodigo La longitud del codigo a adivinar
	 */
	public MaquinaFiveGuess(int pNumeroColores, int pPasosMaximos, int pLongitudCodigo) {
		longitudCodigo = pLongitudCodigo;
		numeroColores = pNumeroColores;
		pasosMaximos = pPasosMaximos;		
	}
	
	/**
	 * Inicializa los codigos posibles dada la longitud de codigo y el numero de colores, utilizando una funcion recursiva
	 * 
	 * @param pLongitudCodigo La longitud del codigo
	 * @param pNumeroColores El numero de colores posibles
	 */
	public void inicializaCodigosPosibles(int pLongitudCodigo, int pNumeroColores) {
		setCodigosPosibles(new ArrayList<>());
	    inicializaPosiblesCodigosRecursivo(pLongitudCodigo, pNumeroColores, new ArrayList<>(), getCodigosPosibles());
	}

	/**
	 * Funcion recursiva para añadir a codigosPosibles todos aquellos codigos que pueden ser la solucion dada una longitud de codigo y un numero de colores
	 * 
	 * @param pLongitudCodigo La longitud del codigo
	 * @param pNumeroColores El numero de colores posibles
	 * @param codigoActual El codigo actual en construccion
	 * @param codigosPosibles La lista de codigos posibles
	 */
	private void inicializaPosiblesCodigosRecursivo(int pLongitudCodigo, int pNumeroColores, List<Integer> codigoActual, List<List<Integer>> codigosPosibles) {
		// En caso de que el codigo creado hasta el momento ya tenga una longitud igual a la longitud de codigo deseada se inserta al conjunto de codigos posibles
	    if (codigoActual.size() == pLongitudCodigo) {
	    	codigosPosibles.add(new ArrayList<>(codigoActual));
	    	
	    // En caso contrario se anade al codigo todas las posibilidades para su siguiente cifra (numeroColores) y se llama a la funcion recursiva 	
	    } else {
	        for (int i = 0; i < pNumeroColores; i++) {
	            codigoActual.add(i);
	            inicializaPosiblesCodigosRecursivo(pLongitudCodigo, pNumeroColores, codigoActual, codigosPosibles);
	            codigoActual.remove(codigoActual.size() - 1); // Importante eliminar la ultima cifra anadida antes de seguir con la siguiente posibilidad
	        }
	    }
	}
	
	
	/**
	 * Resuelve el codigo secreto introducido utilizando el algoritmo Five Guess
	 * 
	 * @param codigoSecreto El codigo secreto a adivinar
	 * @return Una lista de codigos que representa los intentos realizados por la maquina
	 * @throws Exception Si ocurre algun error durante la resolucion del codigo
	 */
		@Override
		public List<List<Integer>> solve(List<Integer> codigoSecreto) throws Exception {
			inicializaCodigosPosibles(longitudCodigo, numeroColores);
			List<List<Integer>> soluciones = new ArrayList<>();
			soluciones.add(generarCodigoInicial()); // Se empieza usando el codigo inicial
			int turnoActual = 1;
			boolean trobat = false;
			if (soluciones.get(0).equals(codigoSecreto)) trobat = true; // En caso de ser la solucion acaba 
			// Mientras no se haya encontrado la solucion y no se haya superado el numero maximo de turnos se busca la mejor solucion
			while (turnoActual < pasosMaximos && !trobat) {
				// Se obtiene la correccion del ultimo codigo enviado
				int[] correccion = obtenResultado(soluciones.get(turnoActual-1), codigoSecreto);	
				// Se actualiza los codigos posibles dada lo correccion aterior
				actualizaCodigosPosibles(soluciones.get(turnoActual-1), correccion);
				// Se obtiene la mejor opcion para ser el siguiente codigo enviado
				List<Integer> siguienteCodigo = miniMax();
				// Se anade al conjunto de codigos de la maquina y se comprueba si se ha adivinado el codigo secreto
				soluciones.add(siguienteCodigo);
				if(siguienteCodigo.equals(codigoSecreto)) trobat = true;
				turnoActual++; // Se incrementa el numero de turno
			}
			return soluciones; // Retorna el conjunto de codigos de la maquina
		}

		/**
		 * Genera el primer codigo introducido por la maquina, dado por Knuth 1122
		 * 
		 * @return El codigo inicial generado
		 */
		private List<Integer> generarCodigoInicial() {
			List<Integer> codigoInicial  = new ArrayList<>();
		    for (int i = 0; i < longitudCodigo / 2; i++) {
		    	codigoInicial.add(0);
		    }
		    for (int i = longitudCodigo / 2; i < longitudCodigo; i++) {
		    	codigoInicial.add(1);
		    }
			return codigoInicial;
		}

	

	/**
	 * Elimina de los codigos posibles aquellos codigos que no cumplirian el resultado dado por el codigo introducido anteriormente
	 * Es decir, aquellos que en caso de ser la solucion no darian ese resultado al corregir el codigo enviado
	 * @param codigoEnviado El codigo enviado anteriormente
	 * @param resultado El resultado de la correccion del codigo enviado
	*/
	public void actualizaCodigosPosibles(List<Integer> codigoEnviado, int[] resultado) {
		List<List<Integer>> nuevosPosiblesCodigos = new ArrayList<>();
	    for (List<Integer> codigo : getCodigosPosibles()) {
	        int[] resultado2 = obtenResultado(codigoEnviado, codigo);
	        if (Arrays.equals(resultado2, resultado)) {
	        	nuevosPosiblesCodigos.add(codigo);
	        }
	    }
	    setCodigosPosibles(nuevosPosiblesCodigos);
	}
	
	
	/**
	 * Obtiene la correccion del codigo introducido
	 * @param codigoIntroducido El codigo introducido
	 * @param codigoSecreto El codigo secreto a adivinar
	 * @return Un arreglo de enteros que representa la correccion del codigo
	 */
	public int[] obtenResultado(List<Integer> codigoIntroducido, List<Integer> codigoSecreto) {
	    int coincidenciaTotal = 0;
	    int coincidenciaParcial = 0;
	    boolean[] codigoIntroducidoUsado = new boolean[longitudCodigo];
	    boolean[] codigoOriginalUsado = new boolean[longitudCodigo];
	    // Cuenta cuantas coincidencias totales hay, misma posicion y valor
	    for (int i = 0; i < longitudCodigo; i++) {
	        if (codigoIntroducido.get(i).equals(codigoSecreto.get(i))) {
	            coincidenciaTotal++;
	            codigoIntroducidoUsado[i] = true;
	            codigoOriginalUsado[i] = true;
	        }
	    }
	    // Cuenta cuantas coincidencias parciales hay, mismo valor,
	    for (int i = 0; i < longitudCodigo; i++) {
	        for (int j = 0; j < longitudCodigo; j++) {
	            if (!codigoIntroducidoUsado[i] && !codigoOriginalUsado[j] && codigoIntroducido.get(i).equals(codigoSecreto.get(j))) {
	                coincidenciaParcial++;
	                codigoIntroducidoUsado[i] = true;
	                codigoOriginalUsado[j] = true;
	            }
	        }
	    }
	    return (new int[] {coincidenciaTotal, coincidenciaParcial});
	}
	
	
	/**
	 * Obtiene todos los posibles resultados que un codigo introducido puede generar
	 * @return Una lista de arreglos de enteros que representa los resultados posibles
	 */
	public List<int[]> obtenResultadosPosibles() {
		List<int[]> resultadosPosibles = new ArrayList<>();
        for (int i = 0; i <= longitudCodigo; i++) {
            for (int j = 0; j <= longitudCodigo - i; j++) {
            	resultadosPosibles.add(new int[]{i, j});
            }
        }
        return resultadosPosibles;
    }
	
	
	/**
	 * Escoge y devuelve la mejor opcion para ser el siguiente codigo enviado utilizando el algoritmo MiniMax.
	 * @return El codigo seleccionado como la mejor opcion.
	 */
    public List<Integer> miniMax() {
    	// Si solo queda un codigo lo devuelve
        if (getCodigosPosibles().size() == 1) {
            return getCodigosPosibles().get(0);
        }
        // Inicializa el siguienteCodigo, contendra el codigo con mayor puntuacion miniMax
        List<Integer> siguienteCodigo = new ArrayList<>();
        
        // Puntuacion maxima entre las minimas de los distintos codigos 
        int minMax = Integer.MIN_VALUE;
        
        // Recorre todos los posibles codigos
        for (List<Integer> codigo : getCodigosPosibles()) {
        	// Puntuacion minima de cada codigo
            int min = Integer.MAX_VALUE;
            // Recorre todos los resultados posibles
            for (int[] resultadoPosible : obtenResultadosPosibles()) {
            	// Numero de codigos eliminados dado un codigo y su correccion
            	int codigosEliminados = 0;
            	// Recorre todos los codigos y cuenta cuantos serian eliminados dado un codigo y su correccion
            	for (List<Integer> codigoAux : getCodigosPosibles()) {
            		// En caso de que el resultadoPosible no coincida con el resultado obtenido significa que en caso de que codigo 
            		// fuera la solucion codigoAux seria descartado
            		if (obtenResultado(codigoAux, codigo) != resultadoPosible) codigosEliminados++; 
            	}
            	if (codigosEliminados < min) min = codigosEliminados; // Se actualiza en caso de ser necesario el numero de codigos que eliminaria en el peor de los casos                  
            }
            // En caso de que el codigo en el peor de los casos elimine mas codigos que el que habia se remplaza el siguiente codigo y se actualiza la puntuacion miniMax
            if (min > minMax) {
            	minMax = min;
            	siguienteCodigo = codigo;
            }
        }
        return siguienteCodigo;        
    }

	public static List<List<Integer>> getCodigosPosibles() {
		return codigosPosibles;
	}

	public static void setCodigosPosibles(List<List<Integer>> codigosPosibles) {
		MaquinaFiveGuess.codigosPosibles = codigosPosibles;
	}	

}

