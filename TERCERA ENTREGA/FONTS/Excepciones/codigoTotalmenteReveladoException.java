package Excepciones;

public class codigoTotalmenteReveladoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public codigoTotalmenteReveladoException() {
		super("ERROR: El codigo secreto ha sido totalmente revelado ");
	}
}
