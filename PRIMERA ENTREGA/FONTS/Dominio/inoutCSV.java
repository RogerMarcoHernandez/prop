package Dominio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class inoutCSV {

	private static List<List<Double>> leeCSVDoubles(String filename) throws FileNotFoundException {
		FileReader fr = new FileReader(
				"./data/dificultadToStepsCSV/" + filename + ".csv");
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

	public static List<List<Double>> cargaArchivoDificultadToSteps(String filename) {
		try {
			return leeCSVDoubles(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
