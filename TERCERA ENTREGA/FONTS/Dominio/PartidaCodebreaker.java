package Dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Excepciones.codigoTotalmenteReveladoException;
import Excepciones.pistasAgotadasException;
import Excepciones.turnoSinAdivinanzaException;

/**
 * Clase variante Codebreaker de Partida.
 * @author Roger Marco
 *
 */
public class PartidaCodebreaker extends Partida implements IPartida,Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Codigo revelador.
	 */
	private Integer[] codigoRevelador;
	/**
	 * Vector de posiciones reveladas.
	 */
	private Integer[] posicionesReveladas;
	/**
	 * Numero de posiciones reveladas.
	 */
	private int numeroPosicionesReveladas = 0;
	
	/**
	 *  Constructor de PartidaCodebreaker
	 * @param id Id de la partida.
	 * @param config Configuracion de la partida.
	 * @param usuarioActivo Usuario creador de la partida.
	 * @throws Exception En caso de parametros invalidos.
	 */
	public PartidaCodebreaker(int id, List<Object> config, Perfil usuarioActivo) throws Exception {
		super(id, config, usuarioActivo);

		int longitudCodigo = (int) config.get(4);
		int numeroColores = (int) config.get(3);
		// Establece el codigo secreto de la partida con un codigo secreto random valido
		// para la configuracion de esta.
		codigoSecreto = generarCodigoSecreto(longitudCodigo, numeroColores);
	}

	/**
	 *  Calcula la puntuacion de la partida
	 */
	public double calculaPuntuacion() {
		int maxTurnos = obtenNumeroTurnos();
		double maximo = obtenLongitudCodigo()*obtenNumeroColores()*1000;
		double puntuacionPorTurno = maximo/maxTurnos;
		puntuacion = ganada ? puntuacionPorTurno*(maxTurnos-obtenTurnoActual()+1) : acabada ? 0 : puntuacionPorTurno*(maxTurnos-obtenTurnoActual());
		return puntuacion;
	}

	
	@Override
	/**
	 *  Metodo para ejecutar el turno y sus respectivas funcionalidades.
	 */
	public int ejecutarTurno(List<Integer> adivinanza) throws Exception {
		// Si el codigo introducido es nulo, por tanto, no se informa una adivinanza.
		if (adivinanza == null)
			throw new turnoSinAdivinanzaException();

		// Obtencion del resultado.
		List<Integer> resultado = generaResultado(adivinanza);
		// Informacion tanto de la adivinanza como del resultado.
		informaAdivinanza(adivinanza);
		informaResultado(resultado);

		avanzarTurno();

		long aciertosTotales = resultado.stream().filter(valColor -> 2 == valColor).count();
		// Si los aciertos totales equivalen a la longitud del codigo, el jugador
		// (Codebreaker) gana.
		boolean ganador = aciertosTotales == codigoSecreto.size();
		boolean turnosFinalizados = obtenTurnoActual() == obtenNumeroTurnos();

		acabada = ganador || turnosFinalizados;

		if (acabada) {
			ganada = ganador;
			return ganador ? 1 : -1;
		}

		return 0;
	}

	/**
	 *  Metodo para generar un codigo secreto al azar de acuerdo a la configuracion de la partida.
	 * @param longitudCodigo Longitud del codigo secreto a generar.
	 * @param numeroColores Numero de colores del codigo secreto a generar.
	 * @return Devuelve el codigo secreto generado.
	 */
	private List<Integer> generarCodigoSecreto(int longitudCodigo, int numeroColores) {
		List<Integer> codigoSecreto = new ArrayList<>();

		for (int i = 0; i < longitudCodigo; i++) {
			int colorRandom = (int) (Math.random() * numeroColores);

			codigoSecreto.add(colorRandom);
		}

		return codigoSecreto;
	}
	
	/**
	 *  Obten la instancia de partida
	 */
	public Partida obtenPartida() {
		return this;
	}

	
	@Override
	/**
	 *  Metodo encargado de generar la pista reveladora de una posicion del codigo secreto
	 */
	public List<Integer> obtenPista() throws Exception {
		if(pistasDisponibles!=0) {
			if(posicionesReveladas==null) {
				posicionesReveladas = new Integer[obtenLongitudCodigo()];
			}
			int posicionFichaRevelada = -1;
			boolean posicionARevelarEncontrada = false;
			if(numeroPosicionesReveladas==obtenLongitudCodigo()) throw new codigoTotalmenteReveladoException();
			while(!posicionARevelarEncontrada) {
				posicionFichaRevelada = (int) (Math.random()*obtenLongitudCodigo());
				if(posicionesReveladas[posicionFichaRevelada]==null) posicionARevelarEncontrada=true;
			}
			posicionesReveladas[posicionFichaRevelada] = 1;
			++numeroPosicionesReveladas;
			--pistasDisponibles;
			return List.of(posicionFichaRevelada,codigoSecreto.get(posicionFichaRevelada));
		}
		else {
			throw new pistasAgotadasException();
		}
	}
}