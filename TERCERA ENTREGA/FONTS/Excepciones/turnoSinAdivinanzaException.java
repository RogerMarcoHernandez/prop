package Excepciones;

public class turnoSinAdivinanzaException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public turnoSinAdivinanzaException() {
		super("ERROR: El Codebreaker debe informar una adivinanza al ejecutar el turno como parametro ");
	}

}
