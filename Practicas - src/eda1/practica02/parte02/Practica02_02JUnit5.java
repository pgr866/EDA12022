package eda1.practica02.parte02;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import eda1.auxiliar.Format;
import eda1.auxiliar.Greater;
import eda1.auxiliar.Less;
import eda1.auxiliar.Pair;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica02_02JUnit5 {
	String directorioEntrada = System.getProperty("user.dir") + File.separator +
			   "Practicas - src" + File.separator + 
			   "eda1" + File.separator + 
			   "practica02" + File.separator + 
			   "parte02" + File.separator;

	
	@Test
	@Order(1)
	public void testPair() {
		Pair<String, Integer> par1 = new Pair<>("hola", 0);
		Pair<String, Integer> par2 = new Pair<>("hola", 0);
		Pair<String, Integer> par3 = new Pair<>("adios", 2);
		
				
		assertEquals("hola <0>", par1.toString());
		assertEquals("hola <0>", par2.toString());
		assertEquals("adios <2>", par3.toString());
		
		assertTrue(par1.compareTo(par2) == 0);
		assertTrue(par1.compareTo(par3) > 0);
		assertTrue(par3.compareTo(par2) < 0);
		assertTrue(par1.equals(par2));
		assertFalse(par1.equals(par3));
		assertFalse(par3.equals(par2));
		
		for (int i=0; i<1000; i++) {
			par1.setValue(par1.getValue()+1);
			par2.setValue(par1.getValue()+2);
			par3.setValue(par1.getValue()+3);
		}
		
		assertEquals("hola <1000>", par1.toString());
		assertEquals("hola <1002>", par2.toString());
		assertEquals("adios <1003>", par3.toString());
		
		
		//Probamos con una coleccion de pares
		
		ArrayList<Pair<String, Integer>> listaPares = new ArrayList<>();
		
		if (!listaPares.contains(par1)) listaPares.add(par1);
		if (!listaPares.contains(par2)) listaPares.add(par2);
		if (!listaPares.contains(par3)) listaPares.add(par3);
		
		assertEquals("[hola <1000>, adios <1003>]",listaPares.toString()); //par1 y par2 son iguales ya que la clave es la misma (palabra "hola")

		listaPares.clear();
		
		//Y ahora trabajamos con un par que incluye como valor una coleccion
		
		Pair<String, ArrayList<Integer>> parColeccion = new Pair<>("pepe", new ArrayList<Integer>());
		parColeccion.getValue().add(3);
		parColeccion.getValue().add(5);
		parColeccion.getValue().add(7);
		
		assertEquals("pepe <[3, 5, 7]>", parColeccion.toString());
		

		//Que tal si lo hacemos de esta forma
		ArrayList<Integer> datos = new ArrayList<>();
		datos.add(4);
		datos.add(2);
		datos.add(1);
		parColeccion.setValue(datos);
		
		assertEquals("pepe <[4, 2, 1]>", parColeccion.toString()); //que ha pasado con el array que contenia a los valores 3, 5 y 7?
		
		datos.clear();
		
		assertEquals("pepe <[]>", parColeccion.toString()); //y ahora que?
		
		parColeccion.getValue().clear();
		
		listaPares = null;
		parColeccion = null;
		par1 = par2 = par3 = null;
		
	}
	
	
	@Test
	@Order(2)
	public void testSort() {		
		ArrayList<Pair<String, String>> lista = new ArrayList<>();
		lista.add(new Pair<>("pedra", Format.formatDouble(10)));
		lista.add(new Pair<>("juan", Format.formatDouble(10)));
		lista.add(new Pair<>("martina", Format.formatDouble(4)));
		lista.add(new Pair<>("amelia", Format.formatDouble(4)));

		//toString()
		assertEquals("[pedra <10.00>, juan <10.00>, martina <4.00>, amelia <4.00>]",lista.toString());
		
		//Ordenamos la lista: criterio = orden natural
		lista.sort(null);
		assertEquals("[amelia <4.00>, juan <10.00>, martina <4.00>, pedra <10.00>]", lista.toString());

		//Ordenamos la lista: criterio = orden natural (equivalente al anterior) 
		lista.sort(new Less<Pair<String, String>>());
		assertEquals("[amelia <4.00>, juan <10.00>, martina <4.00>, pedra <10.00>]", lista.toString());
		
		//Ordenamos la lista: criterio = orden natural inverso
		lista.sort(new Greater<Pair<String, String>>());
		assertEquals("[pedra <10.00>, martina <4.00>, juan <10.00>, amelia <4.00>]",lista.toString());
		
		lista.clear();
		lista = null;
	}
	
	@Test
	@Order(3)
	public void testLibro() {
		Libro libro1 = new Libro("Libro01");
		Libro libro2 = new Libro("Libro02");
		
		//toString() con arbol de palabra vacio
		assertEquals("libro01 -> [empty]", libro1.toString());
		assertEquals("libro02 -> [empty]", libro2.toString());
		
		//Insertamos palabas en book1
		libro1.add("adios", "mundo", "cruel", "adios");
		
		//toString()
		assertEquals("libro01 -> [adios <2>, cruel <1>, mundo <1>]", libro1.toString());

		//Insertamos palabras en book2
		libro2.add("hola", "mundo", "maravilloso", "adios", "mundo", "adios");
		
		//toString()
		assertEquals("libro02 -> [adios <2>, hola <1>, maravilloso <1>, mundo <2>]", libro2.toString());
	
		//equals() y compareTo()
		assertNotEquals(libro1, libro2);
		assertEquals(libro2, new Libro("   libRO02     "));
		assertTrue(libro1.compareTo(libro2) < 0);
		assertTrue(libro1.compareTo(new Libro("libro01")) == 0);
		assertTrue(libro1.compareTo(new Libro("libro00")) > 0);

		//Iterar sobre un libro equivale a iterar sobre los objetos FrecuenciaPalabra que contiene --> atencion al iterador de Libro
		int cont = 0;
		ArrayList<String> salidaEsperada = new ArrayList<String>();
		
		salidaEsperada.add("adios <2>");
		salidaEsperada.add("cruel <1>");
		salidaEsperada.add("mundo <1>");
		
		for (Pair<String, Integer> wF : libro1) {
			assertEquals(salidaEsperada.get(cont++), wF.toString());
		}
		
		salidaEsperada.clear();
		salidaEsperada.add("adios <2>");
		salidaEsperada.add("hola <1>");
		salidaEsperada.add("maravilloso <1>");
		salidaEsperada.add("mundo <2>");
	
		cont = 0;
		for (Pair<String, Integer> wF : libro2) {
			assertEquals(salidaEsperada.get(cont++), wF.toString());
		}
		
		salidaEsperada.clear();
		libro1.clear();
		libro2.clear();
		salidaEsperada = null;
		libro1 = libro2 = null;
	}
	
	@Test
	@Order(4)
	public void testUsuarioLibro() {
		Usuario usuario1 = new Usuario("Pedra");
		Usuario usuario2 = new Usuario("Martina");
		Usuario usuario3 = new Usuario("Juan");
		Usuario usuario4 = new Usuario("Juan");
		Libro libro1 = new Libro("libro001");
		Libro libro2 = new Libro("libro002");
		
		//toString()
		assertEquals("pedra", usuario1.toString());
		assertEquals("martina", usuario2.toString());
		assertEquals("juan", usuario3.toString());
		assertEquals("juan", usuario4.toString());
	
		//equals() y compareTo()
		assertEquals(usuario3, usuario4);
		assertTrue(usuario1.compareTo(usuario3) > 0);
		assertTrue(usuario2.compareTo(usuario3) > 0);
		assertTrue(usuario3.compareTo(usuario4) == 0);
		
		//toString(): libros con palabras...
		libro1.add("hola", "mundo", "mundo", "mundo", "hola");
		libro2.add("adios", "mundo", "mundo", "mundo");
		
		assertEquals("libro001 -> [hola <2>, mundo <3>]", libro1.toString());
		assertEquals("libro002 -> [adios <1>, mundo <3>]", libro2.toString());
		
		libro1.clear();
		libro2.clear();
		
		assertEquals("libro001 -> [empty]", libro1.toString());
		assertEquals("libro002 -> [empty]", libro2.toString());
		
		libro1 = libro2 = null;
		usuario1 = usuario2 = usuario3 = usuario4 = null;
	}
	
	@Test
	@Order(5)
	public void testBiblioteca() {
		Biblioteca biblioteca = new Biblioteca("Biblioteca UAL");
		final int NUM_LIBROS = 5;
		final int NUM_USUARIOS = 2;
		
		biblioteca.addLibro("l1");
		biblioteca.addLibro("l2");
		biblioteca.addLibro("l3");
		biblioteca.addLibro("l4");
		biblioteca.addLibro("l5");
		biblioteca.addUsuario("u1");
		biblioteca.addUsuario("u2");
	
		
		//toString()
		assertEquals("Biblioteca UAL (" + NUM_LIBROS + " libros y " + NUM_USUARIOS + " usuarios)", biblioteca.toString());
		
		//prestarLibro() y devolverLibro()
		assertTrue(biblioteca.prestarLibro("u1", "l1"));
		assertFalse(biblioteca.prestarLibro("u2", "l1")); //devuelve false: el libro ya esta prestado
		assertFalse(biblioteca.prestarLibro("u01", "l1"));  //devuelve false: no existe usuario u01
		assertTrue(biblioteca.prestarLibro("u1", "l2"));
		assertTrue(biblioteca.devolverLibro("u1", "l1"));
		assertTrue(biblioteca.prestarLibro("u1",  "l1"));
		assertTrue(biblioteca.devolverLibro("u1",  "l1"));
		assertFalse(biblioteca.devolverLibro("u1", "l1")); //devuelve false: el libro no esta prestado
		assertFalse(biblioteca.devolverLibro("u2", "l1")); //devuelve false: el libro no esta prestado
		assertTrue(biblioteca.devolverLibro("u1", "l2"));
		assertFalse(biblioteca.prestarLibro("u2", "libro3"));	//devuelve false: No existe libro3
		assertTrue(biblioteca.prestarLibro("u2", "l1"));
		assertTrue(biblioteca.prestarLibro("u2", "l2"));
		assertTrue(biblioteca.prestarLibro("u2", "l3"));
		assertTrue(biblioteca.prestarLibro("u2", "l4"));
		assertTrue(biblioteca.devolverLibro("u2", "l1"));
		assertTrue(biblioteca.devolverLibro("u2", "l2"));
		assertTrue(biblioteca.devolverLibro("u2", "l3"));
		assertTrue(biblioteca.devolverLibro("u2", "l4"));
		assertTrue(biblioteca.prestarLibro("u2", "l1"));
		assertTrue(biblioteca.devolverLibro("u2", "l1"));
		
		//getUsuariosPrestamo()
		assertEquals("[u1, u2]",biblioteca.getUsuariosLibro("l1").toString());
		assertEquals("[u1, u2]", biblioteca.getUsuariosLibro("l2").toString());
		assertEquals("[u2]", biblioteca.getUsuariosLibro("l3").toString());
		assertEquals("[u2]", biblioteca.getUsuariosLibro("l4").toString());
		assertEquals("[]", biblioteca.getUsuariosLibro("l5").toString());
		assertTrue(biblioteca.getUsuariosLibro("l5").size() == 0);
		assertTrue(biblioteca.getUsuariosLibro("l25") == null); //no existe este libro
		
		
		//getLibrosPrestados(usuario)
		assertEquals("[l1, l2, l1]", biblioteca.getLibrosPrestados("u1").toString());
		assertEquals("[l1, l2, l3, l4, l1]", biblioteca.getLibrosPrestados("u2").toString());
		assertTrue(biblioteca.getLibrosPrestados("user03") == null);
		

		//getLibrosPrestados
		assertEquals("[]", biblioteca.getLibrosPrestados().toString());
		assertTrue(biblioteca.getLibrosPrestados().isEmpty());
		
		assertTrue(biblioteca.prestarLibro("u2", "l1"));
		assertTrue(biblioteca.prestarLibro("u1", "l3"));
		
		assertEquals("[l1, l3]", biblioteca.getLibrosPrestados().toString());
		
		assertTrue(biblioteca.devolverLibro("u2", "l1"));
		assertTrue(biblioteca.devolverLibro("u1", "l3"));
		
		assertEquals("[]", biblioteca.getLibrosPrestados().toString());
		
		
		//Volvemos a prestar; libros pares a u1 e impares a u2
		for (int i=1; i <= NUM_LIBROS; i++) {
			biblioteca.prestarLibro("u" + (i%2==0?1:2), "l" + i);			
		}
		
		assertEquals("[l1, l2, l3, l4, l5]", biblioteca.getLibrosPrestados().toString());
		assertEquals("[l1, l2, l1, l3, l2, l4]", biblioteca.getLibrosPrestados("u1").toString());
		assertEquals("[l1, l2, l3, l4, l1, l1, l1, l3, l5]", biblioteca.getLibrosPrestados("u2").toString());
		
		//Devolvemos solo los pares
		for (int i=1; i <= NUM_LIBROS; i++) {
			if (i%2 == 0) continue;
			assertTrue(biblioteca.devolverLibro("u2", "l" + i));			
		}

		assertEquals("[l2, l4]", biblioteca.getLibrosPrestados().toString());
		
		assertTrue(biblioteca.devolverLibro("u1",  "l2"));
		assertTrue(biblioteca.devolverLibro("u1",  "l4"));
		
		assertEquals("[l1, l2, l1, l3, l2, l4]", biblioteca.getLibrosPrestados("u1").toString());
		assertEquals("[l1, l2, l3, l4, l1, l1, l1, l3, l5]", biblioteca.getLibrosPrestados("u2").toString());
		assertTrue(biblioteca.getLibrosPrestados().isEmpty());
		
		biblioteca.clear();
		biblioteca = null;
	}
	
	@Test
	@Order(6)
	public void tesLoad() {
		Biblioteca biblioteca = new Biblioteca("Picasso");
		Usuario usuario1 = new Usuario("Luisa");
		Usuario usuario2 = new Usuario("Carmen");
		Usuario usuario3 = new Usuario("Juan");
		Usuario usuario4 = new Usuario("Pedro");
		Libro libro1 = new Libro("El bosque de los cuatro vientos");
		Libro libro2 = new Libro("Y Julia reto a los dioses");
		Libro libro3 = new Libro("Las alas de Sophie");
		Libro libro4 = new Libro("La madre de Frankestein");
		
		libro1.load(directorioEntrada + "archivo1");
		libro2.load(directorioEntrada + "archivo2");
		libro2.load(directorioEntrada + "archivo2");
		libro3.load(directorioEntrada + "archivo3");
		libro4.load(directorioEntrada + "archivo3");
		libro4.load(directorioEntrada + "archivo4");

		biblioteca.addLibro(libro1);
		biblioteca.addLibro(libro2);
		biblioteca.addLibro(libro3);
		biblioteca.addLibro(libro4);
		biblioteca.addUsuario(usuario1);
		biblioteca.addUsuario(usuario2);
		biblioteca.addUsuario(usuario3);
		biblioteca.addUsuario(usuario4);
		
		assertEquals("Picasso (4 libros y 4 usuarios)", biblioteca.toString());
		
		assertTrue(biblioteca.prestarLibro("Luisa", "La madre de Frankestein"));
		assertTrue(biblioteca.prestarLibro("Juan",  "El bosque de los cuatro vientos"));
		assertTrue(biblioteca.prestarLibro("Pedro", "Las alas de Sophie"));
		assertTrue(biblioteca.prestarLibro("Carmen", "Y Julia reto a los dioses"));
		assertTrue(biblioteca.devolverLibro("Luisa", "La madre de Frankestein"));
		assertTrue(biblioteca.prestarLibro("Pedro", "La madre de Frankestein"));
		assertTrue(biblioteca.devolverLibro("Carmen", "Y Julia reto a los dioses"));
		assertTrue(biblioteca.prestarLibro("Juan", "Y Julia reto a los dioses"));
		assertTrue(biblioteca.devolverLibro("Juan", "Y Julia reto a los dioses"));
		assertTrue(biblioteca.prestarLibro("Pedro", "Y Julia reto a los dioses"));
		assertTrue(biblioteca.devolverLibro("Pedro", "La madre de Frankestein"));
		assertTrue(biblioteca.prestarLibro("Luisa", "La madre de Frankestein"));
		assertTrue(biblioteca.devolverLibro("Luisa", "La madre de Frankestein"));
		
		assertTrue(biblioteca.getLibrosPrestados().size() == 3);
		assertEquals("[el bosque de los cuatro vientos, las alas de sophie, y julia reto a los dioses]", biblioteca.getLibrosPrestados().toString());
		
		
		assertEquals("[la madre de frankestein, la madre de frankestein]", biblioteca.getLibrosPrestados("Luisa").toString());
		assertEquals("[las alas de sophie, la madre de frankestein, y julia reto a los dioses]", biblioteca.getLibrosPrestados("Pedro").toString());
		assertEquals("[el bosque de los cuatro vientos, y julia reto a los dioses]", biblioteca.getLibrosPrestados("Juan").toString());
		
		assertEquals("[juan]", biblioteca.getUsuariosLibro("El bosque de los cuatro vientos").toString());
		assertEquals("[luisa, pedro]", biblioteca.getUsuariosLibro("La madre de Frankestein").toString());
		
		
		
			
		biblioteca.clear();
		libro1.clear();
		libro2.clear();
		libro3.clear();
		libro4.clear();
		usuario1 = usuario2 = usuario3 = usuario4 = null;
		libro1 = libro2 = libro3 = libro4 = null;
	}
}