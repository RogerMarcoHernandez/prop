package Vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BotonCorreccion extends JButton implements ActionListener {
	private int x;
	private int y;
	private Color color;
	private int estado=-1;

    public BotonCorreccion(Color c, int x, int y) { // 
        super();
        color = c;
        setBackground(color);
        setPreferredSize(new Dimension(20, 20));
        addActionListener(this); // Agregar esta línea para registrarse como un ActionListener
        this.x = x;
        this.y = y;
    }

    public void setColor(Color c) {
        color = c;
        setBackground(color);
    }
    public int getcordX() {
        return this.x;
    }
    public int getcordY() {
        return this.y;
    }
    public Color getColor() {
        return color;
    }
    
    public void activa() {
    	estado = 0;
    	setColor(Color.WHITE);
    }
    
    public void estadoSiguiente() {
        if (estado==0) {
        	estado++;
        	setColor(Color.LIGHT_GRAY);
        }
        else if (estado==1) {
        	estado++;
        	setColor(Color.BLACK);
        }
        else if (estado==2) {
        	estado = 0;
        	setColor(Color.WHITE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        estadoSiguiente();
    }
}