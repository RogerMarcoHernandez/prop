package Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import ControladoresPresentacion.ControladorVistaSesion;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.GridLayout;

public class VistaPartidasGuardadas extends JFrame implements ActionListener{

	private ControladorVistaSesion iCtrlVista;

	private JPanel panelDeContenido;
	JPanel panelEspacio;
	
	private JButton salir;
	private JButton botonCargar;
	private JButton botonEliminar;
	
	JPanel panelCabeceras;
	JLabel etiquetaID;
	JLabel etiquetaNumeroTurnos;
	JLabel etiquetaDificultad;
	JLabel etiquetaAyuda;
	JLabel etiquetaLongitudCodigo;
	JLabel etiquetaColores;
	JLabel etiquetaTipo;
	JLabel etiquetaTurno;
	

	public VistaPartidasGuardadas(ControladorVistaSesion pCtrlVistaSesion) throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iCtrlVista = pCtrlVistaSesion;	
		iniciarComponentes();
	}
	
	private void iniciarComponentes() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				
		ArrayList<Integer> partidasGuardadas = iCtrlVista.obtenPartidasGuardadas();		
		int numeroPartidas = partidasGuardadas.size();
		
		panelDeContenido = new JPanel(new GridLayout(numeroPartidas+2, 1));
		
		panelCabeceras = new JPanel(new GridLayout(1, 10));
		panelCabeceras.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		etiquetaID= new JLabel("Id:");
		etiquetaNumeroTurnos = new JLabel("Turnos:");
		etiquetaDificultad = new JLabel("Dificultad:");
		etiquetaAyuda = new JLabel("Ayuda:");
		etiquetaLongitudCodigo = new JLabel("Longitud:");
		etiquetaColores = new JLabel("Colores:");
		etiquetaTipo = new JLabel("Tipo:");
		etiquetaTurno = new JLabel("Turno:");
		panelEspacio = new JPanel();		
		
		etiquetaID.setHorizontalAlignment(JLabel.CENTER);
		etiquetaNumeroTurnos.setHorizontalAlignment(JLabel.CENTER);
		etiquetaDificultad.setHorizontalAlignment(JLabel.CENTER);
		etiquetaAyuda.setHorizontalAlignment(JLabel.CENTER);
		etiquetaLongitudCodigo.setHorizontalAlignment(JLabel.CENTER);
		etiquetaColores.setHorizontalAlignment(JLabel.CENTER);
		etiquetaTipo.setHorizontalAlignment(JLabel.CENTER);
		etiquetaTurno.setHorizontalAlignment(JLabel.CENTER);
		
		panelCabeceras.add(etiquetaID);
		panelCabeceras.add(etiquetaNumeroTurnos);
		panelCabeceras.add(etiquetaDificultad);
		panelCabeceras.add(etiquetaAyuda);
		panelCabeceras.add(etiquetaLongitudCodigo);
		panelCabeceras.add(etiquetaColores);
		panelCabeceras.add(etiquetaTipo);
		panelCabeceras.add(etiquetaTurno);
		panelCabeceras.add(panelEspacio);
		panelEspacio = new JPanel();
		panelCabeceras.add(panelEspacio);
		panelDeContenido.add(panelCabeceras);
		for (int i = 0; i < numeroPartidas; i++ ) {
			JPanel panelPartida = new JPanel(new GridLayout(1, 10));
			panelPartida.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			JLabel idPartida= new JLabel(Integer.toString(partidasGuardadas.get(i)));
			int [] datos = iCtrlVista.obtenDatosPartida(partidasGuardadas.get(i));
			JLabel numeroTurnos = new JLabel(Integer.toString(datos[0]));
			JLabel dificultad = new JLabel(Integer.toString(datos[1]));
			JLabel nivelAyuda = new JLabel(Integer.toString(datos[2]));
			JLabel longitudCodigo = new JLabel(Integer.toString(datos[4]));
			JLabel numeroColores = new JLabel(Integer.toString(datos[3]));
			JLabel tipo = new JLabel("CodeBreaker");
			if (datos[5] == 1) tipo = new JLabel("CodeMaker");
			else dificultad = new JLabel("-");
			JLabel turn = new JLabel(Integer.toString(datos[6]+1));
			
			idPartida.setHorizontalAlignment(JLabel.CENTER);
			numeroTurnos.setHorizontalAlignment(JLabel.CENTER);
			dificultad.setHorizontalAlignment(JLabel.CENTER);
			nivelAyuda.setHorizontalAlignment(JLabel.CENTER);
			longitudCodigo.setHorizontalAlignment(JLabel.CENTER);
			numeroColores.setHorizontalAlignment(JLabel.CENTER);
			tipo.setHorizontalAlignment(JLabel.CENTER);
			turn.setHorizontalAlignment(JLabel.CENTER);
			
			botonCargar  = new JButton("Cargar");
			botonEliminar  = new JButton("Borrar");
			botonCargar.addActionListener(new CargarPartidaListener(partidasGuardadas.get(i),this));
			botonEliminar.addActionListener(new EliminarPartidaListener(partidasGuardadas.get(i),this));
			panelPartida.add(idPartida);
			panelPartida.add(numeroTurnos);
			panelPartida.add(dificultad);
			panelPartida.add(nivelAyuda);
			panelPartida.add(longitudCodigo);
			panelPartida.add(numeroColores);
			panelPartida.add(tipo);
			panelPartida.add(turn);
			panelPartida.add(botonCargar);
			panelPartida.add(botonEliminar);
			
			panelDeContenido.add(panelPartida);
			
		}
		salir  = new JButton("SALIR");
		salir.addActionListener(this);
		panelDeContenido.add(salir);
		setContentPane(panelDeContenido);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(salir==e.getSource()) {
			iCtrlVista.iniciar();
			dispose();
		}
	}
	
	private void reiniciarComponentes() throws Exception {
	    panelDeContenido.removeAll(); // Elimina todos los componentes del panel
	    iniciarComponentes(); // Vuelve a inicializar los componentes
	    panelDeContenido.revalidate(); // Vuelve a validar el panel
	    panelDeContenido.repaint(); // Vuelve a pintar el panel
	    pack();
		setLocationRelativeTo(null);
	}

	public void hacerVisible() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);		
	}

	public void cargarPartida(int idPartida) {
		iCtrlVista.cargarPartida(idPartida);
		dispose();		
	}
	
	public void eliminarPartida(int idPartida) {
		iCtrlVista.eliminarPartida(idPartida);
		try {
			reiniciarComponentes();
		} catch (Exception e1) {
			iCtrlVista.iniciar();
			dispose();
		}	
	}

}