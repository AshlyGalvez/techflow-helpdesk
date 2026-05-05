package interfaces;

import java.util.List;

import entidad.Comentario;

public interface ComentarioDAO {
	
	public int registrar(Comentario c);
    public List<Comentario> listarPorTicket(int id_ticket);

}
