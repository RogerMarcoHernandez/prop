package Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ControladoresPresentacion.ControladorPresentacio;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.FlowLayout;

public class VistaManual extends JFrame implements ActionListener{

	private ControladorPresentacio iCtrlVista;
	
	private JPanel panelTitulo;
	private JPanel panelTexto;
	private JPanel panelBotones;
	private JPanel panelContenido;
	
	private JButton botonAnterior;
	private JButton botonSiguiente;
	private JButton botonCerrar;
	
	private JLabel etiquetaTitulo;
	private JTextPane panelPagina;
	
	private int numeroDePagina;
	private int totalPaginas = 3;
	
	

	// El diseno  del manual esta inspirado en un libro y la mecanica de pasar paginas
	public VistaManual(ControladorPresentacio controladorPresentacio) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iCtrlVista = controladorPresentacio;
		iniciarComponentes();
	}
	
	private void iniciarComponentes() {
		numeroDePagina = 1;
		setBounds(100, 100, 450, 300);
		panelContenido = new JPanel();
		panelContenido.setBackground(new Color(255, 255, 255));
		panelContenido.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panelContenido);
		panelContenido.setLayout(new BorderLayout());
		
		panelTitulo = new JPanel();
		
		panelContenido.add(panelTitulo, BorderLayout.NORTH);
		panelTitulo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		etiquetaTitulo = new JLabel("MANUAL DE JUEGO");
		etiquetaTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));		
		panelTitulo.add(etiquetaTitulo);
		
		panelTexto = new JPanel();
		panelContenido.add(panelTexto);
		
		panelPagina = new JTextPane();
		
		panelPagina.setText(Manual.getTexto(numeroDePagina));
		panelPagina.setEditable(false);
		panelTexto.add(panelPagina);
		
		panelBotones = new JPanel();
		panelContenido.add(panelBotones, BorderLayout.SOUTH);
		
		botonAnterior = new JButton("<-");
		botonAnterior.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botonAnterior.setEnabled(false);
		botonAnterior.addActionListener(this);
		panelBotones.add(botonAnterior);
		
		botonSiguiente = new JButton("->");
		botonSiguiente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botonSiguiente.addActionListener(this);
		panelBotones.add(botonSiguiente);
		
		botonCerrar = new JButton("Cerrar Manual");
		botonCerrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botonCerrar.addActionListener(this);
		panelBotones.add(botonCerrar);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(botonSiguiente==e.getSource()) {
			++numeroDePagina;
			if(numeroDePagina == totalPaginas) botonSiguiente.setEnabled(false);
			botonAnterior.setEnabled(true);
			panelPagina.setText(Manual.getTexto(numeroDePagina));
			pack();
			setLocationRelativeTo(null);
		}
		if(botonAnterior==e.getSource()) {
			--numeroDePagina;
			if(numeroDePagina == 1) botonAnterior.setEnabled(false);
			botonSiguiente.setEnabled(true);
			panelPagina.setText(Manual.getTexto(numeroDePagina));
			pack();
			setLocationRelativeTo(null);
		}
		if(botonCerrar==e.getSource()) {
			iCtrlVista.volver();
            dispose();
		}
		
	}
	public void hacerVisible() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);		
	}
}
