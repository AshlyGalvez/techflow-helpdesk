package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import entidad.Prioridad;
import interfaces.PrioridadDAO;


@WebServlet("/PrioridadServlet")
public class PrioridadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PrioridadServlet() {
        super();
    }
    DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    PrioridadDAO dao = fabrica.getPrioridadDAO();


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo = request.getParameter("tipo");
	    
	    if (tipo == null || tipo.trim().isEmpty()) {
	        tipo = "list";
	    }
		
		switch(tipo) {
			case "list": listar(request, response); break;
			case "regist": registrar(request, response); break;
			case "modif": irAEditar(request, response); break;
			case "edit": grabarModificacion(request, response); break;
			case "elim": eliminar(request, response); break;
		}
		
	}
	
	protected void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Prioridad> lista = dao.listar();
		request.setAttribute("listado", lista);
		request.getRequestDispatcher("mantenimientos/mantenimiento_prioridades.jsp").forward(request, response);
		
	}
	
	protected void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nom_prioridad = request.getParameter("txtNombre");
		int tiempo_horas = Integer.parseInt(request.getParameter("txtTiempo"));
		
		Prioridad p = new Prioridad();
		p.setNom_prioridad(nom_prioridad);
		p.setTiempo_horas(tiempo_horas);
		
		int value = dao.registrar(p);
		
		if(value ==1) {
			request.setAttribute("mensaje", "Prioridad registrada correctamente");
		}else {
			request.setAttribute("mensaje", "Error al registrar la prioridad");
		}
		listar(request, response);
		
	}
	
	protected void irAEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int id_prioridad = Integer.parseInt(request.getParameter("id_prioridad"));
	    Prioridad p = dao.obtener(id_prioridad);
	    
	    List<Prioridad> lista = dao.listar(); 
	    request.setAttribute("listado", lista);
	    
	    request.setAttribute("prioridadEditable", p);
	    request.getRequestDispatcher("mantenimientos/mantenimiento_prioridades.jsp").forward(request, response);
	}
	
	protected void grabarModificacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id_prioridad = Integer.parseInt(request.getParameter("txtId"));
		String nom_prioridad = request.getParameter("txtNombre");
		int tiempo_horas = Integer.parseInt(request.getParameter("txtTiempo"));
		
		Prioridad p = new Prioridad();
		p.setId_prioridad(id_prioridad);
		p.setNom_prioridad(nom_prioridad);
		p.setTiempo_horas(tiempo_horas);
		
		int value = dao.editar(p);
		
		if(value == 1) {
			request.setAttribute("mensaje", "Prioridad actualizada correctamente");
		}else {
			request.setAttribute("mensaje", "Error al actualizar la prioridad");
		}
		listar(request, response);
		
	}
	
	protected void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id_prioridad = Integer.parseInt(request.getParameter("id_prioridad"));
		
		int value = dao.eliminar(id_prioridad);
		
		if(value == 1) {
			request.setAttribute("mensaje", "Prioridad eliminada correctamente");
		}else {
			request.setAttribute("mensaje", "Error al eliminar - puede tener tickets asociados");
		}
		listar(request, response);
	}

}
