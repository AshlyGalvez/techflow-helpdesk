package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.DAOFactory;
import entidad.*;
import interfaces.TicketDAO;

@WebServlet("/TicketServlet")
public class TicketServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    TicketDAO daoTick = fabrica.getTicketDAO();

    protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	        request.setCharacterEncoding("UTF-8");
	        response.setCharacterEncoding("UTF-8");
	
	        String tipo = request.getParameter("tipo");
	        if (tipo == null) tipo = "list";
	
	        switch (tipo) {
	            case "list":          listar(request, response);        break;
	            case "nuevo":         cargarFormulario(request, response); break;
	            case "registrar":     registrar(request, response);     break;
	            case "detalle":       verDetalle(request, response);    break;
	            case "asignar":       asignar(request, response);       break;
	            case "resolver":      resolver(request, response);      break;   
	            case "reabrir":       reabrir(request, response);       break;   
	            case "finalizar":     finalizar(request, response);     break;   
	            case "cambiarEstado": cambiarEstado(request, response); break;   
	            case "comentar":      comentar(request, response);      break;   
	        }
	    }
	
	    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        HttpSession session = request.getSession();
	        Usuario u = (Usuario) session.getAttribute("objUsuario");
	
	        String filtro         = request.getParameter("filtro");
	        String txtBuscar      = request.getParameter("txtBuscar");
	        String fechaInicio    = request.getParameter("fechaInicio");
	        String fechaFin       = request.getParameter("fechaFin");
	        String idTecnicoParam = request.getParameter("idTecnico");
	
	        int idEstado   = (filtro != null && !filtro.trim().isEmpty()) ? Integer.parseInt(filtro) : 0;
	        int idConsulta = (u.getId_rol() == 1) ? 0 : u.getId_usuario();
	
	        List<Ticket> lista;
	        String nomTecnicoFiltro = null;
	
	        if (idTecnicoParam != null && !idTecnicoParam.trim().isEmpty()) {
	            int idTec = Integer.parseInt(idTecnicoParam);
	            lista = daoTick.listarTicketsDinamico(idTec, 2, idEstado, 0);
	            for (Usuario tec : daoTick.listarTecnicos()) {
	                if (tec.getId_usuario() == idTec) {
	                    nomTecnicoFiltro = tec.getNombre();
	                    break;
	                }
	            }
	        } else if (txtBuscar != null && !txtBuscar.trim().isEmpty()) {
	            lista = daoTick.consultarPorTitulo(txtBuscar.trim());
	            
	        } else if ((fechaInicio != null && !fechaInicio.trim().isEmpty()) ||
	                   (fechaFin    != null && !fechaFin.trim().isEmpty())) {
	        	lista = daoTick.consultarPorEstadoYFecha(idEstado, fechaInicio, fechaFin);
	        	
	        } else {
	            lista = daoTick.listarTicketsDinamico(idConsulta, u.getId_rol(), idEstado, 0);
	        }
	
		        request.setAttribute("listadoTickets",   lista);
		        request.setAttribute("estados",          daoTick.listarEstados());
		        request.setAttribute("filtroEstado",     filtro);
		        request.setAttribute("txtBuscar",        txtBuscar);
		        request.setAttribute("fechaInicio",      fechaInicio);
		        request.setAttribute("fechaFin",         fechaFin);
		        request.setAttribute("idTecnicoFiltro",  idTecnicoParam);
		        request.setAttribute("nomTecnicoFiltro", nomTecnicoFiltro);
		        request.getRequestDispatcher("tickets/listadoTickets.jsp").forward(request, response);
		    }
	
	    private void cargarFormulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        request.setAttribute("prioridades", daoTick.listarPrioridades());
	        request.setAttribute("tipos", daoTick.listarTipos());
	        request.setAttribute("subtipos", daoTick.listarSubtipos());
	        request.getRequestDispatcher("tickets/registrar_ticket.jsp").forward(request, response);
	    }
	
	
	    private void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        HttpSession session = request.getSession();
	        Usuario u = (Usuario) session.getAttribute("objUsuario");
	
	        Ticket t = new Ticket();
	        t.setTitulo(request.getParameter("txtTitulo"));
	        t.setDescripcion(request.getParameter("txtDescripcion"));
	        t.setId_usuario_reporta(u.getId_usuario());
	        t.setId_tipo(Integer.parseInt(request.getParameter("idTipo")));
	        t.setId_prioridad(Integer.parseInt(request.getParameter("idPrioridad")));
	
	        String subtipoParam = request.getParameter("idSubtipo");
	        if (subtipoParam != null && !subtipoParam.isEmpty()) {
	            t.setId_subtipo(Integer.parseInt(subtipoParam));
	        }
	        
	        int ok = daoTick.registrarTicket(t);
	        if (ok > 0) {
	            response.sendRedirect("TicketServlet?tipo=list");
	        } else {
	            request.setAttribute("mensaje", "Error al crear ticket");
	            cargarFormulario(request, response);
	        }
	    }
	
	
	    private void verDetalle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        HttpSession session = request.getSession();
	        Usuario u = (Usuario) session.getAttribute("objUsuario");
	
	        Ticket t = daoTick.buscarPorId(id);
	
	        if (u.getId_rol() == 2 && t.getId_estado() == 1) {
	            daoTick.asignarTecnico(id, u.getId_usuario());
	            t.setId_estado(2);
	            t.setNom_estado("En Proceso");
	            t.setId_tecnico_asignado(u.getId_usuario());
	        }
	
	        if (u.getId_rol() == 1) {
	            request.setAttribute("tecnicos", daoTick.listarTecnicos());
	        }
	
	        request.setAttribute("ticket", t);
	        request.setAttribute("comentarios", daoTick.listarComentariosPorTicket(id));
	        request.getRequestDispatcher("tickets/detalle_ticket.jsp").forward(request, response);
	    }
	
	
	    private void asignar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int idT   = Integer.parseInt(request.getParameter("idTicket"));
	        int idTec = Integer.parseInt(request.getParameter("idTecnico"));
	        daoTick.asignarTecnico(idT, idTec);
	        response.sendRedirect("TicketServlet?tipo=detalle&id=" + idT);
	    }
	
	
	    private void resolver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        HttpSession session = request.getSession();
	        Usuario u = (Usuario) session.getAttribute("objUsuario");
	
	        int idT       = Integer.parseInt(request.getParameter("idTicket"));
	        String texto  = request.getParameter("txtSolucion");
	        String solCountParam = request.getParameter("solCount");
	        int solCount  = (solCountParam != null && !solCountParam.isEmpty())
	                        ? Integer.parseInt(solCountParam) : 0;
	
	        daoTick.resolverTicket(idT, u.getId_usuario(), texto);
	
	        if (solCount >= 1) {
	            daoTick.cambiarEstado(idT, 4);
	        }
	        response.sendRedirect("TicketServlet?tipo=detalle&id=" + idT);
	    }
	
	    private void reabrir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int idT = Integer.parseInt(request.getParameter("idTicket"));
	        HttpSession session = request.getSession();
	        Usuario u = (Usuario) session.getAttribute("objUsuario");
	
	        daoTick.cambiarEstado(idT, 2);
	
	        Comentario c = new Comentario();
	        c.setId_ticket(idT);
	        c.setId_usuario(u.getId_usuario());
	        c.setTexto_comentario("CASO REABIERTO: El usuario no está conforme con la solución.");
	        daoTick.registrarComentario(c);
	
	        response.sendRedirect("TicketServlet?tipo=detalle&id=" + idT);
	    }
	
	    private void finalizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int idT = Integer.parseInt(request.getParameter("idTicket"));
	        daoTick.cambiarEstado(idT, 4);
	        response.sendRedirect("TicketServlet?tipo=list");
	    }
	
	
	    private void cambiarEstado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int idT        = Integer.parseInt(request.getParameter("idTicket"));
	        int nuevoEstado = Integer.parseInt(request.getParameter("nuevoEstado"));
	        daoTick.cambiarEstado(idT, nuevoEstado);
	        response.sendRedirect("TicketServlet?tipo=detalle&id=" + idT);
	    }
	
	
	    private void comentar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int idT = Integer.parseInt(request.getParameter("idTicket"));
	        HttpSession session = request.getSession();
	        Usuario u = (Usuario) session.getAttribute("objUsuario");
	
	        String texto = request.getParameter("txtComentario");
	        if (texto != null && !texto.trim().isEmpty()) {
	            Comentario c = new Comentario();
	            c.setId_ticket(idT);
	            c.setId_usuario(u.getId_usuario());
	            c.setTexto_comentario(texto.trim());
	            daoTick.registrarComentario(c);
	        }
	        response.sendRedirect("TicketServlet?tipo=detalle&id=" + idT);
	    }
}