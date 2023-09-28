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

import ControladoresPresentacion.ControladorVistaSesion;


public class VistaSeleccionarTipoPartida extends JFrame implements ActionListener {
	
	private ControladorVistaSesion iCtrlVista;
	
	private JPanel panelDeContenido;
	private JPanel panelBotones;
	private JPanel panelTitulo;
	
	private JButton codeMaker;
	private JButton codeBreaker;
	private JButton volver;
	
	private JLabel etiquetaTitulo;

	public VistaSeleccionarTipoPartida(ControladorVistaSesion pCtrlVistaSesion) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iCtrlVista = pCtrlVistaSesion;
		iniciarComponentes();
	}

	private void iniciarComponentes() {
		panelBotones = new JPanel(new GridLayout(3, 1, 0, 10));
		panelBotones.setBorder(new EmptyBorder(50, 50, 50, 50));
		
		codeMaker = new JButton("CODE-MAKER");
		codeMaker.addActionListener(this);
		codeMaker.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(codeMaker);
		
		codeBreaker = new JButton("CODE-BREAKER");
		codeBreaker.addActionListener(this);
		codeBreaker.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(codeBreaker);
		
		volver = new JButton("Volver Atras");
		volver.addActionListener(this);
		volver.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(volver);
		
		panelTitulo = new JPanel();
		panelTitulo.setBorder(new EmptyBorder(10, 0, 0, 0));
		etiquetaTitulo = new JLabel("SELECCIONA EL TIPO DE PARTIDA");
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
		if (codeMaker == e.getSource()) {
			iCtrlVista.mostrarConfigurarPartida(true);
			dispose();
		}
		if (codeBreaker == e.getSource()) {
			iCtrlVista.mostrarConfigurarPartida(false);
			dispose();
		}
		if (volver == e.getSource()) {
			iCtrlVista.mostrarVistaJugar();
			dispose();
		}
	}

	public void hacerVisible() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
