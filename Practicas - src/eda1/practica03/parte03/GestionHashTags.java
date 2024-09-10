package eda1.practica03.parte03;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import eda1.auxiliar.Greater;
import eda1.auxiliar.Less;

import java.util.TreeMap;
import java.util.TreeSet;

public class GestionHashTags {
	private HashMap<HashTag, TreeMap<String, Integer>> datos;

	public GestionHashTags() {
		this.datos = new HashMap<>();
	}
	
	public void add(String hashTagID, String paisID, String text) {
		HashTag hashTag = new HashTag(hashTagID, paisID.toLowerCase());
		TreeMap<String, Integer> current = datos.get(hashTag);
		if (current == null) current = new TreeMap<>();
		String[] palabras = text.split(" ");
		//1 for()
		for (String palabra : palabras)
			current.put(palabra, (current.keySet().contains(palabra) ? current.get(palabra) + 1 : 1));
		datos.put(hashTag, current);
	}
	
	public void generaDatosAleatorios(int numRepeticiones) {
		for (int i=0; i<numRepeticiones; i++) {
			this.add(Auxiliar.getHashTagRandom(), Auxiliar.getPaisRandom(), Auxiliar.getMensajeRandom());
		}
	}
	
	public int getSumFrecuencias() {
		int sum = 0;
		for (TreeMap<String, Integer> palabrasFrecuencias : datos.values()) {
			for (Integer freq : palabrasFrecuencias.values())
				sum += freq;
		}
		return sum;
	}
	
	public void clear(){
		datos.clear();
	}
	
	public int size() {
		return datos.size();
	}
	
	@Override
	public String toString() {
		TreeMap<String, Integer> result = new TreeMap<>();
		for (Entry<HashTag, TreeMap<String, Integer>> entrada : datos.entrySet())
			result.put(entrada.getKey().toString(), entrada.getValue().size());
		return result.toString();
	}
	
	private String toStringOrdered(Comparator<String> comp) {
		TreeMap<Integer, TreeSet<String>> result = new TreeMap<>(new Greater<>());
		for (Entry<HashTag, TreeMap<String, Integer>> entrada : datos.entrySet()) {
			int freq = entrada.getValue().size();
			if (!result.containsKey(freq)) result.put(freq, new TreeSet<>(comp));
			result.get(freq).add(entrada.getKey().toString());
		}
		return result.toString();
	}
	
	public static void main(String[]args) {
		GestionHashTags gestion = new GestionHashTags();
		
		gestion.add("#hs01", "ES", "es hora de salvar nuestro planeta");
		gestion.add("#hs01", "es", "no podemos esperar ni un minuto mas");
		gestion.add("#hs01", "it", "e ora di salvare il nostro pianeta");
		gestion.add("#hs01", "it", "per favore");
		gestion.add("#hs02", "es", "hoy me siento feliz");
		gestion.add("#hs02", "es", "incluso mas que ayer");
		gestion.add("#hs02", "en", "today i am feeling so happy soo haappy");
		gestion.add("#hs02", "it", "mi sento felice");
		gestion.add("#hs03", "en", "blah1 blah2 blah3");
		
		assertEquals("{hs01 <es>=13, hs01 <it>=9, hs02 <en>=8, hs02 <es>=8, hs02 <it>=3, hs03 <en>=3}", gestion.toString());
		
		assertTrue(gestion.getSumFrecuencias() == 44);
		
		assertEquals("{13=[hs01 <es>], 9=[hs01 <it>], 8=[hs02 <es>, hs02 <en>], 3=[hs03 <en>, hs02 <it>]}", gestion.toStringOrdered(new Greater<>()));
		assertEquals("{13=[hs01 <es>], 9=[hs01 <it>], 8=[hs02 <en>, hs02 <es>], 3=[hs02 <it>, hs03 <en>]}", gestion.toStringOrdered(new Less<>()));
		

		gestion.add("#hs02", "es", "si");
		assertEquals("{13=[hs01 <es>], 9=[hs01 <it>, hs02 <es>], 8=[hs02 <en>], 3=[hs02 <it>, hs03 <en>]}", gestion.toStringOrdered(new Less<>()));
		assertEquals("{13=[hs01 <es>], 9=[hs02 <es>, hs01 <it>], 8=[hs02 <en>], 3=[hs03 <en>, hs02 <it>]}", gestion.toStringOrdered(new Greater<>()));
		
		gestion.add("#hs01", "es", "idem");
		gestion.add("#hs02", "it", "idem");

		assertTrue(gestion.getSumFrecuencias() == 47);
		
		assertEquals("{14=[hs01 <es>], 9=[hs01 <it>, hs02 <es>], 8=[hs02 <en>], 4=[hs02 <it>], 3=[hs03 <en>]}", gestion.toStringOrdered(new Less<>()));
		
		gestion.clear();

		gestion.generaDatosAleatorios(1000000);
		assertTrue(gestion.getSumFrecuencias() == 1000000);
		assertTrue(gestion.size() == 70); // 70??????
		System.out.println("TODO OK!!!");

		gestion.clear();
		gestion = null;
	}
}