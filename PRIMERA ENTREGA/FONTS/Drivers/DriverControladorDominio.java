package Drivers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ControladoresDominio.ControladorDominio;
import Dominio.Pair;

public class DriverControladorDominio {
		
	private ControladorDominio CtlrDom = ControladorDominio.obtenInstancia();
	
	public static void main(String[] args) throws Exception {
		DriverControladorDominio dCtrlDom = new DriverControladorDominio();

		System.out.println("DRIVER DEL CONTROLADOR DE DOMINIO");
		System.out.println("");

		Scanner in = new Scanner(System.in);
		String nombreUsuario;
		String contrasena;
		int numTurnos;
		int longitudCodigo = 0;
		int dificultad;
		int ayuda;
		int colores;
		int idPartida;
		boolean esCodeMaker;
		List<Integer> codigo;

		int opcion = -1;
		while (opcion != 0) {
			imprimeOpciones();
			opcion = in.nextInt();

			switch (opcion) {
			case 0:
				System.out.println("Finalizando el driver");
				in.close();
				break;
				
			case 1:
				System.out.println("Introduce el nombre de usuario (entre 1 y 15 caracteres)");
				nombreUsuario = in.next();
				System.out.println("Introduce la contrasena (entre 1 y 15 caracteres y debe contener una mayuscula, un numero y un simbolo)");
				contrasena = in.next();
				dCtrlDom.testRegistrarUsuario(nombreUsuario, contrasena); // Registra el usuario con los valores introducidos 
				break;
				
			case 2:
				System.out.println("Introduce el nombre de usario");
				nombreUsuario = in.next();
				System.out.println("Introduce la contrasena");
				contrasena = in.next();
				dCtrlDom.testIniciarSesion(nombreUsuario, contrasena); // Inicia sesion en caso de ser un usuario valido con us contrasena correctta
				break;
				
			case 3:
				dCtrlDom.testSalirSesion();								// Cierra la sesion
				break;
				
			case 4:
				System.out.println("Creando Partida");
				System.out.println("Introduzca el Numero de turnos (1-10):");  
				numTurnos = in.nextInt();
				System.out.println("Introduzca la dificultad (0-2):"); 
				dificultad = in.nextInt();
				System.out.println("Introduzca el nivel de ayuda (0-2):"); 
				ayuda = in.nextInt();
				System.out.println("Introduzca el numero colores (2-5):");
				colores = in.nextInt();
				System.out.println("Introduzca la longitud de codigo (1-5):");
				longitudCodigo = in.nextInt();
				System.out.println("Es Codemaker?(0/1):"); // SI o NO
				if(in.nextInt() == 1) esCodeMaker = true;
				else esCodeMaker = false;

				dCtrlDom.testNuevaPartida(numTurnos, dificultad,ayuda,colores,longitudCodigo,esCodeMaker);	// Crea una partida con los parametros introducidos
				break;
				
			case 5:
				dCtrlDom.testMostrarPartidasEmpezadas();				// Muestra las partidas SIN acabar
				break;
				
			case 6:
				dCtrlDom.testMostrarPartidasAcabadas();					// Muestra las partidas acabadas
				break;
				
			case 7:				
                int hayPartidasEmpezadas = dCtrlDom.testMostrarPartidasEmpezadas();			// Muestra las partidas SIN acabar
                if (hayPartidasEmpezadas == 1) {
                	System.out.println("Indica el numero de la partida que quieres reanudar");
                	idPartida = in.nextInt();
                    dCtrlDom.testReanudarPartida(idPartida);								// Reanuda la partida con el numero introducido
                }
                else if (hayPartidasEmpezadas == 0) System.out.println("NO hay partidas sin acabar");                
                break;
			case 8:
				dCtrlDom.testConsultarEstadisticas();					// Imprime en pantalla las estadisticas del usuario
				break;
				
			case 9:
				dCtrlDom.testConsultarRankingGlobal();					// Imprime en pantalla el ranking global
				break;
				
			case 10:
				System.out.println("Tamano de codigo:"); 
				longitudCodigo = in.nextInt();
				System.out.println("Numero de colores:");
				colores = in.nextInt();
				dCtrlDom.testConsultarRecordMovimientoConcreto(longitudCodigo, colores);	// Imprime en pantalla el record indicado
				break;
				
			case 11:
				System.out.println("Records actuales");
				dCtrlDom.testConsultarRecordsMovimientos();				// Imprime en pantalla todos los records					
				break;
				
			case 12:
				System.out.println("Creando Partida CodeBreaker");				
				
				esCodeMaker = false;
				int[] datosPartidaCB = dCtrlDom.leerDatosPartidas();	// Lee datos de Partida
				numTurnos = datosPartidaCB[0];
				dificultad = datosPartidaCB[1];
				ayuda = datosPartidaCB[2];
				colores = datosPartidaCB[3];
				longitudCodigo = datosPartidaCB[4];
				int resCB = dCtrlDom.testNuevaPartida(numTurnos, dificultad,ayuda,colores,longitudCodigo,esCodeMaker); // Crea la nueva partida de tipo codeBreaker
				
				if (resCB == 0)dCtrlDom.jugarPartidaCB(numTurnos, dificultad,ayuda,colores,longitudCodigo, 1);			// Inicia la partida en el primer turno

				break;
				
			case 13:
				System.out.println("Creando Partida CodeMaker");
				esCodeMaker = true;
				int[] datosPartidaCM = dCtrlDom.leerDatosPartidas();	// Lee datos de Partida
				numTurnos = datosPartidaCM[0];
				dificultad = datosPartidaCM[1];
				ayuda = datosPartidaCM[2];
				colores = datosPartidaCM[3];
				longitudCodigo = datosPartidaCM[4];
				int resCM = dCtrlDom.testNuevaPartida(numTurnos, dificultad,ayuda,colores,longitudCodigo,esCodeMaker); // Crea la nueva partida de tipo codemaker
				
				// Inicia la partida en el turno 0, ya que primero hay que introducir el codigo a batir	
				if (resCM == 0) dCtrlDom.jugarPartidaCM(numTurnos, dificultad,ayuda,colores,longitudCodigo, 0);			
				break;
				
			default:
				System.out.println("ERROR: Opcion invalida");
				break;
			}
		}

	}
	
