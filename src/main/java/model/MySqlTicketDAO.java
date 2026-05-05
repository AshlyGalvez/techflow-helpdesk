package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entidad.*;
import interfaces.*;
import util.MySQLConexion;

public class MySqlTicketDAO implements TicketDAO {

    public List<Prioridad> listarPrioridades() {
        List<Prioridad> lista = new ArrayList<>();
        String sql = "SELECT id_prioridad, nom_prioridad FROM tb_prioridad";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql);
             ResultSet rs = psm.executeQuery()) {
            while (rs.next()) {
                Prioridad p = new Prioridad();
                p.setId_prioridad(rs.getInt(1));
                p.setNom_prioridad(rs.getString(2));
                lista.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public List<TipoProblema> listarTipos() {
        List<TipoProblema> lista = new ArrayList<>();
        String sql = "SELECT id_tipo, nom_tipo FROM tb_tipo_problema";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql);
             ResultSet rs = psm.executeQuery()) {
            while (rs.next()) {
                TipoProblema tp = new TipoProblema();
                tp.setId_tipo(rs.getInt(1));
                tp.setNom_tipo(rs.getString(2));
                lista.add(tp);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public int registrarTicket(Ticket t) {
        int result = -1;
        String sql = "INSERT INTO tb_ticket (titulo, descripcion, id_usuario_reporta, id_tipo, id_subtipo, id_prioridad, id_estado, fecha_reg) VALUES (?, ?, ?, ?, ?, ?, 1, NOW())";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql)) {
            psm.setString(1, t.getTitulo());
            psm.setString(2, t.getDescripcion());
            psm.setInt(3, t.getId_usuario_reporta());
            psm.setInt(4, t.getId_tipo());
            psm.setInt(5, t.getId_subtipo());
            psm.setInt(6, t.getId_prioridad());

            result = psm.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    public int asignarTecnico(int idTicket, int idTecnico) {
        int salida = -1;
        String sql = "UPDATE tb_ticket SET id_tecnico_asignado = ?, id_estado = 2 WHERE id_ticket = ?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql)) {
            psm.setInt(1, idTecnico);
            psm.setInt(2, idTicket);
            salida = psm.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return salida;
    }

    public int resolverTicket(int idTicket, int idTecnico, String comentario) {
        String sql = "{CALL usp_resolverTicket2(?, ?, ?)}";
        try (Connection cn = MySQLConexion.getConexion();
             CallableStatement cs = cn.prepareCall(sql)) {
            cs.setInt(1, idTicket);
            cs.setInt(2, idTecnico);
            cs.setString(3, comentario);
            cs.execute();
            return 1;
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

    public int contarTickets(int idUsuario, int idRol, int idEstado) {
        int total = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM tb_ticket WHERE 1=1");
        if (idRol == 2) {
            if (idEstado == 1) sql.append(" AND id_estado = 1");
            else if (idEstado >= 2) sql.append(" AND id_estado = ? AND id_tecnico_asignado = ?");
        } else if (idRol == 3) {
            sql.append(" AND id_usuario_reporta = ?");
            if (idEstado > 0) sql.append(" AND id_estado = ?");
        } else if (idRol == 1) {
            if (idEstado > 0) sql.append(" AND id_estado = ?");
        }
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement pstm = cn.prepareStatement(sql.toString())) {
            int pIdx = 1;
            if (idRol == 2) {
                if (idEstado >= 2) { pstm.setInt(pIdx++, idEstado); pstm.setInt(pIdx++, idUsuario); }
            } else if (idRol == 3) {
                pstm.setInt(pIdx++, idUsuario);
                if (idEstado > 0) pstm.setInt(pIdx++, idEstado);
            } else if (idRol == 1) {
                if (idEstado > 0) pstm.setInt(pIdx++, idEstado);
            }
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) total = rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return total;
    }

    public List<Ticket> listarTicketsDinamico(int idUsuario, int idRol, int idEstado, int limite) {
        List<Ticket> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT t.*, tp.nom_tipo, e.nom_estado, p.nom_prioridad, " +
            "u.nombre as nom_usuario, " +
            "CONCAT(tec.nombre, ' ', tec.apellido) as nom_tecnico " +
            "FROM tb_ticket t " +
            "LEFT JOIN tb_tipo_problema tp ON t.id_tipo = tp.id_tipo " +
            "LEFT JOIN tb_estado e ON t.id_estado = e.id_estado " +
            "LEFT JOIN tb_prioridad p ON t.id_prioridad = p.id_prioridad " +
            "LEFT JOIN tb_usuario u ON t.id_usuario_reporta = u.id_usuario " +
            "LEFT JOIN tb_usuario tec ON t.id_tecnico_asignado = tec.id_usuario " +
            "WHERE 1=1 "
        );
 
        if (idRol == 2) {
            sql.append("AND (t.id_tecnico_asignado = ? OR t.id_tecnico_asignado IS NULL OR t.id_tecnico_asignado = 0) ");
        } else if (idRol == 3) {
            sql.append("AND t.id_usuario_reporta = ? ");
        }
 
        if (idEstado > 0) sql.append("AND t.id_estado = ? ");
        sql.append("ORDER BY t.fecha_reg DESC ");
        if (limite > 0) sql.append("LIMIT ?");
 
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql.toString())) {
            int pIndex = 1;
            if (idRol == 2 || idRol == 3) psm.setInt(pIndex++, idUsuario);
            if (idEstado > 0) psm.setInt(pIndex++, idEstado);
            if (limite > 0) psm.setInt(pIndex, limite);
            try (ResultSet rs = psm.executeQuery()) {
                while (rs.next()) {
                    Ticket t = new Ticket();
                    t.setId_ticket(rs.getInt("id_ticket"));
                    t.setTitulo(rs.getString("titulo"));
                    t.setFecha_reg(rs.getTimestamp("fecha_reg"));
                    t.setNom_tipo(rs.getString("nom_tipo"));
                    t.setNom_estado(rs.getString("nom_estado"));
                    t.setNom_prioridad(rs.getString("nom_prioridad"));
                    t.setNom_usuario_reporta(rs.getString("nom_usuario"));
                    t.setNom_tecnico_asignado(rs.getString("nom_tecnico")); // ← FIX
                    t.setId_prioridad(rs.getInt("id_prioridad"));
                    t.setId_estado(rs.getInt("id_estado"));
                    lista.add(t);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
 

    public Ticket buscarPorId(int id) {
        Ticket t = null;
        String sql = "SELECT t.*, tp.nom_tipo, e.nom_estado, p.nom_prioridad, " +
                     "CONCAT(u.nombre, ' ', u.apellido) as nom_usuario_reporta, " +
                     "CONCAT(tec.nombre, ' ', tec.apellido) as nom_tecnico " +
                     "FROM tb_ticket t " +
                     "INNER JOIN tb_tipo_problema tp ON t.id_tipo = tp.id_tipo " +
                     "INNER JOIN tb_estado e ON t.id_estado = e.id_estado " +
                     "INNER JOIN tb_prioridad p ON t.id_prioridad = p.id_prioridad " +
                     "LEFT JOIN tb_usuario u ON t.id_usuario_reporta = u.id_usuario " +
                     "LEFT JOIN tb_usuario tec ON t.id_tecnico_asignado = tec.id_usuario " +
                     "WHERE t.id_ticket = ?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql)) {
            psm.setInt(1, id);
            try (ResultSet rs = psm.executeQuery()) {
                if (rs.next()) {
                    t = new Ticket();
                    t.setId_ticket(rs.getInt("id_ticket"));
                    t.setTitulo(rs.getString("titulo"));
                    t.setDescripcion(rs.getString("descripcion"));
                    t.setFecha_reg(rs.getTimestamp("fecha_reg"));
                    t.setFecha_cierre(rs.getTimestamp("fecha_cierre"));
                    t.setId_estado(rs.getInt("id_estado"));
                    t.setId_prioridad(rs.getInt("id_prioridad"));
                    t.setId_tecnico_asignado(rs.getInt("id_tecnico_asignado"));
                    t.setNom_tipo(rs.getString("nom_tipo"));
                    t.setNom_estado(rs.getString("nom_estado"));
                    t.setNom_prioridad(rs.getString("nom_prioridad"));
                    t.setNom_usuario_reporta(rs.getString("nom_usuario_reporta")); 
                    t.setNom_tecnico_asignado(rs.getString("nom_tecnico"));       
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return t;
    }

    public int contarTicketsPorEstado(int idEstado) {
        int total = 0;
        String sql = idEstado == 0
            ? "SELECT COUNT(*) FROM tb_ticket"
            : "SELECT COUNT(*) FROM tb_ticket WHERE id_estado = ?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql)) {
            if (idEstado > 0) psm.setInt(1, idEstado);
            try (ResultSet rs = psm.executeQuery()) {
                if (rs.next()) total = rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return total;
    }

    public int contarUsuariosPorRol(int idRol) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM tb_usuario WHERE id_rol = ? AND estado_logico = 1";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql)) {
            psm.setInt(1, idRol);
            try (ResultSet rs = psm.executeQuery()) {
                if (rs.next()) total = rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return total;
    }

    public List<Comentario> listarComentariosPorTicket(int idTicket) {
        List<Comentario> lista = new ArrayList<>();
        String sql = "SELECT c.id_comentario, c.id_ticket, c.id_usuario, " +
                     "c.texto_comentario, c.fecha_reg, c.es_solucion, " +
                     "u.nombre " +
                     "FROM tb_comentario c " +
                     "INNER JOIN tb_usuario u ON c.id_usuario = u.id_usuario " +
                     "WHERE c.id_ticket = ? " +
                     "ORDER BY c.fecha_reg ASC";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql)) {
            psm.setInt(1, idTicket);
            try (ResultSet rs = psm.executeQuery()) {
                while (rs.next()) {
                    Comentario c = new Comentario();
                    c.setId_comentario(rs.getInt("id_comentario"));
                    c.setId_ticket(rs.getInt("id_ticket"));
                    c.setId_usuario(rs.getInt("id_usuario"));      
                    c.setTexto_comentario(rs.getString("texto_comentario"));
                    c.setFecha_reg(rs.getTimestamp("fecha_reg"));
                    c.setEs_solucion(rs.getInt("es_solucion"));   
                    c.setNom_usuario(rs.getString("nombre"));
                    lista.add(c);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public void limpiarTicketsInactivos() {
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = MySQLConexion.getConexion();
            String sql = "UPDATE tb_ticket SET id_estado = 4 " +
                         "WHERE id_estado = 3 AND DATEDIFF(NOW(), fecha_cierre) >= 2";
            pstm = cn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public int cambiarEstado(int id_ticket, int id_estado) {
        int result = -1;
        String sql = "UPDATE tb_ticket SET id_estado = ? WHERE id_ticket = ?";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql)) {
            psm.setInt(1, id_estado);
            psm.setInt(2, id_ticket);
            result = psm.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    public List<Ticket> listar() { return listarTicketsDinamico(0, 1, 0, 0); }

    public List<Usuario> listarTecnicos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, apellido FROM tb_usuario WHERE id_rol = 2 AND estado_logico = 1";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql);
             ResultSet rs = psm.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt(1));
                u.setNombre(rs.getString(2) + " " + rs.getString(3));
                lista.add(u);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public int registrarComentario(Comentario c) {
        int rs = 0;
        String sql = "INSERT INTO tb_comentario (id_ticket, id_usuario, texto_comentario, fecha_reg) VALUES (?, ?, ?, NOW())";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement pstm = cn.prepareStatement(sql)) {
            pstm.setInt(1, c.getId_ticket());
            pstm.setInt(2, c.getId_usuario());
            pstm.setString(3, c.getTexto_comentario());
            rs = pstm.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return rs;
    }

    public List<Estado> listarEstados() {
        List<Estado> lista = new ArrayList<>();
        String sql = "SELECT id_estado, nom_estado FROM tb_estado";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Estado e = new Estado();
                e.setId_estado(rs.getInt(1));
                e.setNom_estado(rs.getString(2));
                lista.add(e);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public List<TipoProblema> listarCategoriasMes() {
        List<TipoProblema> lista = new ArrayList<>();
        String sql =
            "SELECT tp.id_tipo, tp.nom_tipo, COUNT(t.id_ticket) AS cantidad " +
            "FROM tb_tipo_problema tp " +
            "LEFT JOIN tb_ticket t ON tp.id_tipo = t.id_tipo " +
            "GROUP BY tp.id_tipo, tp.nom_tipo " +
            "ORDER BY cantidad DESC";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql);
             ResultSet rs = psm.executeQuery()) {
            while (rs.next()) {
                TipoProblema tp = new TipoProblema();
                tp.setId_tipo(rs.getInt("id_tipo"));
                tp.setNom_tipo(rs.getString("nom_tipo"));
                tp.setCantidad(rs.getInt("cantidad"));
                lista.add(tp);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
 
    
    public List<SubtipoProblema> listarSubtipos() {
        List<SubtipoProblema> lista = new ArrayList<>();
        String sql = "SELECT id_subtipo, id_tipo, nom_subtipo FROM tb_subtipo_problema ORDER BY id_tipo, nom_subtipo";
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql);
             ResultSet rs = psm.executeQuery()) {
            while (rs.next()) {
                SubtipoProblema s = new SubtipoProblema();
                s.setId_subtipo(rs.getInt("id_subtipo"));
                s.setId_tipo(rs.getInt("id_tipo"));
                s.setNom_subtipo(rs.getString("nom_subtipo"));
                lista.add(s);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    } 
    
    public List<Ticket> consultarPorEstadoYFecha(int idEstado, String fechaInicio, String fechaFin) {
        List<Ticket> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        try {
            cn = MySQLConexion.getConexion();
            StringBuilder sql = new StringBuilder(
                "SELECT t.id_ticket, t.titulo, t.fecha_reg, t.fecha_cierre, " +
                "e.nom_estado, p.nom_prioridad, tp.nom_tipo, " +
                "CONCAT(ur.nombre,' ',ur.apellido) AS nom_usuario_reporta, " +
                "CONCAT(tec.nombre,' ',tec.apellido) AS nom_tecnico_asignado " +
                "FROM tb_ticket t " +
                "INNER JOIN tb_estado e    ON t.id_estado    = e.id_estado " +
                "INNER JOIN tb_prioridad p ON t.id_prioridad = p.id_prioridad " +
                "INNER JOIN tb_tipo_problema tp ON t.id_tipo = tp.id_tipo " +
                "INNER JOIN tb_usuario ur  ON t.id_usuario_reporta = ur.id_usuario " +
                "LEFT  JOIN tb_usuario tec ON t.id_tecnico_asignado = tec.id_usuario " +
                "WHERE 1=1 "
            );
            if (idEstado > 0)          sql.append("AND t.id_estado = ? ");
            if (fechaInicio != null && !fechaInicio.isEmpty())
                                       sql.append("AND DATE(t.fecha_reg) >= ? ");
            if (fechaFin    != null && !fechaFin.isEmpty())
                                       sql.append("AND DATE(t.fecha_reg) <= ? ");
            sql.append("ORDER BY t.fecha_reg DESC");

            psm = cn.prepareStatement(sql.toString());
            int idx = 1;
            if (idEstado > 0)                                      psm.setInt(idx++, idEstado);
            if (fechaInicio != null && !fechaInicio.isEmpty())     psm.setString(idx++, fechaInicio);
            if (fechaFin    != null && !fechaFin.isEmpty())        psm.setString(idx++, fechaFin);

            rs = psm.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId_ticket(rs.getInt("id_ticket"));
                t.setTitulo(rs.getString("titulo"));
                t.setFecha_reg(rs.getTimestamp("fecha_reg"));
                t.setFecha_cierre(rs.getTimestamp("fecha_cierre"));
                t.setNom_estado(rs.getString("nom_estado"));
                t.setNom_prioridad(rs.getString("nom_prioridad"));
                t.setNom_tipo(rs.getString("nom_tipo"));
                t.setNom_usuario_reporta(rs.getString("nom_usuario_reporta"));
                t.setNom_tecnico_asignado(rs.getString("nom_tecnico_asignado"));
                lista.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs  != null) rs.close();  } catch (Exception e) { e.printStackTrace(); }
            try { if (psm != null) psm.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (cn  != null) cn.close();  } catch (Exception e) { e.printStackTrace(); }
        }
        return lista;
    }

    public List<Ticket> consultarPorTecnico(int idTecnico) {
        List<Ticket> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        try {
            cn = MySQLConexion.getConexion();
            String sql =
                "SELECT t.id_ticket, t.titulo, t.fecha_reg, t.fecha_cierre, " +
                "e.nom_estado, p.nom_prioridad, tp.nom_tipo, " +
                "CONCAT(ur.nombre,' ',ur.apellido) AS nom_usuario_reporta, " +
                "CONCAT(tec.nombre,' ',tec.apellido) AS nom_tecnico_asignado, " +
                "DATEDIFF(IFNULL(t.fecha_cierre, NOW()), t.fecha_reg) AS dias_atencion " +
                "FROM tb_ticket t " +
                "INNER JOIN tb_estado e    ON t.id_estado    = e.id_estado " +
                "INNER JOIN tb_prioridad p ON t.id_prioridad = p.id_prioridad " +
                "INNER JOIN tb_tipo_problema tp ON t.id_tipo = tp.id_tipo " +
                "INNER JOIN tb_usuario ur  ON t.id_usuario_reporta = ur.id_usuario " +
                "INNER JOIN tb_usuario tec ON t.id_tecnico_asignado = tec.id_usuario " +
                "WHERE t.id_tecnico_asignado = ? " +
                "ORDER BY t.fecha_reg DESC";
            psm = cn.prepareStatement(sql);
            psm.setInt(1, idTecnico);
            rs = psm.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId_ticket(rs.getInt("id_ticket"));
                t.setTitulo(rs.getString("titulo"));
                t.setFecha_reg(rs.getTimestamp("fecha_reg"));
                t.setFecha_cierre(rs.getTimestamp("fecha_cierre"));
                t.setNom_estado(rs.getString("nom_estado"));
                t.setNom_prioridad(rs.getString("nom_prioridad"));
                t.setNom_tipo(rs.getString("nom_tipo"));
                t.setNom_usuario_reporta(rs.getString("nom_usuario_reporta"));
                t.setNom_tecnico_asignado(rs.getString("nom_tecnico_asignado"));
                t.setDias_atencion(rs.getInt("dias_atencion"));
                lista.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs  != null) rs.close();  } catch (Exception e) { e.printStackTrace(); }
            try { if (psm != null) psm.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (cn  != null) cn.close();  } catch (Exception e) { e.printStackTrace(); }
        }
        return lista;
    }

    public List<Ticket> consultarPorTitulo(String titulo) {
        List<Ticket> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        try {
            cn = MySQLConexion.getConexion();
            String sql =
                "SELECT t.id_ticket, t.titulo, t.descripcion, t.fecha_reg, " +
                "e.nom_estado, p.nom_prioridad, tp.nom_tipo, " +
                "CONCAT(ur.nombre,' ',ur.apellido) AS nom_usuario_reporta, " +
                "CONCAT(tec.nombre,' ',tec.apellido) AS nom_tecnico_asignado " +
                "FROM tb_ticket t " +
                "INNER JOIN tb_estado e    ON t.id_estado    = e.id_estado " +
                "INNER JOIN tb_prioridad p ON t.id_prioridad = p.id_prioridad " +
                "INNER JOIN tb_tipo_problema tp ON t.id_tipo = tp.id_tipo " +
                "INNER JOIN tb_usuario ur  ON t.id_usuario_reporta = ur.id_usuario " +
                "LEFT  JOIN tb_usuario tec ON t.id_tecnico_asignado = tec.id_usuario " +
                "WHERE t.titulo LIKE ? " +
                "ORDER BY t.fecha_reg DESC";
            psm = cn.prepareStatement(sql);
            psm.setString(1, "%" + titulo + "%");
            rs = psm.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId_ticket(rs.getInt("id_ticket"));
                t.setTitulo(rs.getString("titulo"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setFecha_reg(rs.getTimestamp("fecha_reg"));
                t.setNom_estado(rs.getString("nom_estado"));
                t.setNom_prioridad(rs.getString("nom_prioridad"));
                t.setNom_tipo(rs.getString("nom_tipo"));
                t.setNom_usuario_reporta(rs.getString("nom_usuario_reporta"));
                t.setNom_tecnico_asignado(rs.getString("nom_tecnico_asignado"));
                lista.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs  != null) rs.close();  } catch (Exception e) { e.printStackTrace(); }
            try { if (psm != null) psm.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (cn  != null) cn.close();  } catch (Exception e) { e.printStackTrace(); }
        }
        return lista;
    }

    public List<Ticket> reporteResumenPorEstado() {
        List<Ticket> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        try {
            cn = MySQLConexion.getConexion();
            String sql =
                "SELECT e.id_estado, e.nom_estado, COUNT(t.id_ticket) AS total " +
                "FROM tb_estado e " +
                "LEFT JOIN tb_ticket t ON e.id_estado = t.id_estado " +
                "GROUP BY e.id_estado, e.nom_estado " +
                "ORDER BY e.id_estado";
            psm = cn.prepareStatement(sql);
            rs = psm.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId_estado(rs.getInt("id_estado"));
                t.setNom_estado(rs.getString("nom_estado"));
                t.setTotal(rs.getInt("total"));
                lista.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { 
            	if (rs  != null) rs.close();  
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            try { if (psm != null) psm.close();
            	} catch (Exception e) { 
            		e.printStackTrace();
            	}
            try { if (cn  != null) cn.close(); 
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
        }
        return lista;
    }

    public List<TipoProblema> reportePorTipo() {
     List<TipoProblema> lista = new ArrayList<>();
     Connection cn = null;
     PreparedStatement psm = null;
     ResultSet rs = null;
     try {
         cn = MySQLConexion.getConexion();
         String sql =
             "SELECT tp.id_tipo, tp.nom_tipo, " +
             "COUNT(t.id_ticket)                                        AS total, " +
             "SUM(CASE WHEN t.id_estado = 4  THEN 1 ELSE 0 END)        AS cerrados, " +
             "SUM(CASE WHEN t.id_estado IN (1,2) THEN 1 ELSE 0 END)    AS pendientes, " +
             "SUM(CASE WHEN t.id_estado = 2  THEN 1 ELSE 0 END)        AS en_proceso " +
             "FROM tb_tipo_problema tp " +
             "LEFT JOIN tb_ticket t ON tp.id_tipo = t.id_tipo " +
             "GROUP BY tp.id_tipo, tp.nom_tipo " +
             "ORDER BY total DESC";
         psm = cn.prepareStatement(sql);
         rs = psm.executeQuery();
         while (rs.next()) {
             TipoProblema tp = new TipoProblema();
             tp.setId_tipo(rs.getInt("id_tipo"));
             tp.setNom_tipo(rs.getString("nom_tipo"));
             tp.setCantidad(rs.getInt("total"));
             tp.setResueltos(rs.getInt("cerrados"));    
             tp.setPendientes(rs.getInt("pendientes"));
             lista.add(tp);
         }
     } catch (Exception e) {
         e.printStackTrace();
     } finally {
         try { if (rs  != null) rs.close();  } catch (Exception e) { e.printStackTrace(); }
         try { if (psm != null) psm.close(); } catch (Exception e) { e.printStackTrace(); }
         try { if (cn  != null) cn.close();  } catch (Exception e) { e.printStackTrace(); }
     }
     return lista;
 }

    public List<Usuario> reporteRendimientoTecnicos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "{CALL usp_reporteRendimiento()}";
        try (Connection cn = MySQLConexion.getConexion();
             CallableStatement cs = cn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre_completo"));
                u.setTotal_asignados(rs.getInt("total_asignados"));
                u.setTotal_resueltos(rs.getInt("total_cerrados"));
                u.setTotal_pendientes(rs.getInt("total_pendientes"));
                u.setPromedio_dias(rs.getDouble("promedio_dias"));
                lista.add(u);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
    
    
    
    
    
    
    
}