package eda1.practica03.parte03;

import java.util.Random;

public class Auxiliar {
	private static final String[] paises   = {"es", "it", "fr", "pt", "ge", "us", "en"};
	private static final String[] hashTags = {"#love", "#cute", "#like", "#photoOfTheDay", "#followMe", "#happy", 
			                                  "#fun", "#smile", "#selfie", "#friends"};
	private static final String[] mensajes = {"mensaje01", "mensaje02", "mensaje03"};
	private static final Random random = new Random();
	
	public static String getPaisRandom() {
		return paises[random.nextInt(paises.length)];
	}
	
	public static String getHashTagRandom() {
		return hashTags[random.nextInt(hashTags.length)];
	}
	
	public static String getMensajeRandom() {
		return mensajes[random.nextInt(mensajes.length)];
	}
}
