package interfaces;

import java.util.List;

import entidad.TipoProblema;

public interface TipoProblemaDAO {
	
	public int registrar(TipoProblema t);
	public List<TipoProblema>listar();
	public TipoProblema obtener(int id_tipo);
	public int editar(TipoProblema t);
	public int eliminar(int id_tipo);

}
