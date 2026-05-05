package entidad;

public class TipoProblema {
	
	private int id_tipo;
	private String nom_tipo;
	private int cantidad;
	private int resueltos;
	private int pendientes;
	
	public TipoProblema() {
		super();
	}

    public TipoProblema(int id_tipo, String nom_tipo) {
        this.id_tipo  = id_tipo;
        this.nom_tipo = nom_tipo;
    }

	public int getId_tipo() {
		return id_tipo;
	}


	public void setId_tipo(int id_tipo) {
		this.id_tipo = id_tipo;
	}


	public String getNom_tipo() {
		return nom_tipo;
	}


	public void setNom_tipo(String nom_tipo) {
		this.nom_tipo = nom_tipo;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public int getResueltos() { 
		return resueltos;
	}
	
	public void setResueltos(int resueltos) { 
		this.resueltos = resueltos;
	}
	public int getPendientes() { 
		return pendientes;
	}
	public void setPendientes(int pendientes) { 
		this.pendientes = pendientes;
	}



	

}