	// Imprime en pantalla las opciones del driver
	private static void imprimeOpciones() {
		System.out.println(" 0 - Finalizar el driver");
		System.out.println(" 1 - Registrar Usuario");
		System.out.println(" 2 - Iniciar Sesion");
		System.out.println(" 3 - Salir Sesion");
		System.out.println(" 4 - Crear Partida");
		System.out.println(" 5 - Mostrar Partidas Empezadas");
		System.out.println(" 6 - Mostrar Partidas Acabadas");
		System.out.println(" 7 - Reanudar Partida");
		System.out.println(" 8 - Consultar Estadisticas");
		System.out.println(" 9 - Consultar Ranking Global");
		System.out.println(" 10 - Consultar un Record");
		System.out.println(" 11 - Consultar Records");
		System.out.println(" 12 - Nueva partida code breaker");
		System.out.println(" 13 - Nueva partida code maker");
	}

	// Registrar usuario
		public void testRegistrarUsuario(String username, String password) {
			try {
				CtlrDom.registrar(username, password);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}			
			System.out.println("Cuenta creada correctamente");
		}
		
		// Iniciar sesion
		public void testIniciarSesion(String username, String password) {
			try {
				CtlrDom.iniciarSesion(username, password);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}
			if (CtlrDom.obtenUsuarioActivo() == null) {
				System.out.println("Error desconocido al iniciar sesion");
			} else {
				System.out.println("Has iniciado sesion, bienvenido " + username + "!");
			}
		}
		
		// Cerrar sesion
		public void testSalirSesion() {
			try {
				CtlrDom.cerrarSesion();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}
			if (CtlrDom.obtenUsuarioActivo() != null) {
				System.out.println("Error al salir de la sesion");
			} else {
				System.out.println("Has salido de la sesion");
			}
		}
		
