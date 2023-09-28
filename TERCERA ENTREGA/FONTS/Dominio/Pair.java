package Dominio;

import java.io.Serializable;
/**
 * Clase generica Pair que representa un par de objetos de tipos T1 y T2.
 *
 * @param <T1> el tipo del primer objeto en el par.
 * @param <T2> el tipo del segundo objeto en el par.
 * 
 * @author Jordi Estany
 */
public class Pair<T1, T2> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** Primer elemento de la pareja */
    private T1 primero;
    
    /** Segundo elemento de la pareja */
    private T2 segundo;

    /**
     * Constructor de la clase Pair.
     *
     * @param primero el primer objeto del par.
     * @param segundo el segundo objeto del par.
     */
    public Pair(T1 primero, T2 segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    /**
     * Metodo para obtener el primer objeto del par.
     *
     * @return el primer objeto del par.
     */
    public T1 obtenPrimero() {
        return primero;
    }

    /**
     * Metodo para obtener el segundo objeto del par.
     *
     * @return el segundo objeto del par.
     */
    public T2 obtenSegundo() {
        return segundo;
    }

    /**
     * Metodo para informar y actualizar el primer objeto del par.
     *
     * @param primero el nuevo valor del primer objeto.
     */
    public void informaPrimero(T1 primero) {
        this.primero = primero;
    }

    /**
     * Metodo para informar y actualizar el segundo objeto del par.
     *
     * @param segundo el nuevo valor del segundo objeto.
     */
    public void informaSegundo(T2 segundo) {
        this.segundo = segundo;
    }

}
