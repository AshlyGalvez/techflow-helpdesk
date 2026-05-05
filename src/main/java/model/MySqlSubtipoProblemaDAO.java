package model;

import entidad.*;
import interfaces.SubtipoProblemaDAO;
import util.MySQLConexion;
import java.sql.*;
import java.util.*;

public class MySqlSubtipoProblemaDAO implements SubtipoProblemaDAO {

    public List<SubtipoProblema> listar() {
        List<SubtipoProblema> lista = new ArrayList<>();
        String sql = "SELECT s.id_subtipo, s.id_tipo, s.nom_subtipo, t.nom_tipo " +
                     "FROM tb_subtipo_problema s " +
                     "INNER JOIN tb_tipo_problema t ON s.id_tipo = t.id_tipo " +
                     "ORDER BY s.id_tipo, s.nom_subtipo";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SubtipoProblema s = new SubtipoProblema();
                s.setId_subtipo(rs.getInt("id_subtipo"));
                s.setId_tipo(rs.getInt("id_tipo"));
                s.setNom_subtipo(rs.getString("nom_subtipo"));
                s.setNom_tipo(rs.getString("nom_tipo"));
                lista.add(s);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public List<SubtipoProblema> listarPorTipo(int idTipo) {
        List<SubtipoProblema> lista = new ArrayList<>();
        String sql = "SELECT id_subtipo, id_tipo, nom_subtipo FROM tb_subtipo_problema " +
                     "WHERE id_tipo = ? ORDER BY nom_subtipo";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idTipo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SubtipoProblema s = new SubtipoProblema();
                    s.setId_subtipo(rs.getInt("id_subtipo"));
                    s.setId_tipo(rs.getInt("id_tipo"));
                    s.setNom_subtipo(rs.getString("nom_subtipo"));
                    lista.add(s);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public int registrar(SubtipoProblema s) {
        int result = -1;
        String sql = "INSERT INTO tb_subtipo_problema (id_tipo, nom_subtipo) VALUES (?, ?)";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, s.getId_tipo());
            ps.setString(2, s.getNom_subtipo());
            result = ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    public SubtipoProblema obtener(int id) {
        SubtipoProblema s = null;
        String sql = "SELECT id_subtipo, id_tipo, nom_subtipo FROM tb_subtipo_problema WHERE id_subtipo = ?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s = new SubtipoProblema();
                    s.setId_subtipo(rs.getInt("id_subtipo"));
                    s.setId_tipo(rs.getInt("id_tipo"));
                    s.setNom_subtipo(rs.getString("nom_subtipo"));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return s;
    }

    public int editar(SubtipoProblema s) {
        int result = -1;
        String sql = "UPDATE tb_subtipo_problema SET id_tipo = ?, nom_subtipo = ? WHERE id_subtipo = ?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, s.getId_tipo());
            ps.setString(2, s.getNom_subtipo());
            ps.setInt(3, s.getId_subtipo());
            result = ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    public int eliminar(int id) {
        int result = -1;
        String sql = "DELETE FROM tb_subtipo_problema WHERE id_subtipo = ?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }
}