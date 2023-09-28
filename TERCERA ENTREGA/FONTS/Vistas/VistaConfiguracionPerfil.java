package Vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ControladoresPresentacion.ControladorVistaSesion;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaConfiguracionPerfil extends JFrame implements ActionListener{
	
	private ControladorVistaSesion iCtrlVista;

	private JPanel panelTema;
	private JPanel panelAtras;
	private JPanel panelTitulo;
	private JPanel panelDeContenido;
	
	private JLabel etiquetaTema;
	private JLabel etiquetaTitulo;
	private JButton botonCambiarTema;
	private JButton botonAtras;
	
    private Color[] temaSeleccionado;
    private int numeroColores = 5;
	
	public VistaConfiguracionPerfil(ControladorVistaSesion pCtrlVistaSesion, Color[] ptema) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iCtrlVista = pCtrlVistaSesion;	
		temaSeleccionado = ptema;
		iniciarComponentes();
	}
	private void iniciarComponentes() {
		panelTema = new JPanel();
		panelTema.setBorder(new EmptyBorder(50, 50, 50, 50));
		
		etiquetaTema = new JLabel("Tema");
		etiquetaTema.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelTema.add(etiquetaTema);
		
		for (int i = 0; i < numeroColores; i++) {
			JButton color = new JButton();
			color.setBackground(temaSeleccionado[i]);
            panelTema.add(color);
        }
		
		botonCambiarTema = new JButton("Cambiar");
		botonCambiarTema.addActionListener(this);
		botonCambiarTema.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelTema.add(botonCambiarTema);

		
		panelAtras = new JPanel();
		botonAtras = new JButton("Atras");
		botonAtras.addActionListener(this);
		botonAtras.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelAtras.add(botonAtras);
		
		panelTitulo = new JPanel();
		panelTitulo.setBorder(new EmptyBorder(10, 0, 0, 0));
		etiquetaTitulo = new JLabel("CONFIGURACION");
		etiquetaTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelTitulo.add(etiquetaTitulo);
		
		panelDeContenido = new JPanel(new BorderLayout());
		panelDeContenido.add(panelTitulo, BorderLayout.NORTH);
		panelDeContenido.add(panelTema, BorderLayout.CENTER);
		panelDeContenido.add(panelAtras, BorderLayout.EAST);
		
		setContentPane(panelDeContenido);
		setSize(500, 320);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (botonCambiarTema == e.getSource()) {
			iCtrlVista.mostrarVistaTemas();			
		}
		if (botonAtras == e.getSource()) {
			iCtrlVista.iniciar();
            dispose();
		}
	}
	
	public void hacerVisible() {
		pack();
		setLocationRelativeTo(null);
		setTitle("MASTERMIND");
		setVisible(true);		
	}
	
	public void actualizar(Color[] pTema) {
		temaSeleccionado = pTema;
		int x = 0;
		for (Component c : panelTema.getComponents()) {
			if (c instanceof JButton) {
				if( x < 5) c.setBackground(temaSeleccionado[x]);
				x++;
			}
		}
	}
	
}
