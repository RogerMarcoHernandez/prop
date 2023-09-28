package Vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ControladoresPresentacion.ControladorPresentacio;
import Dominio.Pair;

public class VistaRankingGlobal extends JFrame implements ActionListener{

	private ControladorPresentacio iCtrlVista;
	
	private JTable tablaDeDatos;
	
	private JPanel panelTitulo;
	private JPanel panelRanking;
	private JPanel panelBotones;
	
	private JLabel etiquetaRankingGlobal;
		
	private JButton botonVolver;
	
	private DefaultTableModel modeloTabla;
	
	private JScrollPane panelDeslizable;

	public VistaRankingGlobal(ControladorPresentacio pControladorPresentacio) {
		iCtrlVista = pControladorPresentacio;
		iniciarComponentes();
	}
	
	private void iniciarComponentes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelTitulo = new JPanel();
		getContentPane().add(panelTitulo, BorderLayout.NORTH);
		
		etiquetaRankingGlobal = new JLabel("RANKING GLOBAL");
		panelTitulo.add(etiquetaRankingGlobal);
		
		panelRanking = new JPanel();	    
		panelRanking.setPreferredSize(new Dimension(600, 200));
		panelRanking.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panelRanking, BorderLayout.CENTER);
		
		String column[]={"Rank", "Usuario","Puntuacion"}; 
	    String data[][] = null;
	    modeloTabla = new DefaultTableModel(data, column) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
	    
	    tablaDeDatos = new JTable(modeloTabla);	
	    tablaDeDatos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		panelDeslizable = new JScrollPane(tablaDeDatos);
		panelRanking.add(panelDeslizable);
		
		panelBotones = new JPanel();
		getContentPane().add(panelBotones, BorderLayout.SOUTH);
		
		botonVolver = new JButton("VOLVER");
		botonVolver.addActionListener(this);
		panelBotones.add(botonVolver);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(botonVolver==e.getSource()) {
			iCtrlVista.volver();
            dispose();
		}
	}
	public void hacerVisible(Pair<String, Integer>[] data) {		
	    modeloTabla.setDataVector(convertirParejaAMatrizDeString(data), new String[]{"Rank", "Usuario","Puntuacion"});
	    tablaDeDatos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);		
	}

	private String[][] convertirParejaAMatrizDeString(Pair<String, Integer>[] data) {
	    String[][] result = new String[data.length][3];
	    for (int i = 0; i < data.length; i++) {
	    	result[i][0] = Integer.toString(i+1);
	    	if (data[i] != null) {
		        result[i][1] = data[i].obtenPrimero();
		        result[i][2] = Integer.toString(data[i].obtenSegundo());
	        }
	    }
	    return result;
	}
}
