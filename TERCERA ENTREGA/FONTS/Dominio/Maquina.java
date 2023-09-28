package Dominio;

import java.util.List;

public interface Maquina {
	/**
	 * Metodo que resuelve el codigo secreto.
	 * @param solution Codigo secreto.
	 * @return Lista de intentos en forma de codigos.
	 * @throws Exception En caso de solucion invalida.
	 */
	public List<List<Integer>> solve(List<Integer> solution) throws Exception;
}