package Dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Perfil {
	
	// Nombre de usuario
	private String nombreUsuario;
	
	// Contrasena de usuario
	private String contrasena;
	
	// Puntero a sus estadisticas
	private Estadisticas estadisticas;
	
	// Vector de punteros a sus partidas
	private ArrayList<Partida> partidas;
	
	// Fecha de registro del usuario
	private LocalDate fechaRegistro;
	
	// Fecha de ultima conexion del usuario
	private LocalDateTime ultimaConexion;
	
	// Puntero a los logros del usuario
	private LogroPerfil[] logroPerfil;
	
	// Puntero a su configuracion
	private ConfiguracionPerfil configPerfil;
	
	// Classe creadora de un usuario
	public Perfil(String pUsername, String pPassword) {
		this.nombreUsuario = pUsername;
		this.contrasena = pPassword;
		this.estadisticas = new Estadisticas();
		this.configPerfil = new ConfiguracionPerfil(0, 0); 
		this.logroPerfil = null; //
		this.fechaRegistro = LocalDate.now();
		this.partidas = new ArrayList<Partida>();
	}

	// Funcion para obtener el nombre de usuario
	public String obtenNombreUsuario() {
		return nombreUsuario;
	}

	// Funcion para cambiar el nombre de usuario
	public void informaNombreUsuario(String pNombreUsuario) {
		this.nombreUsuario = pNombreUsuario;
	}

	// Funcion para consultar la contrasena del usuario
	public String obtenContrasena() {
		return contrasena;
	}
	
	// Funcion para cambiar la contrasena del usuario
	public void informaContrasena(String pContrasena) {
		this.contrasena = pContrasena;
	}

	// Funcion para obtener las partidas ganadas
	public int obtenPartidasGanadas() {
		return estadisticas.obtenPartidasGanadas();
	}

	// Funcion para anadir una partida ganada
	public void informaPartidasGanadas() {
		estadisticas.anadirPartidaGanada();
	}

	// Funcion para consultar las partidas perdidas
	public int obtenPartidasPerdidas() {
		return estadisticas.obtenPartidasPerdidas();
	}

	// Funcion para anadir una partida perdida
	public void informaPartidaPerdida() {
		estadisticas.anadirPartidaPerdida();
	}

	// Funcion para obtener el porcentaje de victoria del usuario
	public double obtenPorcentajeVictoria() {
		return estadisticas.obtenPorcentajeVictoria();
	}
	
	// Funcion para obtener la mejor puntuacion
	public double obtenMejorPuntuacion() {
		return estadisticas.obtenMejorPuntuacion();
	}

	// Funcion para obtener la fecha de registro del usuario
	public LocalDate obtenFechaRegistro() {
		return fechaRegistro;
	}

	// Funcion para actualizar la fecha de registro del usuario
	public void informaFechaRegistro(LocalDate pFechaRegistro) {
		this.fechaRegistro = pFechaRegistro;
	}

	// Funcion para obtener la ultima conexion del usuario
	public LocalDateTime obtenUltimaConexion() {
		return ultimaConexion;
	}

	// Funcion para actualizar la ultima conexion del usuario
	public void informaUltimaConexion() {
		this.ultimaConexion = LocalDateTime.now();
	}

	
	// Funcion para comprobar si la contrasena pasada como parametro coincide con la del usuario
	public boolean contrasenaCorrecta(String pContrasena){
		return this.contrasena.equals(pContrasena);
	}


	// Funcion para obtener las partidas del usuario
	public Partida[] obtenPartidas() {
		return this.partidas.toArray(new Partida[this.partidas.size()]);
	}
	
	// Funcion para obtener el numero de partidas del usuario
	public Integer obtenPartidasJugadas() {
		return this.partidas.size();
	}
	
	// Funcion para obtener una lista con los ids de las partidas sin acabar del perfil
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
	
	// Funcion para obtener una lista con los ids de las partidas acabadas del perfil
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
	
	// Funcion para obtener el numero de partidas acabadas del usuario
		public Integer obtenPartidasAcabadas() {
			return this.estadisticas.obtenPartidasJugadas();
		}

	// Funcion para anadir una partida
	public void informaPartidas(Partida pPartida) {
		this.partidas.add(pPartida);
	}
	
	// Funcion para saber si el logro N ha sido superado
	public boolean logroSuperado(Integer pN) {
		return this.logroPerfil[pN]!=null ? this.logroPerfil[pN].esLogrado() : null;
	}
	
	// Funcion para saber el numero de logros superados
	public Integer obtenNumeroLogrosSuperados() {
		if(this.logroPerfil==null) return 0;
		int logrosSuperados = 0;
		for (int i = 0; i < this.logroPerfil.length; i++) {
			if(this.logroPerfil[i].esLogrado()) logrosSuperados++;
		}
		return logrosSuperados;
	}

	// Funcion para obtener el objeto de estadisticas del perfil
	public Estadisticas obtenEstadisticas() {
		return estadisticas;
	}
	
	// Funcion para cambiar el tema de la configuracion de perfil
	public void informaTema(int pTema) {
		configPerfil.informaTema(pTema);
	}
	
	// Funcion para cambiar el volumen de la configuracion de perfil
	public void informaVolumen(int pVolumen) throws Exception {
		configPerfil.informaVolumen(pVolumen);
	}
	
	// Funcion para intentar cambiar la mejor puntuacion i su partida correspondiente
	public void informaPuntuacion(double pPuntuacion, int pIdPartida) {
		estadisticas.informaPuntuacion(pPuntuacion, pIdPartida);
	}

	// Funcion para obtener el id de la mejor partida
	public int obtenIdMejorPartida() {
		return estadisticas.obtenIdMejorPartida();
	}
}

