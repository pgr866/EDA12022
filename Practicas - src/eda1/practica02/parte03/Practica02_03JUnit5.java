package eda1.practica02.parte03;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import eda1.auxiliar.AVLTree;
import eda1.auxiliar.Pair;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica02_03JUnit5 {
		 			
	@Test
	@Order(1)
	public void testMyTreeMap() {
		MyTreeMap<String, ArrayList<Double>> arbol = new MyTreeMap<String, ArrayList<Double>>();
		ArrayList<Double> aux = null;
		
		assertTrue(arbol.put("Oswald", new ArrayList<Double>()));
		assertTrue(arbol.get("Oswald").size() == 0);
		
		assertFalse(arbol.put("Oswald", null));
		assertTrue(arbol.get("Oswald") == null);
		
		assertFalse(arbol.put("Oswald", new ArrayList<Double>()));
		
		assertTrue(arbol.get("Pepe") == null);
		
		arbol.get("Oswald").add(3.0);
		arbol.get("Oswald").add(2.0);
		arbol.get("Oswald").add(4.0);
		arbol.get("Oswald").add(7.0);
		
		assertTrue(arbol.get("Oswald").size() == 4);
		
		aux = new ArrayList<Double>();
		aux.add(3.0);
		aux.add(2.0);
		aux.add(8.0);
		aux.add(3.0);
		assertTrue(arbol.put("Edward", aux)); 
		assertFalse(arbol.put("Edward", aux)); //devuelve false pq Edward no es una nueva clave...ya existia
		
		assertTrue(arbol.get("Edward").size() == 4);
		
		assertTrue(arbol.size() == 2);
				
		assertEquals("[Edward, Oswald]", arbol.keySet().toString());
		assertEquals("[[3.0, 2.0, 8.0, 3.0], [3.0, 2.0, 4.0, 7.0]]", arbol.values().toString());
		assertEquals("[Edward <[3.0, 2.0, 8.0, 3.0]>, Oswald <[3.0, 2.0, 4.0, 7.0]>]", arbol.entrySet().toString());	
		
		assertEquals(arbol.toString(), arbol.entrySet().toString());
		
		//atencion a lo peligroso que puede llegar a ser el uso de sinonimos (multiples referencias a un mismo objeto)
		assertEquals("[3.0, 2.0, 8.0, 3.0]", arbol.get("Edward").toString());
		aux.clear();
		assertEquals("[]", arbol.get("Edward").toString());
		
		
		assertFalse(arbol.put("Edward", new ArrayList<Double>()));
		assertEquals("[Edward <[]>, Oswald <[3.0, 2.0, 4.0, 7.0]>]", arbol.entrySet().toString());	
		
		assertTrue(arbol.put("Alfred", new ArrayList<Double>()));
		assertTrue(arbol.size() == 3);
		assertEquals("[Alfred <[]>, Edward <[]>, Oswald <[3.0, 2.0, 4.0, 7.0]>]", arbol.entrySet().toString());
		
		aux = arbol.get("Alfred");
		
		assertTrue(arbol.get("Alfred").size() == 0);
		
		aux.add(23.3);
		aux.add(34.3);
		
		assertTrue(arbol.get("Alfred").size() == 2);
		assertFalse(arbol.put("Oswald", aux)); //put() devuelve false pq la clave ya existe; ahora Oswald y Alfred comparten la misma estructura (aux)
		
		assertEquals("[23.3, 34.3]", arbol.get("Oswald").toString());
		assertEquals("[23.3, 34.3]", arbol.get("Alfred").toString());
		
		aux.add(.5);
		aux.add(.6);
		aux.add(.3); 
		
		assertEquals("[23.3, 34.3, 0.5, 0.6, 0.3]", arbol.get("Oswald").toString());
		
		assertEquals("[23.3, 34.3, 0.5, 0.6, 0.3]", arbol.get("Alfred").toString());
		
		assertNull(arbol.get("Bruce"));
		assertFalse(arbol.containsKey("Bruce"));

		assertEquals("[Alfred <[23.3, 34.3, 0.5, 0.6, 0.3]>, Edward <[]>, Oswald <[23.3, 34.3, 0.5, 0.6, 0.3]>]", arbol.toString());
		
		arbol.clear();
		aux.clear();
		
		assertEquals("[]", arbol.entrySet().toString());
		assertEquals("[]", arbol.keySet().toString());
		assertEquals("[]", arbol.values().toString());
		assertEquals(arbol.toString(), arbol.entrySet().toString());
	
		arbol = null;
		aux = null;
	}
	
	@Test
	@Order(2)
	public void testUser() {
		User user01 = new User("@oswald");
		User user02 = new User("@edwarD");
		User user03 = new User("@Alfred");
		
		user01.add("dispositivo01", "hola", "hola", "que", "tal", "esto", "es");
		user01.add("dispositivo01", "una", "prueba");
		user02.add("dispositivo01", "buenas", "que", "tal");
		user02.add("dispositivo02", "hola", "que", "tal");
		user03.add("dispositivo01", "hola", "como", "estas");
		user03.add("dispositivo02", "como", "te", "llamas");
		
	
		assertEquals("@oswald=<1 dispositivo>", user01.toString());
		assertEquals("@edwarD=<2 dispositivos>", user02.toString());
		assertEquals("@Alfred=<2 dispositivos>", user03.toString());
		
		user01.add("dispositivo02");
		user01.add("dispositivo03");
		user01.add("dispositivo04");
		user01.add("dispositivo02", "mas", "mensajes", "de", "prueba");
		user01.add("dispositivo03", "para", "ver", "si", "lo", "esperado", "coincide", "con", "lo", "real");
		
		assertEquals("@oswald=<4 dispositivos>", user01.toString());

		String salidaEsperada = "dispositivo01 <[es, esto, hola, prueba, que, tal, una]>\n" + 
								"dispositivo02 <[de, mas, mensajes, prueba]>\n" + 
								"dispositivo03 <[coincide, con, esperado, lo, para, real, si, ver]>\n" + 
								"dispositivo04 <[]>\n";
								
		String salidaReal = "";
		for (Pair<String, AVLTree<String>> par : user01) {
			salidaReal += par.toString() + "\n";
		}
		assertEquals(salidaEsperada, salidaReal);
		
		user01.clear();
		user02.clear();
		user03.clear();
		salidaEsperada = salidaReal = null;
		user01 = user02 = user03 = null;
	}
}