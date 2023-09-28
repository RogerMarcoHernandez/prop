package Vistas;

import java.awt.BorderLayout;
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

public class VistaRecords extends JFrame implements ActionListener{

	private ControladorPresentacio iCtrlVista;
	
	private JTable tablaDatos;
	
	private JPanel panelBotones;
	private JPanel panelTitulo;
	private JPanel panelRecords;
	
	private JLabel etiquetaRecords;
	
	private JButton botonVolver;
	
	private DefaultTableModel modeloTabla;
	
	private JScrollPane panelDeslizante;

	public VistaRecords(ControladorPresentacio pControladorPresentacio) {
		iCtrlVista = pControladorPresentacio;
		iniciarComponentes();
	}
	
	private void iniciarComponentes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelTitulo = new JPanel();
		getContentPane().add(panelTitulo, BorderLayout.NORTH);
		
		etiquetaRecords = new JLabel("RECORDS");
		panelTitulo.add(etiquetaRecords);
		
		panelRecords = new JPanel();	    		
		panelRecords.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(panelRecords, BorderLayout.CENTER);
		
		String column[]={"Longitud","Colores", "Usuario","Movimientos"}; 
	    String data[][] = null;
	    modeloTabla = new DefaultTableModel(data, column) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
	    tablaDatos = new JTable(modeloTabla);	    
		panelDeslizante = new JScrollPane(tablaDatos);
		panelRecords.add(panelDeslizante);
		tablaDatos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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
	
	public void hacerVisible(Pair<Integer, String>[][] data) {		
	    modeloTabla.setDataVector(convertirParejaAMatrizString(data), new String[]{"Longitud","Colores", "Usuario","Movimientos"});
	    tablaDatos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);		
	}

	private String[][] convertirParejaAMatrizString(Pair<Integer, String>[][] data) {
	    String[][] result = new String[20][4];
	    for(int i = 0; i < 5; i++) {
	    	for(int j = 0; j < 4; j++) {
	    		result[i*4+j][0] = Integer.toString(i+1);
	    		result[i*4+j][1] = Integer.toString(j+2);
	    		if(data[i][j] != null) {
	    			result[i*4+j][2] = data[i][j].obtenSegundo();
	    			result[i*4+j][3] = Integer.toString(data[i][j].obtenPrimero());
	    		}
	    		else {
	    			result[i*4+j][2] = "- - - -";
	    			result[i*4+j][3] = "- - - -";
	    		}	    		
	    		
	    	}
	    }
	    return result;
	}
	
}
