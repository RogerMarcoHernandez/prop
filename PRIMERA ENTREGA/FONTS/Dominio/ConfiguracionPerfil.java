package Dominio;


public class ConfiguracionPerfil {
	
	// Identificador del tema
	private int tema;
	
	// Volumen de audio
	private int volumen;
	
	// Classe creadora de una configuracion de perfil
	public ConfiguracionPerfil(int pTema, int pVolumen) {
		this.tema = pTema;
		this.volumen = pVolumen;
	}

	// Funcion para obtener el tema
	public int obtenTema() {
		return tema;
	}	

	// Funcion para modificar el tema
	public void informaTema(int pTema) {
		this.tema = pTema;
	}

	// Funcion para obtener el volumen
	public int obtenVolumen() {
		return volumen;
	}

	// Funcion para modificar el volumen
	public void informaVolumen(int volumen) throws Exception {
		if (volumen < 0 || volumen > 100) throw new Exception("ERROR: valor volumen incorrecto");
		this.volumen = volumen;	
	}

}