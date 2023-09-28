package Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ControladoresPresentacion.ControladorVistaSesion;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.GridLayout;

public class VistaEstadisticas extends JFrame implements ActionListener{

	private ControladorVistaSesion iCtrlVista;
	
	private JPanel panelEstadisticas;
	private JPanel panelDeContenido;
	
	private JButton botonSalir;
	
	private JLabel etiquetaPartidasJugadas;
	private JLabel etiquetaPartidasGanadas;
	private JLabel etiquetaPartidasPerdidas;
	private JLabel etiquetaPorcentajeDeVictoria;
	
	public VistaEstadisticas(ControladorVistaSesion pCtrlVistaSesion) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iCtrlVista = pCtrlVistaSesion;	
		iniciarComponentes();
	}
	
	private void iniciarComponentes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		int[] stats = iCtrlVista.obtenEstadisticas();	
		
		panelDeContenido = new JPanel(new GridLayout(2, 2));
		
		panelEstadisticas = new JPanel(new GridLayout(4, 1));
		etiquetaPartidasJugadas = new JLabel("Partias jugadas:  " + stats[0]);
		etiquetaPartidasGanadas = new JLabel("Partias ganadas:  " + stats[1]);
		etiquetaPartidasPerdidas = new JLabel("Partias perdidas:  " + stats[2]);
		etiquetaPorcentajeDeVictoria= new JLabel("Porcentaje de victoria:  " + String.format("%.2f", iCtrlVista.obtenWr()) + "%");
		
		panelEstadisticas.add(etiquetaPartidasJugadas);
		panelEstadisticas.add(etiquetaPartidasGanadas);
		panelEstadisticas.add(etiquetaPartidasPerdidas);
		panelEstadisticas.add(etiquetaPorcentajeDeVictoria);
		
		botonSalir  = new JButton("SALIR");
		botonSalir.addActionListener(this);
		panelDeContenido.add(panelEstadisticas);
		panelDeContenido.add(botonSalir);
		
		panelDeContenido.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		setContentPane(panelDeContenido);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(botonSalir==e.getSource()) {
			iCtrlVista.iniciar();
			dispose();
		}
	}

	public void hacerVisible() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);		
	}
}