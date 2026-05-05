package entidad;

public class DetalleTecnico {
	
	private int id_tecnico;
	private String especialidad;
	private int disponibilidad;
	private int tickets_resueltos;
	
	private String nom_tecnico;
	private String correo_tecnico;
	public DetalleTecnico() {
		
	}
	public DetalleTecnico(int id_tecnico, String especialidad, int disponibilidad, int tickets_resueltos) {
		this.id_tecnico = id_tecnico;
		this.especialidad = especialidad;
		this.disponibilidad = disponibilidad;
		this.tickets_resueltos = tickets_resueltos;
	}
	
	
	public int getId_tecnico() {
		return id_tecnico;
	}
	public void setId_tecnico(int id_tecnico) {
		this.id_tecnico = id_tecnico;
	}
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	public int getDisponibilidad() {
		return disponibilidad;
	}
	public void setDisponibilidad(int disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	public int getTickets_resueltos() {
		return tickets_resueltos;
	}
	public void setTickets_resueltos(int tickets_resueltos) {
		this.tickets_resueltos = tickets_resueltos;
	}
	public String getNom_tecnico() {
		return nom_tecnico;
	}
	public void setNom_tecnico(String nom_tecnico) {
		this.nom_tecnico = nom_tecnico;
	}
	public String getCorreo_tecnico() {
		return correo_tecnico;
	}
	public void setCorreo_tecnico(String correo_tecnico) {
		this.correo_tecnico = correo_tecnico;
	}
	

}
