package eda1.practica02.parte01;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica02_01JUnit5 {

	String directorioEntrada = System.getProperty("user.dir") + File.separator +
							   "Practicas - src" + File.separator + 
							   "eda1" + File.separator + 
							   "practica02" + File.separator + 
							   "parte01" + File.separator;

	@Test
	@Order(1)
	public void testProyecto() {
		Proyecto proyecto = new Proyecto("   Proyecto01");
		
		assertEquals("proyecto01: <empty>", proyecto.toString());
		
		assertTrue(proyecto.add("ALMERIA"));
		assertFalse(proyecto.add("almeria")); //Independientemente de mayúsculas o minúsculas...si el elemento está repetido se rechaza, devolviendo false
		assertTrue(proyecto.add("zaragoza"));
		assertTrue(proyecto.add("barCelona"));
		
		
		assertEquals(proyecto, new Proyecto("Proyecto01   ")); //Independientemente de las ciudades, dos proyectos son iguales si sus nombres son exactamente iguales
		assertTrue(proyecto.compareTo(new Proyecto("PROYECTO01")) == 0);

		assertTrue(proyecto.compareTo(new Proyecto("Proyecto00")) > 0);
		assertTrue(proyecto.compareTo(new Proyecto("Proyecto29")) < 0);
		assertTrue(proyecto.compareTo(new Proyecto("ProyectoAlfa")) < 0);
		
		
		assertEquals(proyecto.toString(), "proyecto01: [almeria, zaragoza, barcelona]");
		
		assertTrue(proyecto.size() == 3);
		
		assertTrue(proyecto.remove("AlMeRiA"));
		assertFalse(proyecto.remove("granada"));
		assertFalse(proyecto.remove("Madrid"));
		
		assertTrue(proyecto.size() == 2);
		
		assertEquals("proyecto01: [zaragoza, barcelona]", proyecto.toString());
		proyecto.sort();
		assertEquals("proyecto01: [barcelona, zaragoza]", proyecto.toString());
		
		proyecto.clear();
		assertTrue(proyecto.size() == 0);
		assertEquals("proyecto01: <empty>", proyecto.toString());
		
		proyecto = null;
	}
	
	@Test
	@Order(2)
	public void testEmpresa() {
		Empresa empresa = new Empresa("    Empresa01    ");
		
		assertEquals("empresa01 -> <empty>", empresa.toString());

		empresa.add("Proyecto02", "jaen", "madrid", "sevilla");
		empresa.add("Proyecto01", "almeria");
		empresa.add("Proyecto01", "jAEn");
		empresa.add("Proyecto01", "ZaRaGoza", "maDRid", "jaEn", "madrid");
		
		assertEquals(empresa.toString(), "empresa01 -> [proyecto02: [jaen, madrid, sevilla], proyecto01: [almeria, jaen, zaragoza, madrid]]");
		
		assertTrue(empresa.compareTo(new Empresa("EmpreSa00")) > 0);
		assertTrue(empresa.compareTo(new Empresa("EmprESa01")) == 0);
		assertTrue(empresa.compareTo(new Empresa("Empresa02")) < 0);

		assertNull(empresa.find("   Proyecto00"));
		
		empresa.find("Proyecto02").add("zarAGoza");
		
		assertEquals(empresa.find("Proyecto02   ").toString(), "proyecto02: [jaen, madrid, sevilla, zaragoza]");
		
		empresa.find("Proyecto01").remove("almeRia");
		
		assertEquals(empresa.find("Proyecto01").toString(), "proyecto01: [jaen, zaragoza, madrid]");
		
		empresa.find("Proyecto02").remove("zarAGoza");
		empresa.find("Proyecto02").remove("jaEN");
		empresa.find("Proyecto02").remove("MADrid");
		empresa.find("Proyecto02").remove("sevilla");

		assertEquals("empresa01 -> [proyecto02: <empty>, proyecto01: [jaen, zaragoza, madrid]]", empresa.toString());
		empresa.sort();
		assertEquals("empresa01 -> [proyecto01: [jaen, madrid, zaragoza], proyecto02: <empty>]", empresa.toString());
	
		empresa.clear();
		assertTrue(empresa.size() == 0);
		
		empresa = null;
	}
	
	@Test
	@Order(3)
	public void testGestionEmpresas() {

		GestionEmpresas empresas = new GestionEmpresas();
		
		String archivoEntrada = directorioEntrada + "datos.txt";
				
		empresas.cargarArchivo(archivoEntrada);
		assertTrue(empresas.size() == 6); //6 empresas
		empresas.cargarArchivo(archivoEntrada); //Cada vez que cargo archivo, se debe vaciar el árbol de empresas
		assertTrue(empresas.size() == 6);
		empresas.sort();
		
		assertEquals("empresa01 -> [proyecto01_01: [ciudad01, ciudad04, ciudad14], proyecto01_02: [ciudad07, ciudad09, ciudad12], proyecto01_03: [ciudad08, ciudad17, ciudad19]]\n" +
					 "empresa02 -> [proyecto02_01: [ciudad01, ciudad02, ciudad03, ciudad04], proyecto02_02: [ciudad04, ciudad05, ciudad06, ciudad07, ciudad08, ciudad09, ciudad10], proyecto02_03: [ciudad02, ciudad03, ciudad05, ciudad09, ciudad11, ciudad12]]\n" +
					 "empresa03 -> [proyecto03_01: [ciudad04, ciudad06, ciudad07, ciudad10, ciudad15], proyecto03_02: [ciudad01, ciudad07, ciudad09, ciudad11, ciudad13], proyecto03_03: [ciudad05, ciudad09, ciudad16, ciudad18]]\n" +
					 "empresa04 -> [proyecto04_01: [ciudad02, ciudad03, ciudad07, ciudad10], proyecto04_02: [ciudad01, ciudad03, ciudad04, ciudad05, ciudad09], proyecto04_03: [ciudad01, ciudad04, ciudad06, ciudad09, ciudad13], proyecto04_04: [ciudad03, ciudad04, ciudad05, ciudad09, ciudad10], proyecto04_05: [ciudad03, ciudad04, ciudad07, ciudad09, ciudad13]]\n" +
					 "empresa05 -> [proyecto05_01: [ciudad02, ciudad04, ciudad09, ciudad13, ciudad18], proyecto05_02: [ciudad01, ciudad03, ciudad04, ciudad05, ciudad06, ciudad07, ciudad09]]\n" +
					 "empresa06 -> [proyecto06_01: [ciudad03, ciudad04, ciudad10]]\n", 
					  empresas.toString());
		
		
		assertTrue(empresas.numeroProyectosEmpresa("empresa05") == 2);
		assertTrue(empresas.numeroCiudadesEmpresa("empresa04") == 10);
 		assertTrue(empresas.numeroCiudadesProyecto("proyecto03_03") == 4);

 		assertTrue(empresas.numeroProyectosEmpresa("empresa05s") == -1);
		assertTrue(empresas.numeroCiudadesProyecto("jWork") == -1);
		assertTrue(empresas.numeroCiudadesEmpresa("Macrosoft") == -1);

		assertEquals("[empresa01, empresa02, empresa03, empresa04, empresa05]", empresas.devolverEmpresasCiudad("ciudad09").toString());
	
	
		
		assertEquals("[proyecto01_01, proyecto02_01, proyecto02_02, proyecto03_01, proyecto04_02, proyecto04_03, proyecto04_04, proyecto04_05, proyecto05_01, proyecto05_02, proyecto06_01]", 
   				      empresas.devolverProyectosCiudad("ciudad04"));


		assertEquals("[ciudad01, ciudad04, ciudad14, ciudad07, ciudad09, ciudad12, ciudad08, ciudad17, ciudad19]", empresas.devolverCiudadesEmpresa("empresa01"));
		

		assertEquals("<empty>", empresas.devolverCiudadesEmpresa("empresa25"));

		assertEquals("<empty>", empresas.devolverEmpresasCiudad("ciudad09s"));

		assertEquals("<empty>", empresas.devolverProyectosCiudad("ciudad24"));
	}
}