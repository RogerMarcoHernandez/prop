package Drivers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Dominio.FactoriaPartida;
import Dominio.IPartida;
import Dominio.Partida;
import Dominio.Perfil;

public class DriverPerfil {
	private HashMap<String,Perfil> Usuarios = new HashMap<String,Perfil>();
	
	
	public static void main(String[] args) throws Exception {
		DriverPerfil dPerfil = new DriverPerfil();
		Perfil usuarioActivo = null;
		
		System.out.println("DRIVER DEL PERFIL");
		System.out.println("");

		Scanner in = new Scanner(System.in);
		
		String username;
		String password;
		
		List<Object> listaObjetos = new ArrayList<Object>();
		listaObjetos.add(4);
		listaObjetos.add(0);
		listaObjetos.add(0);
		listaObjetos.add(4);		
		listaObjetos.add(4);
		listaObjetos.add(false);

		int id = 1;
		
		System.out.println("Introduce tu nombre");
		username = in.next();
		System.out.println("Introduce tu contrasenya");
		password = in.next();
		
		dPerfil.TestCreadora(username,password);
		//dPerfil.RellenarLista();
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
				System.out.println("Introduce el nick del usuario");
				username = in.next();
				System.out.println("Introduce la contraseña");
				password = in.next();
				dPerfil.TestCreadora(username, password);
				break;
				
			case 2:
				System.out.println("Introduce el nick del usuario");
				username = in.next();
				System.out.println("Introduce la contraseña");
				password = in.next();
				usuarioActivo = dPerfil.TestIniciarSesion(username, password,usuarioActivo);
				break;
				
			case 3:
				usuarioActivo = dPerfil.TestSalirSesion(usuarioActivo);
				break;
			case 4:
				if (usuarioActivo != null) {
					IPartida partidaI;
					FactoriaPartida partidaFactory = FactoriaPartida.obtenInstancia();
					partidaI = partidaFactory.creaPartida(id, listaObjetos, usuarioActivo);
					Partida partida = partidaI.obtenPartida();
					usuarioActivo.informaPartidas(partida);
					id++;
				} else {
					System.out.println("Tienes que estar logueado para poder anyadir partidas a un usuario");
				}
				
				break;
			case 5:
				if (usuarioActivo != null) {
					IPartida partidaI;
					FactoriaPartida partidaFactory = FactoriaPartida.obtenInstancia();
					partidaI = partidaFactory.creaPartida(id, listaObjetos, usuarioActivo);
					Partida partida = partidaI.obtenPartida();
					partida.informaAcabada(true);
					usuarioActivo.informaPartidas(partida);
					id++;
				} else {
					System.out.println("Tienes que estar logueado para poder anyadir partidas a un usuario");
				}
				break;
			case 6:
				dPerfil.TestMostrarPartidasSinFinalizar(usuarioActivo);
				break;
			case 7:
				dPerfil.TestMostrarPartidasFinalizadas(usuarioActivo);
				break;
			case 8:
				System.out.println("Introduce la nueva contraseña");
				password = in.next();
				dPerfil.TestCambiarContrasena(usuarioActivo, password);						
				break;
			default:
				System.out.println("ERROR: Opcion invalida");
				break;
			}
		}

	}
	
	private static void imprimeOpciones() {
		System.out.println(" 0 - Finalizar el driver");
		System.out.println(" 1 - Registrar Usuario");
		System.out.println(" 2 - Iniciar Sesion");
		System.out.println(" 3 - Salir Sesion");
		System.out.println(" 4 - Añadir Partida Sin Acabar");
		System.out.println(" 5 - Añadir Partida Acabada");
		System.out.println(" 6 - Mostrar Partidas Acabadas");
		System.out.println(" 7 - Mostrar Partidas Sin Acabar");
		System.out.println(" 8 - Cambiar Contrasena");
	}

	public void TestCreadora(String username, String password) {
		if (!Usuarios.containsKey(username)) {
			Perfil prueba = new Perfil(username,password);
			Usuarios.put(username, prueba);
			System.out.println("Se ha creado el usuario " + username);		
		} else {
			System.out.println("El usuario " + username + " ya existe");		
		}
	}
	
	public Perfil TestIniciarSesion(String username, String password, Perfil usuarioActivo) {
		if (Usuarios.containsKey(username)) {
			if (Usuarios.get(username).contrasenaCorrecta(password)) {
				usuarioActivo = Usuarios.get(username);
				System.out.println("Has iniciado sesion");
			} else {
				usuarioActivo = null;
				System.out.println("La contraseña no es correcta");
			}
		} else {
			System.out.println("El usuario no existe");
		}
		
		return usuarioActivo;
	}
	
	public Perfil TestSalirSesion(Perfil usuarioActivo) {
		if (usuarioActivo == null) {
			System.out.println("No estas logueado en ningun usario para poder salirte");
		} else {
			String usuarioViejo = usuarioActivo.obtenNombreUsuario();
			usuarioActivo = null;
			System.out.println("Se ha cerrado la sesion de " + usuarioViejo);
		}	
		return usuarioActivo;
	}
	
	public void TestMostrarPartidasSinFinalizar(Perfil usuarioActivo) {
		if (usuarioActivo != null) {
			ArrayList<Integer> listaAux = null;
			try {
				listaAux = usuarioActivo.obtenIdsPartidasSinAcabar();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Lista de partidas SIN acabar:");
			for(int i = 0; i < listaAux.size(); i++) {
				System.out.print("Partida numero ");
				System.out.print((listaAux.get(i) + 1));
				System.out.println("");
			}
			System.out.println("");
		} else {
			System.out.println("Tienes que estar logueado para poder ver las partidas sin finalizar de un usuario");
		}
	}
	
	public void TestMostrarPartidasFinalizadas(Perfil usuarioActivo) {
		if (usuarioActivo != null) {
			ArrayList<Integer> listaAux = null;
			try {
				listaAux = usuarioActivo.obtenIdsPartidasAcabadas();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Lista de partidas acabadas:");
			for(int i = 0; i < listaAux.size(); i++) {
				System.out.print("Partida numero ");
				System.out.print((listaAux.get(i) + 1));
				System.out.println("");
			}
			System.out.println("");
		} else {
			System.out.println("Tienes que estar logueado para poder ver las partidas sin finalizar de un usuario");
		}
	}
	
	public void TestCambiarContrasena(Perfil usuarioActivo, String password) {	
		boolean mayuscula = false;
		boolean numero = false;
		boolean simbolo = false;
		if (password.length() > 15) System.out.println("ERROR: la contrasena es demasiado larga, debe ocupar maximo 15 caracteres");
		else {
			for (int i = 0; i < password.length() && (!mayuscula || !numero || !simbolo); i++) {
	            char caracter = password.charAt(i);
	            if (Character.isUpperCase(caracter)) {
	            	mayuscula = true;
	            } else if (Character.isDigit(caracter)) {
	            	numero = true;
	            } else if (!Character.isLetterOrDigit(caracter)) {
	            	simbolo = true;
	            }	            
	        }
			if (mayuscula && numero && simbolo) {
				usuarioActivo.informaContrasena(password);
				Usuarios.put(usuarioActivo.obtenNombreUsuario(), usuarioActivo);
            } else {
            	System.out.println("ERROR: la contrasena no contiene un numero, una mayuscula y un simbolo");		
            }
		}
	}
}
