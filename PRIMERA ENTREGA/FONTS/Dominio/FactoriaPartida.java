package Dominio;

import java.util.List;

public class FactoriaPartida {
	// Instancia estatica para hacer de la factoria de partidas una clase Singleton.
	private static FactoriaPartida factoriaPartida;

	// Constructor privado de la factoria.
	private FactoriaPartida() {
	}

	// Metodo para obtener la instancia del singleton.
	public static FactoriaPartida obtenInstancia() {
		if (factoriaPartida == null)
			factoriaPartida = new FactoriaPartida();
		return factoriaPartida;
	}

	// Metodo para crear una partida. En caso de recibir una configuracion
	// incompleta o nula se devuelve NULL, de lo contrario, se devuelve el tipo de
	// partida especificada en la configuracion.
	public IPartida creaPartida(int id, List<Object> config, Perfil usuarioActivo) throws Exception {
		if (config == null || config.size() != 6)
			return null;
		boolean isCodemaker = (boolean) config.get(5);
		if (isCodemaker)
			return new PartidaCodemaker(id, config, usuarioActivo);
		else
			return new PartidaCodebreaker(id, config, usuarioActivo);
	}
}