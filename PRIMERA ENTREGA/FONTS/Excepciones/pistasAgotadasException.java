package Excepciones;

public class pistasAgotadasException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public pistasAgotadasException() {
		super("ERROR: Se han agotado las pistas ");
	}
}
