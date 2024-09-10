package eda1.practica03.parte01;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

import java.io.File;
import java.io.IOException;

public class GestionEmpresas {

	private TreeMap<String, TreeMap<String, TreeSet<String>>> datos = new TreeMap<>();

	public void load(String file) {
		Scanner scan = null;
		String line;
		String[] items;
		this.datos.clear(); 
		try {
			scan = new Scanner(new File(file));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		while (scan.hasNextLine()) {
			line = scan.nextLine().trim();
			if (line.isEmpty()) continue;
			if (line.startsWith("@")) continue;
			items = line.split(" - ");
			add(items[0], items[1], items[2]);
		}
		scan.close();
	}

	public boolean add(String empresaID, String proyectoID, String ciudad) {
		TreeMap<String, TreeSet<String>> proyAux = datos.get(empresaID);
		if (proyAux == null)
			datos.put(empresaID, proyAux = new TreeMap<>());
		TreeSet<String> ciudadesAux = proyAux.get(proyectoID);
		if (ciudadesAux == null)
			proyAux.put(proyectoID, ciudadesAux = new TreeSet<>());
		return ciudadesAux.add(ciudad);
	}

	public int size() {
		return this.datos.size();
	}

	public int numeroProyectosEmpresa(String empresaID) {
		TreeMap<String, TreeSet<String>> proyectoCurr = datos.get(empresaID);
		if (proyectoCurr == null) return -1;
		return proyectoCurr.size();
	}

	public int numeroCiudadesProyecto(String proyectoID) {
		for (Entry<String, TreeMap<String, TreeSet<String>>> empresa : datos.entrySet()) {
			if (empresa.getValue().containsKey(proyectoID))
				return datos.get(empresa.getKey()).get(proyectoID).size();
		}
		return -1;
	}

	public int numeroCiudadesEmpresa(String empresaID) {
		TreeSet<String> ciudades = new TreeSet<>();
		TreeMap<String, TreeSet<String>> empresaCurr = datos.get(empresaID);
		if (empresaCurr == null) return -1;
		for (TreeSet<String> proyecto : empresaCurr.values())
			ciudades.addAll(proyecto);
		return ciudades.size();
	}

	@Override
	public String toString() {
		String aux = "";
		for (Entry<String, TreeMap<String, TreeSet<String>>> proyAux : datos.entrySet()) {
			aux += proyAux.getKey() + " -> [";
			for (Entry<String, TreeSet<String>> ciudadAux : proyAux.getValue().entrySet())
				aux += ciudadAux.getKey() + ": " + ciudadAux.getValue().toString() + (proyAux.getValue().lastEntry().equals(ciudadAux) ? "]" : ", ");
			aux += "\n";
		}
		return aux;
	}

	public String devolverEmpresasCiudad(String ciudad) {
		TreeSet<String> empresasIDaux = new TreeSet<>();
		for (Entry<String, TreeMap<String, TreeSet<String>>> empresa : datos.entrySet()) {
			for (TreeSet<String> proyecto : empresa.getValue().values()) {
				if (proyecto.contains(ciudad)) {
					empresasIDaux.add(empresa.getKey());
					break;
				}
			}
		}
		return empresasIDaux.toString();
	}

	public String devolverProyectosCiudad(String ciudad) {
		ArrayList<String> proyectosIDaux = new ArrayList<>();
		for (Entry<String, TreeMap<String, TreeSet<String>>> empresa : datos.entrySet()) {
			for (Entry<String, TreeSet<String>> proyecto : empresa.getValue().entrySet()) {
				if (proyecto.getValue().contains(ciudad)) {
					proyectosIDaux.add(proyecto.getKey());
				}
			}
		}
		return proyectosIDaux.toString();
	}
	 
	public String devolverCiudadesEmpresa(String empresaID) {
		TreeSet<String> ciudades = null;
		 ciudades = new TreeSet<>();
		 TreeMap<String, TreeSet<String>> empresaCurr = datos.get(empresaID);
		 if (empresaCurr == null) return "[]";
		 for(Entry<String, TreeSet<String>> proyecto : empresaCurr.entrySet())
			 ciudades.addAll(proyecto.getValue());
		 return ciudades.toString();
	}
}