package Vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class BotonPelota extends JButton implements ActionListener {
	private int x;
	private int y;
	private Color color;


    public BotonPelota(Color c, int x, int y) { // 
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
        setBackground(c);

    }
    public int getcordX() {
        return this.x;
    }
    public int getcordY() {
        return this.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ancestor = SwingUtilities.getWindowAncestor(this);
        if (ancestor instanceof VistaPartidaCodeBreaker) {
            VistaPartidaCodeBreaker vista = (VistaPartidaCodeBreaker) ancestor;
            vista.actualizaPelota(this);
        } else if (ancestor instanceof VistaPartidaCodeMaker) {
            VistaPartidaCodeMaker vista = (VistaPartidaCodeMaker) ancestor;
            vista.actualizaPelota(this);
        }
        else if (ancestor instanceof VistaIntroducirCodigo) {
        	VistaIntroducirCodigo vista = (VistaIntroducirCodigo) ancestor;
            vista.pintarPelotaConColorSeleccionado(this);
        } 
    
    }

	public Color getColor() {
		return color;
	}
}