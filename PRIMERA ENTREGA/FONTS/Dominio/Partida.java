package Dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Excepciones.coloresCodigoInvalidosException;
import Excepciones.longitudCodigoInvalidaException;
import Excepciones.resultadoInformadoIncorrectoException;

public abstract class Partida {
	// Instancia de la configuracion correspondiente de la partida.
	private Configuracion configuracion;
	// Identificador de partida.
	private int id;
	
	// Perfil del jugador de la partida
	private Perfil perfil;
	// Tablero de la partida.
	private Tablero tablero;
	// Booleano indicada del estado de acabado de la partida.
	protected boolean acabada = false;
	// Instancia del codigo secreto de la partida.
	protected List<Integer> codigoSecreto;
	// Puntuacion de la partida, incializada a 0 en un principio.
	protected double puntuacion = 0;
	// Pistas disponibles.
	protected int pistasDisponibles = 0;
	// Estado de victoria
	protected boolean ganada = false;

	// Constructor de partida.
	public Partida(int id, List<Object> config, Perfil usuarioActivo) throws Exception {
		configuracion = new Configuracion(config);
		
		pistasDisponibles = configuracion.obtenNivelAyuda()+1;
		
		tablero = new Tablero(configuracion.obtenLongitudCodigo(), configuracion.obtenNumeroTurnos());

		this.id = id;
		
		this.perfil = usuarioActivo;
	}

	// Avanza el numero de turno en una unidad.
	public void avanzarTurno() {
		tablero.avanzarTurno();
	}

	// Comprueba la valida longitud de la fila introducida como parametro respecto
	// la configuracion de la partida.
	public void compruebaValidezFila(List<Integer> fila) throws longitudCodigoInvalidaException {
		if (configuracion.obtenLongitudCodigo() != fila.size())
			throw new longitudCodigoInvalidaException();
	}

	// Metodo para generar el resultado correspondiente a la adivinanza introducida
	// como parametro comparada con la instancia de codigo secreto de la partida.
	public List<Integer> generaResultado(List<Integer> adivinanza) {
		// Inicializacion de la lista resultado donde se calculara el mismo a 0 y con la
		// longitud del codigo.
		List<Integer> resultado = new ArrayList<>(Collections.nCopies(obtenLongitudCodigo(), 0));
		// Instancia auxiliar del codigo secreto para marcar posiciones visitadas sin
		// modificar el codigo secreto original.
		List<Integer> codigoSecretoAux = new ArrayList<>(codigoSecreto);

		// Primer recorrido al codigo secreto para obtener los aciertos totales.
		for (int i = 0; i < obtenLongitudCodigo(); i++) {
			int indiceColorEncontrado = codigoSecretoAux.indexOf(adivinanza.get(i));

			if (i == indiceColorEncontrado) {
				resultado.set(i, 2);
				codigoSecretoAux.set(indiceColorEncontrado, -1);
			} else if (adivinanza.get(i).equals(codigoSecretoAux.get(i))) {
				resultado.set(i, 2);
				codigoSecretoAux.set(i, -1);
			}
		}

		// Segundo recorrido para obtener los aciertos parciales/aciertos de color.
		for (int i = 0; i < obtenLongitudCodigo(); i++) {
			int indiceColorEncontrado = codigoSecretoAux.indexOf(adivinanza.get(i));

			if (indiceColorEncontrado != -1 && resultado.get(i) != 2
					&& codigoSecretoAux.get(indiceColorEncontrado) != -1) {
				resultado.set(i, 1);
				codigoSecretoAux.set(indiceColorEncontrado, -1);
			}
		}

		return resultado;
	}

	// Establece el codigo introducido como codigo secreto de la partida.
	public void informaCodigoSecreto(List<Integer> codigoSecreto) {
		this.codigoSecreto = codigoSecreto;
	}

	// Obten el codigo secreto.
	public List<Integer> obtenCodigoSecreto() {
		return codigoSecreto;
	}

	// Obten la dificultad.
	public int obtenDificultad() {
		return configuracion.obtenDificultad();
	}

	// Obten la adivinanza del turno actual.
	public List<Integer> obtenAdivinanza() {
		return tablero.obtenAdivinanza();
	}
	
