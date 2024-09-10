package eda1.practica02.parte01;

import java.util.ArrayList;
import java.util.Iterator;

public class Proyecto implements Comparable<Proyecto>, Iterable<String> {
	private String proyectoID;
	private ArrayList<String> ciudades;
	
	public Proyecto(String proyectoID){
		this.proyectoID = proyectoID.trim().toLowerCase();
		ciudades = new ArrayList<String>();
	}
	
	public boolean add(String ciudad) {
		if (!ciudades.contains(ciudad.trim().toLowerCase())) {
			ciudades.add(ciudad.trim().toLowerCase());
			return true;
		} else
			return false;
	}
	
	public boolean remove(String ciudad) {
		return ciudades.remove(ciudad.trim().toLowerCase());
	}

	public String getProyectoID(){
		return this.proyectoID;
	}
	
	public int size() {
		return ciudades.size();
	}

	public void clear() {
		this.ciudades.clear();
	}
	
	public boolean contains(String ciudad){
		return this.ciudades.contains(ciudad.trim().toLowerCase());
	}
	
	public void sort() {
		this.ciudades.sort(null);
	}
	
	@Override
	public String toString(){
		return (ciudades.isEmpty() ? proyectoID + ": <empty>" : proyectoID + ": " + ciudades.toString());
	}
	
	@Override
	public boolean equals(Object o) {
		return this.compareTo((Proyecto)o) == 0;
	}
	
	@Override
	public int compareTo(Proyecto otro) {
		return this.proyectoID.compareTo(otro.proyectoID);
	}
	
	@Override
	public Iterator<String> iterator() {
		return ciudades.iterator();
	}
}