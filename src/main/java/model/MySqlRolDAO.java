package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entidad.Rol;
import interfaces.RolDAO;
import util.MySQLConexion;

public class MySqlRolDAO implements RolDAO{

	public int registrar(Rol rol) {
		int value = 0;
		Connection cn = null;
		PreparedStatement psm =  null;
		
		try {
			cn = MySQLConexion.getConexion();
			String sql = "INSERT INTO tb_rol (nom_rol) VALUES (?)";
			psm = cn.prepareStatement(sql);
			psm.setString(1, rol.getNombre());
			
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
	
	public List<Rol> listar(){
		List<Rol>lista = new ArrayList<Rol>();
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		
		try {
			cn = MySQLConexion.getConexion();
			String sql = "SELECT * FROM tb_rol";
			psm = cn.prepareStatement(sql);
			rs = psm.executeQuery();
			
			while(rs.next()) {
				Rol rol = new Rol();
				rol.setId_rol(rs.getInt("id_rol"));
				rol.setNombre(rs.getString("nom_rol"));
				lista.add(rol);
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
	
	public Rol obtener(int id_rol) {
		Rol rol = null;
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
		
		try {
			cn = MySQLConexion.getConexion();
			String sql = "SELECT * FROM tb_rol where id_rol=?";
			psm = cn.prepareStatement(sql);
			psm.setInt(1, id_rol);
			rs = psm.executeQuery();
			if(rs.next()) {
				rol = new Rol();
				rol.setId_rol(rs.getInt("id_rol"));
				rol.setNombre(rs.getString("nom_rol"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs !=null) rs.close();
				if(psm != null) psm.close();
				if(cn != null) cn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return rol;
	}
	
	public int editar(Rol rol) {
		int value = 0;
		Connection cn = null;
		PreparedStatement psm = null;
		
		try {
			cn = MySQLConexion.getConexion();
			String sql = "UPDATE tb_rol SET nom_rol=? WHERE id_rol=?";
			psm = cn.prepareStatement(sql);
			psm.setString(1, rol.getNombre());
			psm.setInt(2, rol.getId_rol());
			
			value = psm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(psm !=null) psm.close();
				if(cn !=null) cn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return value;
	}
}
