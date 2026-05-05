package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import entidad.Usuario;
import entidad.Rol;
import interfaces.UsuarioDAO;
import interfaces.RolDAO;

@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    DAOFactory factoria = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
    UsuarioDAO daoUsuario = factoria.getUsuarioDAO();
    RolDAO daoRol = factoria.getRolDAO();

    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String tipo = request.getParameter("tipo");
        if (tipo == null) tipo = "list";

        switch (tipo) {
            case "list": listar(request, response); break;
            case "registrar": registrar(request, response); break;
            case "modif": irAEditar(request, response); break;
            case "actualizar": actualizar(request, response); break;
            case "delete": eliminar(request, response); break;
            default: listar(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("usuarios", daoUsuario.listarTodos());
        request.setAttribute("roles", daoRol.listar()); 
        request.getRequestDispatcher("mantenimientos/mantenimiento_usuarios.jsp").forward(request, response);
    }

    private void irAEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id_usuario"));
        Usuario u = daoUsuario.buscarPorId(id);
        
        request.setAttribute("u", u);
        request.setAttribute("roles", daoRol.listar()); 
        request.setAttribute("usuarios", daoUsuario.listarTodos()); 
        request.getRequestDispatcher("mantenimientos/mantenimiento_usuarios.jsp").forward(request, response);
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario u = new Usuario();
        u.setDni(request.getParameter("txtDni"));
        u.setNombre(request.getParameter("txtNombre"));
        u.setApellido(request.getParameter("txtApellido"));
        u.setCorreo(request.getParameter("txtCorreo"));
        u.setLogin(request.getParameter("txtLogin"));
        u.setContrasena(request.getParameter("txtContrasena"));
        u.setId_rol(Integer.parseInt(request.getParameter("cboRol")));
        
        int ok = daoUsuario.registrar(u);
        if (ok > 0) request.setAttribute("mensaje", "Usuario registrado!");
        else request.setAttribute("mensaje", "Error al registrar");
        
        listar(request, response);
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario u = new Usuario();
        u.setId_usuario(Integer.parseInt(request.getParameter("txtId")));
        u.setDni(request.getParameter("txtDni"));
        u.setNombre(request.getParameter("txtNombre"));
        u.setApellido(request.getParameter("txtApellido"));
        u.setCorreo(request.getParameter("txtCorreo"));
        u.setLogin(request.getParameter("txtLogin"));
        u.setContrasena(request.getParameter("txtContrasena"));
        u.setId_rol(Integer.parseInt(request.getParameter("cboRol")));
        
        int ok = daoUsuario.actualizar(u);
        if (ok > 0) request.setAttribute("mensaje", "Usuario actualizado!");
        else request.setAttribute("mensaje", "Error al actualizar");
        
        listar(request, response);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id_usuario"));
                daoUsuario.eliminarLogico(id);
        
        response.sendRedirect("UsuarioServlet?tipo=list");
    }
}