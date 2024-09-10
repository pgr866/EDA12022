package eda1.practica02.parte03;

import java.util.Iterator;

import eda1.auxiliar.AVLTree;
import eda1.auxiliar.Pair;


public class User implements Comparable<User>, Iterable<Pair<String,AVLTree<String>>> {

	private String userID;
	private MyTreeMap<String, AVLTree<String>> dispositivos;
		
	public User(String userID) {
		this.userID = userID;
		this.dispositivos= new MyTreeMap<>();
	}
	
	public void clear() {
		this.dispositivos.clear();
	}
	
	public void add(String dispositivoID, String...palabras) {
		AVLTree<String> current = this.dispositivos.get(dispositivoID);
		if (current == null) current = new AVLTree<>();
		for (String palabra : palabras)
			current.add(palabra);
		dispositivos.put(dispositivoID, current);
	}

	@Override
	public String toString() {
		return userID + "=<" + dispositivos.size() + (dispositivos.size() == 1 ? " dispositivo>" : " dispositivos>");
	}
	
	@Override
	public boolean equals(Object o) {
		return this.compareTo((User)o) == 0; 
	}
	
	@Override
	public int compareTo(User o) {
		return this.userID.trim().toLowerCase().compareTo(o.userID.trim().toLowerCase());
	}

	@Override
	public Iterator<Pair<String,AVLTree<String>>> iterator() {
		return dispositivos.entrySet().iterator();
	}
}