package Dominio;

public class RankingGlobal {

	// Instancia del ranking
	private static RankingGlobal ranking;

	// Lista de las partidas del ranking
	private Partida[] partidas;

	// Constructor (maximo 10 partidas de momento)
	private RankingGlobal() {
		partidas = new Partida[10];
	}
	
	// Funcion para obtener la instancia en caso de que exista, si no la crea
	public static RankingGlobal obtenInstancia() {
		if (ranking == null)
			ranking = new RankingGlobal();
		return ranking;
	}
	
	// Funcion anadir una partida
	public void anadirPartida(Partida partida) {
		int size = 0;
		for (int i = 0; i < 10; i++) {
			if (partidas[i] != null) size++;
		}	
		
		int pos = 0;
		for (int j = 0; j < size; j++) {
			if(partida.obtenPuntuacion() <= partidas[j].obtenPuntuacion()) pos++;
		}
		int v = size-1;
		if (v > 8) v = 8;
		for(int x = v; x >= 0; x--) {
			partidas[x + 1] = partidas[x];
		}
		partidas[pos] = partida;		
	}
	
	// Funcion obtener partidas
	public Partida[] obtenPartidas() {
		return partidas;
	}

	// Funcion anadir partidas
	public void informaPartidas(Partida[] partidas) {
		this.partidas = partidas;
	}
	
	// Funcion para vaciar las partidas 
	public void reset() {
		this.partidas = new Partida[10];
	}
	

}
