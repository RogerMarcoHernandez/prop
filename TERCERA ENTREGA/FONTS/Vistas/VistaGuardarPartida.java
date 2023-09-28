package Vistas;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import ControladoresPresentacion.ControladorPresentacio;


public class VistaGuardarPartida extends JFrame implements ActionListener {
	private ControladorPresentacio iCtrlVista;
	
	private JPanel panelBotones;
	private JPanel panelDeContenido;
	private JLabel preguntaText;
	private JButton botonSi;
	private JButton botonNo;
    

    public VistaGuardarPartida(ControladorPresentacio pCtrlPresentacion) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iCtrlVista = pCtrlPresentacion;
		iniciarComponentes();
	}
    
    public void iniciarComponentes() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	panelDeContenido = new JPanel();
        panelDeContenido.setLayout(new GridLayout(2, 1));

        preguntaText = new JLabel("¿Desea guardar la partida?");
        
        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 2));

        botonSi = new JButton("SI");
        botonSi.addActionListener(this);
        botonNo = new JButton("NO");
        botonNo.addActionListener(this);
        panelBotones.add(botonSi);
        panelBotones.add(botonNo);
        
        panelDeContenido.add(preguntaText);
        panelDeContenido.add(panelBotones);
        
        panelDeContenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        setContentPane(panelDeContenido);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonSi) {
			iCtrlVista.guardarPartidas();
			iCtrlVista.volver();
        	dispose();
        } 
        else if (e.getSource() == botonNo) {
        	iCtrlVista.eliminarPartida();
			iCtrlVista.guardarPartidas();
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
