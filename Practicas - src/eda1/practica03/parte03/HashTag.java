package eda1.practica03.parte03;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

public class HashTag implements Comparable<HashTag>{
	private String hashTagID;
	private String paisID;

	public HashTag(String hashTagID, String pais) {
		this.hashTagID = hashTagID.startsWith("#") ? hashTagID.substring(1) : hashTagID;
		this.paisID = pais;
	}

	@Override
	public int hashCode() {
		//Clave principal: hashTagID
		//Clave secundaria: paisID
		return Objects.hash(hashTagID, paisID);
	}

	@Override
	public boolean equals(Object o) {
		return this.hashCode() == ((HashTag) o).hashCode();
	}

	@Override 
	public String toString() {
		return hashTagID + " <" + paisID + ">";
	}

	@Override
	public int compareTo(HashTag o) {
		int cmp = this.hashTagID.compareTo(o.hashTagID);
		return cmp == 0 ? this.paisID.compareTo(o.paisID) : cmp;
	}
	
	public static void main(String[] args) {
		HashTag hashTag01 = new HashTag("#ht01", "es");
		HashTag hashTag02 = new HashTag("ht01", "es");
		HashTag hashTag03 = new HashTag("#ht02", "it");
		HashTag hashTag04 = new HashTag("ht02", "fr");

		assertTrue(hashTag01.equals(hashTag02));
		assertTrue(hashTag01.compareTo(hashTag02) == 0);
		assertFalse(hashTag01.equals(hashTag03));
		assertTrue(hashTag01.compareTo(hashTag03) < 0);
		assertTrue(hashTag04.compareTo(hashTag02) > 0);
		
		assertEquals("ht01 <es>", hashTag01.toString());
		assertEquals("ht02 <it>", hashTag03.toString());
		assertFalse(hashTag01.equals(hashTag04));
		assertFalse(hashTag03.equals(hashTag04));
		assertTrue(hashTag03.equals(new HashTag("ht02", "it")));
		assertEquals("ht02 <fr>", hashTag04.toString());

		System.out.println("TODO OK!!!!!");

		hashTag01 = hashTag02 = hashTag03 = hashTag04 = null;
	}
}