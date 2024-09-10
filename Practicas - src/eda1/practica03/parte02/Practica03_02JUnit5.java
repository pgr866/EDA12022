package eda1.practica03.parte02;

import static org.junit.Assert.*;

import java.io.File;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica03_02JUnit5 {

	String directorioEntrada = System.getProperty("user.dir") + File.separator +
								"Practicas - src" + File.separator +
								"eda1" + File.separator +
								"practica03" + File.separator +
								"parte02" + File.separator;
	
	@Test
	@Order(1)
	public void testArticuloBasico() {
		Articulo articulo01 = new Articulo("titulo01");
		Articulo articulo02 = new Articulo("titulo02");
		Articulo articulo03 = null;
		
		//Metodo add()
		articulo01.add("Este es un ejemplo de articulo", "compuesto por lineas", "compuesto por palabras", "compuesto por letras");
		articulo01.add("articulo", "lineas", "palabras", "letras");
		articulo02.add("Este es otro ejemplo de articulo", "compuesto por lineas", "compuesto por palabras", "compuesto por letras");
		articulo01.add("articulo", "lineas");
		articulo02.add("y mas lineas");
		
		assertEquals("titulo01: {articulo=3, compuesto=3, ejemplo=1, letras=2, lineas=3, palabras=2}", articulo01.toString());
		assertEquals("titulo02: {articulo=1, compuesto=3, ejemplo=1, letras=1, lineas=2, mas=1, palabras=1}", articulo02.toString()); 
		
		//Metodos getFrecuencia() y getSumaFrecuencias()
		assertTrue(articulo01.getFrecuencia("lineas") == 3);
		assertTrue(articulo01.getFrecuencia("linea") == -1);
		assertTrue(articulo02.getFrecuencia("compuESto  ") == 3);
		assertTrue(articulo01.getFrecuencia(" ejemplo ") == articulo02.getFrecuencia(" EJEMPlo "));
		assertTrue(articulo01.getSumaFrecuencias() == 14);
		assertTrue(articulo02.getSumaFrecuencias() == 10);
	
		//Metodo fusionar()
		articulo03 = articulo01.fusionar(articulo02);
		assertEquals("titulo01+titulo02: {articulo=4, compuesto=6, ejemplo=2, letras=3, lineas=5, mas=1, palabras=3}", articulo03.toString());
		articulo02.add("hola", "hola", "HOLA");
		
		articulo03 = articulo01.fusionar(articulo02);
		assertEquals("titulo01+titulo02: {articulo=4, compuesto=6, ejemplo=2, hola=3, letras=3, lineas=5, mas=1, palabras=3}", articulo03.toString());
		
		//getSumaFrecuencias()
		assertTrue(articulo01.getSumaFrecuencias() == 14);
		assertTrue(articulo02.getSumaFrecuencias() == 13);
		assertTrue(articulo03.getSumaFrecuencias() == 27);
		
		
		articulo01.clear();
		articulo02.clear();
		articulo03.clear();
		
		assertTrue(articulo01.size() == 0);
		assertTrue(articulo02.size() == 0);
		assertTrue(articulo03.size() == 0);
		
		articulo01 = articulo02= articulo03 = null;
	}

	@Test
	@Order(2)
	public void testArticuloLoad() {
		Articulo articulo01 = new Articulo("articuloID01");
		Articulo articulo02 = new Articulo("articuloID02");
		
		articulo01.load(this.directorioEntrada + "articulo01");
		articulo02.load(this.directorioEntrada + "articulo02");
		
		assertTrue(articulo01.getSumaFrecuencias() == 295);
		assertTrue(articulo02.getSumaFrecuencias() == 133);
		
		//El metodo loadFile inicializa la estructura palabrasFrecuencias
		articulo01.load(this.directorioEntrada + "articulo01");
		articulo02.load(this.directorioEntrada + "articulo02");
		assertTrue(articulo01.getSumaFrecuencias() == 295);
		assertTrue(articulo02.getSumaFrecuencias() == 133);
		
		assertTrue(articulo01.getFrecuencia("caracteristicas") == 2);
		assertTrue(articulo02.getFrecuencia("algoritmos") == 2);
		
		//Si no existe la palabra, devuelve -1
		assertTrue(articulo01.getFrecuencia("eda") == -1);
		assertTrue(articulo02.getFrecuencia("eda") == -1);
		
		//Fusionamos articulo01 y articulo02
		Articulo articulo03 = articulo01.fusionar(articulo02);
		
		assertTrue(articulo03.getSumaFrecuencias() == 428);
		assertTrue(articulo01.getFrecuencia("decir") == 1);
		assertTrue(articulo01.getFrecuencia("decire") == -1);
	
		
		articulo01.clear();
		articulo02.clear();
		articulo03.clear();
		articulo01 = articulo02= articulo03 = null;
	}
	
		
	@Test
	@Order(3)
	public void testGestionRepositoriosBasico() {
		GestionRepositorios gestion = new GestionRepositorios();
		gestion.addArticulos("articulo01", "articulo02", "articulo03", "articulo04", "articulo04");
		assertTrue(gestion.add("repositorio01", "articulo01", "Pepe", "Maria", "Juan"));
		assertTrue(gestion.add("repositorio02", "articulo02", "Pepe", "Maria", "Juan"));
		assertTrue(gestion.add("repositorio01",  "articulo03", "Adela", "Maria", "Zacarias"));
		assertTrue(gestion.add("repositorio02",  "articulo04", "Adela", "Juan"));
		assertFalse(gestion.add("repositorio01", "articulo20",  "Manuel", "Zacarias", "Juan"));
		
		assertEquals("{repositorio01={Adela=[articulo03: {}], Juan=[articulo01: {}], " +
				                     "Maria=[articulo01: {}, articulo03: {}], " +
				                     "Pepe=[articulo01: {}], Zacarias=[articulo03: {}]}, " +
				      "repositorio02={Adela=[articulo04: {}], " +
				                     "Juan=[articulo02: {}, articulo04: {}], "+ 
				                     "Maria=[articulo02: {}], " + 
				                     "Pepe=[articulo02: {}]}}", gestion.toString());
		
		assertEquals("[articulo01, articulo02]", gestion.getArticulosID("Pepe").toString());
		assertEquals("[articulo03, articulo04]", gestion.getArticulosID("Adela").toString());
		assertEquals("[]", gestion.getArticulosID("maria").toString());
		assertEquals("[articulo01, articulo02, articulo03]", gestion.getArticulosID("Maria").toString());
		assertEquals("[articulo01, articulo02, articulo04]", gestion.getArticulosID("Juan").toString());
		assertEquals("[articulo03]", gestion.getArticulosID("Zacarias").toString());
		assertEquals("[Adela, Maria]", gestion.getCoAutores("Zacarias").toString());
		assertEquals("[Adela, Maria, Pepe]", gestion.getCoAutores("Juan").toString());
		assertEquals("[Juan, Maria]", gestion.getCoAutores("Pepe").toString());
		assertEquals("[Juan, Maria, Zacarias]",gestion.getCoAutores("Adela").toString());
	
		gestion.clear();
		gestion = null;
	}
	
	@Test
	@Order(4)
	public void testGestionRepositoriosLoad() {
		GestionRepositorios gestion = new GestionRepositorios();
		assertTrue(gestion.load(directorioEntrada, "datosArticulos", "datosRepositorios"));
		assertTrue(gestion.size() == 3);
		assertTrue(gestion.load(directorioEntrada, "datosArticulos", "datosRepositorios"));
		assertTrue(gestion.size() == 3);
			
		
		assertEquals("[titulo01_01, titulo01_04, titulo03_01, titulo03_04]", gestion.getArticulosID("Juan").toString());
		assertEquals("[titulo01_02, titulo01_03, titulo02_02, titulo02_03, titulo03_02, titulo03_03]", gestion.getArticulosID("Manuela").toString());
		assertEquals("[]", gestion.getArticulosID("teresa").toString());

		assertEquals("[Alberto, Alejandro]", gestion.getCoAutores("Manuela").toString());
		assertEquals("[Alberto, Alejandro, Juan, Macarena]", gestion.getCoAutores("Pedra").toString());
		assertEquals("[]", gestion.getCoAutores("Manuel").toString());
		
		assertEquals("[algoritmo=7, algoritmos=8, base=7, clasificacion=9, como=7, datos=24, mineria=9, oa=16, tecnicas=9]", 
				      gestion.getPalabrasClave("Pedra", 7).toString());
		
		assertTrue(gestion.getPalabrasClave("Pedra", 6).size() == 15);
		assertTrue(gestion.getPalabrasClave("Pedra", 1).size() == 365);
		assertTrue(gestion.getPalabrasClave("pedra", 1).size() == 0);
		
		
		gestion.clear();
		gestion = null;
	}
}