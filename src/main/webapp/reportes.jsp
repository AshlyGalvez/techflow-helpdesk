<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reportes | TechFlow</title>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:opsz,wght@9..40,400;9..40,500;9..40,600;9..40,700&family=Space+Mono:wght@400;700&display=swap" rel="stylesheet">
    <script src="https://unpkg.com/lucide@latest"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reportes.css">


</head>
<body>
<div class="rp-wrap">
    <jsp:include page="sidebar.jsp" />

    <main class="rp-main">

        <%-- Header --%>
        <div class="rp-header">
            <div class="rp-header-left">
                <div class="rp-header-top">
                    <div class="rp-header-icon">
                        <i data-lucide="bar-chart-3" width="22" height="22"></i>
                    </div>
                    <h1 class="rp-title">Reportes</h1>
                </div>
                <p class="rp-subtitle">Análisis y estadísticas del sistema de tickets</p>
            </div>
            <%-- Botón exportar PDF --%>
            <button class="btn-pdf" onclick="window.print()">
                <i data-lucide="file-down" width="16" height="16"></i>
                Exportar PDF
            </button>
        </div>

        <%-- Tabs --%>
        <div class="rp-tabs">
            <a href="${pageContext.request.contextPath}/DashboardServlet?tipo=reportes&tab=estado"
               class="rp-tab ${tabActivo == 'estado' ? 'active' : ''}">
                <span class="rp-tab-dot" style="background:#5b8dee;"></span>
                Resumen por Estado
            </a>
            <a href="${pageContext.request.contextPath}/DashboardServlet?tipo=reportes&tab=tipo"
               class="rp-tab ${tabActivo == 'tipo' ? 'active' : ''}">
                <span class="rp-tab-dot" style="background:#e0c56b;"></span>
                Por Tipo de Problema
            </a>
            <a href="${pageContext.request.contextPath}/DashboardServlet?tipo=reportes&tab=tecnicos"
               class="rp-tab ${tabActivo == 'tecnicos' ? 'active' : ''}">
                <span class="rp-tab-dot" style="background:#4db87a;"></span>
                Rendimiento Técnicos
            </a>
        </div>

        <%-- ══════════════ TAB 1: ESTADO ══════════════ --%>
        <c:if test="${tabActivo == 'estado'}">
            <div class="rp-card">
                <p class="rp-section-title">Resumen general</p>
                <div class="rp-kpi-grid">
                    <div class="rp-kpi-card kpi-total">
                        <span class="rp-kpi-label">Total tickets</span>
                        <span class="rp-kpi-num num-total">${totalEstado}</span>
                    </div>
                    <c:forEach var="row" items="${reporteEstado}">
                        <%-- Excluir estado 5 (Cerrado por Inactividad) --%>
                        <c:if test="${row.id_estado != 5}">
                            <c:set var="cls" value="${row.id_estado == 1 ? 'abierto' : row.id_estado == 2 ? 'proceso' : row.id_estado == 3 ? 'resuelto' : 'cerrado'}"/>
                            <div class="rp-kpi-card kpi-${cls}">
                                <span class="rp-kpi-label">${row.nom_estado}</span>
                                <span class="rp-kpi-num num-${cls}">${row.total}</span>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>

                <p class="rp-section-title">Distribución por estado</p>
                <div class="rp-estado-cards">
                    <c:forEach var="row" items="${reporteEstado}">
                        <c:if test="${row.id_estado != 5}">
                            <c:set var="pct" value="${totalEstado > 0 ? (row.total * 100) / totalEstado : 0}"/>
                            <c:set var="color" value="${row.id_estado == 1 ? '#e0c56b' : row.id_estado == 2 ? '#5b8dee' : row.id_estado == 3 ? '#4db87a' : '#666e8a'}"/>
                            <div class="rp-estado-card">
                                <span class="rp-estado-pill rp-est-${row.id_estado}">
                                    <span class="rp-estado-dot" style="background:${color};"></span>
                                    ${row.nom_estado}
                                </span>
                                <div class="rp-estado-bar-wrap">
                                    <div class="rp-bar-track">
                                        <div class="rp-bar-fill" style="width:<fmt:formatNumber value="${pct}" maxFractionDigits="0"/>%;background:${color};"></div>
                                    </div>
                                </div>
                                <div class="rp-estado-stats">
                                    <span class="rp-cantidad" style="color:${color};">${row.total}</span>
                                    <span class="rp-pct-badge"><fmt:formatNumber value="${pct}" maxFractionDigits="0"/>%</span>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                    <c:if test="${empty reporteEstado}">
                        <div class="rp-empty"><div class="rp-empty-icon">📊</div><p>No hay datos disponibles.</p></div>
                    </c:if>
                </div>
            </div>
        </c:if>

        <%-- ══════════════ TAB 2: TIPOS ══════════════ --%>
        <c:if test="${tabActivo == 'tipo'}">
            <div class="rp-card">
                <p class="rp-section-title">Total registrado</p>
                <div class="rp-tipo-kpi-grid">
                    <div class="rp-kpi-card kpi-total">
                        <span class="rp-kpi-label">Total tickets</span>
                        <span class="rp-kpi-num num-total">${totalTipo}</span>
                    </div>
                    <c:forEach var="row" items="${reporteTipo}">
                        <div class="rp-kpi-card" style="border-top:3px solid ${row.id_tipo==1?'#e06b5b':row.id_tipo==2?'#5b8dee':row.id_tipo==3?'#4db87a':'#c07af0'};">
                            <span class="rp-kpi-label">${row.nom_tipo}</span>
                            <span class="rp-kpi-num" style="color:${row.id_tipo==1?'#e06b5b':row.id_tipo==2?'#5b8dee':row.id_tipo==3?'#4db87a':'#c07af0'};">${row.cantidad}</span>
                        </div>
                    </c:forEach>
                </div>

                <p class="rp-section-title">Detalle por categoría</p>
                <c:if test="${empty reporteTipo}">
                    <div class="rp-empty"><div class="rp-empty-icon">🏷️</div><p>No hay datos disponibles.</p></div>
                </c:if>
                <div class="rp-tipo-grid">
                    <c:forEach var="row" items="${reporteTipo}">
                        <c:set var="pct" value="${totalTipo > 0 ? (row.cantidad * 100) / totalTipo : 0}"/>
                        <c:set var="clsTipo" value="tipo-${row.id_tipo <= 4 ? row.id_tipo : 'x'}"/>
                        <div class="rp-tipo-card ${clsTipo}">
                            <div class="rp-tipo-top">
                                <div>
                                    <div class="rp-tipo-name">${row.nom_tipo}</div>
                                    <div class="rp-tipo-sub"><fmt:formatNumber value="${pct}" maxFractionDigits="0"/>% del total</div>
                                    <div class="rp-tipo-stats">
                                        <%-- Cerrados (campo resueltos reutilizado) --%>
                                        <span class="rp-stat-chip chip-gray">
                                            <i data-lucide="archive" width="12" height="12"></i>
                                            ${row.resueltos} cerrados
                                        </span>
                                        <%-- Pendientes / en proceso --%>
                                        <span class="rp-stat-chip chip-amber">
                                            <i data-lucide="clock" width="12" height="12"></i>
                                            ${row.pendientes} pendientes
                                        </span>
                                    </div>
                                </div>
                                <div class="rp-tipo-icon">
                                    <c:choose>
                                        <c:when test="${row.id_tipo == 1}"><i data-lucide="cpu"       width="20" height="20"></i></c:when>
                                        <c:when test="${row.id_tipo == 2}"><i data-lucide="code-2"    width="20" height="20"></i></c:when>
                                        <c:when test="${row.id_tipo == 3}"><i data-lucide="wifi"      width="20" height="20"></i></c:when>
                                        <c:when test="${row.id_tipo == 4}"><i data-lucide="key-round" width="20" height="20"></i></c:when>
                                        <c:otherwise>                      <i data-lucide="tag"       width="20" height="20"></i></c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="rp-tipo-num">${row.cantidad}</div>
                            <div style="margin-top:14px;">
                                <div class="rp-tipo-bar-label">
                                    <span>Distribución</span>
                                    <span><fmt:formatNumber value="${pct}" maxFractionDigits="0"/>%</span>
                                </div>
                                <div class="rp-bar-track" style="margin-top:5px;">
                                    <div class="rp-bar-fill" style="width:<fmt:formatNumber value="${pct}" maxFractionDigits="0"/>%;background:${row.id_tipo==1?'#e06b5b':row.id_tipo==2?'#5b8dee':row.id_tipo==3?'#4db87a':'#c07af0'};"></div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>

        <%-- ══════════════ TAB 3: TÉCNICOS ══════════════ --%>
        <c:if test="${tabActivo == 'tecnicos'}">
            <div class="rp-card">
                <p class="rp-section-title">Resumen del equipo</p>
                <div class="rp-tec-kpi-grid">
                    <div class="rp-kpi-card kpi-proceso">
                        <span class="rp-kpi-label">Técnicos activos</span>
                        <span class="rp-kpi-num num-proceso">${fn:length(reporteTecnicos)}</span>
                    </div>
                    <c:set var="totalCerradosTec" value="0"/>
                    <c:forEach var="tec" items="${reporteTecnicos}">
                        <c:set var="totalCerradosTec" value="${totalCerradosTec + tec.total_resueltos}"/>
                    </c:forEach>
                    <div class="rp-kpi-card kpi-cerrado">
                        <span class="rp-kpi-label">Total cerrados</span>
                        <span class="rp-kpi-num num-cerrado">${totalCerradosTec}</span>
                    </div>
                </div>

                <p class="rp-section-title">Ranking de rendimiento</p>
                <c:if test="${empty reporteTecnicos}">
                    <div class="rp-empty"><div class="rp-empty-icon">👨‍💻</div><p>No hay técnicos registrados.</p></div>
                </c:if>
                <div class="rp-tec-list">
                    <c:forEach var="tec" items="${reporteTecnicos}" varStatus="st">
                        <c:set var="eficiencia" value="${tec.total_asignados > 0 ? (tec.total_resueltos * 100) / tec.total_asignados : 0}"/>
                        <c:set var="efRound"><fmt:formatNumber value="${eficiencia}" maxFractionDigits="0"/></c:set>
                        <div class="rp-tec-card">
                            <span class="rp-rank ${st.index == 0 ? 'gold' : st.index == 1 ? 'silver' : st.index == 2 ? 'bronze' : ''}">#${st.index + 1}</span>
                            <div class="rp-avatar">${fn:substring(tec.nombre, 0, 1)}</div>
                            <div class="rp-tec-info">
                                <div class="rp-tec-name">${tec.nombre}</div>
                                <div class="rp-tec-bar-wrap">
                                    <div class="rp-tec-bar-track">
                                        <div class="rp-tec-bar-fill ${eficiencia >= 70 ? 'bar-green' : eficiencia >= 40 ? 'bar-amber' : 'bar-red'}" style="width:${efRound}%;"></div>
                                    </div>
                                    <span class="rp-tec-bar-pct">${efRound}%</span>
                                </div>
                            </div>
                            <div class="rp-tec-chips">
                                <div class="rp-chip chip-blue">
                                    <span class="rp-chip-num">${tec.total_asignados}</span>
                                    <span class="rp-chip-label">Asignados</span>
                                </div>
                                <%-- total_resueltos ahora contiene cerrados --%>
                                <div class="rp-chip chip-green2">
                                    <span class="rp-chip-num">${tec.total_resueltos}</span>
                                    <span class="rp-chip-label">Cerrados</span>
                                </div>
                                <div class="rp-chip chip-amber2">
                                    <span class="rp-chip-num">${tec.total_pendientes}</span>
                                    <span class="rp-chip-label">Pendientes</span>
                                </div>
                            </div>
                            <span class="rp-eficiencia-badge ${eficiencia >= 70 ? 'ef-high' : eficiencia >= 40 ? 'ef-medium' : 'ef-low'}">${efRound}%</span>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>

    </main>
</div>

<script>lucide.createIcons();</script>
</body>
</html>
