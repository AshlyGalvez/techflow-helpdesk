<%@ page import="entidad.Prioridad" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Prioridades | TechFlow</title>
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
                <h1 class="mant-title">Prioridades</h1>
                <p class="mant-subtitle">Define los niveles de urgencia del sistema</p>
            </div>
        </div>

        <!-- ALERTA -->
        <% if (request.getAttribute("mensaje") != null) { %>
            <div class="mant-alert">
                <span>ℹ️ &nbsp;<%= request.getAttribute("mensaje") %></span>
            </div>
        <% } %>

        <%
            Prioridad editable = (Prioridad) request.getAttribute("prioridadEditable");
            List<Prioridad> lista = (List<Prioridad>) request.getAttribute("listado");
        %>

        <div class="mant-grid">

            <!-- ═══ FORMULARIO ═══ -->
            <div class="mant-card">
                <div class="mant-card-title">
                    <span class="card-icon"><%= editable == null ? "➕" : "✏️" %></span>
                    <%= editable == null ? "Registrar Prioridad" : "Editar Prioridad" %>
                </div>

                <form action="${pageContext.request.contextPath}/PrioridadServlet" method="post" class="mant-form">
                    <input type="hidden" name="tipo" value="<%= editable == null ? "regist" : "edit" %>">

                    <% if (editable != null) { %>
                        <div class="field-group">
                            <label class="field-label">ID</label>
                            <input type="text" name="txtId" class="field-input"
                                   value="<%= editable.getId_prioridad() %>" readonly
                                   style="opacity:0.5;cursor:not-allowed;">
                        </div>
                    <% } %>

                    <div class="field-group">
                        <label class="field-label">Nombre de la prioridad</label>
                        <input type="text" name="txtNombre" class="field-input"
                               value="<%= editable != null ? editable.getNom_prioridad() : "" %>"
                               placeholder="Ej. Alta" required>
                    </div>

                    <div class="field-group">
                        <label class="field-label">Tiempo límite (horas)</label>
                        <input type="number" name="txtTiempo" class="field-input"
                               value="<%= editable != null ? editable.getTiempo_horas() : "" %>"
                               placeholder="Ej. 24" min="1" required>
                    </div>

                    <div class="mant-btn-group">
                        <button type="submit" class="btn-mant-save">
                            <i data-lucide="save" style="width:15px;height:15px;"></i>
                            <%= editable == null ? "Registrar Prioridad" : "Guardar Cambios" %>
                        </button>
                        <% if (editable != null) { %>
                            <a href="${pageContext.request.contextPath}/PrioridadServlet?tipo=list"
                               class="btn-mant-cancel">
                                <i data-lucide="x" style="width:15px;height:15px;"></i>
                                Cancelar edición
                            </a>
                        <% } %>
                    </div>
                </form>
            </div>

            <!-- ═══ TABLA ═══ -->
            <div class="mant-card">
                <div class="mant-card-title">
                    <span class="card-icon">🚩</span>
                    Prioridades del sistema
                    <span style="margin-left:auto;font-size:0.75rem;color:#555a6e;font-weight:400;">
                        <%= lista != null ? lista.size() : 0 %> registros
                    </span>
                </div>

                <div class="mant-table-wrap">
                    <% if (lista == null || lista.isEmpty()) { %>
                        <div class="mant-empty">
                            <div class="mant-empty-icon">🚩</div>
                            <p>No hay prioridades registradas.</p>
                        </div>
                    <% } else { %>
                        <table class="mant-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Tiempo límite</th>
                                    <th style="text-align:center;">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                            <%
                                int idx = 0;
                                for (Prioridad p : lista) {
                                    String nom = p.getNom_prioridad().toLowerCase();
                                    String cls = "prio-default";
                                    if (nom.contains("baja"))       cls = "prio-baja";
                                    else if (nom.contains("media")) cls = "prio-media";
                                    else if (nom.contains("alta"))  cls = "prio-alta";
                                    else if (nom.contains("crit"))  cls = "prio-critica";
                            %>
                                <tr class="mant-row" style="animation-delay:<%= idx * 40 %>ms;">
                                    <td style="font-family:'Courier New',monospace;color:#555a6e;">
                                        #<%= p.getId_prioridad() %>
                                    </td>
                                    <td>
                                        <span class="prio-badge <%= cls %>">
                                            <%= p.getNom_prioridad() %>
                                        </span>
                                    </td>
                                    <td>
                                        <span class="tiempo-chip">
                                            <span class="tiempo-num"><%= p.getTiempo_horas() %></span> horas
                                        </span>
                                    </td>
                                    <td>
                                        <div class="action-group">
                                            <a href="${pageContext.request.contextPath}/PrioridadServlet?tipo=modif&id_prioridad=<%= p.getId_prioridad() %>"
                                               class="btn-edit btn-toggle" title="Editar">✏️</a>
                                            <a href="${pageContext.request.contextPath}/PrioridadServlet?tipo=elim&id_prioridad=<%= p.getId_prioridad() %>"
                                               class="btn-toggle btn-toggle-off" title="Eliminar"
                                               onclick="return confirm('¿Eliminar esta prioridad?')">🗑️</a>
                                        </div>
                                    </td>
                                </tr>
                            <% idx++; } %>
                            </tbody>
                        </table>
                    <% } %>
                </div>
            </div>

        </div><!-- /mant-grid -->
    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>lucide.createIcons();</script>
</body>
</html>
