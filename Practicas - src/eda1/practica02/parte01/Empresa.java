package eda1.practica02.parte01;

import java.util.ArrayList;
import java.util.Iterator;

public class Empresa implements Comparable<Empresa>, Iterable<Proyecto> {
	
	private String empresaID;
	private ArrayList<Proyecto> proyectos;
	
	
	public Empresa(String empresaID) {
		this.empresaID = empresaID.trim().toLowerCase();
		this.proyectos = new ArrayList<>();
	}

	public boolean add(String proyectoID, String...ciudades) {
		Proyecto proyectoAux = new Proyecto(proyectoID);
		Proyecto proyectoCurr = null;
		int pos = proyectos.indexOf(proyectoAux);
		if (pos == -1) {
			for (int i=0; i<ciudades.length; i++)
				proyectoAux.add(ciudades[i]);
			proyectos.add(proyectoAux);
		} else {
			proyectoCurr = proyectos.get(pos);
			for (int i=0; i<ciudades.length; i++)
				proyectoCurr.add(ciudades[i]);
			proyectos.set(pos, proyectoCurr);
		}
		return pos == -1;				
	}

	public String getEmpresaID(){
		return this.empresaID;
	}
	
	public int size() {
		return proyectos.size();
	}
	
	public Proyecto find(String proyectoId){
		int pos = 0;
		for (Proyecto proyecto : proyectos) {
			if (proyecto.getProyectoID().trim().toLowerCase().equals(proyectoId.trim().toLowerCase())) return proyectos.get(pos);
			pos++;
		}
		return null;
	}

	public void clear() {
		this.proyectos.clear();
	}
	
	public void sort() {
		for (Proyecto proyecto : proyectos)
			proyecto.sort();
		this.proyectos.sort(null);
	}
	
	@Override
	public String toString() {
		return (proyectos.isEmpty() ? empresaID + " -> <empty>" : empresaID + " -> " + proyectos.toString());
	}

	@Override
	public boolean equals(Object o) {
		return this.compareTo((Empresa)o) == 0;
	}
	
	@Override
	public int compareTo(Empresa otra) {
		return this.empresaID.compareTo(otra.empresaID);
	}

	@Override
	public Iterator<Proyecto> iterator() {
		return proyectos.iterator();
	}
}