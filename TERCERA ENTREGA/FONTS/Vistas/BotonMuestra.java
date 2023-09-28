package Vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class BotonMuestra extends JButton implements ActionListener {
	private Color color;

    public BotonMuestra(Color c) { // 
        super();
        color = c;
        setBackground(color);
        setPreferredSize(new Dimension(20, 20));
        addActionListener(this); // Agregar esta línea para registrarse como un ActionListener
    }

    public void setColor(Color c) {
        color = c;
        setBackground(color);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object ancestor = SwingUtilities.getWindowAncestor(this);
        if (ancestor instanceof VistaPartidaCodeBreaker) {
            VistaPartidaCodeBreaker vista = (VistaPartidaCodeBreaker) ancestor;
            vista.actualizaColorSeleccionado(color);
        } else if (ancestor instanceof VistaIntroducirCodigo) {
        	VistaIntroducirCodigo vista = (VistaIntroducirCodigo) ancestor;
            vista.actualizaColorSeleccionado(color);
        } 
    
    }
}