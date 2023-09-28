package Vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ControladoresPresentacion.ControladorPresentacio;

public class VistaIntroducirCodigo extends JFrame implements ActionListener{

	private ControladorPresentacio iCtrlVista;
	
	private JPanel contentPane;
	private JPanel panelAtras;
	private JPanel panelTitulo;
	private JPanel panelCodigoPropuesto = new JPanel();	
	private JPanel panelColoresDisponibles = new JPanel();	
	
	private Color colorSeleccionado = Color.WHITE;
	private JButton botonEnviar;
	private JButton atras;	
	
	private JLabel titulo;
	
    private Color[] tema;
    private int numeroColores = 5;
    private int longitudCodigo = 5;
    
	public VistaIntroducirCodigo(ControladorPresentacio ctrlPres, Color[] pTema, int pNumeroColores, int pLongitudCodigo) {
		iCtrlVista = ctrlPres;
		tema = pTema;
		numeroColores = pNumeroColores;
		longitudCodigo = pLongitudCodigo;
		iniciarComponentes();
	}
	
	private void iniciarComponentes() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for (int i = 0; i < longitudCodigo; i++) {
			BotonPelota colorCodigo = new BotonPelota(Color.WHITE, -1, i);
            panelCodigoPropuesto.add(colorCodigo);
        }

		for (int i = 0; i < numeroColores; i++) {
			BotonMuestra color = new BotonMuestra(tema[i]);
            panelColoresDisponibles.add(color);
        }
		
		botonEnviar = new JButton("Enviar");
		botonEnviar.addActionListener(this);
		botonEnviar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelCodigoPropuesto.add(botonEnviar);

		panelAtras = new JPanel();
		atras = new JButton("Atras");
		atras.addActionListener(this);
		atras.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelAtras.add(atras);
		
		panelTitulo = new JPanel();
		panelTitulo.setBorder(new EmptyBorder(10, 0, 0, 0));
		titulo = new JLabel("INTRODUCE EL CODIGO A ROMPER");
		titulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelTitulo.add(titulo);
		
		contentPane = new JPanel(new BorderLayout());
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.add(panelCodigoPropuesto, BorderLayout.CENTER);
		contentPane.add(panelAtras, BorderLayout.EAST);
		contentPane.add(panelColoresDisponibles, BorderLayout.SOUTH);
		
		setContentPane(contentPane);
		setSize(500, 320);
}

	public void actionPerformed(ActionEvent e) {
		if(atras==e.getSource()) {
			iCtrlVista.mostrarVistaTipoPartida();
			dispose();			
		}
		if(botonEnviar == e.getSource()) {
			iCtrlVista.informaCodigo(obtenCodigo());
			iCtrlVista.muestraPartida();
			dispose();
		}
	}
	
	private List<Integer> obtenCodigo() {
		List<Integer> codigo = new ArrayList<Integer>();
		for (Component c : panelCodigoPropuesto.getComponents()) {
			if (c instanceof BotonPelota) {
				BotonPelota ball = (BotonPelota) c;
				codigo.add(convertirColorANumero(ball.getColor()));
			}	
		}
		return codigo;
	}
	
	private Integer convertirColorANumero(Color color) {
		for (int i = 0; i < numeroColores; i++) {
			if (color == tema[i]) return i;
		}
		return -1;
	}
	
	public void pintarPelotaConColorSeleccionado(BotonPelota pelota) {		
		for (Component c : panelCodigoPropuesto.getComponents())
			if (c == pelota)  pelota.setColor(colorSeleccionado);        
		    
	}
	
	public void hacerVisible() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void actualizaColorSeleccionado(Color color) {
		colorSeleccionado = color;		
	}

}
