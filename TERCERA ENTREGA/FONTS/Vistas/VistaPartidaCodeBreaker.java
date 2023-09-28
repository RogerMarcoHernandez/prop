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

public class VistaPartidaCodeBreaker extends JFrame implements ActionListener{

	private ControladorPresentacio iCtrlVista;
	
	private JPanel panelJuego, panelColoresMuestra, panelCorrecciones, panelBotones, panelInfo;
    private JButton botonEnviar, botonPista, botonSalir;
    private JLabel timeLabel, scoreLabel;
    JLabel relleno = new JLabel("");

    private Color colorSeleccionado = Color.WHITE; // color de bola seleccionado
    private int numeroColores = 5;
    private int longitudCodigo = 5;
    private int numeroTurnos = 10;
    private int nivelAyuda = 2;
    private int turnoActual = 1;
    private Color[] tema;
    private Map<Color, String> colorMap = new HashMap<>();    
    
    public VistaPartidaCodeBreaker(ControladorPresentacio controladorPresentacio, int pLongitudCodigo, int pNumeroColores,
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
        
        panelColoresMuestra = new JPanel(new GridLayout(1, numeroColores));
        panelColoresMuestra.setPreferredSize(new Dimension(longitudCodigo*70, 50));
        
        panelCorrecciones = new JPanel(new GridLayout(numeroTurnos*2, longitudCodigo/2));
        
        panelBotones = new JPanel(new GridLayout(6, 1));
        
        panelInfo = new JPanel(new GridLayout(1, 2));

        botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(this);
        
        botonPista = new JButton("PISTA");
        botonPista.addActionListener(this);
        
        botonSalir = new JButton("SALIR");
        botonSalir.addActionListener(this);

        timeLabel = new JLabel("Tiempo: 00:00");
        scoreLabel = new JLabel("Puntuación: 0");
        scoreLabel.setText("Puntuación: " + String.valueOf(iCtrlVista.obtenPuntuacion()));

        for (int i = 0; i < numeroTurnos-1; i++) {
        	for (int j = 0; j < longitudCodigo; j++) {
        		BotonPelota botonPelota = new BotonPelota(Color.GRAY, i, j);
                panelJuego.add(botonPelota);

                BotonPelota correctionButton = new BotonPelota(Color.GRAY, i, j);
                panelCorrecciones.add(correctionButton);
        	}
        	if (longitudCodigo%2 != 0) {
        		BotonPelota botonCorreccion = new BotonPelota(Color.GRAY, -1, -1);
        		panelCorrecciones.add(botonCorreccion);
        	}
            
        }
        for (int i = numeroTurnos-1; i < numeroTurnos; i++) {
        	for (int j = 0; j < longitudCodigo; j++) {
        		BotonPelota botonPelota = new BotonPelota(Color.WHITE, i, j);
                panelJuego.add(botonPelota);

                BotonPelota botonCorreccion = new BotonPelota(Color.WHITE, i, j);
                panelCorrecciones.add(botonCorreccion);
        	}
        	if (longitudCodigo%2 != 0) {
        		BotonPelota botonCorreccion = new BotonPelota(Color.GRAY, -1, -1);
        		panelCorrecciones.add(botonCorreccion);
        	}
        }
        
        for (int i = 0; i < numeroColores; i++) {
        	BotonMuestra botonMuestra = new BotonMuestra(tema[i]);            
            panelColoresMuestra.add(botonMuestra);
        }

        panelBotones.add(botonEnviar);
        panelBotones.add(botonPista);
        panelBotones.add(botonSalir);
        for (int i = 0; i < numeroTurnos-3;i++) panelBotones.add(relleno);
        
        panelInfo.add(timeLabel);
        panelInfo.add(scoreLabel);

        getContentPane().add(panelJuego, BorderLayout.CENTER);
        getContentPane().add(panelColoresMuestra, BorderLayout.SOUTH);
        getContentPane().add(panelCorrecciones, BorderLayout.EAST);
        getContentPane().add(panelBotones, BorderLayout.WEST);
        getContentPane().add(panelInfo, BorderLayout.NORTH);
	}
	
