package Excepciones;

public class configuracionConValoresNullException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public configuracionConValoresNullException() {
		super("ERROR: La configuracion no puede contener valores NULL ");
	}

}
