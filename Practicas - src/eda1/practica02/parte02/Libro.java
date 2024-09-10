package eda1.practica02.parte02;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import eda1.auxiliar.AVLTree;
import eda1.auxiliar.Pair;

public class Libro implements Comparable<Libro>, Iterable<Pair<String, Integer>>{
	private String libroID;
	private AVLTree<Pair<String, Integer>> palabrasFrecuencias;
	
	public Libro(String libroID) {
		this.libroID = libroID.trim().toLowerCase();
		this.palabrasFrecuencias = new AVLTree<>();
	}
	
	public String getLibroID() {
		return this.libroID;
	}
		
	public void clear() {
		this.palabrasFrecuencias.clear();
	}
		
	public void load(String fileName) {
		Scanner scan = null;
		String line = "";
		try {
			scan = new Scanner(new File(fileName));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		while (scan.hasNextLine()){
			line = scan.nextLine().trim();
			if (line.isEmpty()) continue;
		}
		scan.close();
	}
	
	public void add(String...palabras) {
		Pair<String, Integer> palabraFreqAux = null;
		Pair<String, Integer> palabraFreqCurrent = null;
		for (String palabra: palabras) {
			palabraFreqAux = new Pair<String, Integer>(palabra, 1);
			palabraFreqCurrent = palabrasFrecuencias.find(palabraFreqAux);
			if (palabraFreqCurrent == null)
				palabrasFrecuencias.add(palabraFreqAux);
			else
				palabraFreqCurrent.setValue(palabraFreqCurrent.getValue() + 1);
		}
	}
		
	@Override
	public String toString() {
		return libroID + " -> " + (palabrasFrecuencias.isEmpty() ? "[empty]" : palabrasFrecuencias.toString());
	}

	@Override
	public boolean equals(Object o) {
		return this.compareTo((Libro)o) == 0;
	}
	
	@Override
	public int compareTo(Libro o) {
		return this.libroID.toLowerCase().trim().compareTo(o.libroID.toLowerCase().trim());
	}
	
	@Override
	public Iterator<Pair<String, Integer>> iterator() {
		return this.palabrasFrecuencias.iterator();
	}
}