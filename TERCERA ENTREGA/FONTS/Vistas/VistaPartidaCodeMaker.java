package Vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import ControladoresPresentacion.ControladorPresentacio;
import java.awt.GridLayout;

public class VistaPartidaCodeMaker extends JFrame implements ActionListener{

	private List<Integer> codigoSecreto;

	private ControladorPresentacio iCtrlVista;
	
	private JPanel panelJuego, panelCodigoSecreto, panelCorrecciones, panelBotones, panelInfo;
    private JButton botonEnviar, botonPista, botonSalir;
    private JLabel timeLabel, scoreLabel;
    JLabel relleno = new JLabel("");

    private int numeroColores = 5;
    private int longitudCodigo = 5;
    private int numeroTurnos = 10;
    private int nivelAyuda = 2;
    private int turnoActual = 1;
    private Color[] tema;
    private Map<Color, String> colorMap = new HashMap<>();    
    
    public VistaPartidaCodeMaker(ControladorPresentacio controladorPresentacio, int pLongitudCodigo, int pNumeroColores,
    		int pNumeroTurnos,int pNivelAyuda, int pTurnoActual, Color[] pTema) {
	    iCtrlVista = controladorPresentacio;
	    longitudCodigo = pLongitudCodigo;
	    numeroColores = pNumeroColores;    
	    numeroTurnos = pNumeroTurnos;
	    nivelAyuda = pNivelAyuda;
	    turnoActual = pTurnoActual;
	    tema = pTema;
	    inicializaColores();
	    iniciarComponentes();
	}
    
	private void inicializaColores() {
		colorMap.put(Color.black,"negro");
		colorMap.put(Color.blue,"azul");
		colorMap.put(Color.cyan,"cyan");
		colorMap.put(Color.darkGray,"gris oscuro");
        colorMap.put(Color.gray,"gris");
        colorMap.put(Color.green,"verde");
        colorMap.put(Color.lightGray,"gris claro");
        colorMap.put(Color.magenta,"magenta");
        colorMap.put(Color.orange,"naranja");
        colorMap.put(Color.pink,"rosa");
        colorMap.put(Color.red,"rojo");
        colorMap.put(Color.white,"blanco");
        colorMap.put(Color.yellow, "amarillo");		
	}

	private void iniciarComponentes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        panelJuego = new JPanel(new GridLayout(numeroTurnos, longitudCodigo));
        panelJuego.setPreferredSize(new Dimension(longitudCodigo*70, numeroTurnos*70));
        
        panelCodigoSecreto = new JPanel(new GridLayout(1, numeroColores));
        panelCodigoSecreto.setPreferredSize(new Dimension(longitudCodigo*70, 50));
        
        panelCorrecciones = new JPanel(new GridLayout(numeroTurnos*2, longitudCodigo/2));
        
        panelBotones = new JPanel(new GridLayout(6, 1));
        
        panelInfo = new JPanel(new GridLayout(1, 2));

        botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(this);
        
        botonPista = new JButton("AUTO CORREGIR");
        botonPista.addActionListener(this);
        
        botonSalir = new JButton("SALIR");
        botonSalir.addActionListener(this);

        timeLabel = new JLabel("Tiempo: 00:00");
        scoreLabel = new JLabel("Puntuación: 0");
        
        for (int i = 0; i < numeroTurnos-1; i++) {
        	for (int j = 0; j < longitudCodigo; j++) {
        		BotonPelota botonPelota = new BotonPelota(Color.GRAY, i, j);
                panelJuego.add(botonPelota);

                BotonCorreccion correctionButton = new BotonCorreccion(Color.GRAY, i, j);
                panelCorrecciones.add(correctionButton);
        	}
        	if (longitudCodigo%2 != 0) {
        		JButton botonCorreccion = new JButton();
        		botonCorreccion.setBackground(Color.GRAY);
        		panelCorrecciones.add(botonCorreccion);
        	}
            
        }
        for (int i = numeroTurnos-1; i < numeroTurnos; i++) {
        	for (int j = 0; j < longitudCodigo; j++) {
        		BotonPelota botonPelota = new BotonPelota(Color.GRAY, i, j);
                panelJuego.add(botonPelota);
                BotonCorreccion botonCorreccion = new BotonCorreccion(Color.WHITE, i, j);
                botonCorreccion.activa();
                panelCorrecciones.add(botonCorreccion);
        	}
        	if (longitudCodigo%2 != 0) {
        		JButton botonCorreccion = new JButton();
        		botonCorreccion.setBackground(Color.GRAY);
        		panelCorrecciones.add(botonCorreccion);
        	}
        }        

