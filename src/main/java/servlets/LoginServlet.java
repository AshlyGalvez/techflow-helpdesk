package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.DAOFactory;
import entidad.Usuario;
import interfaces.UsuarioDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opc = request.getParameter("opc");

        if (opc == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        	if (opc.equals("login")) {
	            String vLogin = request.getParameter("txtUsuario");
	            String vClave = request.getParameter("txtClave");
	
	            DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	            UsuarioDAO dao = fabrica.getUsuarioDAO();
	            Usuario obj = dao.validarAcceso(vLogin, vClave);

            if (obj != null) {
                HttpSession session = request.getSession();
                session.setAttribute("objUsuario", obj);
                session.setAttribute("nombre", obj.getNombre());
                session.setAttribute("apellido", obj.getApellido());
                session.setAttribute("rol", obj.getId_rol());
                response.sendRedirect("DashboardServlet");
                
            } else {
                request.setAttribute("mensaje", "Usuario o clave incorrectos");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } else if (opc.equals("logout")) {
            HttpSession session = request.getSession(false);
            if (session != null) session.invalidate();
            response.sendRedirect("login.jsp");
        }
    }
}