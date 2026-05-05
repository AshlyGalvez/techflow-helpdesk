package interfaces;

import java.util.List;
import entidad.*;

public interface TicketDAO {

    public int registrarTicket(Ticket t);
    public List<Prioridad> listarPrioridades();
    public List<TipoProblema> listarTipos();
    public Ticket buscarPorId(int id_ticket);
    public List<Ticket> listar();
    public int cambiarEstado(int id_ticket, int id_estado);
    public int asignarTecnico(int id_ticket, int id_tecnico);
    public int resolverTicket(int idTicket, int idTecnico, String solucion);
    public List<Ticket> listarTicketsDinamico(int idUsuario, int idRol, int idEstado, int limite);
    public int contarTickets(int idUsuario, int idRol, int idEstado);
    public List<Estado> listarEstados();
    public int registrarComentario(Comentario c);
    public List<Comentario> listarComentariosPorTicket(int idTicket);
    public List<Usuario> listarTecnicos();
    public void limpiarTicketsInactivos();
    public List<TipoProblema> listarCategoriasMes();
    public int contarTicketsPorEstado(int idEstado);
    public int contarUsuariosPorRol(int idRol);
    public List<SubtipoProblema> listarSubtipos();

    public List<Ticket> consultarPorEstadoYFecha(int idEstado, String fechaInicio, String fechaFin);

    public List<Ticket> consultarPorTecnico(int idTecnico);

    public List<Ticket> consultarPorTitulo(String titulo);

    public List<Ticket> reporteResumenPorEstado();

    public List<TipoProblema> reportePorTipo();

    public List<Usuario> reporteRendimientoTecnicos();
}