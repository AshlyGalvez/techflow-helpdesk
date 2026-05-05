<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Roles | TechFlow</title>
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
                <h1 class="mant-title">Roles del Sistema</h1>
                <p class="mant-subtitle">Define los niveles de acceso y permisos</p>
            </div>
        </div>

        <!-- ALERTA -->
        <c:if test="${not empty mensaje}">
            <div class="mant-alert">
                <span>ℹ️ &nbsp;${mensaje}</span>
            </div>
        </c:if>

        <div class="mant-grid">

            <!-- ═══ FORMULARIO ═══ -->
            <div class="mant-card">
                <div class="mant-card-title">
                    <span class="card-icon">${empty rolEdit ? '➕' : '✏️'}</span>
                    ${empty rolEdit ? 'Registrar Rol' : 'Editar Rol'}
                </div>

                <form action="${pageContext.request.contextPath}/RolServlet" method="post" class="mant-form">
                    <input type="hidden" name="tipo" value="${empty rolEdit ? 'regist' : 'edit'}">

                    <c:if test="${not empty rolEdit}">
                        <div class="field-group">
                            <label class="field-label">ID</label>
                            <input type="text" name="id_rol" class="field-input"
                                   value="${rolEdit.id_rol}" readonly
                                   style="opacity:0.5;cursor:not-allowed;">
                        </div>
                    </c:if>

                    <div class="field-group">
                        <label class="field-label">Nombre del rol</label>
                        <input type="text" name="txtNombre" class="field-input"
                               value="${rolEdit.nombre}"
                               placeholder="Ej. Supervisor" required>
                    </div>

                    <div class="mant-btn-group">
                        <button type="submit" class="btn-mant-save">
                            <i data-lucide="save" style="width:15px;height:15px;"></i>
                            ${empty rolEdit ? 'Registrar Rol' : 'Guardar Cambios'}
                        </button>
                        <c:if test="${not empty rolEdit}">
                            <a href="${pageContext.request.contextPath}/RolServlet?tipo=list"
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
                    <span class="card-icon">🛡️</span>
                    Roles disponibles
                    <span style="margin-left:auto;font-size:0.75rem;color:#555a6e;font-weight:400;">
                        ${fn:length(listado)} registros
                    </span>
                </div>

                <div class="mant-table-wrap">
                    <c:choose>
                        <c:when test="${empty listado}">
                            <div class="mant-empty">
                                <div class="mant-empty-icon">🛡️</div>
                                <p>No hay roles registrados.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <table class="mant-table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Rol</th>
                                        <th style="text-align:center;">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="r" items="${listado}" varStatus="st">
                                        <%-- Determinar clases según id_rol --%>
                                        <c:set var="chipClass" value="rol-chip-x"/>
                                        <c:set var="iconClass" value="rol-icon-x"/>
                                        <c:set var="icono"     value="⭐"/>
                                        <c:if test="${r.id_rol == 1}">
                                            <c:set var="chipClass" value="rol-chip-1"/>
                                            <c:set var="iconClass" value="rol-icon-1"/>
                                            <c:set var="icono"     value="👑"/>
                                        </c:if>
                                        <c:if test="${r.id_rol == 2}">
                                            <c:set var="chipClass" value="rol-chip-2"/>
                                            <c:set var="iconClass" value="rol-icon-2"/>
                                            <c:set var="icono"     value="🔧"/>
                                        </c:if>
                                        <c:if test="${r.id_rol == 3}">
                                            <c:set var="chipClass" value="rol-chip-3"/>
                                            <c:set var="iconClass" value="rol-icon-3"/>
                                            <c:set var="icono"     value="👤"/>
                                        </c:if>

                                        <tr class="mant-row" style="animation-delay:${st.index * 40}ms;">
                                            <td style="font-family:'Courier New',monospace;color:#555a6e;">
                                                #${r.id_rol}
                                            </td>
                                            <td>
                                                <div style="display:flex;align-items:center;gap:10px;">
                                                    <div class="rol-icon ${iconClass}">${icono}</div>
                                                    <span class="rol-chip ${chipClass}">
                                                        ${r.nombre}
                                                    </span>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="action-group">
                                                    <a href="${pageContext.request.contextPath}/RolServlet?tipo=modif&id_rol=${r.id_rol}"
                                                       class="btn-edit btn-toggle" title="Editar">✏️</a>
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
