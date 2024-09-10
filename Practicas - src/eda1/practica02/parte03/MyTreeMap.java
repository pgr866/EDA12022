package eda1.practica02.parte03;

import java.util.ArrayList;

import eda1.auxiliar.BSTree;
import eda1.auxiliar.Pair;

public class MyTreeMap<K extends Comparable<K>,V> {
	
	private BSTree<Pair<K,V>> tree = new BSTree<Pair<K,V>>(); 
	
	public boolean put(K key, V value) {
		Pair<K,V> par = this.tree.find(new Pair<>(key, value));
		if (par != null) tree.remove(par);
		tree.add(new Pair<>(key, value));
		return par == null;
	}
	
	public V get(K key) {
		Pair<K,V> aux = this.tree.find(new Pair<>(key, null));
		return aux == null ? null : aux.getValue();
	}
	
	public boolean containsKey(K key) {
		return tree.find(new Pair<>(key, null)) != null;
	}
	
	public void clear() {
		this.tree.clear();
	}
	
	public boolean isEmpty() {
		return this.tree.isEmpty();
	}

	public int size() {
		return this.tree.size();
	}

	public ArrayList<K> keySet(){
		ArrayList<K> resultado  = new ArrayList<>();
		for (Pair<K,V> aux : tree)
			resultado.add(aux.getKey());
		return resultado;
	}
	
	public ArrayList<V> values(){
		ArrayList<V> resultado  = new ArrayList<>();
		for (Pair<K,V> aux : tree)
			resultado.add(aux.getValue());
		return resultado;
	}
	
	public ArrayList<Pair<K,V>> entrySet(){
		ArrayList<Pair<K,V>> resultado  = new ArrayList<>();
		for (Pair<K,V> aux : tree)
			resultado.add(aux);
		return resultado;
	}

	@Override
	public String toString() {
		return this.entrySet().toString();
	}
}