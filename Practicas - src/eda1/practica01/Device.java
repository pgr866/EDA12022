package eda1.practica01;

import java.util.Iterator;
import java.util.LinkedList;

public class Device implements Iterable<String>{
	private static int numDevices=0; //contador de dispositivos...atributo estatico
	private String deviceId = "";
	private LinkedList<String> words = null; 
		
	public static void initializeNumDevices() {
		numDevices = 0;
	}
	
	public Device(String name){
		if (name == null) throw new RuntimeException("El atributo name no puede ser nulo");
		this.deviceId = ++numDevices + ".- " + (name.isEmpty() ? (name = "noName") : (name = name.trim()));
		this.words = new LinkedList<>();
	}
	
	public void clear() {
		this.words.clear();
	}
	
	public int size() {
		return this.words.size();
	}
	
	public void sendMessage(String msg) {
		if (msg == null) return;
		for (String palabra : msg.toLowerCase().split(" "))
			if (words.contains(palabra)) continue; else words.add(palabra);
	}
	
	public boolean contains(String word) {
		return this.words.contains(word.toLowerCase());
	}
	
	public boolean substitute(String word1, String word2) {
		if (word1 == null || word2 == null) return false;
		if (!words.contains(word1.toLowerCase())) return false;
		words.set(words.indexOf(word1.toLowerCase()), word2.toLowerCase());
		return true;
	}
	
	public boolean remove(String word) {
		if (word == null) return false;
		if (words.contains(word.toLowerCase())) return words.remove(word.toLowerCase());
		return false;
	}
	
	@Override
	public String toString() {
		return deviceId;
	}
	
	@Override
	public boolean equals(Object o) {
		return this.deviceId.equals(((Device)o).deviceId);
	}

	@Override
	public Iterator<String> iterator() {
		return words.iterator();
	}
}