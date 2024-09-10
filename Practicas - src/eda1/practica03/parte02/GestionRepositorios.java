 package eda1.practica03.parte02;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map.Entry;

import eda1.auxiliar.AVLTree;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class GestionRepositorios {
	private AVLTree<Articulo> articulos = null;
	private TreeMap<String, TreeMap<String, TreeSet<Articulo>>> datos = null;
	
	public GestionRepositorios() {
		this.articulos = new AVLTree<>();
		this.datos = new TreeMap<>();
	}
	
	public void addArticulos(String...articulosId) {
		for (String articuloId: articulosId) {
			articulos.add(new Articulo(articuloId));
		}
	}
	
	public boolean load(String directorioEntrada, String fileArticulos, String fileRepositorios) {
		Scanner scanArticulos = null;
		Scanner scanRepositorios = null;
		String lineIn;
		String[] items = null;
		String repositorioID = "";
		this.articulos.clear();
		this.datos.clear();
		
		try {
			scanArticulos= new Scanner(new File(directorioEntrada + fileArticulos));
			scanRepositorios= new Scanner(new File(directorioEntrada + fileRepositorios));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		while(scanArticulos.hasNextLine()){
			lineIn = scanArticulos.nextLine().trim();
			if (lineIn.isEmpty()) continue;
			if (lineIn.startsWith("%")) continue;
			items = lineIn.split(" ");
			Articulo articulo = new Articulo(items[0]);
			articulo.load(directorioEntrada + items[1]);
			if (!articulos.contains(articulo))	articulos.add(articulo);
		}
		while(scanRepositorios.hasNextLine()){
			lineIn = scanRepositorios.nextLine().trim();
			if (lineIn.isEmpty()) continue;
			if (lineIn.startsWith("%")) continue;
			if (lineIn.startsWith("@")) {
				repositorioID = lineIn.substring(1);
				continue;
			}
			items = lineIn.split(" ");
			add(repositorioID, items[0], Arrays.copyOfRange(items, 1, items.length));
		}
		scanArticulos.close();
		scanRepositorios.close();
		return true;
	}

	public boolean add(String repositorioID, String articuloID, String ...autoresID) {
		Articulo articulo = articulos.find(new Articulo(articuloID));
		if (articulo == null) return false;
		if (!datos.containsKey(repositorioID)) datos.put(repositorioID, new TreeMap<>());
		//1 for()
		for (String autorID : autoresID) {
			if (!datos.get(repositorioID).containsKey(autorID)) datos.get(repositorioID).put(autorID, new TreeSet<>());
			datos.get(repositorioID).get(autorID).add(articulo);
		}
		return true;
	}
	
	public TreeSet<String> getArticulosID(String autorID) {
		TreeSet<String> result = new TreeSet<>();
		for (TreeMap<String, TreeSet<Articulo>> repositorio : datos.values()) {
			TreeSet<Articulo> autor = repositorio.get(autorID);
			if (autor == null) autor = new TreeSet<>();
			for (Articulo articulo: autor)
				result.add(articulo.getArticuloID());
		}
		return result;
	}
	
	public TreeSet<String> getCoAutores(String autorID) {
		TreeSet<String> result = new TreeSet<>();
		TreeMap<String, TreeSet<String>> articuloAutores = new TreeMap<>();
		for (TreeMap<String, TreeSet<Articulo>> repositorio : datos.values()) {
			for (Entry<String, TreeSet<Articulo>> autor : repositorio.entrySet()) {
				for (Articulo articulo : autor.getValue()) {
					if (!articuloAutores.keySet().contains(articulo.getArticuloID()))	articuloAutores.put(articulo.getArticuloID(), new TreeSet<>());
					articuloAutores.get(articulo.getArticuloID()).add(autor.getKey());
					}
				}
			}
		for (TreeSet<String> autores : articuloAutores.values())
			if (autores.contains(autorID))	result.addAll(autores);
		result.remove(autorID);
		return result;
	}
	
	public TreeSet<String> getPalabrasClave(String autorID, int minFrec) {
		Articulo macroArticulo = new Articulo("");
		TreeSet<String> resultado = new TreeSet<String>();
		for (TreeMap<String, TreeSet<Articulo>> repositorio : datos.values()) {
			if (repositorio.get(autorID) == null) continue;
			for (Articulo articulo : repositorio.get(autorID))
				macroArticulo = macroArticulo.fusionar(articulo);
		}
		for (Entry<String, Integer> palabraFrecuencia : macroArticulo)
			if (palabraFrecuencia.getValue() >= minFrec) resultado.add(palabraFrecuencia.toString());
		return resultado;
	}
	
	public int size() {
		return datos.size();
	}
	
	public void clear() {
		articulos = new AVLTree<>();
		datos = new TreeMap<>();
	}
	
	@Override
	public String toString() {
		return this.datos.toString();
	}
}