		// Crear partida
		public int testNuevaPartida(int numTurnos, int dificultad, int ayuda, int colores, int longitudCodigo, boolean esCodeMaker){
			List<Object> listaObjetos = new ArrayList<Object>();
			listaObjetos.add(numTurnos);
			listaObjetos.add(dificultad);
			listaObjetos.add(ayuda);
			listaObjetos.add(colores);		
			listaObjetos.add(longitudCodigo);
			listaObjetos.add(esCodeMaker);
			try {
				CtlrDom.cargarPartida(CtlrDom.creaPartida(listaObjetos));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return -1;
			}		
			return 0;
		}	
		
		// Historial partida SIN acabar
		public int testMostrarPartidasEmpezadas() {
			ArrayList<Integer> listaAux = null;
			try {
				listaAux = CtlrDom.obtenPartidasPerfilPorAcabar();	// Obtiene las partidas sin acabar
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return -1;
			}
			
			// Lista las partidas en pantalla
			System.out.println("Lista de partidas SIN acabar:");
			for(int i = 0; i < listaAux.size(); i++) {
				System.out.print("Partida numero ");
				System.out.print((listaAux.get(i) + 1));
				if(CtlrDom.esCodemMaker(i)) System.out.println("    Tipo: CodeMaker");
				else System.out.println("    Tipo: CodeBreaker");
			}
			System.out.println("");
			if (listaAux.size() > 0) return 1;
			else return 0;
		}
		
		// Historial partidas acabadas
		public void testMostrarPartidasAcabadas() {
			ArrayList<Integer> listaAux = null;
			try {
				listaAux = CtlrDom.obtenPartidasPerfilAcabadas();  // Obtiene las partidas acabadas
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}
			
			// Lista las partidas en pantalla
			System.out.println("Lista de partidas acabadas:");
			for(int i = 0; i < listaAux.size(); i++) {
				System.out.print("Partida numero ");
				System.out.print((listaAux.get(i) + 1));
				if(CtlrDom.esCodemMaker(i)) System.out.println("    Tipo: CodeMaker");
				else System.out.print("    Tipo: CodeBreaker");
			}
			System.out.println("");
		}
		
		// Reanudar Partida
		public void testReanudarPartida(int idPartida) {
			int[] datosPartida = null;
			
			// Carga la partida al controlador
			try {
				CtlrDom.cargarPartida((idPartida - 1));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}
			
			// Obtiene los datos de la partida indicada
			datosPartida = CtlrDom.obtenDatosPartida();
			Boolean esCodeMaker;
			if (datosPartida[5] == 1) esCodeMaker = true;
			else esCodeMaker = false;
			
			// Inicia la partida en el turno en el que se abandono
			if(esCodeMaker)
				try {
					jugarPartidaCM(datosPartida[0], datosPartida[1], datosPartida[2], datosPartida[3], datosPartida[4], datosPartida[6]);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return;
				}
			else {
				imprimirEstadoPartida(datosPartida[6]);				// En caso de ser una partida codeBreaker imprime el estado de la partida 
				try {
					jugarPartidaCB(datosPartida[0], datosPartida[1], datosPartida[2], datosPartida[3], datosPartida[4], datosPartida[6]);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return;
				}	
			}
					
		}
		
		
		// Lista las estadisticas del usuario logeado
		public void testConsultarEstadisticas() {
			int[] estadisticas;
			try {
				estadisticas = CtlrDom.obtenEstadisticas();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}		
			System.out.println("Estadisticas");
			System.out.println("Numero de partidas jugadas: " + estadisticas[0]);
			System.out.println("Numero de partidas ganadas: " + estadisticas[1]);
			System.out.println("Numero de partidas perdidas: " + estadisticas[2]);
			if (estadisticas[0] > 0) {
				System.out.println("Porcentaje de victoria: " + CtlrDom.obtenPorcentajeVictorias()+"%");
				if (estadisticas[1] > 0) {
					System.out.print("Tu mejor partida fue la Partida numero " + (CtlrDom.obtenIdMejorPartida()+1) +", donde conseguiste una puntuacion de ");
					System.out.printf("%.2f%n", CtlrDom.obtenMejorPuntuacion());
				}				
			}			
			else System.out.println("Todavia no se ha jugado ninguna partida, cuando hayas jugado 1 partida apareceran mas estadisticas");
			System.out.println("");
		}

