package model;
import entidad.Usuario;
import interfaces.UsuarioDAO;
import util.MySQLConexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUsuarioDAO implements UsuarioDAO {


    public Usuario validarAcceso(String login, String password) {
        Usuario u = null;
        String sql = "SELECT u.*, r.nom_rol FROM tb_usuario u " +
                     "INNER JOIN tb_rol r ON u.id_rol = r.id_rol " +
                     "WHERE u.login=? AND u.contrasena=? AND u.estado_logico=1";
        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setId_rol(rs.getInt("id_rol"));
                u.setNom_rol(rs.getString("nom_rol"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return u;
    }


    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.*, r.nom_rol FROM tb_usuario u " +
                     "INNER JOIN tb_rol r ON u.id_rol = r.id_rol";
        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setDni(rs.getString("dni"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setCorreo(rs.getString("correo"));
                u.setLogin(rs.getString("login"));
                u.setContrasena(rs.getString("contrasena")); 
                u.setId_rol(rs.getInt("id_rol"));
                u.setNom_rol(rs.getString("nom_rol"));
                u.setEstado_logico(rs.getInt("estado_logico")); 
                lista.add(u);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }


    public Usuario buscarPorId(int id) {
        Usuario u = null;
        String sql = "SELECT * FROM tb_usuario WHERE id_usuario = ?";
        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setDni(rs.getString("dni"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setCorreo(rs.getString("correo"));
                u.setLogin(rs.getString("login"));
                u.setContrasena(rs.getString("contrasena"));
                u.setId_rol(rs.getInt("id_rol"));
                u.setEstado_logico(rs.getInt("estado_logico"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return u;
    }


    public int registrar(Usuario u) {
        int res = 0;
        String sql = "INSERT INTO tb_usuario VALUES (null,?,?,?,?,?,?,?,1)";
        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getDni());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getLogin());
            ps.setString(6, u.getContrasena());
            ps.setInt(7, u.getId_rol());
            res = ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return res;
    }


    public int actualizar(Usuario u) {
        int res = 0;
        String sql = "UPDATE tb_usuario SET dni=?, nombre=?, apellido=?, correo=?, login=?, contrasena=?, id_rol=? WHERE id_usuario=?";
        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getDni());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setString(4, u.getCorreo());
            ps.setString(5, u.getLogin());
            ps.setString(6, u.getContrasena());
            ps.setInt(7, u.getId_rol());
            ps.setInt(8, u.getId_usuario());
            res = ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return res;
    }

    public int eliminarLogico(int id) {
        int res = 0;
        String sql = "UPDATE tb_usuario SET estado_logico = (1 - estado_logico) WHERE id_usuario = ?";
        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            res = ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return res;
    }

    public List<Usuario> listarTecnicos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre, u.apellido " +
                     "FROM tb_usuario u " +
                     "LEFT JOIN tb_detalle_tecnico dt ON u.id_usuario = dt.id_tecnico " +
                     "WHERE u.estado_logico = 1 AND dt.id_tecnico IS NULL";
        
        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                lista.add(u);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
}