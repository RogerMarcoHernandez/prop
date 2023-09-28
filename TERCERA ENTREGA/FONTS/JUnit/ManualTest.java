package JUnit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import Vistas.Manual;

public class ManualTest {
	
	Manual manual;
	
	@Before
	public void setUp() throws Exception {
		manual = new Manual();
	}

	@Test
	public void textoManualPrimeraPagina() {
		String textoEsperado=("Página 1:\r\n"
				+ "\r\n"
				+ "El Mastermind es un juego de estrategia que se juega con dos jugadores. El objetivo del juego es que un jugador adivine el código secreto que ha creado \r\n"
				+ "el otro jugador. El juego fue inventado en 1970 por Mordecai Meirowitz y ha sido un éxito desde entonces.\r\n"
				+ "\r\n"
				+ "Normas del juego:\r\n"
				+ "\r\n"
				+ "El juego se juega con dos jugadores: uno crea un código secreto y el otro intenta adivinarlo.\r\n"
				+ "El código secreto consiste en una combinación de cuatro colores, que pueden repetirse.\r\n"
				+ "El jugador que adivina el código tiene 10 oportunidades para hacerlo.\r\n"
				+ "Después de cada intento, el jugador que ha creado el código proporciona pistas para ayudar al otro jugador a adivinar el código.\r\n"
				+ "Las pistas se dan en forma de fichas negras y blancas. Una ficha negra significa que un color está en la posición correcta, mientras que una ficha blanca \r\n"
				+ "significa que un color es correcto pero está en la posición equivocada.\r\n"
				+ "El jugador que adivina el código gana si lo hace antes de agotar sus 10 oportunidades. Si no lo hace, el jugador que creó el código gana.");
		assertEquals(textoEsperado,manual.getTexto(1));
	}
	@Test
	public void textoManualSegundaPagina() {
		String textoEsperado=("Página 2:\r\n"
				+ "\r\n"
				+ "Cómo se juega:\r\n"
				+ "\r\n"
				+ "El jugador que crea el código secreto selecciona cuatro colores y los coloca en un orden determinado en un soporte especial que viene con el juego.\r\n"
				+ "Los colores que se pueden usar son rojo, azul, verde, amarillo, blanco y negro. Pueden repetirse.\r\n"
				+ "El otro jugador intenta adivinar el código proporcionando una combinación de cuatro colores.\r\n"
				+ "El jugador que creó el código proporciona pistas después de cada intento, en forma de fichas negras y blancas.\r\n"
				+ "El otro jugador utiliza estas pistas para hacer un nuevo intento.\r\n"
				+ "El juego continúa hasta que el jugador adivina el código o se agotan las 10 oportunidades.\r\n"
				+ "Ejemplo de juego:\r\n"
				+ "\r\n"
				+ "El jugador que crea el código secreto selecciona los colores rojo, azul, verde y amarillo en ese orden.\r\n"
				+ "El otro jugador intenta adivinar el código y selecciona los colores rojo, verde, blanco y negro.\r\n"
				+ "El jugador que creó el código proporciona las pistas. En este caso, daría una ficha negra para el rojo y otra para el verde.\r\n"
				+ "El otro jugador utiliza estas pistas para hacer un nuevo intento. Digamos que selecciona los colores verde, azul, amarillo y blanco.\r\n"
				+ "El jugador que creó el código proporciona las pistas. En este caso, daría una ficha negra para el verde y una ficha blanca para el amarillo.\r\n"
				+ "El juego continúa hasta que el jugador adivina el código o se agotan las 10 oportunidades."
				+ "");
		assertEquals(textoEsperado,manual.getTexto(2));
	}
	@Test
	public void textoManualTerceraPagina() {
		String textoEsperado=("Página 3:\r\n"
				+ "\r\n"
				+ "Consejos para jugar mejor:\r\n"
				+ "\r\n"
				+ "Asegúrate de prestar atención a las pistas que te da el otro jugador después de cada intento. Utiliza esta información para hacer un nuevo intento más informado.\r\n"
				+ "Trata de ser sistemático al hacer tus intentos. Por ejemplo, puedes empezar probando combinaciones que tengan todos los colores diferentes, y luego ir probando \r\n"
				+ "combinaciones con colores repetidos.\r\n"
				+ "No te rindas si no adivinas el código a la primera. El juego requiere"
				+ "");
		assertEquals(textoEsperado,manual.getTexto(3));
	}

}
