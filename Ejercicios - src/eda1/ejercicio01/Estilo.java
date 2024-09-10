package eda1.ejercicio01;

import java.util.ArrayList;

public class Estilo {
	
	public static void main(String[]args) {
		ArrayList<String> arr = new ArrayList<String>();
		
		for(int i=1; i<=50; i++)
			arr.add(String.valueOf(Math.random()< .9 ? i : -i));
		System.out.println(arr.toString());
		
		int i=0;
		while (true) {
			if (i == arr.size()) {
				System.out.println("No se ha encontrado ningún valor negativo");
				break;
			}
			if (Integer.valueOf(arr.get(i)) >= 0) {
				i++;
				continue;
			}
			System.out.println("Hen encontrado el primer valor negativo: " + arr.get(i));
			break;
		}
		System.out.println("He terminado");
	}
}
