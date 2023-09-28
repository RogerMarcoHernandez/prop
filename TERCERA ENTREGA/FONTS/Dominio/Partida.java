package Dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Excepciones.coloresCodigoInvalidosException;
import Excepciones.longitudCodigoInvalidaException;
import Excepciones.resultadoInformadoIncorrectoException;

/**
 * Clase partida central i principal del juego.
 * @author Roger Marco
 *
 */
public abstract class Partida implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 *  Instancia de la configuracion correspondiente de la partida.
	 */
	private Configuracion configuracion;
	/**
	 *  Identificador de partida.
	 */
	private int id;
	
	/**
	 *  Perfil del jugador de la partida
	 */
	private Perfil perfil;
	/**
	 *  Tablero de la partida.
	 */
	private Tablero tablero;
	/**
	 *  Booleano indicada del estado de acabado de la partida.
	 */
	protected boolean acabada = false;
	/**
	 *  Instancia del codigo secreto de la partida.
	 */
	protected List<Integer> codigoSecreto;
	/**
	 *  Puntuacion de la partida, incializada a 0 en un principio.
	 */
	protected double puntuacion = 0;
	/**
	 *  Pistas disponibles.
	 */
	protected int pistasDisponibles = 0;
	/**
	 *  Estado de victoria
	 */
	protected boolean ganada = false;

	/**
	 *  Constructor de partida.
	 * @param id Id de la partida.
	 * @param config Configuracion de la partida.
	 * @param usuarioActivo Usuario creador de la partida.
	 * @throws Exception En caso de parametros invalidos.
	 */
	public Partida(int id, List<Object> config, Perfil usuarioActivo) throws Exception {
		configuracion = new Configuracion(config);
		
		pistasDisponibles = configuracion.obtenNivelAyuda()+1;
		
		tablero = new Tablero(configuracion.obtenLongitudCodigo(), configuracion.obtenNumeroTurnos());

		this.id = id;
		
		this.perfil = usuarioActivo;
	}

	/**
	 *  Avanza el numero de turno en una unidad.
	 */
	public void avanzarTurno() {
		tablero.avanzarTurno();
	}

	/**
	 *  Comprueba la valida longitud de la fila introducida como parametro respecto la configuracion de la partida.
	 * @param fila Fila a comprobar.
	 * @throws longitudCodigoInvalidaException En caso de fila invalida.
	 */
	public void compruebaValidezFila(List<Integer> fila) throws longitudCodigoInvalidaException {
		if (configuracion.obtenLongitudCodigo() != fila.size())
			throw new longitudCodigoInvalidaException();
	}

	/**
	 *  Metodo para generar el resultado correspondiente a la adivinanza introducida como parametro comparada con la instancia de codigo secreto de la partida.
	 * @param adivinanza Adivinanza de la que generar el resultado.
	 * @return Devuelve el resultado correspondiente a la adivinanza.
	 */
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

	/**
	 *  Establece el codigo introducido como codigo secreto de la partida.
	 * @param codigoSecreto Codigo secreto a informar.
	 */
	public void informaCodigoSecreto(List<Integer> codigoSecreto) {
		this.codigoSecreto = codigoSecreto;
	}

	/**
	 *  Obten el codigo secreto.
	 * @return Devuelve el codigo secreto.
	 */
	public List<Integer> obtenCodigoSecreto() {
		return codigoSecreto;
	}

	/**
	 *  Obten la dificultad.
	 * @return Devuelve la dificultad.
	 */
	public int obtenDificultad() {
		return configuracion.obtenDificultad();
	}

	/**
	 *  Obten la adivinanza del turno actual.
	 * @return Devuelve la adivinanza.
	 */
	public List<Integer> obtenAdivinanza() {
		return tablero.obtenAdivinanza();
	}
	
	/**
	 *  Obten la adivinanza del turno anterior o el inicial en caso que el anterior corresponda a uno negativo.
	 * @return Devuelve la adivinanza del turno anterior.
	 */
	public List<Integer> obtenAdivinanzaAnterior() {
		return tablero.obtenAdivinanzaAnterior();
	}

	/**
	 *  Obten la longitud del codigo de la configuracion de la partida.
	 * @return Devuelve la longitud del codigo.
	 */
	public int obtenLongitudCodigo() {
		return configuracion.obtenLongitudCodigo();
	}

	/**
	 *  Obten el numero de colores de la configuracion de la partida.
	 * @return Devuelve el numero de colores.
	 */
	public int obtenNumeroColores() {
		return configuracion.obtenNumeroColores();
	}

	/**
	 *  Obten el numero de tunros de la configuracion de la partida.
	 * @return Devuelve el numero de turnos.
	 */
	public int obtenNumeroTurnos() {
		return configuracion.obtenNumeroTurnos();
	}

	/**
	 *  Calcula y obten la puntuacion de la partida en el turno actual.
	 * @return Devuelve la puntuacion calculada.
	 */
	public double obtenPuntuacion() {
		puntuacion = calculaPuntuacion();
		return puntuacion;
	}
	
	/**
	 *  Metodo abstracto que calcula la puntuacion de la partida en el turno actual.
	 * @return Devuelve la puntuacion calculada.
	 */
	public abstract double calculaPuntuacion();
	
	/**
	 *  Metodo abstracto que obtiene la pista.
	 * @return Devuelve la pista.
	 * @throws Exception En caso de no poder obtener pista por normas.
	 */
	public abstract List<Integer> obtenPista() throws Exception;

	/**
	 *  Obten el resultado del turno actual
	 * @return Devuelve el resultado del turno actual.
	 */
	public List<Integer> obtenResultado() {
		return tablero.obtenResultado();
	}
	
	/**
	 *  Obten el resultado del turno anterior o el inicial en caso que el anterior corresponda a uno negativo.
	 * @return Devuelve el resultado del turno anterior.
	 */
	public List<Integer> obtenResultadoAnterior() {
		return tablero.obtenResultadoAnterior();
	}

	/**
	 *  Metodo para obtener el turno actual de la partida.
	 * @return Devuelve el turno actual.
	 */
	public int obtenTurnoActual() {
		return tablero.obtenTurnoActual();
	}
	
	/**
	 *  Obten el numero de pistas disponibles
	 * @return Devuelve el numero de pistas disponibles.
	 */
	public int obtenPistasDisponibles() {
		return pistasDisponibles;
	}
	
	/**
	 *  Obten un booleano indicando si el jugador es Codemaker
	 * @return Devuelve si es codemaker.
	 */
	public boolean esCodemaker() {
		return configuracion.obtenEsCodemaker();
	}

	/**
	 *  Metodo para informar la adivinanza al tablero de la partida, controlando la validez del parametro introducido.
	 * @param adivinanza Adivinanza a informar.
	 * @throws Exception En caso de adivinanza invalida.
	 */
	public void informaAdivinanza(List<Integer> adivinanza) throws Exception {
		compruebaValidezFila(adivinanza);

		// Comprobacion de colores introducidos y sus valores validos respecto a la
		// configuracion de la partida.
		for(Integer val: adivinanza) if(val>(obtenNumeroColores()-1) || val < 0) throw new coloresCodigoInvalidosException();

		tablero.informaAdivinanza(adivinanza);
	}

	/**
	 *  Metodo para informar el resultado al tablero de la partida, controlado la validez del parametro introducido.
	 * @param resultado Resultado a informar.
	 * @throws Exception En caso de resultado invalido.
	 */
	public void informaResultado(List<Integer> resultado) throws Exception {
		compruebaValidezFila(resultado);
		// Comprueba que los valores de la lista resultado solo sean 0 (fallo), o 1
		// (acierto color) o 2 (acierto total).
		for(Integer val: resultado) if(val>2 || val < 0) throw new coloresCodigoInvalidosException();
		
		if(!resultado.toString().equals(generaResultado(obtenAdivinanza()).toString())) throw new resultadoInformadoIncorrectoException();

		tablero.informaResultado(resultado);
	}

	/**
	 *  Obten el identificador de la partida
	 * @return Devuelve la id.
	 */
	public int obtenId() {
		return this.id;
	}

	/**
	 *  Obten el perfil asociado a la partida
	 * @return Devuelve el perfil.
	 */
	public Perfil obtenPerfil() {
		return this.perfil;
	}

	/**
	 *  Informa el perfil indicado como parametro
	 * @param perfil Perfil a informar.
	 */
	public void informaPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	/**
	 *  Informa la puntuacion indicada como parametro
	 * @param puntuacion Puntuacion a informar.
	 */
	public void informaPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	/**
	 *  Obten si la partida ha acabado
	 * @return Devuelve si la partida ha acabado.
	 */
	public boolean partidaAcabada() {
		return this.acabada;
	}
	
	/**
	 *  Obten el tablero completo de adivinanzas
	 * @return Devuelve el tablero de adivinanzas.
	 */
	public List<List<Integer>> obtenTableroAdivinanzas(){
		return tablero.obtenTableroAdivinanzas();
	}
	
	/**
	 *  Obten el tablero completo de resultados
	 * @return Devuelve el tablero de resultados.
	 */
	public List<List<Integer>> obtenTableroResultados(){
		return tablero.obtenTableroResultados();
	}
	
	/**
	 *  Metodo abstracto que ejecuta el turno
	 * @param codigo Codigo del turno para ejecutar.
	 * @return Devuelve el estado de la partida.
	 * @throws Exception En caso de ejecucion erronea o invalida.
	 */
	public abstract int ejecutarTurno(List<Integer> codigo) throws Exception;

	/**
	 *  Obten el nivel de ayuda de la configuracion de la partida
	 * @return Devuelve el nivel de ayuda.
	 */
	public int obtenNivelAyuda() {
		return configuracion.obtenNivelAyuda();
	}
	
	/**
	 *  Obten si se ha ganado la partida
	 * @return Devuelve si la partida se ha ganado.
	 */
	public boolean obtenGanada() {
		return ganada;
	}
	
	/**
	 *  Informa del estado de acabado de la partida
	 * @param acabada Booleano a informar del estado de acabado de la partida.
	 */
	public void informaAcabada(boolean acabada) {
		this.acabada = acabada;
	}
	
	/**
	 *  Informa el estado de ganado indicado
	 * @param ganada Booleano a informar del estado de victoria de la partida.
	 */
	public void informaGanada(boolean ganada) {
		this.ganada = ganada;
	}
	
	/**
	 *  Informa el numero de pistas disponibles indicadas
	 * @param pistasDisponibles Numero de pistas disponibles a informar.
	 */
	public void informaPistasDisponibles(Integer pistasDisponibles) {
		this.pistasDisponibles=pistasDisponibles;
	}
	
	/**
	 *  Informa el turno actual indicado
	 * @param turnoActual Numero de turno actual a informar.
	 */
	public void informaTurnoActual(Integer turnoActual) {
		tablero.informaTurnoActual(turnoActual);
	}
	
	/**
	 *  Obten la configuracion en lista de la partida
	 * @return Devuelve la configuracion en lista de objetos.
	 */
	public List<Object> obtenConfiguracion(){
		return configuracion.toList();
	}
	
	/**
	 *  Informa el tablero de adivinanzas indicado
	 * @param tableroAdivinanzas Tablero de adivinanzas a informar.
	 */
	public void informaTableroAdivinanzas(List<List<Integer>> tableroAdivinanzas) {
		tablero.informaTableroAdivinanzas(tableroAdivinanzas);
	}
	
	/**
	 *  Informa el tablero de resultados indicado
	 * @param tableroResultados Tablero de resultados a informar.
	 */
	public void informaTableroResultados(List<List<Integer>> tableroResultados) {
		tablero.informaTableroResultados(tableroResultados);
	}

	
	@Override
	/**
	 *  Convierte a string los atributos de la clase
	 */
	public String toString() {
		return "Partida [configuracion=" + configuracion.toString() + ", id=" + id + ", perfil=" + perfil + ", tablero=" + tablero.toString()
				+ ", acabada=" + acabada + ", codigoSecreto=" + codigoSecreto + ", puntuacion=" + puntuacion
				+ ", pistasDisponibles=" + pistasDisponibles + ", ganada=" + ganada + "]";
	}	
	
	
}