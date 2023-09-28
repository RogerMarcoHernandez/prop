package Excepciones;

public class coloresCodigoInvalidosException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public coloresCodigoInvalidosException() {
		super("ERROR: El codigo contiene valores de color invalidos ");
	}
}
