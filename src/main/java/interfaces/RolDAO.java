package interfaces;

import java.util.List;

import entidad.Rol;

public interface RolDAO {
	public int registrar(Rol rol);
	public List<Rol>listar();
	public Rol obtener(int id_rol);
	public int editar(Rol rol);
	
	
}
