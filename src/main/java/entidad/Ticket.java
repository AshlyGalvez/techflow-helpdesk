package entidad;

import java.util.Date;
import java.text.SimpleDateFormat;
public class Ticket {
	
	private int id_ticket;
	private String titulo;
	private String descripcion;
	private Date fecha_reg;
	private Date fecha_cierre;
	
	private int id_usuario_reporta;
	private int id_tecnico_asignado;
	private int id_tipo;
	private int id_subtipo;
	private int id_estado;
	private int id_prioridad;
	
	private String nom_usuario_reporta;
	private String nom_tecnico_asignado;
	private String nom_tipo;
	private String nom_estado;
	private String nom_prioridad;
	private int total;
	private int dias_atencion;
	
	
	
	public Ticket() {
		
	}
	public Ticket(int id_ticket, String titulo, String descripcion, Date fecha_reg, Date fecha_cierre,
			int id_usuario_reporta, int id_tecnico_asignado, int id_tipo, int id_estado, int id_prioridad) {
		
		this.id_ticket = id_ticket;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fecha_reg = fecha_reg;
		this.fecha_cierre = fecha_cierre;
		this.id_usuario_reporta = id_usuario_reporta;
		this.id_tecnico_asignado = id_tecnico_asignado;
		this.id_tipo = id_tipo;
		this.id_estado = id_estado;
		this.id_prioridad = id_prioridad;
	}
	
	public int getId_ticket() {
		return id_ticket;
	}
	public void setId_ticket(int id_ticket) {
		this.id_ticket = id_ticket;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFecha_reg() {
		return fecha_reg;
	}
	public void setFecha_reg(Date fecha_reg) {
		this.fecha_reg = fecha_reg;
	}
	public Date getFecha_cierre() {
		return fecha_cierre;
	}
	public void setFecha_cierre(Date fecha_cierre) {
		this.fecha_cierre = fecha_cierre;
	}
	public int getId_usuario_reporta() {
		return id_usuario_reporta;
	}
	public void setId_usuario_reporta(int id_usuario_reporta) {
		this.id_usuario_reporta = id_usuario_reporta;
	}
	public int getId_tecnico_asignado() {
		return id_tecnico_asignado;
	}
	public void setId_tecnico_asignado(int id_tecnico_asignado) {
		this.id_tecnico_asignado = id_tecnico_asignado;
	}
	public int getId_tipo() {
		return id_tipo;
	}
	public void setId_tipo(int id_tipo) {
		this.id_tipo = id_tipo;
	}
	public int getId_estado() {
		return id_estado;
	}
	public void setId_estado(int id_estado) {
		this.id_estado = id_estado;
	}
	public int getId_prioridad() {
		return id_prioridad;
	}
	public void setId_prioridad(int id_prioridad) {
		this.id_prioridad = id_prioridad;
	}
	public String getNom_usuario_reporta() {
		return nom_usuario_reporta;
	}
	public void setNom_usuario_reporta(String nom_usuario_reporta) {
		this.nom_usuario_reporta = nom_usuario_reporta;
	}
	public String getNom_tecnico_asignado() {
		return nom_tecnico_asignado;
	}
	public void setNom_tecnico_asignado(String nom_tecnico_asignado) {
		this.nom_tecnico_asignado = nom_tecnico_asignado;
	}
	public String getNom_tipo() {
		return nom_tipo;
	}
	public void setNom_tipo(String nom_tipo) {
		this.nom_tipo = nom_tipo;
	}
	public String getNom_estado() {
		return nom_estado;
	}
	public void setNom_estado(String nom_estado) {
		this.nom_estado = nom_estado;
	}
	public String getNom_prioridad() {
		return nom_prioridad;
	}
	public void setNom_prioridad(String nom_prioridad) {
		this.nom_prioridad = nom_prioridad;
	}
	
	public int getId_subtipo() { 
		return id_subtipo; 
	}
	
	public void setId_subtipo(int id_subtipo) {
		this.id_subtipo = id_subtipo; 
	}
	public int getTotal() { 
		return total; 
	}	
	public void setTotal(int total) {
		this.total = total; 
	}	
	public int getDias_atencion() { 
		return dias_atencion;
	}
	public void setDias_atencion(int dias_atencion) {
		this.dias_atencion = dias_atencion;
	}
											
	public String getFechaCorta() {
	    if (this.fecha_reg == null) return "Sin fecha";
	    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, hh:mm a");
	    return sdf.format(this.fecha_reg);
	}
	
	
	

}
