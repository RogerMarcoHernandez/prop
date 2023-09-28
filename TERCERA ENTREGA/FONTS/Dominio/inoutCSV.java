package Dominio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase encargada de la lectura de matrizes de dobles en formato csv.
 * @author Roger Marco
 *
 */
public class inoutCSV {

	/**
	 * Lee archivo .csv de dobles.
	 * @param filename Nombre del archivo a leer.
	 * @return Devuelve la matriz de dobles.
	 * @throws FileNotFoundException En caso de no encontrar el archivo.
	 */
	private static List<List<Double>> leeCSVDoubles(String filename) throws FileNotFoundException {
		FileReader fr;
		try {
			fr = new FileReader("../../DATA/" + filename + ".csv");
		}
		catch(FileNotFoundException e) {
			fr = new FileReader("../DATA/" + filename + ".csv");
		}
		Scanner scan = new Scanner(fr);

		List<List<Double>> matriz = new ArrayList<>();
		List<Double> fila;
		while (scan.hasNextLine()) {
			fila = new ArrayList<>();
			String[] fs = scan.nextLine().split(";");

			for (String s : fs)
				fila.add(Double.parseDouble(s));
			matriz.add(fila);
		}
		return matriz;
	}

	/**
	 * Cargadora del archivo de dificultad.
	 * @param filename Nombre del archivo.
	 * @return Devuelve la matriz de dobles.
	 */
	public static List<List<Double>> cargaArchivoDificultadToSteps(String filename) {
		try {
			return leeCSVDoubles(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
