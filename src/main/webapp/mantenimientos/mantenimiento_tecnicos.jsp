<%@ page import="entidad.DetalleTecnico" %>
<%@ page import="entidad.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Técnicos | TechFlow</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://unpkg.com/lucide@latest"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mantenimientos.css">
</head>
<body>
<div class="app-layout">
    <jsp:include page="/sidebar.jsp" />

    <main class="main-content">

        <!-- HEADER -->
        <div class="mant-header">
            <div>
                <h1 class="mant-title">Staff Técnico</h1>
                <p class="mant-subtitle">Gestiona los especialistas del sistema</p>
            </div>
        </div>

        <!-- ALERTA -->
        <% if (request.getAttribute("mensaje") != null) { %>
            <div class="mant-alert">
                <span>ℹ️ &nbsp;<%= request.getAttribute("mensaje") %></span>
            </div>
        <% } %>

        <%
            DetalleTecnico editable = (DetalleTecnico) request.getAttribute("tecnicoEditable");
            List<Usuario>  usuarios = (List<Usuario>)  request.getAttribute("usuarios");
            List<DetalleTecnico> lista = (List<DetalleTecnico>) request.getAttribute("listado");
        %>

        <div class="mant-grid">

            <!-- ═══ FORMULARIO ═══ -->
            <div class="mant-card">
                <div class="mant-card-title">
                    <span class="card-icon"><%= editable == null ? "➕" : "✏️" %></span>
                    <%= editable == null ? "Vincular Técnico" : "Editar Técnico" %>
                </div>

                <form action="${pageContext.request.contextPath}/TecnicoServlet" method="post" class="mant-form">
                    <input type="hidden" name="tipo" value="<%= editable == null ? "regist" : "edit" %>">

                    <% if (editable != null) { %>
                        <input type="hidden" name="txtId" value="<%= editable.getId_tecnico() %>">
                        <div class="field-group">
                            <label class="field-label">Técnico seleccionado</label>
                            <input type="text" class="field-input"
                                   value="<%= editable.getNom_tecnico() %>" readonly
                                   style="opacity:0.6;cursor:not-allowed;">
                        </div>
                    <% } else { %>
                        <div class="field-group">
                            <label class="field-label">Seleccionar usuario</label>
                            <select name="cboTecnico" class="field-select" required>
                                <option value="">— Buscar técnico —</option>
                                <% if (usuarios != null) {
                                    for (Usuario u : usuarios) { %>
                                        <option value="<%= u.getId_usuario() %>">
                                            <%= u.getNombre() %> <%= u.getApellido() %>
                                        </option>
                                <% } } %>
                            </select>
                        </div>
                    <% } %>

                    <div class="field-group">
                        <label class="field-label">Especialidad</label>
                        <input type="text" name="txtEspecialidad" class="field-input"
                               value="<%= editable != null ? editable.getEspecialidad() : "" %>"
                               placeholder="Ej. Soporte de Redes" required>
                    </div>

                    <% if (editable != null) { %>
                        <div class="field-group">
                            <label class="field-label">Disponibilidad</label>
                            <select name="cboDisponibilidad" class="field-select">
                                <option value="1" <%= editable.getDisponibilidad() == 1 ? "selected" : "" %>>✅ Disponible</option>
                                <option value="0" <%= editable.getDisponibilidad() == 0 ? "selected" : "" %>>❌ No disponible</option>
                            </select>
                        </div>
                    <% } %>

                    <div class="mant-btn-group">
                        <button type="submit" class="btn-mant-save">
                            <i data-lucide="save" style="width:15px;height:15px;"></i>
                            <%= editable == null ? "Vincular Técnico" : "Guardar Cambios" %>
                        </button>
                        <% if (editable != null) { %>
                            <a href="${pageContext.request.contextPath}/TecnicoServlet?accion=tecnicos"
                               class="btn-mant-cancel">
                                <i data-lucide="x" style="width:15px;height:15px;"></i>
                                Cancelar edición
                            </a>
                        <% } %>
                    </div>
                </form>
            </div>

            <!-- ═══ LISTADO DE TARJETAS ═══ -->
            <div class="mant-card">
                <div class="mant-card-title">
                    <span class="card-icon">🔧</span>
                    Especialistas registrados
                    <span style="margin-left:auto;font-size:0.75rem;color:#555a6e;font-weight:400;">
                        <%= lista != null ? lista.size() : 0 %> técnicos
                    </span>
                </div>

                <% if (lista == null || lista.isEmpty()) { %>
                    <div class="empty-tec">
                        <div class="empty-tec-icon">🔧</div>
                        <p style="font-size:0.85rem;margin:0;">No hay técnicos vinculados aún.</p>
                    </div>
                <% } else {
                    for (DetalleTecnico dt : lista) {
                        String inicial = dt.getNom_tecnico().substring(0, 1).toUpperCase();
                %>
                    <div class="tec-card">
                        <!-- Fila superior: avatar + nombre + estado + editar -->
                        <div style="display:flex;align-items:center;gap:12px;">
                            <div class="tec-avatar"><%= inicial %></div>
                            <div style="flex:1;">
                                <p class="tec-name"><%= dt.getNom_tecnico() %></p>
                                <p class="tec-email"><%= dt.getCorreo_tecnico() %></p>
                            </div>
                            <div style="display:flex;align-items:center;gap:8px;">
                                <% if (dt.getDisponibilidad() == 1) { %>
                                    <span class="pill-disp pill-ok">● Disponible</span>
                                <% } else { %>
                                    <span class="pill-disp pill-off">● Ocupado</span>
                                <% } %>
                                <a href="${pageContext.request.contextPath}/TecnicoServlet?tipo=modif&id_tecnico=<%= dt.getId_tecnico() %>"
                                   class="btn-tec-edit">
                                    <i data-lucide="pencil" style="width:13px;height:13px;"></i> Editar
                                </a>
                            </div>
                        </div>

                        <hr class="tec-divider">

                        <!-- Fila inferior: especialidad + logros + ver tickets -->
                        <div style="display:flex;align-items:flex-end;gap:24px;flex-wrap:wrap;">
                            <div>
                                <p class="tec-label">Especialidad</p>
                                <p class="tec-value" style="color:#4d8ef0;">🏷️ <%= dt.getEspecialidad() %></p>
                            </div>
                            <div>
                                <p class="tec-label">Tickets cerrados</p>
								<p class="tec-value tec-trophy">🔒 <%= dt.getTickets_resueltos() %></p>
                            </div>
                            <div style="margin-left:auto;">
								<a href="${pageContext.request.contextPath}/TicketServlet?tipo=list&idTecnico=<%= dt.getId_tecnico() %>"
   									class="btn-tec-ver">
                                    <i data-lucide="eye" style="width:13px;height:13px;"></i> Ver Tickets
                                </a>
                            </div>
                        </div>
                    </div>
                <% } } %>
            </div>

        </div><!-- /mant-grid -->
    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>lucide.createIcons();</script>
</body>
</html>
