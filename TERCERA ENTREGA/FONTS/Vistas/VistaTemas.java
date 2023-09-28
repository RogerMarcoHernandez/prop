package Vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ControladoresPresentacion.ControladorVistaSesion;

public class VistaTemas extends JFrame implements ActionListener{

	private ControladorVistaSesion iCtrlVista;
	
	private JPanel contentPane;
	private JPanel panelTitulo;
	private JPanel panelAtras;
	private JPanel panelBotones;
	
	private JLabel tema1, tema2, tema3;
	private JButton seleccionarTema1, seleccionarTema2, seleccionarTema3, atras;
	
	private JLabel titulo;
	
    private List<Color[]> temas;
    private int numeroColores = 5;
    private int temaSeleccionado = 0;
    
	public VistaTemas(List<Color[]> ptemas, ControladorVistaSesion piCtrlVista) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iCtrlVista = piCtrlVista;
		temas = ptemas;
		iniciarComponentes();
	}
	private void iniciarComponentes() {
		panelBotones = new JPanel(new GridLayout(3, 1, 0, 10));
		panelBotones.setBorder(new EmptyBorder(50, 50, 50, 50));
		
		tema1 = new JLabel("Tema 1");
		tema1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelBotones.add(tema1);
		
		for (int i = 0; i < numeroColores; i++) {
			JButton color = new JButton();
			color.setBackground(temas.get(0)[i]);
            panelBotones.add(color);
        }
		
		seleccionarTema1 = new JButton("Seleccionar Tema");
		seleccionarTema1.addActionListener(this);
		seleccionarTema1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(seleccionarTema1);
		
		tema2 = new JLabel("Tema 2");
		tema2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelBotones.add(tema2);

		for (int i = 0; i < numeroColores; i++) {
			JButton color = new JButton();
			color.setBackground(temas.get(1)[i]);
            panelBotones.add(color);
        }
		
		seleccionarTema2 = new JButton("Seleccionar Tema");
		seleccionarTema2.addActionListener(this);
		seleccionarTema2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(seleccionarTema2);
		
		tema3 = new JLabel("Tema 3");
		tema3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelBotones.add(tema3);
		
		for (int i = 0; i < numeroColores; i++) {
			JButton color = new JButton();
			color.setBackground(temas.get(2)[i]);
            panelBotones.add(color);
        }
		
		seleccionarTema3 = new JButton("Seleccionar Tema");
		seleccionarTema3.addActionListener(this);
		seleccionarTema3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelBotones.add(seleccionarTema3);

		panelAtras = new JPanel();
		atras = new JButton("Atras");
		atras.addActionListener(this);
		atras.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelAtras.add(atras);
		
		panelTitulo = new JPanel();
		panelTitulo.setBorder(new EmptyBorder(10, 0, 0, 0));
		titulo = new JLabel("CONFIGURACION");
		titulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelTitulo.add(titulo);
		
		contentPane = new JPanel(new BorderLayout());
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.add(panelBotones, BorderLayout.CENTER);
		contentPane.add(panelAtras, BorderLayout.SOUTH);
		
		setContentPane(contentPane);
		setSize(500, 320);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(atras==e.getSource()) {				
			if(temaSeleccionado != 0) {
				iCtrlVista.actualizaTema(temaSeleccionado);
			}
			dispose();			
		}
		if(seleccionarTema1 == e.getSource()) {
			temaSeleccionado = 1;
			iCtrlVista.actualizaTema(temaSeleccionado);
			dispose();	
		}
		if(seleccionarTema2 == e.getSource()) {
			temaSeleccionado = 2;
			iCtrlVista.actualizaTema(temaSeleccionado);
			dispose();
		}
		if(seleccionarTema3 == e.getSource()) {
			temaSeleccionado = 3;
			iCtrlVista.actualizaTema(temaSeleccionado);
			dispose();
		}
	}
	public void hacerVisible() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
