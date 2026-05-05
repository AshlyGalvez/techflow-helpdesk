package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entidad.TipoProblema;
import interfaces.TipoProblemaDAO;
import util.MySQLConexion;

public class MySqlTipoProblemaDAO implements TipoProblemaDAO {

	public int registrar(TipoProblema t) {
		int value = 0;
		Connection cn = null;
		PreparedStatement psm =  null;
		
		try {
			cn = MySQLConexion.getConexion();
			String sql = "INSERT INTO tb_tipo_Problema VALUES (null, ?)";
			psm = cn.prepareStatement(sql);
			psm.setString(1, t.getNom_tipo());
			
			value = psm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(psm != null) psm.close();
				if(cn != null) cn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return value;
	}
	
	public List<TipoProblema> listar(){
		List<TipoProblema>lista = new ArrayList<TipoProblema>();
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		
		try {
			cn = MySQLConexion.getConexion();
			String sql = "SELECT * FROM tb_tipo_Problema";
			psm = cn.prepareStatement(sql);
			rs = psm.executeQuery();
			
			while(rs.next()) {
				TipoProblema tipo = new TipoProblema();
				tipo.setId_tipo(rs.getInt("id_tipo"));
				tipo.setNom_tipo(rs.getString("nom_tipo"));
				lista.add(tipo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs !=null) rs.close();
				if(psm != null) psm.close();
				if(cn != null) cn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return lista;
	}
	
	public TipoProblema obtener(int id_tipo) {
		TipoProblema t = null;
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		
		try {
			cn = MySQLConexion.getConexion();
			String sql = "SELECT * FROM tb_tipo_problema where id_tipo=?";
			psm = cn.prepareStatement(sql);
			psm.setInt(1, id_tipo);
			rs = psm.executeQuery();
			
			if(rs.next()) {
				t = new TipoProblema();
				t.setId_tipo(rs.getInt("id_tipo"));
				t.setNom_tipo(rs.getString("nom_tipo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null)rs.close();
				if(psm != null)psm.close();
				if(cn != null)cn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return t;
	}
	
	public int editar(TipoProblema t) {
		int value = 0;
		Connection cn = null;
		PreparedStatement psm = null;
		
		try {
			cn = MySQLConexion.getConexion();
			String sql = "UPDATE tb_tipo_problema SET nom_tipo=? WHERE id_tipo=?";
			psm= cn.prepareStatement(sql);
			psm.setString(1, t.getNom_tipo());
			psm.setInt(2, t.getId_tipo());
			
			value = psm.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(psm != null) psm.close();
				if(cn != null) cn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	public int eliminar(int id_tipo) {
		int value = 0;
		Connection cn = null;
		PreparedStatement psm = null;
		
		try {
			cn = MySQLConexion.getConexion();
			String sql = "DELETE FROM tb_tipo_Problema where id_tipo=?";
			psm = cn.prepareStatement(sql);
			psm.setInt(1, id_tipo);
			
			value = psm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(psm != null) psm.close();
				if(cn != null) cn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return value;
	}
	
}
