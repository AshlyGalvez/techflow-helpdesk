<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>TechFlow — Panel Administrativo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/panel_admin.css">

</head>
<body>
<div class="app-layout">
    <jsp:include page="sidebar.jsp" />

    <main class="main-wrap">

        <!-- HEADER -->
        <div class="page-header">
            <div>
                <div class="page-title">Panel Administrativo</div>
                <div class="page-sub">// gestión operativa · TechFlow HelpDesk</div>
            </div>
        </div>

        <div class="kpi-grid">
            <div class="kpi-card c-blue">
                <div class="kpi-label">Total</div>
                <div class="kpi-value">${cantTotal}</div>
                <div class="kpi-icon">🎫</div>
            </div>
            <div class="kpi-card c-amber">
                <div class="kpi-label">Abiertos</div>
                <div class="kpi-value">${cantPendientes}</div>
                <div class="kpi-icon">📬</div>
            </div>
            <div class="kpi-card c-purple">
                <div class="kpi-label">En Proceso</div>
                <div class="kpi-value">${cantProceso}</div>
                <div class="kpi-icon">⚙️</div>
            </div>
            <div class="kpi-card c-green">
                <div class="kpi-label">Resueltos</div>
                <div class="kpi-value">${cantResueltos}</div>
                <div class="kpi-icon">✅</div>
            </div>
            <div class="kpi-card c-red">
                <div class="kpi-label">Cerrados</div>
                <div class="kpi-value">${cantCerrados}</div>  
                <div class="kpi-icon">🔒</div>
            </div>
        </div>

        <div class="dashboard-grid">
            <div>

                <div class="panel">
                    <div class="panel-header">
                        <div class="panel-title">🕐 Actividad Reciente</div>
                        <a href="${pageContext.request.contextPath}/TicketServlet?tipo=list"
                           class="link-ver-todos">Ver todos →</a>
                    </div>
                    <table class="hd-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Título</th>
                                <th>Estado</th>
                                <th>Usuario</th>
                                <th>Técnico</th>
                                <th>Fecha</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty ticketsRecientes}">
                                    <c:forEach var="t" items="${ticketsRecientes}">
                                        <tr onclick="location.href='${pageContext.request.contextPath}/TicketServlet?tipo=detalle&id=${t.id_ticket}'">
                                            <td class="td-id">#${t.id_ticket}</td>
                                            <td class="td-title"><c:out value="${t.titulo}"/></td>
                                            <td>
                                                <span class="badge-hd
                                                    ${t.id_estado == 1 ? 'b-abierto'  :
                                                      t.id_estado == 2 ? 'b-proceso'  :
                                                      t.id_estado == 3 ? 'b-resuelto' : 'b-cerrado'}">
                                                    <c:out value="${t.nom_estado}"/>
                                                </span>
                                            </td>
                                            <td class="td-user"><c:out value="${t.nom_usuario_reporta}"/></td>
                                            <td class="td-tech">
                                                <c:choose>
                                                    <c:when test="${not empty t.nom_tecnico_asignado}">
                                                        <c:out value="${t.nom_tecnico_asignado}"/>
                                                    </c:when>
                                                    <c:otherwise>—</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="td-date">${t.getFechaCorta()}</td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="6" style="text-align:center;padding:28px;color:var(--txt-muted);font-size:.85rem;">
                                            Sin actividad reciente
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>

                <div class="panel">
                    <div class="panel-header">
						<div class="panel-title">📊 Tickets por Categoría</div>
                    </div>
                    <div class="panel-body">
                        <c:choose>
                            <c:when test="${not empty listaTopCategorias}">
                                <div class="cat-list">
                                    <%-- Calcular el máximo para las barras proporcionales --%>
                                    <c:set var="maxCat" value="1"/>
                                    <c:forEach var="cat" items="${listaTopCategorias}">
                                        <c:if test="${cat.cantidad > maxCat}">
                                            <c:set var="maxCat" value="${cat.cantidad}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:forEach var="cat" items="${listaTopCategorias}">
                                        <div>
                                            <div class="cat-top">
                                                <span class="cat-name"><c:out value="${cat.nom_tipo}"/></span>
                                                <span class="cat-count">${cat.cantidad}</span>
                                            </div>
                                            <div class="cat-bar-bg">
                                                <div class="cat-bar-fill"
                                                     style="width:${(cat.cantidad * 100) / maxCat}%"></div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p style="color:var(--txt-muted);font-size:.85rem;text-align:center;padding:20px 0;">
                                    Sin datos este mes
                                </p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="panel">
                    <div class="panel-header">
                        <div class="panel-title">⚙️ Configuración y Mantenimiento</div>
                    </div>
                    <div class="panel-body">
                        <div class="mant-grid">
                            <a href="${pageContext.request.contextPath}/RolServlet?tipo=list" class="mant-item">
                                <span class="mant-icon">🛡️</span>
                                <div><div class="mant-label">Roles</div><div class="mant-sub">Gestión de permisos</div></div>
                            </a>
                            <a href="${pageContext.request.contextPath}/EstadoServlet?accion=estados" class="mant-item">
                                <span class="mant-icon">🔖</span>
                                <div><div class="mant-label">Estados</div><div class="mant-sub">Flujo de tickets</div></div>
                            </a>
                            <a href="${pageContext.request.contextPath}/PrioridadServlet?accion=prioridades" class="mant-item">
                                <span class="mant-icon">🚩</span>
                                <div><div class="mant-label">Prioridades</div><div class="mant-sub">Niveles de urgencia</div></div>
                            </a>
                            <a href="${pageContext.request.contextPath}/TipoProblemaServlet?accion=tipos" class="mant-item">
                                <span class="mant-icon">🏷️</span>
                                <div><div class="mant-label">Tipos de Problema</div><div class="mant-sub">Categorías de incidencia</div></div>
                            </a>
                        </div>
                    </div>
                </div>

            </div>

            <div>

                
                <div class="panel">
                    <div class="panel-header">
                        <div class="panel-title">👥 Equipo</div>
                    </div>
                    <div class="panel-body">
                        <%-- Usa totalUsuarios y totalTecnicos (nombres del Servlet) --%>
                        <div class="side-stat">
                            <span class="side-label">Usuarios registrados</span>
                            <span class="side-val" style="color:var(--blue);">${totalUsuarios}</span>
                        </div>
                        <div class="side-stat">
                            <span class="side-label">Técnicos activos</span>
                            <span class="side-val" style="color:var(--purple);">${totalTecnicos}</span>
                        </div>
                        <a href="${pageContext.request.contextPath}/UsuarioServlet?accion=usuarios"
                           class="btn-side btn-users">👤 Gestionar Usuarios</a>
                        <a href="${pageContext.request.contextPath}/TecnicoServlet?accion=tecnicos"
                           class="btn-side btn-tech">🔧 Gestionar Técnicos</a>
                    </div>
                </div>

               
                <div class="panel">
                    <div class="panel-header">
                        <div class="panel-title">📈 Reportes</div>
                    </div>
                    <div class="panel-body">
                        <div class="panel">
    <div class="panel-header">
        <div class="panel-title">📈 Reportes</div>
    </div>
    <div class="panel-body">
        <a href="${pageContext.request.contextPath}/DashboardServlet?tipo=reportes&tab=estado"
           style="display:flex;align-items:center;gap:12px;padding:12px 0;text-decoration:none;border-bottom:1px solid var(--border);">
            <span style="font-size:1.2rem;">📊</span>
            <div>
                <div style="font-size:.83rem;font-weight:600;color:var(--txt);">Resumen por Estado</div>
                <div style="font-size:.72rem;color:var(--txt-muted);">Distribución de tickets por estado</div>
            </div>
        </a>
        <a href="${pageContext.request.contextPath}/DashboardServlet?tipo=reportes&tab=tipo"
           style="display:flex;align-items:center;gap:12px;padding:12px 0;text-decoration:none;border-bottom:1px solid var(--border);">
            <span style="font-size:1.2rem;">🏷️</span>
            <div>
                <div style="font-size:.83rem;font-weight:600;color:var(--txt);">Por Tipo de Problema</div>
                <div style="font-size:.72rem;color:var(--txt-muted);">Hardware, Software, Redes, Accesos</div>
            </div>
        </a>
        <a href="${pageContext.request.contextPath}/DashboardServlet?tipo=reportes&tab=tecnicos"
           style="display:flex;align-items:center;gap:12px;padding:12px 0;text-decoration:none;">
            <span style="font-size:1.2rem;">👨‍💻</span>
            <div>
                <div style="font-size:.83rem;font-weight:600;color:var(--txt);">Rendimiento Técnicos</div>
                <div style="font-size:.72rem;color:var(--txt-muted);">Ranking y eficiencia del equipo</div>
            </div>
        </a>
    </div>
</div>
                    </div>
                </div>

                <div class="note-box">
                    ⚡ <strong>Recordatorio:</strong> Revisa los tickets abiertos sin técnico asignado. Los tickets sin asignar permanecen en estado <em>Abierto</em> indefinidamente.
                </div>

            </div>
        </div>

    </main>
</div>
</body>
</html>
