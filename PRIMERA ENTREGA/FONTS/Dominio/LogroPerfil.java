package Dominio;

import java.time.LocalDate;

public class LogroPerfil {
	// Boleano que indica si el logro ha sido logrado
	private boolean logrado;
	// Fecha de asolimiento del logro
	private LocalDate fechaLogrado;
	// Puntero del logro que representa
	private Logro logro;

	// Creadora de LogroPerfil
	public LogroPerfil(Logro pLogro) {
		this.logrado = false;		
		this.logro = pLogro;
	}

	// Retorna true si ha sido logrado, false en el otro caso
	public boolean esLogrado() {
		return logrado;
	}

	// Marca el logro como logrado y guarda la fecha en que se ha logrado
	public void informaLogrado() {
		this.logrado = true;
		this.fechaLogrado = LocalDate.now();
	}

	// Devuelve la fecha en que se ha logrado
	public LocalDate obtenFechaLogrado() {
		return fechaLogrado;
	}

	// Devuelve el ID del logro al que representa 
	public int obtenIdLogro() {
		return this.logro.obtenId();
	}
	
	// Añade el logro al que representa
	public void informaLogro(Logro logro) {
		this.logro = logro;
	}	


}
