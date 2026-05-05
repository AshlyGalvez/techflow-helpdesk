<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>HelpDesk | Tickets</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/listado.css">
</head>
<body>

<div class="lv-wrap">
    <jsp:include page="../sidebar.jsp" />

    <main class="lv-main">

        <div class="lv-page-header">
            <div>
                <h1 class="lv-page-title">Gestión de Tickets</h1>
                <p class="lv-page-subtitle">
                    <c:choose>
                        <c:when test="${not empty idTecnicoFiltro}">
                            Tickets asignados a: <strong>${nomTecnicoFiltro}</strong>
                        </c:when>
                        <c:when test="${sessionScope.objUsuario.id_rol == 1}">Todos los tickets del sistema</c:when>
                        <c:when test="${sessionScope.objUsuario.id_rol == 2}">Tickets asignados a tu área</c:when>
                        <c:otherwise>Mis tickets registrados</c:otherwise>
                    </c:choose>
                </p>
            </div>
            <div class="lv-header-right">
                <c:if test="${sessionScope.objUsuario.id_rol == 3}">
                    <a href="${pageContext.request.contextPath}/TicketServlet?tipo=nuevo" class="lv-btn-nuevo">
                        <span>＋</span> Nuevo Ticket
                    </a>
                </c:if>
                <div class="lv-count-badge">
                    <span class="lv-count-num">${fn:length(listadoTickets)}</span>
                    <span class="lv-count-label">tickets</span>
                </div>
            </div>
        </div>

        <c:if test="${not empty idTecnicoFiltro and sessionScope.objUsuario.id_rol == 1}">
            <div style="
                display:flex; align-items:center; justify-content:space-between;
                background:#1a2540; border:1px solid #2a3a60; border-radius:12px;
                padding:12px 18px; margin-bottom:16px; gap:12px;
            ">
                <div style="display:flex; align-items:center; gap:10px;">
                    <span style="font-size:18px;">🔧</span>
                    <div>
                        <span style="font-size:13px; font-weight:600; color:#f0f2f8;">
                            Filtrando por técnico:
                        </span>
                        <span style="
                            font-size:13px; font-weight:700; color:#5b8dee;
                            background:#0d1a30; border:1px solid #2a3a60;
                            padding:2px 10px; border-radius:6px; margin-left:6px;
                        ">
                            ${nomTecnicoFiltro}
                        </span>
                    </div>
                    <span style="font-size:12px; color:#606880; margin-left:4px;">
                        — ${fn:length(listadoTickets)} ticket(s)
                    </span>
                </div>
                <a href="${pageContext.request.contextPath}/TicketServlet?tipo=list"
                   style="
                       font-size:12px; font-weight:600; color:#e06b5b;
                       background:#2a1e1e; border:1px solid #3e2020;
                       padding:5px 12px; border-radius:8px; text-decoration:none;
                   ">
                    ✕ Quitar filtro
                </a>
            </div>
        </c:if>

        <c:if test="${empty idTecnicoFiltro}">
            <div class="lv-search-panel">

                <div class="lv-search-row">
                    <form action="${pageContext.request.contextPath}/TicketServlet" method="GET"
                          class="lv-form-buscar">
                        <input type="hidden" name="tipo" value="list">
                        <div class="lv-search-wrap">
                            <span class="lv-search-icon">🔍</span>
                            <input type="text" name="txtBuscar" class="lv-search-input"
                                   placeholder="Buscar por título..."
                                   value="${txtBuscar}">
                        </div>
                        <button type="submit" class="lv-search-btn">Buscar</button>
                        <c:if test="${not empty txtBuscar}">
                            <a href="${pageContext.request.contextPath}/TicketServlet?tipo=list"
                               class="lv-search-clear">✕ Limpiar</a>
                        </c:if>
                    </form>
                </div>

                <c:if test="${sessionScope.objUsuario.id_rol == 1}">
                    <div class="lv-date-row">
                        <form action="${pageContext.request.contextPath}/TicketServlet" method="GET"
                              class="lv-form-fecha">
                            <input type="hidden" name="tipo" value="list">
                            <span class="lv-date-label">📅 Rango de fechas:</span>
                            <input type="date" name="fechaInicio" class="lv-date-input"
                                   value="${fechaInicio}">
                            <span class="lv-date-sep">—</span>
                            <input type="date" name="fechaFin" class="lv-date-input"
                                   value="${fechaFin}">
                            <button type="submit" class="lv-search-btn">Filtrar</button>
                            <c:if test="${not empty fechaInicio or not empty fechaFin}">
                                <a href="${pageContext.request.contextPath}/TicketServlet?tipo=list"
                                   class="lv-search-clear">✕ Limpiar</a>
                            </c:if>
                        </form>
                    </div>
                </c:if>

                <c:if test="${not empty txtBuscar or not empty fechaInicio or not empty fechaFin}">
                    <div class="lv-search-msg">
                        <c:choose>
                            <c:when test="${not empty txtBuscar}">
                                🔍 Resultados para: <strong>"${txtBuscar}"</strong>
                                — ${fn:length(listadoTickets)} ticket(s) encontrado(s)
                            </c:when>
                            <c:otherwise>
                                📅 Filtro por fechas activo
                                <c:if test="${not empty fechaInicio}"> desde <strong>${fechaInicio}</strong></c:if>
                                <c:if test="${not empty fechaFin}"> hasta <strong>${fechaFin}</strong></c:if>
                                — ${fn:length(listadoTickets)} ticket(s) encontrado(s)
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </div>
        </c:if>

        <c:if test="${empty txtBuscar and empty fechaInicio and empty fechaFin and empty idTecnicoFiltro}">
            <div class="lv-filter-bar">
                <a href="${pageContext.request.contextPath}/TicketServlet?tipo=list"
                   class="lv-filter-tab ${empty filtroEstado ? 'active' : ''}">Todos</a>
                <a href="${pageContext.request.contextPath}/TicketServlet?tipo=list&filtro=1"
                   class="lv-filter-tab lv-est-tab-1 ${filtroEstado == '1' ? 'active' : ''}">
                    <span class="lv-tab-dot"></span> Abierto</a>
                <a href="${pageContext.request.contextPath}/TicketServlet?tipo=list&filtro=2"
                   class="lv-filter-tab lv-est-tab-2 ${filtroEstado == '2' ? 'active' : ''}">
                    <span class="lv-tab-dot"></span> En Proceso</a>
                <a href="${pageContext.request.contextPath}/TicketServlet?tipo=list&filtro=3"
                   class="lv-filter-tab lv-est-tab-3 ${filtroEstado == '3' ? 'active' : ''}">
                    <span class="lv-tab-dot"></span> Resuelto</a>
                <a href="${pageContext.request.contextPath}/TicketServlet?tipo=list&filtro=4"
                   class="lv-filter-tab lv-est-tab-4 ${filtroEstado == '4' ? 'active' : ''}">
                    <span class="lv-tab-dot"></span> Cerrado</a>
            </div>
        </c:if>

        <div class="lv-table-wrapper">
            <c:choose>
                <c:when test="${empty listadoTickets}">
                    <div class="lv-empty-state">
                        <div class="lv-empty-icon">🎫</div>
                        <p class="lv-empty-title">No hay tickets</p>
                        <p class="lv-empty-sub">
                            <c:choose>
                                <c:when test="${not empty idTecnicoFiltro}">
                                    Este técnico no tiene tickets asignados.
                                </c:when>
                                <c:when test="${not empty txtBuscar}">
                                    No se encontraron tickets con el título
                                    <strong>"${txtBuscar}"</strong>.
                                </c:when>
                                <c:when test="${not empty fechaInicio or not empty fechaFin}">
                                    No se encontraron tickets en el rango de fechas seleccionado.
                                </c:when>
                                <c:otherwise>
                                    No se encontraron tickets con el filtro seleccionado.
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </c:when>
                <c:otherwise>
                    <table class="lv-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Título</th>
                                <th>Tipo</th>
                                <th>Prioridad</th>
                                <th>Estado</th>
                                <th>Cliente</th>
                                <th>Técnico</th>
                                <th>Fecha</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="tk" items="${listadoTickets}" varStatus="st">
                                <tr class="lv-row" style="animation-delay: ${st.index * 40}ms">
                                    <td class="lv-cell-id">TK-<c:out value="${tk.id_ticket}"/></td>
                                    <td><span class="lv-titulo-text"><c:out value="${tk.titulo}"/></span></td>
                                    <td><c:out value="${tk.nom_tipo}"/></td>
                                    <td>
                                        <span class="lv-prio-badge lv-prio-${tk.id_prioridad}">
                                            <c:out value="${tk.nom_prioridad}"/>
                                        </span>
                                    </td>
                                    <td>
                                        <span class="lv-estado-pill lv-est-${tk.id_estado}">
                                            <c:out value="${tk.nom_estado}"/>
                                        </span>
                                    </td>
                                    <td>
                                        <div class="lv-avatar-row">
                                            <div class="lv-avatar">${fn:substring(tk.nom_usuario_reporta, 0, 1)}</div>
                                            <span><c:out value="${tk.nom_usuario_reporta}"/></span>
                                        </div>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty tk.nom_tecnico_asignado}">
                                                <div class="lv-avatar-row">
                                                    <div class="lv-avatar lv-avatar-tec">${fn:substring(tk.nom_tecnico_asignado, 0, 1)}</div>
                                                    <span><c:out value="${tk.nom_tecnico_asignado}"/></span>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="lv-sin-asignar">— Sin asignar</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><span class="lv-fecha-text">${fn:substring(tk.fecha_reg, 0, 10)}</span></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/TicketServlet?tipo=detalle&id=${tk.id_ticket}"
                                           class="lv-btn-ver">Ver →</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>

    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
