package entidad;

public class Prioridad {
	
	private int id_prioridad;
	private String nom_prioridad;
	private int tiempo_horas;
	
	
	public Prioridad() {
		
	}


	public Prioridad(int id_prioridad, String nom_prioridad, int tiempo_horas) {
		
		this.id_prioridad = id_prioridad;
		this.nom_prioridad = nom_prioridad;
		this.tiempo_horas = tiempo_horas;
	}


	public int getId_prioridad() {
		return id_prioridad;
	}


	public void setId_prioridad(int id_prioridad) {
		this.id_prioridad = id_prioridad;
	}


	public String getNom_prioridad() {
		return nom_prioridad;
	}


	public void setNom_prioridad(String nom_prioridad) {
		this.nom_prioridad = nom_prioridad;
	}


	public int getTiempo_horas() {
		return tiempo_horas;
	}


	public void setTiempo_horas(int tiempo_horas) {
		this.tiempo_horas = tiempo_horas;
	}
	
	
	
	
}
