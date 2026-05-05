package entidad;

public class SubtipoProblema {
	
    private int id_subtipo;
    private int id_tipo;
    private String nom_subtipo;
    private String nom_tipo;

	public SubtipoProblema() {
	}

	public SubtipoProblema(int id_subtipo, int id_tipo, String nom_subtipo) {
		super();
		this.id_subtipo = id_subtipo;
		this.id_tipo = id_tipo;
		this.nom_subtipo = nom_subtipo;
	}

	public int getId_subtipo() {
		return id_subtipo;
	}

	public void setId_subtipo(int id_subtipo) {
		this.id_subtipo = id_subtipo;
	}

	public int getId_tipo() {
		return id_tipo;
	}

	public void setId_tipo(int id_tipo) {
		this.id_tipo = id_tipo;
	}

	public String getNom_subtipo() {
		return nom_subtipo;
	}

	public void setNom_subtipo(String nom_subtipo) {
		this.nom_subtipo = nom_subtipo;
	} 
	
    public String getNom_tipo() { 
    	return nom_tipo;
    }
    
    public void setNom_tipo(String nom_tipo) {
    	this.nom_tipo = nom_tipo;
    }
	
    
    
    
    
    
    
    
    


}