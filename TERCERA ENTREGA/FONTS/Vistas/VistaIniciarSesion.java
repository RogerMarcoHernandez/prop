package Vistas;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ControladoresPresentacion.ControladorVistaPantallaPrincipal;


public class VistaIniciarSesion extends JFrame implements ActionListener {
	
	private ControladorVistaPantallaPrincipal iCtrlVista;	
	
	private JPanel panelDatos;
	private JPanel panelBotones;
	private JPanel panelContenido;
	
	private JTextField campoDeTextoNombreUsuario;
    private JPasswordField campoDeTextoContrasena;
    private JButton botonEnviar;
    private JButton botonVolver;
    
    private JLabel etiquetaNombreUsuario;
    private JLabel etiquetaContrasena;
    
    public VistaIniciarSesion(ControladorVistaPantallaPrincipal pCtrlVistapantallaPrincipal) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iCtrlVista = pCtrlVistapantallaPrincipal;
		iniciarComponentes();
	}
    
    public void iniciarComponentes() {
    	
        panelDatos = new JPanel();
        panelDatos.setLayout(new GridLayout(4, 2));

        etiquetaNombreUsuario = new JLabel("Usuario:");
        campoDeTextoNombreUsuario = new JTextField();
        
        etiquetaContrasena = new JLabel("Contraseña:");
        campoDeTextoContrasena = new JPasswordField();

        
        panelDatos.add(etiquetaNombreUsuario);
        panelDatos.add(campoDeTextoNombreUsuario);
        panelDatos.add(etiquetaContrasena);
        panelDatos.add(campoDeTextoContrasena);

        botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(this);
        
        botonVolver = new JButton("Volver");
        botonVolver.addActionListener(this);

        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 2));
        panelBotones.add(botonEnviar);
        panelBotones.add(botonVolver);
        
        
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.add(panelDatos, BorderLayout.CENTER);
        panelContenido.add(panelBotones, BorderLayout.SOUTH);
        
        panelContenido.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        setContentPane(panelContenido);
        
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonEnviar) {
            String usuario = campoDeTextoNombreUsuario.getText();
            String contrasena = new String(campoDeTextoContrasena.getPassword());
            int resultadoRegistro = iCtrlVista.iniciarSesionUsuario(usuario, contrasena);
            if (resultadoRegistro == 0) {
		    	dispose();
		    	JOptionPane.showMessageDialog(null, "Bienvenido " + usuario + "!");
		    	
		    }
		    else JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");            
            
        } else if (e.getSource() == botonVolver) {
            iCtrlVista.iniciar();
            dispose();
        }
    }

    public void hacerVisible() {
		campoDeTextoNombreUsuario.setText("");
        campoDeTextoContrasena.setText("");
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}   
    
    
}
