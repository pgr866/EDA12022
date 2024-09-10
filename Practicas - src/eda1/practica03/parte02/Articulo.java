package eda1.practica03.parte02;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Articulo implements Comparable<Articulo>, Iterable<Entry<String, Integer>> {
	private String articuloID;
	private TreeMap<String, Integer> palabrasFrecuencias;
	
	public Articulo(String articuloID) {
		this.articuloID = articuloID.toLowerCase().trim();
		this.palabrasFrecuencias =  new TreeMap<>();
	}
	
	public String getArticuloID() {
		return this.articuloID;
	}
	
	public int size() {
		return palabrasFrecuencias.size();
	}
	
	public void clear() {
		this.palabrasFrecuencias.clear();
	}
	
	public int getFrecuencia(String palabra) {
		Integer freq = palabrasFrecuencias.get(palabra.trim().toLowerCase());
		return freq == null ? -1 : freq;
	}
	
	public int getSumaFrecuencias() {
		int suma = 0;
		for (int freq : palabrasFrecuencias.values())
			suma += freq;
		return suma;
	}
	
	public void load(String file) {
		Scanner scan = null;
		this.palabrasFrecuencias.clear();
		try {
			scan = new Scanner(new File(file));
		}catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		while (scan.hasNextLine()){
			this.add(scan.nextLine());
		}
	}
	
	public void add(String...lineas) {
		Integer cont = null;
		if (lineas == null)	return;
		for (String linea : lineas) {
			for (String palabra : linea.split("[0123456789/(/)+-;,.¿?¡! ]+")) {
				palabra = palabra.trim().toLowerCase();
				if (palabra.isEmpty() || Auxiliar.isStopWord(palabra))	continue;
				cont = palabrasFrecuencias.get(palabra);
				palabrasFrecuencias.put(palabra, (cont == null ? 1 : ++cont));
			}
		}
	}

	
	public Articulo fusionar(Articulo otro) {
		Articulo aux = new Articulo(this.articuloID + "+" + otro.articuloID);
		Integer cont = null;
		aux.palabrasFrecuencias.putAll(this.palabrasFrecuencias);
		for (String palabra : otro.palabrasFrecuencias.keySet()) {
			cont = otro.getFrecuencia(palabra) + (aux.getFrecuencia(palabra) > 0 ? aux.getFrecuencia(palabra) : 0);
			aux.palabrasFrecuencias.put(palabra, cont);
		}
		return aux;
	}
	
	@Override
	public String toString() {
		return articuloID + ": " + palabrasFrecuencias.toString();
	}
	
	@Override
	public Iterator<Entry<String, Integer>> iterator() {
		return palabrasFrecuencias.entrySet().iterator();
	}
	
	@Override
	public int compareTo(Articulo otro) {
		return this.articuloID.compareTo(otro.articuloID);
	}
}