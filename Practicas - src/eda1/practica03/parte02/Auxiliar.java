package eda1.practica03.parte02;

import java.util.ArrayList;
import java.util.Arrays;


public class Auxiliar {
	private static ArrayList<String> stopWords = new ArrayList<>(Arrays.asList("a", "al", "con", "de", "del", "el", "en", "es", "si", "la",
			                                                        "las", "los", "las", "se", "y", "o", "por", "han", "ya", "lo",
			                                                        "para", "que", "un", "este", "esta", "aquel", "aquella", "otro", "otra"));
	
	public static boolean isStopWord(String word) {
		return stopWords.contains(word);
	}
}
