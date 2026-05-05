package interfaces;

import java.util.List;

import entidad.Prioridad;

public interface PrioridadDAO {
	
	public int registrar(Prioridad p);
    public List<Prioridad> listar();
    public Prioridad obtener(int id_prioridad);
    public int editar(Prioridad p);
    public int eliminar(int id_prioridad);

}
