package entidad;

public class Usuario {
    private int id_usuario;
    private String dni;
    private String nombre;
    private String apellido;
    private String correo;
    private String login;
    private String contrasena;
    private int id_rol;
    private int estado_logico;
    private int total_asignados;
    private int total_resueltos;
    private int total_pendientes;
    private double promedio_dias;    
    private String nom_rol;

    public Usuario() {}

    public int getId_usuario() { 
    	return id_usuario;
    }
    public void setId_usuario(int id_usuario) { 
    	this.id_usuario = id_usuario; 
    }
    public String getDni() { 
    	return dni; 
    }
    public void setDni(String dni) { 
    	this.dni = dni; 
    }
    public String getNombre() { 
    	return nombre; 
    }
    public void setNombre(String nombre) { 
    	this.nombre = nombre; 
    }
    public String getApellido() {
    	return apellido;
    }
    public void setApellido(String apellido) {
    	this.apellido = apellido; 
    }
    public String getCorreo() {
    	return correo; 
    }
    public void setCorreo(String correo) {
    	this.correo = correo;
    }
    public String getLogin() {
    	return login; 
    }
    public void setLogin(String login) { 
    	this.login = login; 
    }
    public String getContrasena() { 
    	return contrasena; 
    }
    public void setContrasena(String contrasena) {
    	this.contrasena = contrasena;
    }
    public int getId_rol() { 
    	return id_rol; 
    }
    public void setId_rol(int id_rol) { 
    	this.id_rol = id_rol; 
    }
    public int getEstado_logico() {
    	return estado_logico; 
    }
  
    public String getNom_rol() {
		return nom_rol;
	}

	public void setNom_rol(String nom_rol) {
		this.nom_rol = nom_rol;
	}

	public void setEstado_logico(int estado_logico) {
		this.estado_logico = estado_logico;
	}
	public int getTotal_asignados() { 
		return total_asignados; 
	}
	public void setTotal_asignados(int total_asignados) {
		this.total_asignados = total_asignados;
	}
	public int getTotal_resueltos() { 
		return total_resueltos; 
	}
	public void setTotal_resueltos(int total_resueltos) {
		this.total_resueltos = total_resueltos;
	}
	public int getTotal_pendientes() {
		return total_pendientes; 
	}
	public void setTotal_pendientes(int total_pendientes) {
		this.total_pendientes = total_pendientes;
	}
	public double getPromedio_dias() { 
		return promedio_dias;
	}
	public void setPromedio_dias(double promedio_dias) {
		this.promedio_dias = promedio_dias;
	}
}




