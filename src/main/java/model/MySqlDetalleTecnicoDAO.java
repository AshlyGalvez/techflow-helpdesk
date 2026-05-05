package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entidad.DetalleTecnico;
import interfaces.DetalleTecnicoDAO;
import util.MySQLConexion;

public class MySqlDetalleTecnicoDAO implements DetalleTecnicoDAO {

    public int registrar(DetalleTecnico dt) {
        String sql = "INSERT INTO tb_detalle_tecnico (id_tecnico, especialidad, disponibilidad) VALUES (?, ?, 1)";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql)) {
            psm.setInt(1, dt.getId_tecnico());
            psm.setString(2, dt.getEspecialidad());
            return psm.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public List<DetalleTecnico> listar() {
        List<DetalleTecnico> lista = new ArrayList<>();
        String sql =
            "SELECT dt.id_tecnico, dt.especialidad, dt.disponibilidad, CONCAT(u.nombre, ' ', u.apellido) AS nom_tecnico, " +
            "u.correo AS correo_tecnico, " +
            "COUNT(CASE WHEN t.id_estado = 4 THEN 1 END) AS tickets_resueltos " +
            "FROM tb_detalle_tecnico dt " +
            "JOIN tb_usuario u ON dt.id_tecnico = u.id_usuario " +
            "LEFT JOIN tb_ticket t ON t.id_tecnico_asignado = dt.id_tecnico " +
            "GROUP BY dt.id_tecnico, dt.especialidad, dt.disponibilidad, nom_tecnico, correo_tecnico";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql);
             ResultSet rs = psm.executeQuery()) {
            while (rs.next()) {
                DetalleTecnico dt = new DetalleTecnico();
                dt.setId_tecnico(rs.getInt("id_tecnico"));
                dt.setEspecialidad(rs.getString("especialidad"));
                dt.setDisponibilidad(rs.getInt("disponibilidad"));
                dt.setTickets_resueltos(rs.getInt("tickets_resueltos"));
                dt.setNom_tecnico(rs.getString("nom_tecnico"));
                dt.setCorreo_tecnico(rs.getString("correo_tecnico"));
                lista.add(dt);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public DetalleTecnico obtener(int id_tecnico) {
        DetalleTecnico dt = null;
        String sql =
            "SELECT dt.*, CONCAT(u.nombre, ' ', u.apellido) AS nom_tecnico,u.correo AS correo_tecnico " +
            "FROM tb_detalle_tecnico dt " +
            "JOIN tb_usuario u ON dt.id_tecnico = u.id_usuario " +
            "WHERE dt.id_tecnico = ?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql)) {
            psm.setInt(1, id_tecnico);
            try (ResultSet rs = psm.executeQuery()) {
                if (rs.next()) {
                    dt = new DetalleTecnico();
                    dt.setId_tecnico(rs.getInt("id_tecnico"));
                    dt.setEspecialidad(rs.getString("especialidad"));
                    dt.setDisponibilidad(rs.getInt("disponibilidad"));
                    dt.setTickets_resueltos(rs.getInt("tickets_resueltos"));
                    dt.setNom_tecnico(rs.getString("nom_tecnico"));
                    dt.setCorreo_tecnico(rs.getString("correo_tecnico"));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return dt;
    }

    public int editar(DetalleTecnico dt) {
        String sql = "UPDATE tb_detalle_tecnico SET especialidad = ?, disponibilidad = ? WHERE id_tecnico = ?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql)) {
            psm.setString(1, dt.getEspecialidad());
            psm.setInt(2, dt.getDisponibilidad());
            psm.setInt(3, dt.getId_tecnico());
            return psm.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
}