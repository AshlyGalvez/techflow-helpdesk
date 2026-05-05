package entidad;

import java.util.Date;

public class Comentario {

	private int id_comentario;
	private int id_ticket;
	private int id_usuario;
	private String texto_comentario;
	private Date fecha_reg;
	private int es_solucion;
	
	private String nom_usuario;

	public Comentario() {
		
	}

	public Comentario(int id_comentario, int id_ticket, int id_usuario, String texto_comentario, Date fecha_reg,
			int es_solucion) {
		
		this.id_comentario = id_comentario;
		this.id_ticket = id_ticket;
		this.id_usuario = id_usuario;
		this.texto_comentario = texto_comentario;
		this.fecha_reg = fecha_reg;
		this.es_solucion = es_solucion;
	}

	public int getId_comentario() {
		return id_comentario;
	}

	public void setId_comentario(int id_comentario) {
		this.id_comentario = id_comentario;
	}

	public int getId_ticket() {
		return id_ticket;
	}

	public void setId_ticket(int id_ticket) {
		this.id_ticket = id_ticket;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getTexto_comentario() {
		return texto_comentario;
	}

	public void setTexto_comentario(String texto_comentario) {
		this.texto_comentario = texto_comentario;
	}

	public Date getFecha_reg() {
		return fecha_reg;
	}

	public void setFecha_reg(Date fecha_reg) {
		this.fecha_reg = fecha_reg;
	}

	public int getEs_solucion() {
		return es_solucion;
	}

	public void setEs_solucion(int es_solucion) {
		this.es_solucion = es_solucion;
	}

	public String getNom_usuario() {
		return nom_usuario;
	}

	public void setNom_usuario(String nom_usuario) {
		this.nom_usuario = nom_usuario;
	}
	
	
	
	
}
