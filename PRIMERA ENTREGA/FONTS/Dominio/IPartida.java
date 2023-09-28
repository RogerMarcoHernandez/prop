package Dominio;

import java.util.List;

public interface IPartida {
	public int ejecutarTurno(List<Integer> codigo) throws Exception;

	public double calculaPuntuacion();
	
	public Partida obtenPartida();
}