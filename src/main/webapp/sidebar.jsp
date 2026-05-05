<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<aside class="sidebar">
    <div class="logo">
        <div class="logo-box">
            <i data-lucide="shield-check"></i>
        </div>
        <span class="logo-text">TechFlow</span>
    </div>

    <c:set var="uri" value="${pageContext.request.requestURI}" />

    <nav class="nav-group">
        <a href="${pageContext.request.contextPath}/DashboardServlet" 
           class="nav-link ${uri.contains('panel_admin.jsp') || uri.contains('Dashboard') ? 'active' : ''}">
            <i data-lucide="layout-grid"></i>
            <span>Panel Principal</span>
        </a>
        
        <a href="${pageContext.request.contextPath}/TicketServlet?tipo=list" 
           class="nav-link ${uri.contains('TicketServlet') ? 'active' : ''}">
            <i data-lucide="ticket"></i>
            <span>Gestión de Tickets</span>
        </a>

        <c:if test="${sessionScope.objUsuario.id_rol == 1}">
            
            <div class="nav-label">Gestión</div>
            
            <a href="${pageContext.request.contextPath}/UsuarioServlet?accion=usuarios" 
               class="nav-link ${param.accion == 'usuarios' ? 'active' : ''}">
                <i data-lucide="users"></i>
                <span>Usuarios</span>
            </a>
            
            <a href="${pageContext.request.contextPath}/TecnicoServlet?accion=tecnicos" 
               class="nav-link ${param.accion == 'tecnicos' ? 'active' : ''}">
                <i data-lucide="wrench"></i>
                <span>Técnicos</span>
            </a>

            <div class="nav-label">Configuración</div>
            
            <a href="${pageContext.request.contextPath}/RolServlet?tipo=list" 
               class="nav-link ${uri.contains('RolServlet') || uri.contains('mant_roles.jsp') ? 'active' : ''}">
                <i data-lucide="shield"></i>
                <span>Roles</span>
            </a>
            
            <a href="${pageContext.request.contextPath}/EstadoServlet?accion=estados" 
               class="nav-link ${param.accion == 'estados' ? 'active' : ''}">
                <i data-lucide="bookmark"></i>
                <span>Estados</span>
            </a>
            
            <a href="${pageContext.request.contextPath}/PrioridadServlet?accion=prioridades" 
               class="nav-link ${param.accion == 'prioridades' ? 'active' : ''}">
                <i data-lucide="flag"></i>
                <span>Prioridades</span>
            </a>

            <a href="${pageContext.request.contextPath}/TipoProblemaServlet?accion=tipos" 
               class="nav-link ${param.accion == 'tipos' ? 'active' : ''}">
                <i data-lucide="tag"></i>
                <span>Tipos de Problema</span>
            </a>

            <div class="nav-label">Análisis</div>
            <a href="${pageContext.request.contextPath}/reportes.jsp" 
               class="nav-link ${uri.contains('reportes') ? 'active' : ''}">
                <i data-lucide="bar-chart-3"></i>
                <span>Reportes</span>
            </a>
        </c:if>
    </nav>

    <div class="sidebar-profile">
        <div class="profile-box">
            <p class="u-name">${sessionScope.objUsuario.nombre}</p>
            <p class="u-role">
            <c:choose>
                <c:when test="${sessionScope.objUsuario.id_rol == 1}">Administrador</c:when>
                <c:when test="${sessionScope.objUsuario.id_rol == 2}">Técnico</c:when>
                <c:when test="${sessionScope.objUsuario.id_rol == 3}">Usuario</c:when>
                <c:otherwise>Invitado</c:otherwise>
            </c:choose>
            </p>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/LoginServlet?opc=logout" class="nav-link logout-item">
        <i data-lucide="log-out"></i>
        <span>Salir del Sistema</span>
    </a>
</aside>

<script src="https://unpkg.com/lucide@latest"></script>
<script>
    lucide.createIcons();
</script>