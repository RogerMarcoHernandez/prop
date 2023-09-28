package Excepciones;

public class turnoInicialSinCodigoSecretoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public turnoInicialSinCodigoSecretoException() {
		super("ERROR: El Codemaker debe informar del codigo secreto en el turno inicial como parametro ");
	}

}
