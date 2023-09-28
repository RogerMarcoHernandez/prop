package ControladoresPersistencia;

import java.util.ArrayList;
import java.util.List;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;
import java.io.File;

/**
 * CtrlPersistencia es una clase que actúa como el controlador principal de la capa de persistencia. 
 * Se encarga de escribir y leer los objetos
 * 
 * @author Alex Pertusa i Roger Marco
 */
public class ControladorPersistencia {

	/**
	 * Instancia del controlador de persistencia
	 */
	private static ControladorPersistencia ctrl;
	
	private FileInputStream fileInputStream;
	private FileOutputStream fileOutputStream;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	
	/**
	 * Constructor privado del controlador de persistencia
	 */
	private ControladorPersistencia() {
	}

	/**
	 * Metodo que obtiene la instancia del controlador de persistencia si existe o lo crea en caso de que no exista
	 * @return Instancia del controlador de persistencia
	 */
	public static ControladorPersistencia obtenInstancia() {
		if (ctrl == null)
			ctrl = new ControladorPersistencia();
		return ctrl;
	}
	
	/**
	 * Metodo para escribir las partidas del ranking global en su archivo correspondiente
	 * @param partidas Lista de partidas a escribir
	 * @throws Exception Si ocurre un error durante la escritura
	 */
	public void escribeRankingGlobal(List<List<Object>> partidas) throws Exception{
		String nombreFichero = "rankingGlobal.txt";
		File archivoRanking = new File(nombreFichero);
		if (archivoRanking.exists()) archivoRanking.delete();
		abreArchivoConEscriptura(nombreFichero);
	    escribeObjeto(partidas);
	    cierraOutputStream();
	}
	
	/**
	 * Metodo para leer las partidas del ranking global de su archivo correspondiente
	 * @return Lista de partidas del ranking global
	 * @throws Exception Si ocurre un error durante la lectura
	 */
	public List<List<Object>> leeRankingGlobal() throws Exception {
		String nombreFichero = "rankingGlobal.txt";
		abreArchivoConLectura(nombreFichero);
	    List<List<Object>> partidasRankingGlobal = (List<List<Object>>) objectInputStream.readObject();
		cierraInputStream();
	    return partidasRankingGlobal;
	}
	
	/**
	 * Metodo para leer los records movimientos de su archivo correspondiente
	 * @return Objeto de records movimientos
	 * @throws Exception Si ocurre un error durante la lectura
	 */
	public Object leeRecordsMovimientos() throws Exception {
		String nombreFichero = "RecordsMovimientos.txt";
		abreArchivoConLectura(nombreFichero);
		Object recordsMovimientos = new Object();
	    try {
			recordsMovimientos = objectInputStream.readObject();
		} catch (EOFException e) {
			// TODO: handle exception
		}
		cierraInputStream();
	    return recordsMovimientos;
	}
	
	/**
	 * Metodo para escribir los records movimientos en su archivo correspondiente
	 * @param recordsMovimientos Objeto de records movimientos a escribir
	 * @throws Exception Si ocurre un error durante la escritura
	 */
	public void escribeRecordsMovimientos(Object recordsMovimientos) throws Exception{
		String nombreFichero = "RecordsMovimientos.txt";
		File archivoRecords = new File(nombreFichero);
		if (archivoRecords.exists()) archivoRecords.delete();
		abreArchivoConEscriptura(nombreFichero);
		escribeObjeto(recordsMovimientos);
	    cierraOutputStream();
	}
	
	/**
	 * Metodo para leer los perfiles de su archivo correspondiente
	 * @return Lista de perfiles
	 * @throws Exception Si ocurre un error durante la lectura
	 */
	public List<Object> leePerfiles() throws Exception {
		String nombreFichero = "Perfiles.txt";
		abreArchivoConLectura(nombreFichero);
	    List<Object> Perfiles = new ArrayList<>();
	    Object perfil;
	    try {
	    	while ((perfil = objectInputStream.readObject()) != null) {
	            Perfiles.add(perfil);
	        }
	    } catch(EOFException e){
	    	
	    }
		cierraInputStream();
	    return Perfiles;
	}
	
	/**
	 * Metodo para escribir los perfiles en su archivo correspondiente
	 * @param Perfiles Lista de perfiles
	 * @throws Exception Si ocurre un error durante la escritura
	 */
	public void escribePerfiles(List<Object> Perfiles) throws Exception{
		String nombreFichero = "Perfiles.txt";
		File archivoPerfiles = new File(nombreFichero);
		if (archivoPerfiles.exists()) archivoPerfiles.delete();
		abreArchivoConEscriptura(nombreFichero);
		for (Object perfil: Perfiles) {
			escribeObjeto(perfil);
		}
	    cierraOutputStream();

	}
	
	/**
	 * Metodo para cerrar el flujo de escritura de objetos
	 * @throws Exception Si ocurre un error durante el cierre del flujo
	 */
	private void cierraOutputStream() throws Exception {
		try {
			objectOutputStream.flush();
		} catch (IOException e) {
			throw new Exception("ERROR: "+e.getMessage());
		}
	    try {
			objectOutputStream.close();
		} catch (IOException e) {
			throw new Exception("ERROR: "+e.getMessage());
		}
	}
		
	/**
	 * Metodo para cerrar el flujo de lectura de objetos
	 * @throws Exception Si ocurre un error durante el cierre del flujo
	 */
	private void cierraInputStream() throws Exception {
		try {
			objectInputStream.close();
		} catch (IOException e) {
			throw new Exception("ERROR: "+e.getMessage());
		}
	}
	
	/**
	 * Metodo para escribir un objeto
	 * @param objeto Objeto a escribir
	 * @throws Exception Si ocurre un error durante la escritura
	 */
	private void escribeObjeto(Object objeto) throws Exception {
		try {
			objectOutputStream.writeObject(objeto);
		} catch (IOException e) {
			throw new Exception("ERROR: "+e.getMessage());
		}
	}
	
	
	/**
	 * Metodo para preparar las variables del controlador para que puedan abrir un archivo con modo lectura
	 * @param nombreFichero Nombre del fichero a leer
	 * @throws Exception Si ocurre un error durante la asignacion de las variables
	 */
	private void abreArchivoConLectura(String nombreFichero) throws Exception {
		try {
			fileInputStream = new FileInputStream("../../DATA/"+nombreFichero);
		} catch (FileNotFoundException e) {
			throw new Exception("ERROR: "+e.getMessage());
		}
	    try {
			objectInputStream = new ObjectInputStream(fileInputStream);
		} catch (IOException e) {
			throw new Exception("ERROR: "+e.getMessage());
		}
	}
	
	/**
	 * Metodo para preparar las variables del controlador para que puedan abrir un archivo con modo escritura
	 * @param nombreFichero Nombre del fichero a escribir
	 * @throws IOException Si ocurre un error durante la asignacion de las variables
	 */
	private void abreArchivoConEscriptura(String nombreFichero) throws IOException {
		fileOutputStream = new FileOutputStream("../../DATA/"+nombreFichero);
	    objectOutputStream = new ObjectOutputStream(fileOutputStream);
	}

}
