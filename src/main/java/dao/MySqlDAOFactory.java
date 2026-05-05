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
import model.MySqlComentarioDAO;
import model.MySqlEstadoDAO;
import model.MySqlPrioridadDAO;
import model.MySqlRolDAO;
import model.MySqlTicketDAO;
import model.MySqlTipoProblemaDAO;
import model.*;

public class MySqlDAOFactory extends DAOFactory{
	
	public TipoProblemaDAO getTipoProblemaDAO() {
		return new MySqlTipoProblemaDAO();
	}
	
	public RolDAO getRolDAO() {
		return new MySqlRolDAO();
	}
	
	public UsuarioDAO getUsuarioDAO() {
		return new MySqlUsuarioDAO(); 
	}
	
    public EstadoDAO getEstadoDAO() {
    	return new MySqlEstadoDAO();
    }
    
    
    public PrioridadDAO getPrioridadDAO() {
    	return new MySqlPrioridadDAO();
    }
    
    public TicketDAO getTicketDAO() {
    	return new MySqlTicketDAO();
    }
    
    public ComentarioDAO getComentarioDAO() {
    	return new MySqlComentarioDAO();
    }
    
    public DetalleTecnicoDAO getDetalleTecnicoDAO() {
    	return new MySqlDetalleTecnicoDAO();
    }
    
    public SubtipoProblemaDAO getSubtipoProblemaDAO() {
        return new MySqlSubtipoProblemaDAO();
    }
    
    
}
