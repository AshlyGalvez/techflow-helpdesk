<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Usuarios | TechFlow</title>
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
                <h1 class="mant-title">Gestión de Usuarios</h1>
                <p class="mant-subtitle">Administra los usuarios del sistema</p>
            </div>
        </div>

        <!-- ALERTA -->
        <c:if test="${not empty mensaje}">
            <div class="mant-alert">
                <i data-lucide="info" style="width:16px;height:16px;flex-shrink:0;"></i>
                <span>${mensaje}</span>
            </div>
        </c:if>

        <!-- GRID -->
        <div class="mant-grid">

            <!-- ═══ FORMULARIO ═══ -->
            <div class="mant-card">
                <div class="mant-card-title">
                    <span class="card-icon">${empty u ? '➕' : '✏️'}</span>
                    ${empty u ? 'Registrar Usuario' : 'Editar Usuario'}
                </div>

                <form action="${pageContext.request.contextPath}/UsuarioServlet" method="post" class="mant-form">
                    <input type="hidden" name="tipo" value="${empty u ? 'registrar' : 'actualizar'}">
                    <c:if test="${not empty u}">
                        <input type="hidden" name="txtId" value="${u.id_usuario}">
                    </c:if>

                    <div class="field-group">
                        <label class="field-label">DNI</label>
                        <input type="text" name="txtDni" class="field-input"
                               value="${u.dni}" placeholder="12345678" maxlength="8" required>
                    </div>

                    <div class="field-row">
                        <div class="field-group">
                            <label class="field-label">Nombre</label>
                            <input type="text" name="txtNombre" class="field-input"
                                   value="${u.nombre}" placeholder="Juan" required>
                        </div>
                        <div class="field-group">
                            <label class="field-label">Apellido</label>
                            <input type="text" name="txtApellido" class="field-input"
                                   value="${u.apellido}" placeholder="Pérez" required>
                        </div>
                    </div>

                    <div class="field-group">
                        <label class="field-label">Correo electrónico</label>
                        <input type="email" name="txtCorreo" class="field-input"
                               value="${u.correo}" placeholder="juan@empresa.com" required>
                    </div>

                    <div class="field-row">
                        <div class="field-group">
                            <label class="field-label">Login</label>
                            <input type="text" name="txtLogin" class="field-input"
                                   value="${u.login}" placeholder="jperez" required>
                        </div>
                        <div class="field-group">
                            <label class="field-label">Contraseña</label>
                            <input type="text" name="txtContrasena" class="field-input"
                                   value="${u.contrasena}" placeholder="••••••" required>
                        </div>
                    </div>

                    <div class="field-group">
                        <label class="field-label">Rol del sistema</label>
                        <select name="cboRol" class="field-select" required>
                            <option value="">— Seleccionar rol —</option>
                            <c:forEach var="r" items="${roles}">
                                <option value="${r.id_rol}" ${u.id_rol == r.id_rol ? 'selected' : ''}>
                                    ${r.nombre}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mant-btn-group">
                        <button type="submit" class="btn-mant-save">
                            <i data-lucide="save" style="width:15px;height:15px;"></i>
                            ${empty u ? 'Registrar Usuario' : 'Guardar Cambios'}
                        </button>
                        <c:if test="${not empty u}">
                            <a href="${pageContext.request.contextPath}/UsuarioServlet?accion=usuarios"
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
                    <span class="card-icon">👥</span>
                    Usuarios registrados
                    <span style="margin-left:auto;font-size:0.75rem;color:#555a6e;font-weight:400;">
                        ${fn:length(usuarios)} en total
                    </span>
                </div>

                <div class="mant-table-wrap">
                    <c:choose>
                        <c:when test="${empty usuarios}">
                            <div class="mant-empty">
                                <div class="mant-empty-icon">👤</div>
                                <p>No hay usuarios registrados</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <table class="mant-table">
                                <thead>
                                    <tr>
                                        <th>Usuario</th>
                                        <th>Correo</th>
                                        <th>Contraseña</th>
                                        <th>Rol</th>
                                        <th>Estado</th>
                                        <th style="text-align:center;">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="usr" items="${usuarios}" varStatus="st">
                                        <tr class="mant-row" style="animation-delay:${st.index * 35}ms;">
                                            <td>
                                                <div class="user-cell">
                                                    <div class="user-avatar">
                                                        ${fn:substring(usr.nombre, 0, 1)}
                                                    </div>
                                                    <div>
                                                        <div class="user-name">${usr.nombre} ${usr.apellido}</div>
                                                        <div class="user-login">${usr.login}</div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td style="font-size:0.78rem;">${usr.correo}</td>
                                            <td class="pass-cell">${usr.contrasena}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${usr.id_rol == 1}">
                                                        <span class="rol-badge rol-admin">Admin</span>
                                                    </c:when>
                                                    <c:when test="${usr.id_rol == 2}">
                                                        <span class="rol-badge rol-tecnico">Técnico</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="rol-badge rol-usuario">Usuario</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${usr.estado_logico == 1}">
                                                        <span class="estado-activo">✔ Activo</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="estado-inactivo">✖ Inactivo</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="action-group">
                                                    <a href="${pageContext.request.contextPath}/UsuarioServlet?tipo=modif&id_usuario=${usr.id_usuario}"
                                                       class="btn-edit btn-toggle" title="Editar">✏️</a>
                                                    <c:choose>
                                                        <c:when test="${usr.estado_logico == 1}">
                                                            <a href="${pageContext.request.contextPath}/UsuarioServlet?tipo=delete&id_usuario=${usr.id_usuario}"
                                                               class="btn-toggle btn-toggle-off" title="Inhabilitar"
                                                               onclick="return confirm('¿Desea inhabilitar a este usuario?')">🚫</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="${pageContext.request.contextPath}/UsuarioServlet?tipo=delete&id_usuario=${usr.id_usuario}"
                                                               class="btn-toggle btn-toggle-on" title="Activar"
                                                               onclick="return confirm('¿Desea reactivar a este usuario?')">✅</a>
                                                        </c:otherwise>
                                                    </c:choose>
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
