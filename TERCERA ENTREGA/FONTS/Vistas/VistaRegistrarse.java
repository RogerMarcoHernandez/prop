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


public class VistaRegistrarse extends JFrame implements ActionListener {
	private ControladorVistaPantallaPrincipal iCtrlVista;
	
	private JPanel panelDatos;
	private JPanel panelBotones;
	private JPanel panelContenido;
	
	private JTextField campoDeTextoNombreUsuario;
    private JPasswordField campoDeTextoContrasena;
    private JPasswordField campoDeTextoConfirmarContrasena;
    
    private JButton botonEnviar;
    private JButton botonVolver;
    
    private JLabel etiquetaNombreUsuario;
    private JLabel etiquetaContrasena;
    private JLabel etiquetaConfirmarContrasena;
    

    public VistaRegistrarse(ControladorVistaPantallaPrincipal pCtrlVistapantallaPrincipal) {
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
        
        etiquetaConfirmarContrasena = new JLabel("Confirmar Contraseña:");
        campoDeTextoConfirmarContrasena = new JPasswordField();
        
        panelDatos.add(etiquetaNombreUsuario);
        panelDatos.add(campoDeTextoNombreUsuario);
        panelDatos.add(etiquetaContrasena);
        panelDatos.add(campoDeTextoContrasena);
        panelDatos.add(etiquetaConfirmarContrasena);
        panelDatos.add(campoDeTextoConfirmarContrasena);

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
        
        panelContenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        setContentPane(panelContenido);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonEnviar) {
            String usuario = campoDeTextoNombreUsuario.getText();
            String contrasena = new String(campoDeTextoContrasena.getPassword());
            String confirmarContrasena = new String(campoDeTextoConfirmarContrasena.getPassword());
                        			
            if (contrasena.equals(confirmarContrasena)) {
            	if (usuario.length() <= 15 && contrasena.length() <= 15) {
            		boolean mayuscula = false;
        			boolean numero = false;
        			boolean simbolo = false;				
        			for (int i = 0; i < contrasena.length() && (!mayuscula || !numero || !simbolo); i++) {
        	            char caracter = contrasena.charAt(i);
        	            if (Character.isUpperCase(caracter)) {
        	            	mayuscula = true;
        	            } else if (Character.isDigit(caracter)) {
        	            	numero = true;
        	            } else if (!Character.isLetterOrDigit(caracter)) {
        	            	simbolo = true;
        	            }	            
        	        }
        			if (mayuscula && numero && simbolo) {
        				int resultRegistro = iCtrlVista.registrarUsuario(usuario, contrasena);
        			    if (resultRegistro == 0) {
        			    	dispose();
        			    	JOptionPane.showMessageDialog(null, "Bienvenido " + usuario + "!");
        			    	
        			    }
        			    else JOptionPane.showMessageDialog(null, "El usuario ya existe!");
                    }
                    else JOptionPane.showMessageDialog(null, "La contrasena no contiene un numero, una mayuscula y un simbolo");        		
            		
            	}
            	else JOptionPane.showMessageDialog(null, "El nombre de usuario y la contrasena deben tener maximo 15 caracteres");
            } else {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden. Por favor, inténtelo de nuevo.");
            }
        } else if (e.getSource() == botonVolver) {
            iCtrlVista.iniciar();
            dispose();
        }
    }

    public void hacerVisible() {
		campoDeTextoNombreUsuario.setText("");
        campoDeTextoContrasena.setText("");
        campoDeTextoConfirmarContrasena.setText("");
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}  
    
    
    
}
