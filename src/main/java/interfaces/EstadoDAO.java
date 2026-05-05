package interfaces;

import java.util.List;

import entidad.Estado;

public interface EstadoDAO {
	
	public int registrar(Estado e);
    public List<Estado> listar();
    public Estado obtener(int id_estado);
    public int editar(Estado e);
    public int eliminar(int id_estado);

}
