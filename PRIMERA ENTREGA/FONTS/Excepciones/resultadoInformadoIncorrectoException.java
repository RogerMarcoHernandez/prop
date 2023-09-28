package Excepciones;

public class resultadoInformadoIncorrectoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public resultadoInformadoIncorrectoException() {
		super("ERROR: El resultado informado es incorrecto");
	}
}
