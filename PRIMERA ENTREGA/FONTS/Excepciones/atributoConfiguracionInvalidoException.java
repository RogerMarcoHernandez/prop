package Excepciones;

public class atributoConfiguracionInvalidoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public atributoConfiguracionInvalidoException(String nombreAtributo, Object atributo) {
		super("ERROR: "+nombreAtributo+": "+atributo+" invalido");
	}

}