        panelBotones.add(botonEnviar);
        panelBotones.add(botonPista);
        panelBotones.add(botonSalir);
        for (int i = 0; i < numeroTurnos-3;i++) panelBotones.add(relleno);
        
        panelInfo.add(timeLabel);
        panelInfo.add(scoreLabel);

        getContentPane().add(panelJuego, BorderLayout.CENTER);
        getContentPane().add(panelCodigoSecreto, BorderLayout.SOUTH);
        getContentPane().add(panelCorrecciones, BorderLayout.EAST);
        getContentPane().add(panelBotones, BorderLayout.WEST);
        getContentPane().add(panelInfo, BorderLayout.NORTH);
	}
	
	public void hacerVisible(List<Integer> pCodigoSecreto, List<List<Integer>> adivinanzas, List<List<Integer>> resultados) {	
		codigoSecreto = pCodigoSecreto;
		for (int i = 0; i < longitudCodigo; i++) {
			JButton botonCodigoSecreto = new JButton();
			botonCodigoSecreto.setBackground(tema[codigoSecreto.get(i)]);   
            panelCodigoSecreto.add(botonCodigoSecreto);
        }
		
			List<Integer> fila = adivinanzas.get(turnoActual-1);
			for (Component c : panelJuego.getComponents()) {
				if (c instanceof BotonPelota) {
					BotonPelota ball = (BotonPelota) c;
					if (ball.getcordX() == numeroTurnos-turnoActual) {
						ball.setColor( tema[fila.get(ball.getcordY())] );  
					}
				}
			}
			for (Component c : panelCorrecciones.getComponents()) {
				if (c instanceof BotonCorreccion) {
					BotonCorreccion ball = (BotonCorreccion) c;
					if (ball.getcordX() == numeroTurnos-turnoActual) ball.activa();  
				}
			}
		
		scoreLabel.setText("Puntuación: " + String.valueOf(iCtrlVista.obtenPuntuacion()));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);		
	}

	public void actualizaPelota(BotonPelota pelota) {}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean correct = false;
		if(botonEnviar==e.getSource()) {
			try {
				iCtrlVista.enviarCorreccion(obtenCorreccion());
				correct = true;
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "correccion incorrecta");				
			}
			int resultado = -1;
			if (correct) {
				
				try {
					resultado = iCtrlVista.procesar();
					iCtrlVista.procesar();
					if (resultado == 0) ejecutarTurno();
					else if(resultado == 1) {
						JOptionPane.showMessageDialog(null, "GANAS");	
						iCtrlVista.volver();
						dispose();
					}
					else if(resultado == -1) {
						JOptionPane.showMessageDialog(null, "PIERDES");	
						iCtrlVista.volver();
						dispose();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				scoreLabel.setText("Puntuación: " + String.valueOf(iCtrlVista.obtenPuntuacion()));
			}
		}						  
		
		if(botonPista==e.getSource()) {
			if(nivelAyuda > 0) {
				mostrarPista();		
				nivelAyuda--;
			}
			else JOptionPane.showMessageDialog(null, "NO QUEDAN PISTAS");	
		}
		if(botonSalir==e.getSource()) {
			iCtrlVista.mostrarVistaGuardarPartida();
			dispose();
		}
	}

	
	private void escribeRespuesta(List<Integer> respuesta) {
		for (Component c : panelJuego.getComponents()) {
			if (c instanceof BotonPelota) {
				BotonPelota ball = (BotonPelota) c;
				if (ball.getcordX() == numeroTurnos-turnoActual) {
					ball.setColor( tema[respuesta.get(ball.getcordY())] );  
				}
			}
		}
		
	}

	private List<Integer> obtenCorreccion() {
		List<Integer> correccion = new ArrayList<>();
		for (Component c : panelCorrecciones.getComponents()) {
			if (c instanceof BotonCorreccion) {
				BotonCorreccion ball = (BotonCorreccion) c;
				if (ball.getcordX() == numeroTurnos-turnoActual) {
					if (ball.getColor() == Color.WHITE) correccion.add(0);
					else if (ball.getColor() == Color.LIGHT_GRAY) correccion.add(1);
					else correccion.add(2);
				}
			}
		}
		return correccion;
	}

	private void mostrarPista() {		
		List<Integer> pista = iCtrlVista.obtenPista();
		for (Component c : panelCorrecciones.getComponents()) {
			if (c instanceof BotonCorreccion) {
				BotonCorreccion ball = (BotonCorreccion) c;
				if (ball.getcordX() == numeroTurnos-turnoActual) {
					if(pista.get(ball.getcordY()) == 0) ball.setColor(Color.WHITE); 
					else if(pista.get(ball.getcordY()) == 1) ball.setColor(Color.LIGHT_GRAY); 
					else ball.setColor(Color.BLACK); 
				}
			}
		}		
	}

	private void pintaResultados(List<List<Integer>> tableroResult) {
		List<Integer> fila = tableroResult.get(turnoActual-1);
		int parcial = 0;
		int complet = 0;
		for (Integer i : fila) {
			if (i == 1) parcial++;
			else if (i ==2) complet++;
		}
		for (Component c : panelCorrecciones.getComponents()) {
			if (c instanceof BotonCorreccion) {
				BotonCorreccion ball = (BotonCorreccion) c;
				if (ball.getcordX() == numeroTurnos-turnoActual) {
					if (complet > 0 ) {
						ball.setColor(Color.BLACK);
						complet--;						
					}
					else if (parcial > 0 ) {
						ball.setColor(Color.LIGHT_GRAY);
						parcial--;						
					}
				}
			}
		}
		
	}	
	
	private void ejecutarTurno () {
		List<Integer> respuesta = iCtrlVista.obtenRespuestaMaquina(turnoActual);
		turnoActual++;
		for (Component c : panelCorrecciones.getComponents()) {
			if (c instanceof BotonCorreccion) {
				BotonCorreccion ball = (BotonCorreccion) c;
				if (ball.getcordX() == numeroTurnos-turnoActual) ball.activa();  
			}
		}
		escribeRespuesta(respuesta);
	}

	public void cargar(List<List<Integer>> obtenTableroAdivinanzas, List<List<Integer>> obtenTableroResultados) {
		int filasHechas= obtenTableroAdivinanzas.size();
		for (int i = 0; i < filasHechas; i++) {
			List<Integer> filaAdivinanza = obtenTableroAdivinanzas.get(i);
			if(filaAdivinanza.size() != 0) {
				for (Component c : panelJuego.getComponents()) {
					if (c instanceof BotonPelota) {
						BotonPelota ball = (BotonPelota) c;
						if (ball.getcordX() == numeroTurnos-i-1) {
							ball.setColor(tema[filaAdivinanza.get(ball.getcordY())]); 
						}
					}
				}
				
				int saveTurno = turnoActual;
				for (int j = 1; j < saveTurno; j++) {
					turnoActual = j;
					for (Component c : panelCorrecciones.getComponents()) {
						if (c instanceof BotonCorreccion) {
							BotonCorreccion ball = (BotonCorreccion) c;
							if (ball.getcordX() == numeroTurnos-turnoActual) ball.activa();  
						}
					}
					pintaResultados(obtenTableroResultados);
				}
				turnoActual = saveTurno;
			}
		
	}	
	
	}
}
