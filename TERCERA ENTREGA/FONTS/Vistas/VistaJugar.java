package Vistas;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ControladoresPresentacion.ControladorVistaSesion;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class VistaJugar extends JFrame implements ActionListener{
	
	private ControladorVistaSesion iCtrlVista;

	private JPanel panelDeContenido;
	private JPanel panelBotones;
	
	private JButton botonCrearPartida;
	private JButton botonJugarPartida;
	private JButton botonAtras;
	
	public VistaJugar(ControladorVistaSesion pCtrlVistaSesion) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iCtrlVista = pCtrlVistaSesion;	
		iniciarComponentes();
	}
	
	private void iniciarComponentes() {
		
		setBounds(100, 100, 450, 300);
		panelDeContenido = new JPanel(new BorderLayout());
		panelBotones = new JPanel(new GridLayout(3, 1, 0, 10));
		
		botonCrearPartida = new JButton("Crear Partida");
		botonCrearPartida.addActionListener(this);
		botonCrearPartida.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonCrearPartida);
		
		botonJugarPartida = new JButton("Cargar Partida");
		botonJugarPartida.addActionListener(this);
		
		botonJugarPartida.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonJugarPartida);
		
		botonAtras = new JButton("Atras");
		botonAtras.addActionListener(this);
		botonAtras.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonAtras);
		
		panelDeContenido.add(panelBotones);
		panelDeContenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        setContentPane(panelDeContenido);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(botonCrearPartida==e.getSource()) {
			iCtrlVista.mostrarTipoPartida();
			dispose();
		}
		if(botonJugarPartida==e.getSource()) {
			iCtrlVista.mostrarPartidasGuardadas();
			dispose();
		}
		if(botonAtras ==e.getSource()) {
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
