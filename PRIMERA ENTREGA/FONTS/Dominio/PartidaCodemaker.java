package Dominio;

import java.util.List;

import Excepciones.pistasAgotadasException;
import Excepciones.turnoInicialSinCodigoSecretoException;

public class PartidaCodemaker extends Partida implements IPartida {
	// Instancia de maquina.
	private Maquina maquina;

	// Metodo para inicializar la maquina de acuerdo a la configuracion de la
	// partida.
	private void configuraMaquina() {
		int dificultad = obtenDificultad();

		String dif;

		if (dificultad == 0)
			dif = "facil";
		else if (dificultad == 1)
			dif = "normal";
		else
			dif = "dificil";

		List<List<Double>> tablaDificultadToSteps = Dominio.inoutCSV.cargaArchivoDificultadToSteps(dif);

		double steps = tablaDificultadToSteps.get(obtenLongitudCodigo() - 1).get(obtenNumeroColores() - 1);
		int maxSteps = (int) steps/4+1;

		this.maquina = new MaquinaGenetic(obtenNumeroColores(), maxSteps, obtenLongitudCodigo());
		ConfiguracionGenetic.informaNumeroCodigosPoblacion(2);
		ConfiguracionGenetic.informaDificultad(dificultad);
	}

	// Constructor de PartidaCodemaker.
	public PartidaCodemaker(int id, List<Object> config, Perfil usuarioActivo) throws Exception {
		super(id, config, usuarioActivo);
		configuraMaquina();
	}

	// Calcula la puntuacion de la partida del Codemaker
	public double calculaPuntuacion() {
		int maxTurnos = obtenNumeroTurnos();
		double maximo = obtenLongitudCodigo()*obtenNumeroColores()*1000;
		double puntuacionPorTurno = maximo/maxTurnos;
		puntuacion = ganada ? puntuacionPorTurno*(obtenTurnoActual()+1) : acabada ? 0 : puntuacionPorTurno*obtenTurnoActual();
		return puntuacion;
	}

	// Metodo para ejecutar el turno y sus respectivas funcionalidades.
	@Override
	public int ejecutarTurno(List<Integer> codigoJugador) throws Exception {
		// En caso de no informar el codigo secreto (solo en el inicio de la partida),
		// se lanza una excepcion, de lo contraria se informa el codigo secreto.
		if (codigoSecreto == null)
			if (codigoJugador == null)
				throw new turnoInicialSinCodigoSecretoException();
			else {
				if (codigoJugador.size() != obtenLongitudCodigo())
					throw new IllegalArgumentException(
							"ERROR: El codigo secreto introducido no cumple con los parametros de la partida!");
				for (Integer val : codigoJugador)
					if (val > (obtenNumeroColores() - 1) || val < 0)
						throw new IllegalArgumentException(
								"ERROR: El codigo secreto introducido no cumple con los parametros de la partida!");
				codigoSecreto = codigoJugador;
			}
				
		
		else {
			boolean turnosFinalizados = obtenTurnoActual() == obtenNumeroTurnos()-1;
			if (!turnosFinalizados || !acabada) {
				sincronizarConfiguracionGenetic();
				if(obtenAdivinanza()==null || obtenAdivinanza().isEmpty()) {
					// Obtencion del conjunto de codigos generados por la maquina en los steps que
					// ha tardado en resolver el codigo o maxSteps si ha conseguido resolverlo.
					List<List<Integer>> codigosMaquina = maquina.solve(codigoSecreto);
					// Instancia para obtener el ultimo codigo de la lista (el mejor).
					List<Integer> mejorCodigoMaquina = codigosMaquina.get(codigosMaquina.size() - 1);

					// Informacion tanto de la adivinanza como del resultado.
					informaAdivinanza(mejorCodigoMaquina);
				}
				else if(obtenResultado()!=null && !obtenResultado().isEmpty()) {
					List<Integer> resultado = obtenResultado();
					informaResultado(resultado);
					long aciertosTotales = resultado.stream().filter(valColor -> 2 == valColor).count();
					// Si los aciertos totales equivalen a la longitud del codigo, el (Codebreaker)
					// gana, por tanto, el actual jugador (Codemaker) pierde.
					boolean perdedor = aciertosTotales == codigoSecreto.size();

					acabada = perdedor || turnosFinalizados;

					if (acabada) {
						ganada = !perdedor;
						return perdedor ? -1 : 1;
					}
					avanzarTurno();
				}
			}		

			
		}

		return 0;
	}
	
	// Obten la instancia de partida
	public Partida obtenPartida() {
		return this;
	}
	
	// Sincroniza en caso la configuracion de la maquina con la configuracion de la partida
	private void sincronizarConfiguracionGenetic() {
		if(ConfiguracionGenetic.obtenLongitudCodigo()!=obtenLongitudCodigo() || 
				ConfiguracionGenetic.obtenNumeroColores()!=obtenNumeroColores() ||
				ConfiguracionGenetic.obtenDificultad()!=obtenDificultad()) configuraMaquina();
	}

	// Obten la pista correspondiente al codigo de resolucion relativo a la adivinanza hecha en el turno correspondiente
	@Override
	public List<Integer> obtenPista() throws pistasAgotadasException {
		if(pistasDisponibles!=0) {
			--pistasDisponibles;
			return generaResultado(obtenAdivinanza());
		}
		else {
			throw new pistasAgotadasException();
		}
	}
}