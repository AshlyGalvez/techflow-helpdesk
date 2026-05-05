package interfaces;

import entidad.SubtipoProblema;
import java.util.List;

public interface SubtipoProblemaDAO {
    List<SubtipoProblema> listar();
    List<SubtipoProblema> listarPorTipo(int idTipo);
    int registrar(SubtipoProblema s);
    SubtipoProblema obtener(int id);
    int editar(SubtipoProblema s);
    int eliminar(int id);
}