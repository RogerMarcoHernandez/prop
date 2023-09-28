package Dominio;

public class Estadisticas {
	
	// Numero de partidas ganadas
	private int partidasGanadas;
	
	// Numero de partidas perdidas
	private int partidasPerdidas;
	
	// Puntuacion de la mejor partida
	private double mejorPuntuacion;
	
	// Id de la mejor partida
	private int idMejorPartida;

	// Classe creadora de las estadisticas
	public Estadisticas() {
		this.partidasGanadas = 0;
		this.partidasPerdidas = 0;
		this.mejorPuntuacion = 0;
		this.idMejorPartida = -1;
	}

	// Funcion para obtener el numero de partidas jugadas
	public int obtenPartidasJugadas() {
		return partidasGanadas + partidasPerdidas;
	}

	// Funcion para obtener el numero de partidas ganadas
	public int obtenPartidasGanadas() {
		return partidasGanadas;
	}

	// Funcion para modificar el numero de partidas ganadas
	public void informaPartidasGanadas(int pPartidasGanadas) {
		this.partidasGanadas = pPartidasGanadas;
	}
	
	// Funcion para obtener el id de la mejor partida
	public int obtenIdMejorPartida() {
		return idMejorPartida;
	}

	// Funcion para obtener el numero de partidas perdidas
	public int obtenPartidasPerdidas() {
		return partidasPerdidas;
	}

	// Funcion para modificar el numero de partidas perdidas
	public void informaPartidasPerdidas(int pPartidasPerdidas) {
		this.partidasPerdidas = pPartidasPerdidas;
	}

	// Funcion para obtener la mejor puntuacion
	public double obtenMejorPuntuacion() {
		return mejorPuntuacion;
	}

	// Funcion para comprobar y modificar la puntuacion y su correspondiente partida
	public void informaPuntuacion(double pPuntuacion, int pIdPartida) {
		if (this.mejorPuntuacion < pPuntuacion) {
			this.mejorPuntuacion = pPuntuacion;
			this.idMejorPartida = pIdPartida;
		}
	}
	
	// Funcion para anadir una partida perdida
	public void anadirPartidaPerdida() {
		this.partidasPerdidas += 1;
	}

	// Funcion para anadir una partida ganada
	public void anadirPartidaGanada() {
		this.partidasGanadas += 1;
	}

	// Funcion para obtener porcentaje de victorias
	public double obtenPorcentajeVictoria() {
		if (obtenPartidasJugadas() == 0) return 0.0;
		return ((double) this.partidasGanadas / (this.partidasGanadas + this.partidasPerdidas)) * 100;
	}

}
