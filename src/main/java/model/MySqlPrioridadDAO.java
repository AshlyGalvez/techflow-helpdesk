package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entidad.Prioridad;
import interfaces.PrioridadDAO;
import util.MySQLConexion;

public class MySqlPrioridadDAO implements PrioridadDAO{
	
	public int registrar(Prioridad p) {
        int value = 0;
        Connection cn = null;
        PreparedStatement psm = null;
        try {
            cn = MySQLConexion.getConexion();
            String sql = "INSERT INTO tb_prioridad (nom_prioridad, tiempo_horas) VALUES (?,?)";
            psm = cn.prepareStatement(sql);
            psm.setString(1, p.getNom_prioridad());
            psm.setInt(2, p.getTiempo_horas());
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
	
	public List<Prioridad> listar() {
        List<Prioridad> lista = new ArrayList<Prioridad>();
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        try {
            cn = MySQLConexion.getConexion();
            psm = cn.prepareStatement("SELECT * FROM tb_prioridad");
            rs = psm.executeQuery();
            while (rs.next()) {
                Prioridad p = new Prioridad();
                p.setId_prioridad(rs.getInt("id_prioridad"));
                p.setNom_prioridad(rs.getString("nom_prioridad"));
                p.setTiempo_horas(rs.getInt("tiempo_horas"));
                lista.add(p);
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
	
	public Prioridad obtener(int id_prioridad) {
        Prioridad p = null;
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        try {
            cn = MySQLConexion.getConexion();
            psm = cn.prepareStatement(
                "SELECT * FROM tb_prioridad WHERE id_prioridad = ?");
            psm.setInt(1, id_prioridad);
            rs = psm.executeQuery();
            if (rs.next()) {
                p = new Prioridad();
                p.setId_prioridad(rs.getInt("id_prioridad"));
                p.setNom_prioridad(rs.getString("nom_prioridad"));
                p.setTiempo_horas(rs.getInt("tiempo_horas"));
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
        return p;
    }

	
	public int editar(Prioridad p) {
        int value = 0;
        Connection cn = null;
        PreparedStatement psm = null;
        try {
            cn = MySQLConexion.getConexion();
                String sql = "UPDATE tb_prioridad SET nom_prioridad=?, tiempo_horas=? WHERE id_prioridad=?";
            psm=cn.prepareStatement(sql);    
            psm.setString(1, p.getNom_prioridad());
            psm.setInt(2, p.getTiempo_horas());
            psm.setInt(3, p.getId_prioridad());
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
	
	public int eliminar(int id_prioridad) {
        int value = 0;
        Connection cn = null;
        PreparedStatement psm = null;
        try {
            cn = MySQLConexion.getConexion();
            String sql= "DELETE FROM tb_prioridad WHERE id_prioridad = ?";
            psm = cn.prepareStatement(sql);
            psm.setInt(1, id_prioridad);
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
