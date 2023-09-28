package Vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminarPartidaListener implements ActionListener {
	private int idPartida;
	private VistaPartidasGuardadas vista;
	
	public EliminarPartidaListener(int idPartida,  VistaPartidasGuardadas pVista) {
		vista = pVista;
		this.idPartida = idPartida;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		vista.eliminarPartida(idPartida);
	}
}
