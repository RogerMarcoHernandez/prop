package Dominio;

public class RecordsMovimientos {

	// Instancia de records
	private static RecordsMovimientos recordsMoviments;

	// Records (Filas -> Tamano de codigo ; Columnas -> Colores)
	private Pair<Integer, String>[][] records; 

	// Constructor
	private RecordsMovimientos() {
		records = new Pair[5][4];
		
	}
	
	// Funcion para obtener la instancia en caso de que exista, si no la crea
	public static RecordsMovimientos obtenInstancia() {
		if (recordsMoviments == null)
			recordsMoviments = new RecordsMovimientos();
		return recordsMoviments;
	}


	// Funcion para anadir partida en caso de que sea nuevo record
	public void anadirPartida(String usuario, int longitudCodigo, int numeroColores, int turnos) {
		if (records[longitudCodigo-1][numeroColores-2] == null) {
			records[longitudCodigo-1][numeroColores-2] = new Pair<>(turnos,usuario);
		}
	else if (records[longitudCodigo-1][numeroColores-2].obtenPrimero() > turnos) {
			records[longitudCodigo-1][numeroColores-2].informaPrimero(turnos);
			records[longitudCodigo-1][numeroColores-2].informaSegundo(usuario);
		}
		
	}
	
	// Funcion para obtener el record de una longitud i numero de colores determinados
	public Pair<Integer, String> obtenRecord(int longitudCodigo, int numeroColores) throws Exception {
		if (longitudCodigo < 1 || longitudCodigo > 5) throw new Exception("ERROR: longitud de codigo incorrecta");
		if (numeroColores < 2 || numeroColores > 5) throw new Exception("ERROR: numero de colores incorrectos");
		if (records[longitudCodigo-1][numeroColores-2] == null) throw new Exception("ERROR: nadie ha ganado en este modo");
		return records[longitudCodigo-1][numeroColores-2];
	}

	// Funcion para obtener todos los records posibles
	public Pair<Integer, String>[][] obtenRecords() {
		return records;
	}

	// Funcion para modificar todos los records
	public void informaRecords(Pair<Integer, String>[][] records) {
		this.records = records;
	}	
	
	// Funcion que resetea los records
	public void reset() {
		this.records = new Pair[5][4];
	}

}

