<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard | TechFlow</title>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:opsz,wght@9..40,400;9..40,500;9..40,600;9..40,700&family=Space+Mono:wght@400;700&display=swap" rel="stylesheet">
    <script src="https://unpkg.com/lucide@latest"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    
</head>
<body class="dashboard-body">

    <div class="app-layout">

        <jsp:include page="sidebar.jsp" />

        <main class="content-area">

            <header class="main-header">
                <div class="header-text">
                    <h1>Bienvenido, ${sessionScope.objUsuario.nombre}</h1>
                    <p>Panel de control · TechFlow Soporte Técnico</p>
                </div>
                <c:choose>
                    <c:when test="${sessionScope.objUsuario.id_rol == 2}">
                        <span class="role-badge tecnico">Técnico</span>
                    </c:when>
                    <c:otherwise>
                        <span class="role-badge usuario">Usuario</span>
                    </c:otherwise>
                </c:choose>
            </header>

            <section class="metrics-grid">

                <div class="metric-card">
                    <div class="metric-content">
                        <span class="metric-label">Total Tickets</span>
                        <span class="metric-number">${cantTotal}</span>
                    </div>
                    <div class="metric-icon total">
                        <i data-lucide="layers" width="20" height="20"></i>
                    </div>
                </div>

                <div class="metric-card border-pending">
                    <div class="metric-content">
                        <span class="metric-label">Pendientes</span>
                        <span class="metric-number">${cantPendientes}</span>
                    </div>
                    <div class="metric-icon pending">
                        <i data-lucide="alert-circle" width="20" height="20"></i>
                    </div>
                </div>

                <div class="metric-card border-process">
                    <div class="metric-content">
                        <span class="metric-label">En Proceso</span>
                        <span class="metric-number">${cantProceso}</span>
                    </div>
                    <div class="metric-icon progress">
                        <i data-lucide="clock" width="20" height="20"></i>
                    </div>
                </div>

                <div class="metric-card border-resolved">
                    <div class="metric-content">
                        <span class="metric-label">Resueltos</span>
                        <span class="metric-number">${cantResueltos}</span>
                    </div>
                    <div class="metric-icon resolved">
                        <i data-lucide="check-circle-2" width="20" height="20"></i>
                    </div>
                </div>

                <div class="metric-card border-closed">
                    <div class="metric-content">
                        <span class="metric-label">Cerrados</span>
                        <span class="metric-number">${cantCerrados}</span>
                    </div>
                    <div class="metric-icon closed">
                        <i data-lucide="archive" width="20" height="20"></i>
                    </div>
                </div>

            </section>

            <div class="dashboard-main-grid">

                <section class="table-section card">
                    <div class="table-header">
                        <h2>Actividad Reciente</h2>
                        <a href="TicketServlet?tipo=list" class="view-all">Ver todos →</a>
                    </div>
                    <div class="table-container">
                        <table class="modern-table">
                            <thead>
                                <tr>
                                    <th>Ticket</th>
                                    <th>Estado</th>
                                    <th>Prioridad</th>
                                    <th>Acción</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="t" items="${ticketsRecientes}">
                                    <tr>
                                        <td>
                                            <div class="ticket-block">
                                                <span class="t-title">${t.titulo}</span>
                                                <span class="t-meta">#${t.id_ticket} &bull; ${t.getFechaCorta()}</span>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="status-cell">
                                                <span class="dot st-dot-${t.id_estado}"></span>
                                                <span>${t.nom_estado}</span>
                                            </div>
                                        </td>
                                        <td>
                                            <span class="prio-badge badge-${t.id_prioridad}">${t.nom_prioridad}</span>
                                        </td>
                                        <td>
                                            <a href="TicketServlet?tipo=detalle&id=${t.id_ticket}" class="btn-action">
                                                <i data-lucide="arrow-right" width="16" height="16"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>

                                <c:if test="${empty ticketsRecientes}">
                                    <tr>
                                        <td colspan="4" style="text-align:center;padding:32px 20px;color:#404866;font-size:13px;">
                                            No hay tickets recientes.
                                        </td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </section>

                <aside class="right-panel">

                    <div class="card sidebar-box">
                        <h3>Acciones Rápidas</h3>
                        <div class="actions-list">
							<c:if test="${sessionScope.objUsuario.id_rol == 3}">
                            <a href="TicketServlet?tipo=nuevo" class="quick-btn">
                                <div class="q-icon bg-blue">
                                    <i data-lucide="plus" width="18" height="18"></i>
                                </div>
                                <span>Crear Nuevo Ticket</span>
                            </a>
                            </c:if>

                            <a href="TicketServlet?tipo=list" class="quick-btn">
                                <div class="q-icon bg-cyan">
                                    <i data-lucide="search" width="18" height="18"></i>
                                </div>
                                <span>Consultar Historial</span>
                            </a>

                            <c:if test="${sessionScope.objUsuario.id_rol == 2}">
                                <a href="TicketServlet?tipo=list&filtro=2" class="quick-btn">
                                    <div class="q-icon bg-purple">
                                        <i data-lucide="list-checks" width="18" height="18"></i>
                                    </div>
                                    <span>Mis Tickets Asignados</span>
                                </a>
                            </c:if>

                        </div>
                    </div>

                    <div class="system-dark">
                        <h3>Estado del Sistema</h3>
                        <div class="system-content">
                            <h4 class="servidor-status">Servidor Activo</h4>
                            <p>Sincronizado con MySQL Workbench.</p>

                            <div class="sys-bar-wrap">
                                <div class="sys-bar-label">
                                    <span>Carga del servidor</span>
                                    <span>34%</span>
                                </div>
                                <div class="sys-bar">
                                    <div class="sys-bar-fill" style="width:34%;"></div>
                                </div>
                            </div>

                            <div class="sys-bar-wrap">
                                <div class="sys-bar-label">
                                    <span>Tickets activos</span>
                                    <span>${cantProceso} / ${cantTotal}</span>
                                </div>
                                <div class="sys-bar">
                                    <c:set var="pct" value="${cantTotal > 0 ? (cantProceso * 100 / cantTotal) : 0}" />
                                    <div class="sys-bar-fill blue" style="width:${pct}%;"></div>
                                </div>
                            </div>

                        </div>
                    </div>

                </aside>

            </div>

        </main>
    </div>

    <script>lucide.createIcons();</script>
</body>
</html>
