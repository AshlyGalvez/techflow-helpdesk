package dao;

import interfaces.ComentarioDAO;
import interfaces.DetalleTecnicoDAO;
import interfaces.EstadoDAO;
import interfaces.PrioridadDAO;
import interfaces.RolDAO;
import interfaces.SubtipoProblemaDAO;
import interfaces.TicketDAO;
import interfaces.TipoProblemaDAO;
import interfaces.UsuarioDAO;

public abstract class DAOFactory {
	
	public static final int MYSQL = 1;
	
	public abstract TipoProblemaDAO getTipoProblemaDAO();
	public abstract RolDAO getRolDAO();
    public abstract UsuarioDAO getUsuarioDAO();
    public abstract EstadoDAO getEstadoDAO();
    public abstract PrioridadDAO getPrioridadDAO();
    public abstract TicketDAO getTicketDAO();
    public abstract ComentarioDAO getComentarioDAO();
    public abstract DetalleTecnicoDAO getDetalleTecnicoDAO();
    public abstract SubtipoProblemaDAO getSubtipoProblemaDAO();
  
	public static DAOFactory getDAOFactory (int q) {
		switch(q) {
		case MYSQL:
			return new MySqlDAOFactory();
		default:
			return null;
		}
	}
}
