package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entidad.Estado;
import interfaces.EstadoDAO;
import util.MySQLConexion;

public class MySqlEstadoDAO implements EstadoDAO{
	
	 public int registrar(Estado e) {
	        int value = 0;
	        Connection cn = null;
	        PreparedStatement psm = null;
	        try {
	            cn = MySQLConexion.getConexion();
	            String sql = "INSERT INTO tb_estado (nom_estado) VALUES (?)";
	            psm = cn.prepareStatement(sql);
	            psm.setString(1, e.getNom_estado());
	            value = psm.executeUpdate();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        } finally {
	            try {
	                if (psm != null) psm.close();
	                if (cn != null) cn.close();
	            } catch (Exception ex) { ex.printStackTrace(); }
	        }
	        return value;
	    }
	 
	 public List<Estado> listar() {
	        List<Estado> lista = new ArrayList<Estado>();
	        Connection cn = null;
	        PreparedStatement psm = null;
	        ResultSet rs = null;
	        try {
	            cn = MySQLConexion.getConexion();
	            psm = cn.prepareStatement("SELECT * FROM tb_estado");
	            rs = psm.executeQuery();
	            while (rs.next()) {
	                Estado e = new Estado();
	                e.setId_estado(rs.getInt("id_estado"));
	                e.setNom_estado(rs.getString("nom_estado"));
	                lista.add(e);
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
	 
	 public Estado obtener(int id_estado) {
	        Estado e = null;
	        Connection cn = null;
	        PreparedStatement psm = null;
	        ResultSet rs = null;
	        try {
	            cn = MySQLConexion.getConexion();
	            psm = cn.prepareStatement("SELECT * FROM tb_estado WHERE id_estado = ?");
	            psm.setInt(1, id_estado);
	            rs = psm.executeQuery();
	            if (rs.next()) {
	                e = new Estado();
	                e.setId_estado(rs.getInt("id_estado"));
	                e.setNom_estado(rs.getString("nom_estado"));
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (psm != null) psm.close();
	                if (cn != null) cn.close();
	            } catch (Exception ex) { ex.printStackTrace(); }
	        }
	        return e;
	    }
	 
	 public int editar(Estado e) {
	        int value = 0;
	        Connection cn = null;
	        PreparedStatement psm = null;
	        try {
	            cn = MySQLConexion.getConexion();
	            psm = cn.prepareStatement(
	                "UPDATE tb_estado SET nom_estado = ? WHERE id_estado = ?");
	            psm.setString(1, e.getNom_estado());
	            psm.setInt(2, e.getId_estado());
	            value = psm.executeUpdate();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        } finally {
	            try {
	                if (psm != null) psm.close();
	                if (cn != null) cn.close();
	            } catch (Exception ex) { ex.printStackTrace(); }
	        }
	        return value;
	    }
	 
	 public int eliminar(int id_estado) {
	        int value = 0;
	        Connection cn = null;
	        PreparedStatement psm = null;
	        try {
	            cn = MySQLConexion.getConexion();
	            psm = cn.prepareStatement(
	                "DELETE FROM tb_estado WHERE id_estado = ?");
	            psm.setInt(1, id_estado);
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

}