	public void hacerVisible() {	    
		pack();
		setLocationRelativeTo(null);
		setVisible(true);		
	}
	
	public void actualizaPelota(BotonPelota pelota) {		
	for (Component c : panelJuego.getComponents())
		if (c == pelota && pelota.getcordX() == numeroTurnos-turnoActual)  pelota.setColor(colorSeleccionado);        
	    
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(botonEnviar==e.getSource()) {		
			boolean filaLlena = llena();			
			if(filaLlena) ejecutarTurno(); 			  
		}
		if(botonPista==e.getSource()) {
			mostrarPista();			
		}
		if(botonSalir==e.getSource()) {
			iCtrlVista.mostrarVistaGuardarPartida();
			dispose();	
		}
	}

	private void mostrarPista() {
		if (nivelAyuda > 0) {			
			nivelAyuda--;
			List<Integer> listaPista = null;			
			listaPista = iCtrlVista.obtenPista();
			int pos = -1, num = 0;
			pos=listaPista.get(0);
			num=listaPista.get(1);
			if (pos != -1) JOptionPane.showMessageDialog(null, "En el codigo correcto hay " + colorMap.get(tema[num]) + " en la posicion " + (pos+1));			  
		
		}
		else JOptionPane.showMessageDialog(null, "NO QUEDAN PISTAS");
	}		
	

	private void desbloqueaFila() {
		for (Component c : panelJuego.getComponents()) {
			if (c instanceof BotonPelota) {
				BotonPelota ball = (BotonPelota) c;
				if (ball.getcordX() == numeroTurnos-turnoActual) ball.setColor(Color.WHITE);  
			}
		}	
		for (Component c : panelCorrecciones.getComponents()) {
			if (c instanceof BotonPelota) {
				BotonPelota ball = (BotonPelota) c;
				if (ball.getcordX() == numeroTurnos-turnoActual) ball.setColor(Color.WHITE);  
			}
		}
	}

	private boolean llena() {
		boolean result = true;
		for (Component c : panelJuego.getComponents()) {
			if (c instanceof BotonPelota) {
				BotonPelota ball = (BotonPelota) c;
				if (ball.getcordX() == numeroTurnos-turnoActual && ball.getColor() == Color.WHITE) result = false;  
			}
		}
		return result;
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
			if (c instanceof BotonPelota) {
				BotonPelota ball = (BotonPelota) c;
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
	
	private List<Integer> getCodigo() {
		List<Integer> codigo = new ArrayList<Integer>();;
		for (Component c : panelJuego.getComponents()) {
			if (c instanceof BotonPelota) {
				BotonPelota ball = (BotonPelota) c;
				if (ball.getcordX() == numeroTurnos-turnoActual) {
					Color col = ball.getColor();
					for (int i = 0; i < numeroColores; i++) 
						if(col == tema[i]) codigo.add(i);
				}
			}
		}
		return codigo;
	}
	
	
	private void ejecutarTurno () {
		int resultadoTurno = iCtrlVista.ejecutarTurno(getCodigo());
		pintaResultados(iCtrlVista.obtenTableroResultados());
		turnoActual++;
		scoreLabel.setText("Puntuación: " + String.valueOf(iCtrlVista.obtenPuntuacion()));
		if (resultadoTurno == 0) desbloqueaFila();
		else if (resultadoTurno == 1) {
			JOptionPane.showMessageDialog(null, "GANASTE!");
			iCtrlVista.volver();
			dispose();
		}
		else {
			JOptionPane.showMessageDialog(null, "PERDISTE!");
			iCtrlVista.volver();
			dispose();
		}
	}

	public void cargar(List<List<Integer>> obtenTableroAdivinanzas, List<List<Integer>> obtenTableroResultados) {
		desbloqueaFila();
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
						if (c instanceof BotonPelota) {
							BotonPelota ball = (BotonPelota) c;
							if (ball.getcordX() == numeroTurnos-turnoActual) ball.setColor(Color.WHITE);  
						}
					}
					pintaResultados(obtenTableroResultados);
				}
				turnoActual = saveTurno;
			}
		
	}	
	
	}

	public void actualizaColorSeleccionado(Color color) {
		colorSeleccionado = color;		
	}
}