	// Obten la adivinanza del turno anterior o el inicial en caso que el anterior corresponda a uno negativo.
	public List<Integer> obtenAdivinanzaAnterior() {
		return tablero.obtenAdivinanzaAnterior();
	}

	// Obten la longitud del codigo de la configuracion de la partida.
	public int obtenLongitudCodigo() {
		return configuracion.obtenLongitudCodigo();
	}

	// Obten el numero de colores de la configuracion de la partida.
	public int obtenNumeroColores() {
		return configuracion.obtenNumeroColores();
	}

	// Obten el numero de tunros de la configuracion de la partida.
	public int obtenNumeroTurnos() {
		return configuracion.obtenNumeroTurnos();
	}

	// Calcula y obten la puntuacion de la partida en el turno actual.
	public double obtenPuntuacion() {
		puntuacion = calculaPuntuacion();
		return puntuacion;
	}
	
	// Metodo abstracto que calcula la puntuacion de la partida en el turno actual.
	public abstract double calculaPuntuacion();
	
	// Metodo abstracto que obtiene la pista.
	public abstract List<Integer> obtenPista() throws Exception;

	// Obten el resultado del turno actual
	public List<Integer> obtenResultado() {
		return tablero.obtenResultado();
	}
	
	// Obten el resultado del turno anterior o el inicial en caso que el anterior corresponda a uno negativo.
	public List<Integer> obtenResultadoAnterior() {
		return tablero.obtenResultadoAnterior();
	}

	// Metodo para obtener el turno actual de la partida.
	public int obtenTurnoActual() {
		return tablero.obtenTurnoActual();
	}
	
	// Obten el numero de pistas disponibles
	public int obtenPistasDisponibles() {
		return pistasDisponibles;
	}
	
	// Obten un booleano indicando si el jugador es Codemaker
	public boolean esCodemaker() {
		return configuracion.obtenEsCodemaker();
	}

	// Metodo para informar la adivinanza al tablero de la partida, controlando la
	// validez del parametro introducido.
	public void informaAdivinanza(List<Integer> adivinanza) throws Exception {
		compruebaValidezFila(adivinanza);

		// Comprobacion de colores introducidos y sus valores validos respecto a la
		// configuracion de la partida.
		for(Integer val: adivinanza) if(val>(obtenNumeroColores()-1) || val < 0) throw new coloresCodigoInvalidosException();

		tablero.informaAdivinanza(adivinanza);
	}

	// Metodo para informar el resultado al tablero de la partida, controlado la
	// validez del parametro introducido.
	public void informaResultado(List<Integer> resultado) throws Exception {
		compruebaValidezFila(resultado);
		// Comprueba que los valores de la lista resultado solo sean 0 (fallo), o 1
		// (acierto color) o 2 (acierto total).
		for(Integer val: resultado) if(val>2 || val < 0) throw new coloresCodigoInvalidosException();
		
		if(!resultado.toString().equals(generaResultado(obtenAdivinanza()).toString())) throw new resultadoInformadoIncorrectoException();

		tablero.informaResultado(resultado);
	}

	// Obten el identificador de la partida
	public int obtenId() {
		return this.id;
	}

	// Obten el perfil asociado a la partida
	public Perfil obtenPerfil() {
		return this.perfil;
	}

	// Informa el perfil indicado como parametro
	public void informaPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	// Informa la puntuacion indicada como parametro
	public void informaPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	// Obten si la partida ha acabado
	public boolean partidaAcabada() {
		return this.acabada;
	}
	
	// Obten el tablero completo de adivinanzas
	public List<List<Integer>> obtenTableroAdivinanzas(){
		return tablero.obtenTableroAdivinanzas();
	}
	
	// Obten el tablero completo de resultados
	public List<List<Integer>> obtenTableroResultados(){
		return tablero.obtenTableroResultados();
	}
	
	// Metodo abstracto que ejecuta el turno
	public abstract int ejecutarTurno(List<Integer> codigo) throws Exception;

	// Obten el nivel de ayuda de la configuracion de la partida
	public int obtenNivelAyuda() {
		return configuracion.obtenNivelAyuda();
	}
	
	// Obten si se ha ganado la partida
	public boolean obtenGanada() {
		return ganada;
	}
	
	// Informa del estado de acabado de la partida
	public void informaAcabada(boolean acabada) {
		this.acabada = acabada;
	}	
}