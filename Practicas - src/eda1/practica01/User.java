package eda1.practica01;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;

public class User implements Iterable<Device>{
	private static int numUsers = 0;
	private String userId = "";
	private ArrayList<Device> devices = null;
	
	
	public static void initializeNumUsers() {
		numUsers = 0;
	}
	
	public User(String name) {
		if (name == null || name.isEmpty()) throw new RuntimeException("El atributo name no puede ser ni nulo ni vacio");
		this.userId = ++numUsers + ".- " + (name = name.trim());
		this.devices = new ArrayList<>();
	}
	
	public void clear() {
		devices.clear();
	}
	
	
	public boolean addDevices(Device... devs) {
		if (devs == null) return false;
		for (Device dev : devs) {
			if (devices.contains(dev)) continue;
			devices.add(dev);
		}
		return true;
	}
	
	public int getNumDevices() {
		return this.devices.size();
	}

	public boolean loadMessages(Device dev, String fileName) {
		Scanner scan = null;
		String line; 
		if (!this.devices.contains(dev)) return false;
		try {
			scan = new Scanner(new File(fileName));
		}catch(Exception e) {
			System.out.println("Error: no encuentra el archivo");
			return false;
		}
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			if (line.isEmpty()) continue; 
			sendMessage(dev, line);
		}
		scan.close();
		return true;
	}
	
	public boolean sendMessage(Device dev, String msg) {
		if (dev == null || !devices.contains(dev)) return false;
		dev.sendMessage(msg);
		return true;
	}
	
	public void substitute(String word1, String word2) {
		if (word1 == null || word2 == null) return;
		for (Device dev : devices)
			dev.substitute(word1, word2);
	}
	
	public void remove(String word) {
		if (word == null) return;
		for (Device dev : devices)
			if (dev.contains(word)) dev.remove(word);	
	}
	
	public boolean contains(String word) {
		for (Device dev : devices)
			if (dev.contains(word)) return true;
		return false;
	}
	
	public String getWords() {
		String result = "";
		for (Device dev : devices) {
			if (dev.size() == 0) continue;
			result += dev.toString() + ": ";
			for (String palabra : dev)
				result += palabra + " ";
			result += "\n";
		}
		return result;
	}
	
	public ArrayList<String> getOrderedWords() {
		ArrayList<String> result = new ArrayList<String>();
		for (Device dev : devices) {
			for (String palabra : dev)
				if (!result.contains(palabra)) result.add(palabra);
		}
		result.sort(null);
		return result;
	}
	
	@Override
	public String toString() {
		return this.userId;
	}
	
	@Override
	public boolean equals(Object o) {
		User otro = (User) o;
		return this.userId.toLowerCase().substring(this.userId.indexOf("-") + 2).equals(otro.userId.toLowerCase().substring(otro.userId.indexOf("-") + 2));
//		return this.userId.toLowerCase().split(" ")[1].equals(otro.userId.toLowerCase().split(" ")[1]);
	}
	
	@Override
	public Iterator<Device> iterator() {
		return devices.iterator();
	}
	
}