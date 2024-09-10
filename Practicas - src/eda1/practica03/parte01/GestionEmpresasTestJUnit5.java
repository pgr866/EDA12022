package eda1.practica03.parte01;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.jupiter.api.Test;


public class GestionEmpresasTestJUnit5 {

	String directorioEntrada = System.getProperty("user.dir") + File.separator +
							   "Practicas - src" + File.separator + 
							   "eda1" + File.separator + 
							   "practica03" + File.separator + 
							   "parte01" + File.separator;

	
	@Test
	public void testGestionEmpresas() {

		GestionEmpresas gestionEmpresas = new GestionEmpresas();
		
		String archivoEntrada = directorioEntrada + "datos.txt";
				
		gestionEmpresas.load(archivoEntrada);
		assertTrue(gestionEmpresas.size() == 6); //6 empresas
		gestionEmpresas.load(archivoEntrada); //Cada vez que cargo archivo, se debe vaciar la estructura 
		assertTrue(gestionEmpresas.size() == 6);
		assertEquals("empresa01 -> [proyecto01_01: [ciudad01, ciudad04, ciudad14], proyecto01_02: [ciudad07, ciudad09, ciudad12], proyecto01_03: [ciudad08, ciudad17, ciudad19]]\n" +
					 "empresa02 -> [proyecto02_01: [ciudad01, ciudad02, ciudad03, ciudad04], proyecto02_02: [ciudad04, ciudad05, ciudad06, ciudad07, ciudad08, ciudad09, ciudad10], proyecto02_03: [ciudad02, ciudad03, ciudad05, ciudad09, ciudad11, ciudad12]]\n" +
					 "empresa03 -> [proyecto03_01: [ciudad04, ciudad06, ciudad07, ciudad10, ciudad15], proyecto03_02: [ciudad01, ciudad07, ciudad09, ciudad11, ciudad13], proyecto03_03: [ciudad05, ciudad09, ciudad16, ciudad18]]\n" +
					 "empresa04 -> [proyecto04_01: [ciudad02, ciudad03, ciudad07, ciudad10], proyecto04_02: [ciudad01, ciudad03, ciudad04, ciudad05, ciudad09], proyecto04_03: [ciudad01, ciudad04, ciudad06, ciudad09, ciudad13], proyecto04_04: [ciudad03, ciudad04, ciudad05, ciudad09, ciudad10], proyecto04_05: [ciudad03, ciudad04, ciudad07, ciudad09, ciudad13]]\n" +
					 "empresa05 -> [proyecto05_01: [ciudad02, ciudad04, ciudad09, ciudad13, ciudad18], proyecto05_02: [ciudad01, ciudad03, ciudad04, ciudad05, ciudad06, ciudad07, ciudad09]]\n" +
					 "empresa06 -> [proyecto06_01: [ciudad03, ciudad04, ciudad10]]\n", 
					  gestionEmpresas.toString());
		
		assertTrue(gestionEmpresas.numeroProyectosEmpresa("empresa05") == 2);
		assertTrue(gestionEmpresas.numeroProyectosEmpresa("EmpresA05") == -1);
		assertTrue(gestionEmpresas.numeroCiudadesEmpresa("empresa04") == 10);
		assertTrue(gestionEmpresas.numeroCiudadesEmpresa("emPresa04") == -1);
 		assertTrue(gestionEmpresas.numeroCiudadesProyecto("proyecto03_03") == 4);
 		assertTrue(gestionEmpresas.numeroCiudadesProyecto("proyecto03_33") == -1);

 		assertTrue(gestionEmpresas.numeroProyectosEmpresa("empresa05s") == -1);
		assertTrue(gestionEmpresas.numeroCiudadesProyecto("jWork") == -1);
		assertTrue(gestionEmpresas.numeroCiudadesEmpresa("Macrosoft") == -1);

		assertEquals("[empresa01, empresa02, empresa03, empresa04, empresa05]", gestionEmpresas.devolverEmpresasCiudad("ciudad09").toString());
		assertEquals("[]", gestionEmpresas.devolverEmpresasCiudad("CiudaD09").toString());
	
	
		
		assertEquals("[proyecto01_01, proyecto02_01, proyecto02_02, proyecto03_01, proyecto04_02, proyecto04_03, proyecto04_04, proyecto04_05, proyecto05_01, proyecto05_02, proyecto06_01]", 
   				      gestionEmpresas.devolverProyectosCiudad("ciudad04"));


		assertEquals("[ciudad01, ciudad04, ciudad07, ciudad08, ciudad09, ciudad12, ciudad14, ciudad17, ciudad19]", gestionEmpresas.devolverCiudadesEmpresa("empresa01"));
		assertEquals("[ciudad01, ciudad02, ciudad03, ciudad04, ciudad05, ciudad06, ciudad07, ciudad09, ciudad13, ciudad18]", gestionEmpresas.devolverCiudadesEmpresa("empresa05"));
		
		assertEquals("[]", gestionEmpresas.devolverCiudadesEmpresa("empresa25"));

		assertEquals("[]", gestionEmpresas.devolverEmpresasCiudad("ciudad09s"));

		assertEquals("[]", gestionEmpresas.devolverProyectosCiudad("ciudad24"));
	}
}