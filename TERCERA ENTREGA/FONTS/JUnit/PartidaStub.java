package JUnit;

import java.util.List;

import Dominio.Partida;

//Definicion de la clase stub para Partida
	public class PartidaStub extends Partida {
	    private int puntuacion;

	    public PartidaStub(int id, List<Object> config) throws Exception {
	        super(id, config,null);
	        this.puntuacion = id*1000; // Inicializar la puntuacion en 0
	    }

	    @Override
	    public double obtenPuntuacion() {
	        return puntuacion; // Devolver la puntuacion fija del stub
	    }

		@Override
		public int ejecutarTurno(List<Integer> codigo) throws Exception {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public double calculaPuntuacion() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public List<Integer> obtenPista() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	}
