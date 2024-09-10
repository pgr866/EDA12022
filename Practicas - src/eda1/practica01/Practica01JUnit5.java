package eda1.practica01;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica01JUnit5 {
	private String directorioEntrada = System.getProperty("user.dir") + File.separator +
							           "Practicas - src" + File.separator + 
							           "eda1" + File.separator + 
				
							           "practica01" + File.separator; 
							           
	@Test
	@Order(1)
	public void testDevice(){
 		Device.initializeNumDevices(); //Inicializamos atributo estatico (contador de dispositivos)
		
 		Device d1 = new Device("     iPhone 13"); //Cuidado con los espacios en blanco detrás y delante del texto --> uso del metodo trim()
		Device d2 = new Device("iPhone 13 Pro       ");
		Device d3 = new Device("  Nubia Lite   ");
		Device d4 = null;
		
		try {
			d4 = new Device(null); //Si el nombre del dispositivo es null, se lanza una excepción
		}catch(Exception e) {
			assertEquals(e.getMessage(), "El atributo name no puede ser nulo");
		}

		d4 = new Device("");
		
		assertEquals("1.- iPhone 13",   d1.toString());
		assertEquals("2.- iPhone 13 Pro",  d2.toString());
		assertEquals("3.- Nubia Lite", d3.toString());
		assertEquals("4.- noName",     d4.toString());
		
		
		for (int i=0; i<1000; i++) {
			new Device("");
		}
		
		d4 = new Device("");
		assertEquals(d4.toString(), "1005.- noName"); //1005???
		
		d1.clear();
		d2.clear();
		d3.clear();
		d4.clear();
		
		d1 = d2 = d3 = d4 = null;
	}
	@Test
	@Order(2)
	public void testDevicesSendMessagesRemove() {
 		Device.initializeNumDevices(); 
 	
 		Device d1 = new Device("dev1");
 		
 		d1.sendMessage("Hola amiga que tal");
 		d1.sendMessage("Hola hOLa hoLA amigo como estamos");
 		
 		String[] salidaEsperada = {"hola", "amiga", "que", "tal", "amigo", "como", "estamos"}; //Se eliminan palabras repetidas; todas en minúsculas
 		
 		int cont=0;
 		for (String word: d1) { //Observad la forma en la que iteramos sobre el dispositivo dev1 (sobre sus mensajes)
 			assertEquals(salidaEsperada[cont++], word);
 		}
 		
 		assertFalse(d1.contains("zapato")); //No contiene la palabra zapato (devuelve false)
 		assertTrue(d1.contains("hOLa")); //Contiene la palabra hola (devuelve true)
 		
 		
  		assertTrue(d1.substitute("amiga", "perla")); //se sustituye amiga por perla --> devuelve true
 		
 		assertFalse(d1.substitute("amiga", "perla")); //ahora devuelve false??
 
 		assertTrue(d1.substitute("estamos", "vAs"));
 		 
 		assertFalse(d1.substitute("amigo", null)); //false -> no se permite un argumento = null
 		assertFalse(d1.substitute(null, "que")); //false -> no se permite un argumento = null
 		assertFalse(d1.substitute("tal", null)); //false -> no se permite un argumento = null
		
 		assertFalse(d1.remove(null)); 
 		assertTrue(d1.remove("aMiGo"));
 		assertTrue(d1.remove("tal"));
 		
 		String[] salidaEsperada2 = {"hola", "perla", "que", "como", "vas"};
 		
 		cont=0;
 		for (String word: d1) {
 			assertEquals(salidaEsperada2[cont++],word);
 		}
 		
 		assertTrue(d1.size() == 5);
 		
 		d1.clear();

 		assertTrue(d1.size() == 0);

 		d1 = null;
	}
	@Test
	@Order(3)
	public void testUser(){
		User.initializeNumUsers();
		User u1 = new User("   Bob   "); 
		User u2 = new User("Alice  ");
		User u3 = null;
		
		try {
			u3 = new User(null);
		}catch (Exception e) {
			assertEquals("El atributo name no puede ser ni nulo ni vacio", e.getMessage());
		}
		
		try {
			u3 = new User("");
		}catch (Exception e) {
			assertEquals("El atributo name no puede ser ni nulo ni vacio", e.getMessage());
		}
		
		u3 = new User("Eve");
		
		
		assertEquals("1.- Bob", u1.toString()); //Como podeis observar, se respetan las mayusculas y minusculas
		assertEquals("2.- Alice", u2.toString());
		assertEquals("3.- Eve", u3.toString());
		
		u1.clear();
		u2.clear();
		u3.clear();
		u1 = u2 = u3 = null;
	}
	@Test
	@Order(4)
	public void testUserDevices() {
 		Device.initializeNumDevices();
 		User.initializeNumUsers();

		User u1 = new User("bob");
		User u2 = new User("alice");
		Device d1 = new Device("iPhone 11");
		Device d2 = new Device("Motorola MG8");
		Device d3 = new Device("iPhone 11");
		
		u1.addDevices(d1, d1, d2, d3, d3); //Asociamos 5 dispositivos con el usuario u1
	
		assertTrue(u1.getNumDevices() == 3); //Y segun esto, el usuario u1 solo tiene 3 dispositivos...¿¿¿????????????
	
		for (int i=0; i<1000; i++) {
			u2.addDevices(new Device("__"));
		}
	
		assertTrue(u2.getNumDevices() == 1000); //¿¿???
		
		assertTrue(u1.sendMessage(d1, "este es un comentario de tranquilidad")); //d1 esta en la listas de dispositivos de u1
		assertTrue(u1.sendMessage(d2, "este es un segundo comentario"));
		assertFalse(u1.sendMessage(new Device("_"), "este es un segundo comentario")); //este nuevo dispositivo no esta en la lista de u1
		
		u1.substitute("comentario", "mensaje");
		u1.remove("tranquilidad"); 
	
		assertFalse(u1.contains("guerra"));
		assertTrue(u1.contains("mensaJe"));
	
		assertEquals("1.- iPhone 11: este es un mensaje de \n" +
					 "2.- Motorola MG8: este es un segundo mensaje \n",
	 			 	 u1.getWords());
		
		assertEquals("[de, es, este, mensaje, segundo, un]", u1.getOrderedWords().toString());
		
		u1.clear();
		u2.clear();
		
		u1 = u2 = null;
		d1 = d2 = d3 = null;
	}
	@Test
	@Order(5)
	public void testLoad() {
		User u1 = new User("u1");
		User u2 = new User("u2");
		Device d1 = new Device("d1");
		Device d2 = new Device("d2");
		Device d3 = new Device("d3");
		
		u1.addDevices(d1,d2);
		u2.addDevices(d3);
		
		assertTrue(u1.loadMessages(d1, directorioEntrada + "archivo1"));
		assertTrue(u1.loadMessages(d2, directorioEntrada + "archivo1"));
		assertFalse(u2.loadMessages(d1, directorioEntrada + "archivo2")); //d1 no pertenece a u3 
		assertTrue(u2.loadMessages(d3, directorioEntrada + "archivo2"));
		
		assertTrue(u1.getOrderedWords().size() == 107);
		assertTrue(u2.getOrderedWords().size() == 109);
		
		assertTrue(u1.contains("cielos"));
		u1.substitute("cielos", "infiernos");
		assertFalse(u1.contains("cielos"));
		assertTrue(u1.contains("infiernos"));
		
		u1.clear();
		u2.clear();
		d1.clear();
		d2.clear();
		d3.clear();
		
		u1 = u2 = null;
		d1 = d2 = d3 = null;
	}
}