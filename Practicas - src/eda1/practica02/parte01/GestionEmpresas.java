package eda1.practica02.parte01;

import java.util.ArrayList;
import java.util.Scanner;

import eda1.auxiliar.AVLTree;

import java.io.File;
import java.io.IOException;

public class GestionEmpresas {

	private AVLTree<Empresa> empresas = new AVLTree<Empresa>();

	public void cargarArchivo(String file) {
		Scanner scan = null;
		String line;
		String[] items;
		this.empresas.clear();
		try {
			scan = new Scanner(new File(file));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		while (scan.hasNextLine()) {
			line = scan.nextLine().trim();
			if (line.isEmpty() || line.trim().charAt(0) == '@') continue;
			items = line.trim().split(" - ");
			add(items[0], items[1], items[2]);
		}
		scan.close();
	}
	
	public void sort() {
		for (Empresa empresa : empresas)
			empresa.sort();
	}

	public boolean add(String empresaID, String proyectoID, String ciudad) {
		Empresa empresaAux = new Empresa(empresaID);
		Empresa empresaCurr = null;
		if (!empresas.contains(empresaAux)) {
			empresaAux.add(proyectoID, ciudad);
			empresas.add(empresaAux);
		} else {
			empresaCurr = empresas.find(empresaAux);
			empresaCurr.add(proyectoID, ciudad);
			empresas.remove(empresaCurr);
			empresas.add(empresaCurr);
		}
		return empresaCurr == null;
	}

	public int size() {
		return this.empresas.size();
	}

	public int numeroProyectosEmpresa(String empresaID) {
		Empresa empresaAux = new Empresa(empresaID);
		Empresa empresaCurr = empresas.find(empresaAux);
		if (empresaCurr == null) return -1;
		return empresaCurr.size();
	}

	public int numeroCiudadesProyecto(String proyectoID) {
		Proyecto proyectoCurr = null;
		for (Empresa empresa : empresas) {
			proyectoCurr = empresa.find(proyectoID);
			if (proyectoCurr != null) return proyectoCurr.size();
		}
		return -1;
	}

	public int numeroCiudadesEmpresa(String empresaID) {
		ArrayList<String> ciudades = null;
		ciudades = new ArrayList<>();
		Empresa empresaAux = new Empresa(empresaID);
		Empresa empresaCurr = empresas.find(empresaAux);
		if (empresaCurr == null) return -1;
		for (Proyecto proyecto : empresaCurr) {
			for (String ciudad : proyecto)
				if (!ciudades.contains(ciudad)) ciudades.add(ciudad);
		}
		return ciudades.size();
	}

	@Override
	public String toString() {
		String cadena = "";
		for (Empresa empresa : empresas)
			cadena += empresa.toString() + "\n";
			return cadena;
	}

	public String devolverEmpresasCiudad(String ciudad) {
		ArrayList<String> empresasIDaux = new ArrayList<>();
		for (Empresa empresa : empresas) {
			for (Proyecto proyecto : empresa) {
				if (proyecto.contains(ciudad)) {
					empresasIDaux.add(empresa.getEmpresaID());
					break;
				}
			}
		}
		return empresasIDaux.isEmpty() ? "<empty>" : empresasIDaux.toString();
	}

	public String devolverProyectosCiudad(String ciudad) {
		ArrayList<String> proyectosIDaux = new ArrayList<>();
		for (Empresa empresa : empresas) {
			for (Proyecto proyecto : empresa)
				if (proyecto.contains(ciudad)) proyectosIDaux.add(proyecto.getProyectoID());
		}
		return proyectosIDaux.isEmpty() ? "<empty>" : proyectosIDaux.toString();
	}
	
	 public String devolverCiudadesEmpresa(String empresaID) {
		 ArrayList<String> ciudades = null;
		 Empresa empresaCurr = empresas.find(new Empresa(empresaID));
		 if (empresaCurr == null) return "<empty>";
		 ciudades = new ArrayList<>();
		 for (Proyecto proyecto : empresaCurr) {
			 for (String ciudad : proyecto)
				 ciudades.add(ciudad);
		 }
		 return ciudades.toString();
	}
}