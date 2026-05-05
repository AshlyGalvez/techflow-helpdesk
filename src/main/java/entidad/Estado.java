package entidad;

public class Estado {
	private int id_estado;
	private String nom_estado;
	
	
	public Estado() {
		
	}


	public Estado(int id_estado, String nom_estado) {
		this.id_estado = id_estado;
		this.nom_estado = nom_estado;
	}


	public int getId_estado() {
		return id_estado;
	}


	public void setId_estado(int id_estado) {
		this.id_estado = id_estado;
	}


	public String getNom_estado() {
		return nom_estado;
	}


	public void setNom_estado(String nom_estado) {
		this.nom_estado = nom_estado;
	}
	
	
	

}
