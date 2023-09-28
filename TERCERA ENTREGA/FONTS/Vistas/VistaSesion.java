package Vistas;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ControladoresPresentacion.ControladorVistaSesion;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaSesion extends JFrame implements ActionListener{
	
	private ControladorVistaSesion iCtrlVista;

	private JPanel panelDeContenido;
	private JPanel panelTitulo;
	
	private JButton botonJugar;
	private JButton botonConfigurarPerfil;
	private JButton botonEstadisticas;
	private JButton botonCerrarSesion;
	private JButton botonRankingGlobal;
	private JButton botonVerManual;
	private JButton botonRecords;
	
	private JLabel etiquetaTitulo;

	public VistaSesion(ControladorVistaSesion pControladorVistaSesion) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iCtrlVista = pControladorVistaSesion;
		iniciarComponentes();
	}
	
	private void iniciarComponentes() {
		JPanel panelBotones = new JPanel(new GridLayout(7, 1, 0, 10));
		panelBotones.setBorder(new EmptyBorder(10, 100, 10, 100));
		
		botonJugar = new JButton("Jugar");
		botonJugar.addActionListener(this);
		botonJugar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonJugar);
		
		botonConfigurarPerfil = new JButton("Configuracion");
		botonConfigurarPerfil.addActionListener(this);
		botonConfigurarPerfil.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonConfigurarPerfil);
		
		botonEstadisticas = new JButton("Estadisticas");
		botonEstadisticas.addActionListener(this);
		botonEstadisticas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonEstadisticas);
		
		botonCerrarSesion = new JButton("Cerrar Sesion");
		botonCerrarSesion.addActionListener(this);
		botonCerrarSesion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonCerrarSesion);
		
		botonRankingGlobal = new JButton("Ranking Global");
		botonRankingGlobal.addActionListener(this);
		botonRankingGlobal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonRankingGlobal);
		
		botonRecords = new JButton("Ver records");
		botonRecords.addActionListener(this);
		botonRecords.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonRecords);
		
		botonVerManual = new JButton("Manual");
		botonVerManual.addActionListener(this);
		botonVerManual.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(botonVerManual);
		
		panelTitulo = new JPanel();
		panelTitulo.setBorder(new EmptyBorder(10, 0, 0, 0));
		etiquetaTitulo = new JLabel("MASTERMIND");
		etiquetaTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelTitulo.add(etiquetaTitulo);
		
		panelDeContenido = new JPanel(new BorderLayout());
		panelDeContenido.add(panelTitulo, BorderLayout.NORTH);
		panelDeContenido.add(panelBotones, BorderLayout.CENTER);
		
		setContentPane(panelDeContenido);
		setSize(500, 320);
	}
	

    @Override
    public void actionPerformed(ActionEvent e) {
        if (botonJugar == e.getSource()) {
			iCtrlVista.mostrarVistaJugar();
		}
        if (botonConfigurarPerfil == e.getSource()) {
			iCtrlVista.mostrarVistaConfiguracion();
        }
        if (botonEstadisticas == e.getSource()) {
        	iCtrlVista.mostrarVistaEstadisticas();
        }
        if (botonCerrarSesion == e.getSource()) {
        	iCtrlVista.cerrarSesion();
        	JOptionPane.showMessageDialog(null, "Sesion cerrada correctamente.");
        	dispose();
        }
        if (botonRankingGlobal == e.getSource()) {
        	iCtrlVista.mostrarVistaRankingGlobal();
        }
        if (botonRecords == e.getSource()) {
        	iCtrlVista.mostrarVistaRecords();
        }
        if (botonVerManual == e.getSource()) {
			iCtrlVista.mostrarVistaManual();
		}
    }
	
	public void hacerVisible() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
