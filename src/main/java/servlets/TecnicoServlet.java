package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import entidad.*;
import interfaces.*;


@WebServlet("/TecnicoServlet")
public class TecnicoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    DetalleTecnicoDAO daoDetalle = fabrica.getDetalleTecnicoDAO();
    UsuarioDAO daoUsuario = fabrica.getUsuarioDAO();

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String tipo = request.getParameter("tipo");
        if (tipo == null) tipo = "list";
        
        switch (tipo) {
            case "list":   listar(request, response); break;
            case "regist": registrar(request, response); break;
            case "modif":  irAEditar(request, response); break;
            case "edit":   grabarModificacion(request, response); break;
            case "listarPorTecnico": verTicketsTecnico(request, response); break;
        }
    }
    
    protected void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        List<DetalleTecnico> listaDetalle = daoDetalle.listar();
        
        List<Usuario> listaUsuarios = daoUsuario.listarTecnicos();
        
        request.setAttribute("listado", listaDetalle);
        request.setAttribute("usuarios", listaUsuarios);
        
        request.getRequestDispatcher("/mantenimientos/mantenimiento_tecnicos.jsp").forward(request, response);
    }

    protected void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id_tecnico = Integer.parseInt(request.getParameter("cboTecnico"));
            String especialidad = request.getParameter("txtEspecialidad");
            
            DetalleTecnico dt = new DetalleTecnico();
            dt.setId_tecnico(id_tecnico);
            dt.setEspecialidad(especialidad);
            dt.setDisponibilidad(1); 
            dt.setTickets_resueltos(0);
            
            int res = daoDetalle.registrar(dt);
            
            if(res > 0) {
                request.setAttribute("mensaje", "Técnico configurado exitosamente en TechFlow.");
            } else {
                request.setAttribute("mensaje", "Error: Este usuario ya está registrado como técnico.");
            }
        } catch (Exception e) {
            request.setAttribute("mensaje", "Error en el registro: " + e.getMessage());
        }
        listar(request, response);
    }

    protected void irAEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id_tecnico"));
            
            DetalleTecnico dt = daoDetalle.obtener(id);
            List<Usuario> listaUsuarios = daoUsuario.listarTecnicos();
            List<DetalleTecnico> listaDetalle = daoDetalle.listar();
            
            request.setAttribute("tecnicoEditable", dt);
            request.setAttribute("usuarios", listaUsuarios);
            request.setAttribute("listado", listaDetalle);
            
            request.getRequestDispatcher("/mantenimientos/mantenimiento_tecnicos.jsp").forward(request, response);
        } catch (Exception e) {
            listar(request, response);
        }
    }

    protected void grabarModificacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("txtId"));
            String esp = request.getParameter("txtEspecialidad");
            int disp = Integer.parseInt(request.getParameter("cboDisponibilidad"));
            
            DetalleTecnico dt = new DetalleTecnico();
            dt.setId_tecnico(id);
            dt.setEspecialidad(esp);
            dt.setDisponibilidad(disp);
            
            daoDetalle.editar(dt);
            request.setAttribute("mensaje", "Perfil de técnico actualizado correctamente.");
        } catch (Exception e) {
            request.setAttribute("mensaje", "Error al actualizar los datos.");
        }
        listar(request, response);
    }
    
    protected void verTicketsTecnico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idTecnico = Integer.parseInt(request.getParameter("id"));
            DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
            TicketDAO daoTick = fabrica.getTicketDAO();

            List<Ticket> tickets = daoTick.consultarPorTecnico(idTecnico);
            DetalleTecnico dt = daoDetalle.obtener(idTecnico);

            request.setAttribute("ticketsTecnico", tickets);
            request.setAttribute("tecnico", dt);
            request.getRequestDispatcher("/TicketServlet?tipo=list").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("TecnicoServlet?accion=tecnicos");
        }
    }
    
    
    
    
}