		// Consultar ranking
		public void testConsultarRankingGlobal() {
			Pair<String, Integer>[] ranking = null;
			try {
				ranking = CtlrDom.obtenRanking();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}
			System.out.println("Ranking Actual");
			for (int i = 0; i < ranking.length; i++) {
				if (ranking[i] != null) System.out.println((i + 1) + "     " + ranking[i].obtenPrimero() + "     " + ranking[i].obtenSegundo());
			}
		}
		
		
		
		// Consultar record concreto
		public void testConsultarRecordMovimientoConcreto(int longitudCodigo, int colores) {
			Pair<Integer, String> record = null;
			try {
				record = CtlrDom.obtenRecordConcreto(longitudCodigo, colores);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}			
			System.out.println("El record de una partida con "+ longitudCodigo + " turnos y " + colores + " colores es de ");
			System.out.println(record.obtenPrimero() + " turnos y fue conseguido por " + record.obtenSegundo() + ".");
		}
		
		// Consultar todos los records
		public void testConsultarRecordsMovimientos() {
			Pair<Integer, String>[][] records = CtlrDom.obtenRecords();			
			for (int i = 0; i < records.length; i++) {
	            for (int j = 0; j < records[0].length; j++) {
	                if(records[i][j] != null) {
	                	System.out.println("El record de una partida con tamano de codigo de "+ (i + 1) + " y " + (j + 2) + " colores es de ");
	                	System.out.println(records[i][j].obtenPrimero() + " turnos y fue conseguido por " + records[i][j].obtenSegundo() + ".");
	                }
	                else {
	                	System.out.println("Aun no hay record de una partida con tamano de codigo de "+ (i + 1) + " y " + (j + 2) + " colores.");
	                }
	            }
	        }
		}
		

