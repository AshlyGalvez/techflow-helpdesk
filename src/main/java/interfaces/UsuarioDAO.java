package interfaces;
import entidad.Usuario;
import java.util.List;

public interface UsuarioDAO {
    public Usuario validarAcceso(String login, String password); 
    public List<Usuario> listarTodos(); 
    public List<Usuario> listarTecnicos(); 
    public int registrar(Usuario u);
    public int actualizar(Usuario u);
    public int eliminarLogico(int id);
    public Usuario buscarPorId(int id);
}