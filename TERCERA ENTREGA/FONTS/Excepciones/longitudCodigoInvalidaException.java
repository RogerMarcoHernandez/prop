package Excepciones;

public class longitudCodigoInvalidaException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public longitudCodigoInvalidaException() {
		super("ERROR: La longitud del codigo informado es invalida ");
	}
}
