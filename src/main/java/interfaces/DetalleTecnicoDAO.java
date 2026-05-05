package interfaces;

import java.util.List;

import entidad.DetalleTecnico;

public interface DetalleTecnicoDAO {
	
	public int registrar(DetalleTecnico dt);
    public List<DetalleTecnico> listar();
    public DetalleTecnico obtener(int id_tecnico);
    public int editar(DetalleTecnico dt);

}
