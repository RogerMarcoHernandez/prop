package Dominio;

import java.io.Serializable;

/**
 * Clase que representa los registros de movimientos en el juego.
 * @author Jordi Estany
 */
public class RecordsMovimientos implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** Instancia de la clase */
	private static RecordsMovimientos recordsMoviments;

	/** Conjunto de records de la clase 
	 * Filas: Tamano de codigo
	 * Columnas: Colores
	 */
	private Pair<Integer, String>[][] records; 

	/**
     * Constructor privado de la clase RecordsMovimientos.
     * Inicializa el array de registros con un tamano predeterminado.
     */
	private RecordsMovimientos() {
		records = new Pair[5][4];
		
	}
	
	/**
     * Metodo para obtener la instancia unica de RecordsMovimientos.
     * Si la instancia no existe, se crea una nueva instancia.
     *
     * @return la instancia unica de RecordsMovimientos.
     */
	public static RecordsMovimientos obtenInstancia() {
		if (recordsMoviments == null)
			recordsMoviments = new RecordsMovimientos();
		return recordsMoviments;
	}


	/**
     * Metodo para anadir una partida a los registros si es un nuevo record.
     *
     * @param usuario         el nombre de usuario del jugador.
     * @param longitudCodigo  la longitud del codigo de la partida.
     * @param numeroColores   el numero de colores de la partida.
     * @param turnos          los turnos realizados en la partida.
     * @return true si se anadio la partida como record, false de lo contrario.
     */
	public boolean anadirPartida(String usuario, int longitudCodigo, int numeroColores, int turnos) {
		if (records[longitudCodigo-1][numeroColores-2] == null) {
			records[longitudCodigo-1][numeroColores-2] = new Pair<>(turnos,usuario);
			return(true);
		}
		else if (records[longitudCodigo-1][numeroColores-2].obtenPrimero() > turnos) {
			records[longitudCodigo-1][numeroColores-2].informaPrimero(turnos);
			records[longitudCodigo-1][numeroColores-2].informaSegundo(usuario);
			return(true);
		}
		else {
			return(false);
		}		
	}
	
	/**
     * Metodo para obtener el record de una longitud y numero de colores determinados.
     *
     * @param longitudCodigo  la longitud del codigo.
     * @param numeroColores   el numero de colores.
     * @return el par de valores (turnos, usuario) que representa el record.
     * @throws Exception si la longitud de codigo o el numero de colores son incorrectos, o si no hay record para esos valores.
     */
	public Pair<Integer, String> obtenRecord(int longitudCodigo, int numeroColores) throws Exception {
		if (longitudCodigo < 1 || longitudCodigo > 5) throw new Exception("ERROR: longitud de codigo incorrecta");
		if (numeroColores < 2 || numeroColores > 5) throw new Exception("ERROR: numero de colores incorrectos");
		if (records[longitudCodigo-1][numeroColores-2] == null) throw new Exception("ERROR: nadie ha ganado en este modo");
		return records[longitudCodigo-1][numeroColores-2];
	}

	/**
     * Metodo para obtener todos los registros de records.
     *
     * @return un array bidimensional de tipo Pair que contiene todos los records.
     */
	public Pair<Integer, String>[][] obtenRecords() {
		return records;
	}

	/**
     * Metodo para informar y actualizar todos los registros de records.
     *
     * @param records el nuevo array bidimensional de records a establecer.
     */
	public void informaRecords(Pair<Integer, String>[][] records) {
		this.records = records;
	}	
	
	/**
     * Metodo para restablecer los registros, eliminando todos los records.
     * Se inicializa el array de registros con un nuevo array vacio.
     */
	public void reset() {
		this.records = new Pair[5][4];
	}

}

