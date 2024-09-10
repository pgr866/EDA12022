package eda1.auxiliar;


public class Pair <K extends Comparable<K>,V> implements Comparable<Pair<K,V>>{
	
	private final K key;
	private V value;
	
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return this.key;
	}

	public V getValue() {
		return this.value;
	}
	
	public void setValue(V value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return key + " <" + value + ">";
	}

	@Override
	public boolean equals(Object o) {
		Pair<?,?> p = (Pair<?,?>)o;
		return this.key.equals(p.key);
	}
	
	@Override
	public int compareTo(Pair<K,V> o) {
		return this.key.compareTo(o.key);
	}
}
