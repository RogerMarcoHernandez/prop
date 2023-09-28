package Vistas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ControladoresPresentacion.ControladorVistaPantallaPrincipal;


public class VistaPantallaPrincipal extends JFrame implements ActionListener {
	
	private ControladorVistaPantallaPrincipal iCtrlVista;
	
	private JLabel etiquetaTitulo;
	
	private JButton botonRegistrarse;
	private JButton botonIniciarSesion;
	private JButton botonRankingGlobal;
	private JButton botonRecords;
	private JButton botonManual;
	private JButton botonSalir;
	
	private JPanel panelContenido;
	private JPanel panelBotones;
	private JPanel panelTitulo;
	
	
	public VistaPantallaPrincipal(ControladorVistaPantallaPrincipal pCtrlVistapantallaPrincipal) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iCtrlVista = pCtrlVistapantallaPrincipal;
		iniciarComponentes();
	}
	

	private void iniciarComponentes() {
		panelBotones = new JPanel(new GridLayout(6, 1, 0, 10));
		panelBotones.setBorder(new EmptyBorder(10, 100, 10, 100));
		
		botonRegistrarse = new JButton("Registrarse");
		botonRegistrarse.addActionListener(this);
		botonRegistrarse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonRegistrarse);
		
		botonIniciarSesion = new JButton("Iniciar Sesion");
		botonIniciarSesion.addActionListener(this);
		botonIniciarSesion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonIniciarSesion);
		
		botonRecords = new JButton("Ver records");
		botonRecords.addActionListener(this);
		botonRecords.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonRecords);
		
		botonRankingGlobal = new JButton("Ranking Global");
		botonRankingGlobal.addActionListener(this);
		botonRankingGlobal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonRankingGlobal);
		
		botonManual = new JButton("Manual");
		botonManual.addActionListener(this);
		botonManual.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonManual);
		
		botonSalir = new JButton("Salir");
		botonSalir.addActionListener(this);
		botonSalir.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonSalir);
		
		panelTitulo = new JPanel();
		panelTitulo.setBorder(new EmptyBorder(10, 0, 0, 0));
		etiquetaTitulo = new JLabel("MASTERMIND");
		etiquetaTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelTitulo.add(etiquetaTitulo);
		
		panelContenido = new JPanel(new BorderLayout());
		panelContenido.add(panelTitulo, BorderLayout.NORTH);
		panelContenido.add(panelBotones, BorderLayout.CENTER);
		
		setContentPane(panelContenido);
		setSize(500, 320);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (botonRegistrarse == e.getSource()) {
			iCtrlVista.mostrarVistaRegistro();
		}
		if (botonIniciarSesion == e.getSource()) {
			iCtrlVista.mostrarVistaIniciarSesion();
		}
		if (botonRankingGlobal == e.getSource()) {
			iCtrlVista.mostrarVistaRankingGlobal();
		}
		if (botonRecords == e.getSource()) {
			iCtrlVista.mostrarVistaRecords();
		}
		if (botonManual == e.getSource()) {
			iCtrlVista.mostrarVistaManual();
		}
		if (botonSalir == e.getSource())
			System.exit(0);
	}

	public void hacerVisible() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
