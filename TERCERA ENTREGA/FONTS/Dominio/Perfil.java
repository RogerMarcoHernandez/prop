package Dominio;

import java.awt.Color;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * La clase Perfil representa un perfil de usuario en el sistema.
 * @author Alex Pertusa
 */

public class Perfil implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** Nombre del perfil */
	private String nombreUsuario;
	
	/** Contrasena del perfil */
	private String contrasena;
	
	/** Puntero a sus estadisticas */
	private Estadisticas estadisticas;
	
	/** Vector de punteros a sus partidas */
	private ArrayList<Partida> partidas;
	
	/** Fecha de Registro del perfil */
	private LocalDate fechaRegistro;
	
	/** Fecha de su ultima conexion al juego */
	private LocalDateTime ultimaConexion;
	
	/** Puntero a la configuracion */
	private ConfiguracionPerfil configPerfil;
	
	/**
	 * Crea una instancia de perfil
	 * @param pUsername Nombre del nuevo perfil
	 * @param pPassword Contrasena del nuevo perfil
	 */
	public Perfil(String pUsername, String pPassword) {
		this.nombreUsuario = pUsername;
		this.contrasena = pPassword;
		this.estadisticas = new Estadisticas();
		this.configPerfil = new ConfiguracionPerfil(0); 
		this.fechaRegistro = LocalDate.now();
		this.partidas = new ArrayList<Partida>();
	}

	/** Metodo para obtener el nombre del perfil
	 *  @return Devuelve el nombre del perfil
	 */
	public String obtenNombreUsuario() {
		return nombreUsuario;
	}

	/** Metodo para cambiar el nombre del perfil
	 *  @param pNombreUsuario Nombre del nuevo perfil
	 */
	public void informaNombreUsuario(String pNombreUsuario) {
		this.nombreUsuario = pNombreUsuario;
	}

	/** Metodo para obtener la contrasena del perfil
	 *  @return Devuelve la constrasena del perfil
	 */
	public String obtenContrasena() {
		return contrasena;
	}
	
	/** Metodo para cambiar la constrasena del perfil
	 *  @param pContrasena Nueva contrasena del perfil
	 */
	public void informaContrasena(String pContrasena) {
		this.contrasena = pContrasena;
	}

	/** Metodo para obtener el numero de partidas ganadas del perfil
	 *  @return Devuelve el numero de partidas ganadas del perfil
	 */
	public int obtenPartidasGanadas() {
		return estadisticas.obtenPartidasGanadas();
	}

	/** 
	 * Metodo para anadir una nueva partida ganada
	 */
	public void informaPartidasGanadas() {
		estadisticas.anadirPartidaGanada();
	}

	/** Metodo para obtener el numero de partidas perdidas del perfil
	 *  @return Devuelve el numero de partidas perdidas del perfil
	 */
	public int obtenPartidasPerdidas() {
		return estadisticas.obtenPartidasPerdidas();
	}

	/** 
	 * Metodo para anadir una nueva partida perdida
	 */
	public void informaPartidaPerdida() {
		estadisticas.anadirPartidaPerdida();
	}

	/** Metodo para obtener el porcentaje de victorias del perfil
	 *  @return Devuelve el numero el porcentaje de victorias del perfil
	 */
	public double obtenPorcentajeVictoria() {
		return estadisticas.obtenPorcentajeVictoria();
	}
	
	/** Metodo para obtener la mejor puntuacion del perfil
	 *  @return Devuelve el numero con la mejor puntuacion del perfil
	 */
	public double obtenMejorPuntuacion() {
		return estadisticas.obtenMejorPuntuacion();
	}

	/** Metodo para obtener la fecha de registro del perfil
	 *  @return Devuelve la fecha de registro del perfil
	 */
	public LocalDate obtenFechaRegistro() {
		return fechaRegistro;
	}

	/** Metodo para informar la fecha de registro del perfil
	 *  @param pFechaRegistro Nueva fecha de registro del perfil
	 */
	public void informaFechaRegistro(LocalDate pFechaRegistro) {
		this.fechaRegistro = pFechaRegistro;
	}

	/** Metodo para obtener la ultima conexion del perfil
	 *  @return Devuelve la ultima conexion del perfil
	 */
	public LocalDateTime obtenUltimaConexion() {
		return ultimaConexion;
	}

	/**
	 *  Metodo para informar la ultima conexion del perfil
	 */
	public void informaUltimaConexion() {
		this.ultimaConexion = LocalDateTime.now();
	}

	
	/** Metodo para comprobar si la contrasena recibida por parametro es identica a la contrasena del perfil
	 *  @param pContrasena Contrasena a comprobar
	 *  @return Devuelve true si la contrasena recibida por parametro y la contrasena del perfil son iguales, en caso contrario devuelve false
	 */
	public boolean contrasenaCorrecta(String pContrasena){
		return this.contrasena.equals(pContrasena);
	}


	/** Metodo para obtener las partidas del perfil
	 *  @return Devuelve la lista de partidas del perfil
	 */
	public Partida[] obtenPartidas() {
		return this.partidas.toArray(new Partida[this.partidas.size()]);
	}
	
	/** Metodo para obtener las partidas jugadas por el perfil
	 *  @return Devuelve el numero de partidas jugadadas por el perfil
	 */
	public Integer obtenPartidasJugadas() {
		return this.partidas.size();
	}
	
	/** Metodo para obtener los ids de las partidas sin acabar del perfil
	 *  @return Devuelve un vector de enteros, donde cada posicion del vector es una id de una partida sin acabar
	 *  @throws Exception Si no existen partidas
	 */
	public ArrayList<Integer> obtenIdsPartidasSinAcabar() throws Exception{
		ArrayList<Integer> listaPartidas = new ArrayList<>();
		for(int i = 0; i < partidas.size(); i++) {
			if (!this.partidas.get(i).partidaAcabada()) {
				listaPartidas.add(partidas.get(i).obtenId());
			}
		}
		if (listaPartidas.size() < 1) throw new Exception("ERROR: no hay partidas sin acabar");
		return listaPartidas;
	}
	
	/** Metodo para obtener los ids de las partidas acabadas del perfil
	 *  @return Devuelve un vector de enteros, donde cada posicion del vector es una id de una partida acabada
	 *  @throws Exception Si no existen partidas
	 */
	public ArrayList<Integer> obtenIdsPartidasAcabadas() throws Exception{
		ArrayList<Integer> listaPartidas = new ArrayList<>();
		for(int i = 0; i < partidas.size(); i++) {
			if (partidas.get(i).partidaAcabada()) {
				listaPartidas.add(partidas.get(i).obtenId());
			}
		}
		if (listaPartidas.size() < 1) throw new Exception("ERROR: no hay partidas acabadas");
		return listaPartidas;
	}
	
	/** Metodo para obtener el id mas grande de una partida del perfil
	 *  @return Devuelve el numero de la partida mas grande o 0 en caso de que no tenga partidas
	 */
	public int obtenIdUltimaPartida() {
		int ultimoID;
		ultimoID = 0;
		for(int i = 0; i < partidas.size(); i++) {
			if (partidas.get(i).obtenId() > ultimoID) {
				ultimoID = partidas.get(i).obtenId();
			}
		}
		return ultimoID;
	}
	
	/** Metodo para informar una partida en el perfil
	 *  @param pPartida Nueva partida del perfil
	 */
	public void informaPartidas(Partida pPartida) {
		this.partidas.add(pPartida);
	}
		
	/** Metodo para obtener el las estadisticas del perfil
	 *  @return Devuelve la instancia de estadisticas del perfil
	 */
	public Estadisticas obtenEstadisticas() {
		return estadisticas;
	}
	
	/** Metodo para informar un tema del perfil
	 *  @param pTema Nuevo tema del perfil
	 */
	public void informaTema(int pTema) {
		configPerfil.informaTema(pTema);
	}
		
	/** Metodo para informar una nueva posible mejor puntuacion del perfil
	 *  @param pPuntuacion Numero con la nueva puntuacion
	 *  @param pIdPartida Numero con el id de la partida 
	 */
	public void informaPuntuacion(double pPuntuacion, int pIdPartida) {
		estadisticas.informaPuntuacion(pPuntuacion, pIdPartida);
	}

	/** Metodo para obtener el id de la mejor partida jugada por el perfil
	 *  @return Devuelve el numero de la mejor partida jugada por el perfil
	 */
	public int obtenIdMejorPartida() {
		return estadisticas.obtenIdMejorPartida();
	}
	
	/** Metodo para informar todas las partidas del perfil
	 *  @param partidas Lista con todas las partidas a informar
	 */
	public void asignaPartidas(List<Partida> partidas) {
		this.partidas = (ArrayList<Partida>) partidas;
	}

	/** Metodo para obtener el numero del tema del perfil
	 *  @return Devuelve el numero del tema del perfil
	 */
	public int obtenTema() {
		return configPerfil.obtenTema();
	}
	
	/** Metodo para eliminar una partida del perfil
	 *  @param idPartida Id de la partida a eliminar
	 */
	public void eliminarPartida(int idPartida) {
		Iterator<Partida> it = partidas.iterator();
		while (it.hasNext()) {
			Partida partida = it.next();
			if (partida.obtenId() == idPartida) {
				it.remove();
				break;
			}
		}
	}
	
	/** Metodo para obtener una partida del perfil
	 *  @param idPartida Id de la partida a devolver
	 *  @return Instancia de la partida
	 */
	public Partida ObtenPartida(int idPartida) {
		Iterator<Partida> it = partidas.iterator();
		while (it.hasNext()) {
			Partida partida = it.next();
			if (partida.obtenId() == idPartida) {
				return partida;
			}
		}
		return null;
	}
	
}

