package Dominio;

public class Logro {
	
	// Identificador del logro
	private int id;
	
	// Descripcion del logro
	private String descripcion;
	
	// Puntuacion que otorga completar el logro
	private int puntuacion;

	// Creadora 
	public Logro(int pId, String pDescripcion, int pPuntuacion) {
		this.id = pId;
		this.descripcion = pDescripcion;
		this.puntuacion = pPuntuacion;
	}
	
	// Funcion para obtener el id del logro
	public int obtenId() {
		return id;
	}

	// Funcion para obtener la descripcion del logro
	public String obtenDescripcion() {
		return descripcion;
	}

	// Funcion para obtener la puntuacion del logro
	public int obtenPuntuacion() {
		return puntuacion;
	}

}
