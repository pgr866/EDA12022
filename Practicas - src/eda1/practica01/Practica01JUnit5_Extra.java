package eda1.practica01;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica01JUnit5_Extra {
							         
	//Vamos a modificar la clase User de la Practica01 (sin que afecte al resto del codigo) para que, independiente del id de cada usuario,
	//se considere que dos usuarios son iguales sii sus nombres son iguales.
	//La modificacion consiste en implementar un unico metodo...
	
	@Test
	@Order(1)
	public void testUserEquals(){
		ArrayList<User> usuarios = new ArrayList<>();
		
		String[] userNames = {"Pepe", "Maria", "Pepe", "Jesus", "MaRiA", "Maria", "pePE", "Pepe", "PEPE", "Maria", "maria", "pepe"};
		
		for (String userName : userNames){
			User user = new User(userName);
			if (usuarios.contains(user)) continue;
			usuarios.add(user);
		}
		
		assertTrue(usuarios.size() == 3);
		assertEquals("[1.- Pepe, 2.- Maria, 4.- Jesus]", usuarios.toString());
	}	
	
	//Como podéis comprobar, en este caso hay errores en el codigo. ¿Como es posible?
	//Hay que modificar la clase User para que: (1) no haya errores; y (2) el test se ejecute correctamente (verde)

	
	@Test
	@Order(2)
	public void testIterator(){
		User user = new User("Pepe");
		user.addDevices(new Device("d1"), new Device("d2"), new Device("d3"), new Device("d4"));
		
		for (Device dev: user) {
			assertTrue(dev.size() == 0);
		}
	}
}