		// Jugar partidas de tipo codeBreaker con los parametros especificados en el turno indicado en "numeroTurno"
	private void jugarPartidaCB(int numTurnos, int dificultad, int ayuda, int colores, int longitudCodigo, int numeroTurno) {
		System.out.println("Recuerda: La partida puede ser parada, lista para ser reanudada posteriormente introduciendo -1");
		List<Integer> codigo;
		Scanner in = new Scanner(System.in);
		int resultadoTurno = 0;
		
		// Entramos en un bucle de ejecutar turnos hasta llegar al numero de turnos de la partidas o ganar
		while(resultadoTurno == 0 && numeroTurno <= numTurnos) {
			
			// Obtenemos el numero de pistas disponibles
			int pistasRestantes = 0;
			try {
				pistasRestantes = CtlrDom.obtenPistasDisponibles();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}
			
			// Indicamos al usuario el numero de pistas restantes en caso de que queden y le preguntamos si quiere usar una
			int pista = 0;
			if (pistasRestantes > 0) {
				System.out.println("Quieres usar una pista? (1/0)");
				System.out.println("Te quedan " + pistasRestantes + " pistas");
				pista = in.nextInt();
				if (pista == -1) return;	// En caso de que el usuario introduzca -1 se abandonara la partida que podra ser reanudada posteriormente
			}			
			
			// En caso de querer usar una pista obtenemos la pista y le informamos al usuario una posicion de un numero correcto
			if (pista == 1) {
				List<Integer> listaPista = null;
				try {
					listaPista = CtlrDom.obtenPista();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				int pos = -1, num = 0;
				pos=listaPista.get(0);
				num=listaPista.get(1);
				if (pos != -1) System.out.println("En el codigo correcto hay un " + num + " en la posicion " + (pos+1));
			}
			
			// Obtenemos los datos de la partida y pedimos al usuario que ingrese un codigo con la longitud y numero de colores correcta
			int[] datosPartida = CtlrDom.obtenDatosPartida();
			System.out.println("Introduzca un codigo, debe tener una longitud de " + datosPartida[4] + " i los numeros pueden ir del 0 al " + (datosPartida[3]-1));
			System.out.println("Recuerda dejar un espacio entre los numero o introducirlos de uno en uno");
			
			// Leemos el codigo introducido
			codigo = new ArrayList<>();
			for (int i = 0; i < longitudCodigo; i++) {
				int input = in.nextInt();
				if (input == -1) return;	// En caso de que el usuario introduzca -1 se abandonara la partida que podra ser reanudada posteriormente
				codigo.add(input);
			}
			try {
				// Ejecutamos un turno con el codigo introducido
				resultadoTurno = testEjecutarTurno(codigo);
			} catch (Exception e) { 
				// En caso de error imprimira el mensaje de error y abortara la partida
				System.out.println(e.getMessage());
				return;
			}
			// Iprimimos el estado de la partida despues de ejecutar el turno
			imprimirEstadoPartida(numeroTurno);	
			// Incrementamos el numero de turno
			numeroTurno++;
		}
		// Informamos al usuario de si ha ganado o perdido
		if (resultadoTurno == 1) System.out.println("FELICIDADES GANASTE!");
		else System.out.println("PERDISTE");		
	}

	
	
	// Jugar partidas de tipo codeMaker con los parametros especificados en el turno indicado en "numeroTurno"
	private void jugarPartidaCM(int numTurnos, int dificultad, int ayuda, int colores, int longitudCodigo, int numeroTurno) {
		System.out.println("Recuerda: La partida puede ser parada, lista para ser reanudada posteriormente introduciendo -1");
		List<Integer> codigo;
		Scanner in = new Scanner(System.in);
		int[] datosPartida = CtlrDom.obtenDatosPartida();
		int resultadoTurno = 0;
		List<Integer> codigoOriginal = CtlrDom.obtenCodigoOriginal();
		
		// En caso que la partida no haya sido iniciada y que no contenga un codigo que adivinar se pedira al usuario que lo introduzca
		if (codigoOriginal == null) {
			System.out.println("Introduzca el codigo a batir, debe tener una longitud de " + datosPartida[4] + " i los numeros pueden ir del 0 al " + (datosPartida[3]-1));
			System.out.println("Recuerda dejar un espacio entre los numero o introducirlos de uno en uno");		
			codigo = new ArrayList<>();
			
			// Leemos el codigo del usuario
			for (int i = 0; i < longitudCodigo; i++) {
				int input = in.nextInt();
				if (input == -1) {
					System.out.println("Partida abortada");
					return;	// En caso de que el usuario introduzca -1 se abandonara la partida que podra ser reanudada posteriormente
				}
				codigo.add(input);
			}			
			try {
				// Ejecutamos un turno introduciendo el codigo a adivinar
				resultadoTurno = testEjecutarTurno(codigo);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Partida abortada");
				return;
			}
			try {
				// Ejecutamos un turno con codigo nulo para que la maquina procese su primer codigo
				testEjecutarTurno(null);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Partida abortada");
				return;
			}
		}		
		
		List<Integer> resultado = new ArrayList<>();
		// Entramos en un bucle de ejecutar turnos hasta llegar al numero de turnos de la partidas o perder
		while(resultadoTurno == 0 && numeroTurno <= numTurnos) {
			imprimirEstadoPartida(numeroTurno);
			int pista = 0;
			int pistasRestantes = 0;
			// Indicamos al usuario el numero de pistas restantes en caso de que queden y le preguntamos si quiere usar una
			try {
				pistasRestantes = CtlrDom.obtenPistasDisponibles();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			boolean correccionCorrecta = false;
			// Ejecutamos un bucle que no terminara hasta que se introduzca la corrección correcta, se use una pista o se introduzca -1
			do {
				// Indicamos al usuario el numero de pistas restantes en caso de que queden y le preguntamos si quiere usar una
				if (pistasRestantes > 0) {
					System.out.println("Quieres usar la autocorreccio? (te gastara una pista) (0/1)");
					System.out.println("Te quedan " + pistasRestantes + " pistas");
					pista = in.nextInt();
					if (pista == -1) {
						System.out.println("Partida abortada");
						return;	// En caso de que el usuario introduzca -1 se abandonara la partida que podra ser reanudada posteriormente
					}
				}		
				
				// En caso de querer usar una pista obtenemos la pista, la cual sera usada para corregir correctamente 
				if (pista == 1) {
					List<Integer> listaPista = null;
					try {
						listaPista = CtlrDom.obtenPista();
					} catch (Exception e) {
						System.out.println(e.getMessage());
						System.out.println("Partida abortada");
						return;
					}
					System.out.println("Pista usada");
					try {
						informaResultado(listaPista);
					} catch (Exception e) {
						System.out.println(e.getMessage());
						System.out.println("Partida abortada");
						return;
					}
					correccionCorrecta = true;
				}
				
				// En caso de no usar una pista el usuario debera introducir la correccion
				else { 
					System.out.print("Introduzca la correccion, el codigo original es: ");
					imprimirCodigoOriginal();
					resultado = new ArrayList<>();
					for (int i = 0; i < longitudCodigo; i++) {
						int input = in.nextInt();
						if (input == -1) {
							System.out.println("Partida abortada");
							return;	// En caso de que el usuario introduzca -1 se abandonara la partida que podra ser reanudada posteriormente
						}
						resultado.add(input);
					}
					correccionCorrecta = true;
					try {
						// El codigo introducido sera enviado a la partida, en caso de ser incorrecto el usuario continuara en el bucle
						informaResultado(resultado);
					} catch (Exception e) {
						System.out.println(e.getMessage());
						correccionCorrecta = false;
					}
				}
			} while (!correccionCorrecta);
			
			
			// Una vez finalizada la correccion, se ejecutara un turno con codigo nulo para que la maquina analize la correccion
			try {
				resultadoTurno = testEjecutarTurno(null);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Partida abortada");
				return;
			}
			
			// Se imprimira el estado de la partida 
			imprimirEstadoPartida(numeroTurno);
			
			try {
				// Una vez finalizada la correccion, se ejecutara un turno con codigo nulo para que la maquina introduzca su nuevo codigo
				testEjecutarTurno(null);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Partida abortada");
				return;
			}
			numeroTurno++;					
		}
		// Informamos al usuario de si ha ganado o perdido
		if (resultadoTurno == 1) System.out.println("FELICIDADES GANASTE!");
		else System.out.println("PERDISTE");			
	}

	// Lee los datos de partida y los añade a un vector para ser consultados
	private int[] leerDatosPartidas() {
		Scanner in = new Scanner(System.in);
		int[] datosPartida = new int[5];
		System.out.println("Introduzca el Numero de turnos (1-10):"); 
		datosPartida[0] = in.nextInt();
		System.out.println("Introduzca la dificultad (0-2):"); 
		datosPartida[1] = in.nextInt();
		System.out.println("Introduzca el nivel de ayuda (0-2):"); 
		datosPartida[2] = in.nextInt();		
		System.out.println("Introduzca el numero colores (2-5):"); 
		datosPartida[3] = in.nextInt();
		System.out.println("Introduzca la longitud de codigo (1-5):");
		datosPartida[4] = in.nextInt();
		return datosPartida;
	}	
	
	// Informa a la partida del resultado de corregir su codigo
	private void informaResultado(List<Integer> resultado) throws Exception {
		CtlrDom.informaResultado(resultado);
	}
	
	// Imprime en pantalla el estado actual de la partida
	private void imprimirEstadoPartida(int numeroTurno) {
		System.out.println("Turno " + numeroTurno);
		List<List<Integer>> codigos = CtlrDom.obtenTableroResultados();
		List<List<Integer>> adivinanzas = CtlrDom.obtenTableroAdivinanzas();
		
        System.out.println("Mastermind:");
        System.out.println("Colores:                Correcciones:");

        for (int i = 0; i < codigos.size(); i++) {
            System.out.printf("%-25s %-25s%n", adivinanzas.get(i), codigos.get(i));
        }


        System.out.println("----------------------------------------");
	}
	
	// Ejecuta un turno con el codigo proporcionado
	public int testEjecutarTurno(List<Integer> codigo) throws Exception {
		int resultadoTurno = CtlrDom.ejecutarTurno(codigo);		
		return resultadoTurno;
	}
	
	// Imprime en pantalla el codigo que hay que descifrar en la partida 
	private void imprimirCodigoOriginal() {
		List<Integer> codigo = CtlrDom.obtenCodigoOriginal();
		
		for (int i = 0; i < codigo.size(); i++) {
            System.out.print(codigo.get(i));
            if (i < codigo.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
		
	}
	
}
