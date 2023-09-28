	package Main;

import ControladoresPresentacion.ControladorPresentacio;

public class MainPres {
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater (
			      new Runnable() {
			        public void run() {
			        	ControladorPresentacio controladorPresentacio = new ControladorPresentacio();
			        	controladorPresentacio.inicializarPresentacion();
			    }});
			  }
	}

