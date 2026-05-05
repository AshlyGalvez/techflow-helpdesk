package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import entidad.Rol;
import interfaces.RolDAO;

@WebServlet("/RolServlet")
public class RolServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    RolDAO dao = fabrica.getRolDAO();
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String tipo = request.getParameter("tipo");
        if (tipo == null || tipo.isEmpty()) {
            listar(request, response);
            return;
        }
        
        switch(tipo) {
            case "list": listar(request, response); break;
            case "regist": registrar(request, response); break;
            case "modif": modificar(request, response); break; 
            case "edit": grabarModificacion(request, response); break;
            default: listar(request, response); break;
        }
    }
    
    protected void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Rol> lista = dao.listar();
        request.setAttribute("listado", lista);
        request.getRequestDispatcher("mantenimientos/mantenimiento_roles.jsp").forward(request, response);
    }
    
    protected void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("txtNombre");
        Rol r = new Rol();
        r.setNombre(nombre);
        
        int value = dao.registrar(r);
        
        if(value == 1) {
            request.setAttribute("mensaje", "Rol registrado con éxito");
        } else {
            request.setAttribute("mensaje", "Error al registrar el rol");
        }
        listar(request, response); 
    }
    
    protected void modificar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id_rol"));
            Rol rol = dao.obtener(id);
            
            request.setAttribute("rolEdit", rol); 
        } catch (Exception e) {
            request.setAttribute("mensaje", "Error al recuperar datos del rol");
        }
        listar(request, response); 
    }
    
    protected void grabarModificacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id_rol = Integer.parseInt(request.getParameter("id_rol")); 
            String nombre = request.getParameter("txtNombre");
            
            Rol rol = new Rol();
            rol.setId_rol(id_rol);
            rol.setNombre(nombre);
            
            int value = dao.editar(rol);
            
            if(value == 1) {
                request.setAttribute("mensaje", "Rol actualizado correctamente");
            } else {
                request.setAttribute("mensaje", "Error al actualizar en la base de datos");
            }
        } catch (Exception e) {
            request.setAttribute("mensaje", "Datos inválidos para la actualización");
        }
        listar(request, response);
    }
}