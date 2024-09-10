package eda1.practica02.parte02;

import java.util.ArrayList;

import eda1.auxiliar.AVLTree;
import eda1.auxiliar.Pair;

public class Biblioteca {
	private String bibliotecaID;
 	private AVLTree<Libro> libros;
	private AVLTree<Usuario> usuarios;
	private AVLTree<Pair<Libro, Usuario>> prestamos;
	private AVLTree<Pair<Usuario, ArrayList<Libro>>> historicoPrestamos;
	
	
	public Biblioteca(String bibliotecaID) {
		this.bibliotecaID = bibliotecaID;
		this.libros = new AVLTree<>();
		this.usuarios = new AVLTree<>();
		this.prestamos = new AVLTree<>();
		this.historicoPrestamos = new AVLTree<>();
	}
	
	public boolean addLibro(Libro libro) {
		return this.libros.add(libro);
	}
	
	public boolean addLibro(String libroID) {
		return this.libros.add(new Libro(libroID));
	}
		
	public boolean addUsuario(Usuario usuario) {
		return this.usuarios.add(usuario);
	}

	public boolean addUsuario(String usuarioID) {
		return this.usuarios.add(new Usuario(usuarioID));
	}
	
	public void clear() {
		this.libros.clear();
		this.usuarios.clear();
		this.prestamos.clear();
		this.historicoPrestamos.clear();
	}
	
	public boolean prestarLibro(String usuarioID, String libroID) {
		Pair<Libro, Usuario> par = new Pair<>(this.libros.find(new Libro(libroID)), this.usuarios.find(new Usuario(usuarioID)));
		if (par.getKey() == null || par.getValue() == null) return false;
		Pair<Libro, Usuario> parCurrent = prestamos.find(new Pair<>(par.getKey(), null));
		if (parCurrent != null)
			return false;
		else {
			prestamos.add(par);
			guardarHistorico(par.getKey(), par.getValue());
			return true;
		}
	}
	
	public boolean devolverLibro(String usuarioID, String libroID) {
		Pair<Libro, Usuario> par = new Pair<>(this.libros.find(new Libro(libroID)), this.usuarios.find(new Usuario(usuarioID)));
		if (par.getKey() == null || par.getValue() == null) return false;
		if (prestamos.find(par) == null) return false;
		prestamos.remove(par);
		return true;
	}
	
	private void guardarHistorico(Libro libro, Usuario usuario) {
		Pair<Usuario, ArrayList<Libro>> parCurrent = this.historicoPrestamos.find(new Pair<>(usuario, null));
		if (parCurrent != null)
			parCurrent.getValue().add(libro);
		else {
			ArrayList<Libro> historicoPrestamoAux = new ArrayList<>();
			historicoPrestamoAux.add(libro);
			historicoPrestamos.add(new Pair<>(usuario, historicoPrestamoAux));
		}
	}

	public ArrayList<String> getUsuariosLibro(String libroID){
		ArrayList<String> result = new ArrayList<>();
		Libro libro = new Libro(libroID);
		if (!libros.contains(libro)) return null;
		for (Pair<Usuario, ArrayList<Libro>> par : historicoPrestamos)
			if (par.getValue().contains(libro)) result.add(par.getKey().toString());
		return result;
	}
	
	public ArrayList<String> getLibrosPrestados(String usuarioID){
		Usuario usuario = this.usuarios.find(new Usuario(usuarioID));
		if (usuario == null) return null;
		Pair<Usuario, ArrayList<Libro>> parCurrent = historicoPrestamos.find(new Pair<>(usuario, null));
		if (parCurrent == null) return null;
		ArrayList<String> result = new ArrayList<>();
		for (Libro libro : parCurrent.getValue())
			result.add(libro.getLibroID());
		return result;
	}
		
	public ArrayList<String> getLibrosPrestados(){
		ArrayList<String> result = new ArrayList<>();
		for (Pair<Libro, Usuario> par : prestamos)
			result.add(par.getKey().getLibroID());
		return result;
	}
	
	@Override
	public String toString() {
		return this.bibliotecaID + " (" + this.libros.size() + " libros y " + this.usuarios.size() + " usuarios" + ")"; 
	}
}