<%@ page import="entidad.Estado" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Estados | TechFlow</title>
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
                <h1 class="mant-title">Estados de Ticket</h1>
                <p class="mant-subtitle">Configura el ciclo de vida de los tickets</p>
            </div>
        </div>

        <!-- ALERTA -->
        <c:if test="${not empty mensaje}">
            <div class="mant-alert">
                <span>ℹ️ &nbsp;${mensaje}</span>
            </div>
        </c:if>

        <%
            List<entidad.Estado> listado = (List<entidad.Estado>) request.getAttribute("listado");
        %>

        <div class="mant-grid">

            <!-- ═══ FORMULARIO ═══ -->
            <div class="mant-card">
                <div class="mant-card-title">
                    <span class="card-icon">${empty estadoEditable ? '➕' : '✏️'}</span>
                    ${empty estadoEditable ? 'Registrar Estado' : 'Editar Estado'}
                </div>

                <form action="${pageContext.request.contextPath}/EstadoServlet" method="post" class="mant-form">
                    <input type="hidden" name="tipo" value="${empty estadoEditable ? 'regist' : 'edit'}">

                    <c:if test="${not empty estadoEditable}">
                        <div class="field-group">
                            <label class="field-label">ID</label>
                            <input type="text" name="txtId" class="field-input"
                                   value="${estadoEditable.id_estado}" readonly
                                   style="opacity:0.5;cursor:not-allowed;">
                        </div>
                    </c:if>

                    <div class="field-group">
                        <label class="field-label">Nombre del estado</label>
                        <input type="text" name="txtNombre" class="field-input"
                               value="${estadoEditable.nom_estado}"
                               placeholder="Ej. En Proceso" required>
                    </div>

                    <div class="mant-btn-group">
                        <button type="submit" class="btn-mant-save">
                            <i data-lucide="save" style="width:15px;height:15px;"></i>
                            ${empty estadoEditable ? 'Registrar Estado' : 'Guardar Cambios'}
                        </button>
                        <c:if test="${not empty estadoEditable}">
                            <a href="${pageContext.request.contextPath}/EstadoServlet?tipo=list"
                               class="btn-mant-cancel">
                                <i data-lucide="x" style="width:15px;height:15px;"></i>
                                Cancelar edición
                            </a>
                        </c:if>
                    </div>
                </form>
            </div>

            <!-- ═══ TABLA ═══ -->
            <div class="mant-card">
                <div class="mant-card-title">
                    <span class="card-icon">🔖</span>
                    Estados registrados
                    <span style="margin-left:auto;font-size:0.75rem;color:#555a6e;font-weight:400;">
                        ${fn:length(listado)} registros
                    </span>
                </div>

                <div class="mant-table-wrap">
                    <c:choose>
                        <c:when test="${empty listado}">
                            <div class="mant-empty">
                                <div class="mant-empty-icon">🔖</div>
                                <p>No hay estados registrados.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <table class="mant-table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Estado</th>
                                        <th style="text-align:center;">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="e" items="${listado}" varStatus="st">
                                        <%-- Clase de color según id_estado --%>
                                        <c:set var="estClass" value="est-x"/>
                                        <c:if test="${e.id_estado == 1}"><c:set var="estClass" value="est-1"/></c:if>
                                        <c:if test="${e.id_estado == 2}"><c:set var="estClass" value="est-2"/></c:if>
                                        <c:if test="${e.id_estado == 3}"><c:set var="estClass" value="est-3"/></c:if>
                                        <c:if test="${e.id_estado == 4}"><c:set var="estClass" value="est-4"/></c:if>
                                        <c:if test="${e.id_estado == 5}"><c:set var="estClass" value="est-5"/></c:if>

                                        <tr class="mant-row" style="animation-delay:${st.index * 40}ms;">
                                            <td style="font-family:'Courier New',monospace;color:#555a6e;">
                                                #${e.id_estado}
                                            </td>
                                            <td>
                                                <span class="estado-pill ${estClass}">
                                                    ${e.nom_estado}
                                                </span>
                                            </td>
                                            <td>
                                                <div class="action-group">
                                                    <a href="${pageContext.request.contextPath}/EstadoServlet?tipo=modif&id_estado=${e.id_estado}"
                                                       class="btn-edit btn-toggle" title="Editar">✏️</a>
                                                    <a href="${pageContext.request.contextPath}/EstadoServlet?tipo=elim&id_estado=${e.id_estado}"
                                                       class="btn-toggle btn-toggle-off" title="Eliminar"
                                                       onclick="return confirm('¿Eliminar este estado?')">🗑️</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

        </div><!-- /mant-grid -->
    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>lucide.createIcons();</script>
</body>
</html>
