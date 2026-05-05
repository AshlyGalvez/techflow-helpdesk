package entidad;

public class Rol {
	private int id_rol;
	private String nom_rol;
	
	public Rol() {
	}

	public Rol(int id_rol, String nombre) {
		this.id_rol = id_rol;
		this.nom_rol = nombre;
	}
	///
	public int getId_rol() {
		return id_rol;
	}

	public void setId_rol(int id_rol) {
		this.id_rol = id_rol;
	}

	public String getNombre() {
		return nom_rol;
	}

	public void setNombre(String nombre) {
		this.nom_rol = nombre;
	}
	
	
	

}
