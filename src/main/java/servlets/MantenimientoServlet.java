package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MySqlTicketDAO; // Tu DAO que acabamos de arreglar
import model.MySqlUsuarioDAO; // El DAO de usuarios para los roles

@WebServlet("/MantenimientoServlet")
public class MantenimientoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    MySqlTicketDAO daoTick = new MySqlTicketDAO();

    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if (accion == null) {
            response.sendRedirect("DashboardServlet");
            return;
        }

        switch (accion) {
            case "estados":
                request.getRequestDispatcher("mantenimientos/mantenimiento_estados.jsp").forward(request, response);
                break;
                
            case "prioridades":
                request.setAttribute("lista", daoTick.listarPrioridades());
                request.getRequestDispatcher("mantenimientos/mantenimiento_prioridades.jsp").forward(request, response);
                break;
                
            case "tipos":
                request.setAttribute("lista", daoTick.listarTipos());
                request.getRequestDispatcher("mantenimientos/mantenimiento_tipos.jsp").forward(request, response);
                break;
                
            case "roles":
                request.getRequestDispatcher("mantenimientos/mantenimiento_roles.jsp").forward(request, response);
                break;

            case "usuarios":
                request.getRequestDispatcher("mantenimientos/mantenimiento_usuarios.jsp").forward(request, response);
                break;
                
            default:
                response.sendRedirect("DashboardServlet");
                break;
        }
    }
}