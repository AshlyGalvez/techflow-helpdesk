package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entidad.Comentario;
import interfaces.ComentarioDAO;
import util.MySQLConexion;

public class MySqlComentarioDAO implements ComentarioDAO{

	    public int registrar(Comentario c) {
	        int value = 0;
	        Connection cn = null;
	        PreparedStatement psm = null;
	        try {
	            cn = MySQLConexion.getConexion();
	            String sql = "INSERT INTO tb_comentario (id_ticket, id_usuario, texto_comentario, es_solucion)VALUES (?,?,?,?)";
	            psm = cn.prepareStatement(sql);
	            psm.setInt(1, c.getId_ticket());
	            psm.setInt(2, c.getId_usuario());
	            psm.setString(3, c.getTexto_comentario());
	            psm.setInt(4, c.getEs_solucion());
	            value = psm.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (psm != null) psm.close();
	                if (cn != null) cn.close();
	            } catch (Exception e) { e.printStackTrace(); }
	        }
	        return value;
	    }

	    public List<Comentario> listarPorTicket(int id_ticket) {
	        List<Comentario> lista = new ArrayList<Comentario>();
	        Connection cn = null;
	        PreparedStatement psm = null;
	        ResultSet rs = null;
	        try {
	            cn = MySQLConexion.getConexion();
	            String sql = "SELECT c.*, CONCAT(u.nombre,' ',u.apellido) AS nom_usuario FROM tb_comentario c " +
	                         "JOIN tb_usuario u ON c.id_usuario = u.id_usuario " +
	                         "WHERE c.id_ticket = ? ORDER BY c.fecha_reg ASC";
	            psm = cn.prepareStatement(sql);
	            psm.setInt(1, id_ticket);
	            rs = psm.executeQuery();
	            while (rs.next()) {
	                Comentario c = new Comentario();
	                c.setId_comentario(rs.getInt("id_comentario"));
	                c.setId_ticket(rs.getInt("id_ticket"));
	                c.setId_usuario(rs.getInt("id_usuario"));
	                c.setTexto_comentario(rs.getString("texto_comentario"));
	                c.setFecha_reg(rs.getTimestamp("fecha_reg"));
	                c.setEs_solucion(rs.getInt("es_solucion"));
	                c.setNom_usuario(rs.getString("nom_usuario"));
	                lista.add(c);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (psm != null) psm.close();
	                if (cn != null) cn.close();
	            } catch (Exception e) { e.printStackTrace(); }
	        }
	        return lista;
	    }

}
