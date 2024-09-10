package eda1.practica02.parte02;


public class Usuario implements Comparable<Usuario> {
	private String usuarioID;

	public Usuario(String usuarioID) {
		this.usuarioID = usuarioID;
	}
	
	public String getUsuarioID() {
		return this.usuarioID;
	}
	
	@Override
	public String toString() {
		return this.usuarioID.toLowerCase().trim();
	}
	
	@Override
	public boolean equals(Object o) {
		return this.compareTo((Usuario)o) == 0;
	}
	@Override
	public int compareTo(Usuario o) {
		return this.usuarioID.toLowerCase().trim().compareTo(o.usuarioID.toLowerCase().trim());
	}
}