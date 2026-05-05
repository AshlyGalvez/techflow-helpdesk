package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import entidad.*;
import interfaces.TicketDAO;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario u = (Usuario) session.getAttribute("objUsuario");

        if (u == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        TicketDAO daoTick  = fabrica.getTicketDAO();

        String tipo = request.getParameter("tipo");
        if (tipo == null) tipo = "dashboard";

        switch (tipo) {
            case "dashboard":  dashboard(request, response, u, daoTick);  break;
            case "reportes":   reportes(request, response, daoTick);  break;
        }
    }

    private void dashboard(HttpServletRequest request, HttpServletResponse response,
                           Usuario u, TicketDAO daoTick)
            throws ServletException, IOException {

        int total, abiertos, proceso, resueltos, cerrados;
        if (u.getId_rol() == 1) {
            total     = daoTick.contarTicketsPorEstado(0);
            abiertos  = daoTick.contarTicketsPorEstado(1);
            proceso   = daoTick.contarTicketsPorEstado(2);
            resueltos = daoTick.contarTicketsPorEstado(3);
            cerrados  = daoTick.contarTicketsPorEstado(4);
        } else if (u.getId_rol() == 2) {
            total     = daoTick.contarTickets(0, 2, 0);
            abiertos  = daoTick.contarTickets(0, 2, 1);
            proceso   = daoTick.contarTickets(u.getId_usuario(), 2, 2);
            resueltos = daoTick.contarTickets(u.getId_usuario(), 2, 3);
            cerrados  = daoTick.contarTickets(u.getId_usuario(), 2, 4);
        } else {
            total     = daoTick.contarTickets(u.getId_usuario(), u.getId_rol(), 0);
            abiertos  = daoTick.contarTickets(u.getId_usuario(), u.getId_rol(), 1);
            proceso   = daoTick.contarTickets(u.getId_usuario(), u.getId_rol(), 2);
            resueltos = daoTick.contarTickets(u.getId_usuario(), u.getId_rol(), 3);
            cerrados  = daoTick.contarTickets(u.getId_usuario(), u.getId_rol(), 4);
        }

        List<Ticket> listaReciente;
        if      (u.getId_rol() == 1) listaReciente = daoTick.listarTicketsDinamico(0, 1, 0, 5);
        else if (u.getId_rol() == 2) listaReciente = daoTick.listarTicketsDinamico(u.getId_usuario(), 2, 0, 5);
        else                         listaReciente = daoTick.listarTicketsDinamico(u.getId_usuario(), 3, 0, 5);

        
        List<TipoProblema> listaTopCategorias = daoTick.listarCategoriasMes();

        request.setAttribute("cantTotal",           total);
        request.setAttribute("cantPendientes",       abiertos);
        request.setAttribute("cantProceso",          proceso);
        request.setAttribute("cantResueltos",        resueltos);
        request.setAttribute("cantCerrados",         cerrados);
        request.setAttribute("ticketsRecientes",     listaReciente);
        request.setAttribute("listaTopCategorias",   listaTopCategorias);
        request.setAttribute("totalUsuarios",        daoTick.contarUsuariosPorRol(3));
        request.setAttribute("totalTecnicos",        daoTick.contarUsuariosPorRol(2));

        String jspDestino = (u.getId_rol() == 1) ? "panel_admin.jsp" : "dashboard.jsp";
        request.getRequestDispatcher(jspDestino).forward(request, response);
    }

    private void reportes(HttpServletRequest request, HttpServletResponse response,TicketDAO daoTick)throws ServletException, IOException {

		List<Ticket> reporteEstado       = daoTick.reporteResumenPorEstado();
		List<TipoProblema> reporteTipo   = daoTick.reportePorTipo();
		List<Usuario> reporteTecnicos    = daoTick.reporteRendimientoTecnicos();

		int totalEstado = reporteEstado.stream().mapToInt(Ticket::getTotal).sum();
		int totalTipo   = reporteTipo.stream().mapToInt(TipoProblema::getCantidad).sum();

		String tab = request.getParameter("tab");
		if (tab == null || tab.isEmpty()) tab = "estado";

			request.setAttribute("reporteEstado",    reporteEstado);
			request.setAttribute("reporteTipo",      reporteTipo);
			request.setAttribute("reporteTecnicos",  reporteTecnicos);
			request.setAttribute("totalEstado",      totalEstado);
			request.setAttribute("totalTipo",        totalTipo);
			request.setAttribute("tabActivo",        tab);
			request.getRequestDispatcher("reportes.jsp").forward(request, response);
    	}
    

}