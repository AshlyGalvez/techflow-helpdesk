package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import entidad.Estado;
import interfaces.EstadoDAO;


@WebServlet("/EstadoServlet")
public class EstadoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EstadoServlet() {
     
    }
    
    DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    EstadoDAO dao = fabrica.getEstadoDAO();

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String tipo = request.getParameter("tipo");
		if(tipo == null) tipo = "list";
		
		switch (tipo) {
			case "list": listar(request, response); break;
			case "regist": registrar(request, response); break;
			case "modif": irAEditar(request, response); break;
			case "edit": grabarModificacion(request, response);break;
			case "elim": eliminar(request, response); break;
		}
		
	}
	
	protected void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Estado> lista = dao.listar();
		request.setAttribute("listado", lista);
		request.getRequestDispatcher("mantenimientos/mantenimiento_estados.jsp").forward(request, response);
		
	}
	
	protected void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nom_estado = request.getParameter("txtNombre");
		
		Estado e = new Estado();
		e.setNom_estado(nom_estado);
		
		int value = dao.registrar(e);
		
		if(value == 1) {
			request.setAttribute("mensaje", "Estado registrado correctamente");
		}else {
			request.setAttribute("mensaje", "Error al registrar el estado");
			
		}
		listar(request, response);		
	}
	
	protected void irAEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int id_estado = Integer.parseInt(request.getParameter("id_estado"));
	    Estado e = dao.obtener(id_estado);
	    
	    request.setAttribute("estadoEditable", e);

	    request.setAttribute("listado", dao.listar()); 
	    
	    request.getRequestDispatcher("mantenimientos/mantenimiento_estados.jsp").forward(request, response);
	}
	
	protected void grabarModificacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id_estado = Integer.parseInt(request.getParameter("txtId"));
		String nom_estado = request.getParameter("txtNombre");
		
		Estado e = new Estado();
		e.setId_estado(id_estado);
		e.setNom_estado(nom_estado);
		
		int value = dao.editar(e);
		
		if(value == 1) {
			request.setAttribute("mensaje", "Estado actualizado correctamente");
		}else {
			request.setAttribute("mensaje", "Error al actualizar el estado");
		}
		listar(request, response);
		
	}
	
	protected void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id_estado = Integer.parseInt(request.getParameter("id_estado"));
		int value = dao.eliminar(id_estado);
		
		if(value == 1) {
			request.setAttribute("mensaje", "Estado eliminado correctado");
		}else {
			request.setAttribute("mensaje", "Error al eliminar - puede tener tickets asosiados");
		}
		listar(request, response);
	}

}
