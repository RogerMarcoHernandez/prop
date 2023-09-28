package Vistas;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ControladoresPresentacion.ControladorVistaSesion;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


public class VistaConfiguracionPartida extends JFrame implements ActionListener {
	
	private ControladorVistaSesion iCtrlVista;
	
	private JPanel panelTitulo;
	private JPanel panelConfig;
	private JPanel panelBotones;
	private JPanel contentPane;
	
	private JLabel etiquetaTitulo;
	private JLabel etiquetaNumeroTurnos;
	private JLabel etiquetaTamanoCodigo;
	private JLabel etiquetaNivelAyuda;
	private JLabel etiquetaDificultad;
	private JLabel etiquetaNumeroColores;
	
	private JSpinner valorTamanoCodigo;
	private JSpinner valorNumeroTurnos;
	private JSpinner valorNumerocolores;
	private JSpinner valorAyuda;
	private Choice valorDificultad;
	private boolean codeMaker = true;
	
	private JButton volverAtras;
	private JButton empezarPartida;	
	
	private String opcion1 = "Genetic - Facil";
	private String opcion2 = "Genetic - Medio";
	private String opcion3 = "Genetic - Dificil";
	private String opcion4 = "Five Guess";

	public VistaConfiguracionPartida(ControladorVistaSesion pCtrlVistaSesion) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iCtrlVista = pCtrlVistaSesion;
		iniciarComponentes();
	}

	private void iniciarComponentes() {
		
		panelTitulo = new JPanel();
		panelTitulo.setBorder(new EmptyBorder(10, 0, 0, 0));
		etiquetaTitulo = new JLabel("CONFIGURA TU PARTIDA");
		etiquetaTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelTitulo.add(etiquetaTitulo);
		
		contentPane = new JPanel(new BorderLayout());
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		
		setContentPane(contentPane);
		contentPane.setBorder(new EmptyBorder(10, 20, 10, 20));
		
		panelConfig = new JPanel();
		contentPane.add(panelConfig, BorderLayout.CENTER);
		panelConfig.setLayout(new GridLayout(5, 2, 5, 5));
		
		etiquetaNumeroTurnos = new JLabel("Numero de turnos");
		panelConfig.add(etiquetaNumeroTurnos);
		
		valorNumeroTurnos = new JSpinner();
		valorNumeroTurnos.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		panelConfig.add(valorNumeroTurnos);
		
		etiquetaTamanoCodigo = new JLabel("Tamaño de codigo");
		panelConfig.add(etiquetaTamanoCodigo);
		
		valorTamanoCodigo = new JSpinner();
		valorTamanoCodigo.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		panelConfig.add(valorTamanoCodigo);
		
		etiquetaNumeroColores = new JLabel("Numero de Colores");
		panelConfig.add(etiquetaNumeroColores);
		
		valorNumerocolores = new JSpinner();
		valorNumerocolores.setModel(new SpinnerNumberModel(2, 2, 5, 1));
		panelConfig.add(valorNumerocolores);
		
		etiquetaNivelAyuda = new JLabel("Nivel de ayuda");
		panelConfig.add(etiquetaNivelAyuda);
		
		valorAyuda = new JSpinner();
		valorAyuda.setModel(new SpinnerNumberModel(0, 0, 2, 1));
		panelConfig.add(valorAyuda);
		
		etiquetaDificultad = new JLabel("Dificultad");
		panelConfig.add(etiquetaDificultad);
		panelConfig.setBorder(new EmptyBorder(10, 10, 10, 10));
		valorDificultad = new Choice();
		valorDificultad.add(opcion1);
		valorDificultad.add(opcion2);
		valorDificultad.add(opcion3);
		valorDificultad.add(opcion4);
		panelConfig.add(valorDificultad);
		
		panelBotones = new JPanel();
		contentPane.add(panelBotones, BorderLayout.SOUTH);
		
		volverAtras = new JButton("Volver Atras");
		volverAtras.addActionListener(this);
		
		empezarPartida = new JButton("Empezar Partida");
		empezarPartida.addActionListener(this);
		panelBotones.add(empezarPartida);
		panelBotones.add(volverAtras);
		setSize(500, 320);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (volverAtras == e.getSource()) {
			iCtrlVista.mostrarTipoPartida();
			dispose();
		}
		if (empezarPartida == e.getSource()) {
			int longitudCodigo = (int) valorTamanoCodigo.getValue();
			int numeroColores = (int) valorNumerocolores.getValue();
			int numeroTurnos = (int) valorNumeroTurnos.getValue();
			int nivelAyuda = (int) valorAyuda.getValue();
			int nivelDificultad = 0;
			String seleccion = valorDificultad.getSelectedItem();
			if (seleccion == opcion2) nivelDificultad = 1;
			else if (seleccion == opcion3) nivelDificultad = 2;
			else if (seleccion == opcion4) nivelDificultad = 3;
			
			iCtrlVista.empezarPartida(longitudCodigo, numeroColores, numeroTurnos, nivelAyuda, codeMaker, nivelDificultad);
			dispose();
		}
	}

	public void hacerVisible(boolean codeMaker) {
		pack();
		setLocationRelativeTo(null);
		if (!codeMaker) {
			this.codeMaker = false;
			panelConfig.remove(etiquetaDificultad);
			panelConfig.remove(valorDificultad);
		}
		else {
			this.codeMaker = true;
			panelConfig.add(etiquetaDificultad);
			panelConfig.add(valorDificultad);
		}
		setVisible(true);
	}
}
