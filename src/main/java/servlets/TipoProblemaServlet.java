package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import entidad.SubtipoProblema;
import entidad.TipoProblema;
import interfaces.SubtipoProblemaDAO;
import interfaces.TipoProblemaDAO;

@WebServlet("/TipoProblemaServlet")
public class TipoProblemaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

		  DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		  TipoProblemaDAO dao = fabrica.getTipoProblemaDAO();
		  SubtipoProblemaDAO daoSub = fabrica.getSubtipoProblemaDAO();

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String tipo = request.getParameter("tipo");
        if (tipo == null) tipo = "list";

        switch (tipo) {
            case "list":   listar(request, response); break;
            case "regist": registrar(request, response); break;
            case "modif":  irAEditar(request, response); break;
            case "edit":   grabarModificacion(request, response); break;
            case "delete": eliminar(request, response); break;

            case "regist-sub":  registrarSubtipo(request, response);break;
            case "modif-sub":   irAEditarSubtipo(request, response);break;
            case "edit-sub":    editarSubtipo(request, response);break;
            case "delete-sub":  eliminarSubtipo(request, response);break;
            default: listar(request, response);
        }
    }

    private void cargarVista(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        request.setAttribute("listado",    dao.listar());
        request.setAttribute("listadoSub", daoSub.listar());
        request.setAttribute("tipos",      dao.listar()); 
        request.getRequestDispatcher("mantenimientos/mantenimiento_tipos.jsp")
               .forward(request, response);
    }

    protected void listar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        cargarVista(request, response);
    }

    protected void registrar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	
        TipoProblema tp = new TipoProblema();
        tp.setNom_tipo(request.getParameter("txtNombre"));
        int r = dao.registrar(tp);
        request.setAttribute("mensaje", r == 1 ? "Tipo registrado correctamente." : "Error al registrar el tipo.");
        cargarVista(request, response);
    }

    protected void irAEditar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	
        int id = Integer.parseInt(request.getParameter("id_tipo"));
        request.setAttribute("tData", dao.obtener(id));
        cargarVista(request, response);
    }

    protected void grabarModificacion(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	
        int id = Integer.parseInt(request.getParameter("id_tipo"));
        String nom = request.getParameter("txtNombre");
        int r = dao.editar(new TipoProblema(id, nom));
        request.setAttribute("mensaje", r == 1 ? "Tipo actualizado correctamente." : "Error al actualizar.");
        cargarVista(request, response);
    }

    protected void eliminar(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	
        int id = Integer.parseInt(request.getParameter("id"));
        int r  = dao.eliminar(id);
        request.setAttribute("mensaje", r == 1 ? "Tipo eliminado correctamente." : "Error: tiene registros asociados.");
        cargarVista(request, response);
    }

    protected void registrarSubtipo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	
        SubtipoProblema s = new SubtipoProblema();
        s.setId_tipo(Integer.parseInt(request.getParameter("idTipoSub")));
        s.setNom_subtipo(request.getParameter("txtNombreSub"));
        int r = daoSub.registrar(s);
        request.setAttribute("mensaje", r == 1 ? "Problema específico registrado." : "Error al registrar el problema.");
        cargarVista(request, response);
    }

    protected void irAEditarSubtipo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	
        int id = Integer.parseInt(request.getParameter("id_subtipo"));
        request.setAttribute("sData", daoSub.obtener(id));
        cargarVista(request, response);
    }

    protected void editarSubtipo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	
        SubtipoProblema s = new SubtipoProblema();
        s.setId_subtipo(Integer.parseInt(request.getParameter("id_subtipo")));
        s.setId_tipo(Integer.parseInt(request.getParameter("idTipoSub")));
        s.setNom_subtipo(request.getParameter("txtNombreSub"));
        int r = daoSub.editar(s);
        request.setAttribute("mensaje", r == 1 ? "Problema actualizado correctamente." : "Error al actualizar.");
        cargarVista(request, response);
    }

    protected void eliminarSubtipo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	
        int id = Integer.parseInt(request.getParameter("id"));
        int r  = daoSub.eliminar(id);
        request.setAttribute("mensaje", r == 1 ? "Problema eliminado correctamente." : "Error al eliminar.");
        cargarVista(request, response);
    }
}