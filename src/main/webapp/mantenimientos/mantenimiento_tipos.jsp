<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Tipos de Problema | TechFlow</title>
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
                <h1 class="mant-title">Tipos de Problema</h1>
                <p class="mant-subtitle">Categorías y problemas específicos para clasificar los tickets</p>
            </div>
        </div>

        <!-- ALERTA -->
        <c:if test="${not empty mensaje}">
            <div class="mant-alert">
                <span>ℹ️ &nbsp;${mensaje}</span>
            </div>
        </c:if>

        <!-- ══════════════════════════════════════════════
             BLOQUE 1: TIPOS PRINCIPALES
        ══════════════════════════════════════════════ -->
        <div class="mant-grid" style="margin-bottom: 32px;">

            <!-- FORMULARIO TIPO -->
            <div class="mant-card">
                <div class="mant-card-title">
                    <span class="card-icon">${empty tData ? '➕' : '✏️'}</span>
                    ${empty tData ? 'Registrar Categoría' : 'Editar Categoría'}
                </div>

                <form action="${pageContext.request.contextPath}/TipoProblemaServlet"
                      method="post" class="mant-form">
                    <input type="hidden" name="tipo" value="${empty tData ? 'regist' : 'edit'}">

                    <c:if test="${not empty tData}">
                        <div class="field-group">
                            <label class="field-label">ID</label>
                            <input type="text" name="id_tipo" class="field-input"
                                   value="${tData.id_tipo}" readonly
                                   style="opacity:0.5;cursor:not-allowed;">
                        </div>
                    </c:if>

                    <div class="field-group">
                        <label class="field-label">Nombre de la categoría</label>
                        <input type="text" name="txtNombre" class="field-input"
                               value="${tData.nom_tipo}"
                               placeholder="Ej: Hardware, Software..." required>
                    </div>

                    <div class="mant-btn-group">
                        <button type="submit" class="btn-mant-save">
                            <i data-lucide="save" style="width:15px;height:15px;"></i>
                            ${empty tData ? 'Registrar Categoría' : 'Guardar Cambios'}
                        </button>
                        <c:if test="${not empty tData}">
                            <a href="${pageContext.request.contextPath}/TipoProblemaServlet?tipo=list"
                               class="btn-mant-cancel">
                                <i data-lucide="x" style="width:15px;height:15px;"></i>
                                Cancelar edición
                            </a>
                        </c:if>
                    </div>
                </form>
            </div>

            <!-- TABLA TIPOS -->
            <div class="mant-card">
                <div class="mant-card-title">
                    <span class="card-icon">🏷️</span>
                    Categorías registradas
                    <span style="margin-left:auto;font-size:0.75rem;color:#555a6e;font-weight:400;">
                        ${fn:length(listado)} registros
                    </span>
                </div>

                <div class="mant-table-wrap">
                    <c:choose>
                        <c:when test="${empty listado}">
                            <div class="mant-empty">
                                <div class="mant-empty-icon">🏷️</div>
                                <p>No hay categorías registradas.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <table class="mant-table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Categoría</th>
                                        <th style="text-align:center;">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${listado}" varStatus="st">
                                        <c:set var="nom" value="${fn:toLowerCase(item.nom_tipo)}"/>
                                        <c:set var="tipoCls"  value="tipo-default"/>
                                        <c:set var="tipoIcon" value="🔹"/>
                                        <c:if test="${fn:contains(nom, 'hardware')}">
                                            <c:set var="tipoCls"  value="tipo-hardware"/>
                                            <c:set var="tipoIcon" value="🖥️"/>
                                        </c:if>
                                        <c:if test="${fn:contains(nom, 'software')}">
                                            <c:set var="tipoCls"  value="tipo-software"/>
                                            <c:set var="tipoIcon" value="💻"/>
                                        </c:if>
                                        <c:if test="${fn:contains(nom, 'red') or fn:contains(nom, 'network')}">
                                            <c:set var="tipoCls"  value="tipo-redes"/>
                                            <c:set var="tipoIcon" value="🌐"/>
                                        </c:if>
                                        <c:if test="${fn:contains(nom, 'acceso') or fn:contains(nom, 'accesos')}">
                                            <c:set var="tipoCls"  value="tipo-accesos"/>
                                            <c:set var="tipoIcon" value="🔐"/>
                                        </c:if>

                                        <tr class="mant-row" style="animation-delay:${st.index * 40}ms;">
                                            <td style="font-family:'Courier New',monospace;color:#555a6e;">
                                                #${item.id_tipo}
                                            </td>
                                            <td>
                                                <div style="display:flex;align-items:center;gap:10px;">
                                                    <span style="font-size:1.1rem;">${tipoIcon}</span>
                                                    <span class="tipo-chip ${tipoCls}">${item.nom_tipo}</span>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="action-group">
                                                    <a href="${pageContext.request.contextPath}/TipoProblemaServlet?tipo=modif&id_tipo=${item.id_tipo}"
                                                       class="btn-edit btn-toggle" title="Editar">✏️</a>
                                                    <a href="${pageContext.request.contextPath}/TipoProblemaServlet?tipo=delete&id=${item.id_tipo}"
                                                       class="btn-toggle btn-toggle-off" title="Eliminar"
                                                       onclick="return confirm('¿Eliminar esta categoría? Se eliminarán también sus problemas específicos.')">🗑️</a>
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

        </div><!-- /mant-grid tipos -->

        <!-- ══════════════════════════════════════════════
             BLOQUE 2: PROBLEMAS ESPECÍFICOS (SUBTIPOS)
        ══════════════════════════════════════════════ -->
        <div class="mant-section-divider">
            <span class="mant-section-label">⚡ Problemas específicos</span>
        </div>

        <div class="mant-grid">

            <!-- FORMULARIO SUBTIPO -->
            <div class="mant-card">
                <div class="mant-card-title">
                    <span class="card-icon">${empty sData ? '➕' : '✏️'}</span>
                    ${empty sData ? 'Registrar Problema' : 'Editar Problema'}
                </div>

                <form action="${pageContext.request.contextPath}/TipoProblemaServlet"
                      method="post" class="mant-form">
                    <input type="hidden" name="tipo" value="${empty sData ? 'regist-sub' : 'edit-sub'}">

                    <c:if test="${not empty sData}">
                        <div class="field-group">
                            <label class="field-label">ID</label>
                            <input type="text" name="id_subtipo" class="field-input"
                                   value="${sData.id_subtipo}" readonly
                                   style="opacity:0.5;cursor:not-allowed;">
                        </div>
                    </c:if>

                    <div class="field-group">
                        <label class="field-label">Categoría</label>
                        <select name="idTipoSub" class="field-input" required>
                            <option value="" disabled selected>Seleccionar categoría...</option>
                            <c:forEach var="tp" items="${tipos}">
                                <option value="${tp.id_tipo}"
                                    ${not empty sData and sData.id_tipo == tp.id_tipo ? 'selected' : ''}>
                                    ${tp.nom_tipo}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="field-group">
                        <label class="field-label">Descripción del problema</label>
                        <input type="text" name="txtNombreSub" class="field-input"
                               value="${sData.nom_subtipo}"
                               placeholder="Ej: PC no enciende" required>
                    </div>

                    <div class="mant-btn-group">
                        <button type="submit" class="btn-mant-save">
                            <i data-lucide="save" style="width:15px;height:15px;"></i>
                            ${empty sData ? 'Registrar Problema' : 'Guardar Cambios'}
                        </button>
                        <c:if test="${not empty sData}">
                            <a href="${pageContext.request.contextPath}/TipoProblemaServlet?tipo=list"
                               class="btn-mant-cancel">
                                <i data-lucide="x" style="width:15px;height:15px;"></i>
                                Cancelar edición
                            </a>
                        </c:if>
                    </div>
                </form>
            </div>

            <!-- TABLA SUBTIPOS -->
            <div class="mant-card">
                <div class="mant-card-title">
                    <span class="card-icon">⚡</span>
                    Problemas específicos registrados
                    <span style="margin-left:auto;font-size:0.75rem;color:#555a6e;font-weight:400;">
                        ${fn:length(listadoSub)} registros
                    </span>
                </div>

                <div class="mant-table-wrap">
                    <c:choose>
                        <c:when test="${empty listadoSub}">
                            <div class="mant-empty">
                                <div class="mant-empty-icon">⚡</div>
                                <p>No hay problemas específicos registrados.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <table class="mant-table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Categoría</th>
                                        <th>Problema específico</th>
                                        <th style="text-align:center;">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="sub" items="${listadoSub}" varStatus="st">
                                        <c:set var="nomTipo" value="${fn:toLowerCase(sub.nom_tipo)}"/>
                                        <c:set var="subCls"  value="tipo-default"/>
                                        <c:if test="${fn:contains(nomTipo, 'hardware')}">
                                            <c:set var="subCls" value="tipo-hardware"/>
                                        </c:if>
                                        <c:if test="${fn:contains(nomTipo, 'software')}">
                                            <c:set var="subCls" value="tipo-software"/>
                                        </c:if>
                                        <c:if test="${fn:contains(nomTipo, 'red') or fn:contains(nomTipo, 'network')}">
                                            <c:set var="subCls" value="tipo-redes"/>
                                        </c:if>
                                        <c:if test="${fn:contains(nomTipo, 'acceso') or fn:contains(nomTipo, 'accesos')}">
                                            <c:set var="subCls" value="tipo-accesos"/>
                                        </c:if>

                                        <tr class="mant-row" style="animation-delay:${st.index * 40}ms;">
                                            <td style="font-family:'Courier New',monospace;color:#555a6e;">
                                                #${sub.id_subtipo}
                                            </td>
                                            <td>
                                                <span class="tipo-chip ${subCls}">${sub.nom_tipo}</span>
                                            </td>
                                            <td style="color:#e8eaf0;font-weight:500;">
                                                ${sub.nom_subtipo}
                                            </td>
                                            <td>
                                                <div class="action-group">
                                                    <a href="${pageContext.request.contextPath}/TipoProblemaServlet?tipo=modif-sub&id_subtipo=${sub.id_subtipo}"
                                                       class="btn-edit btn-toggle" title="Editar">✏️</a>
                                                    <a href="${pageContext.request.contextPath}/TipoProblemaServlet?tipo=delete-sub&id=${sub.id_subtipo}"
                                                       class="btn-toggle btn-toggle-off" title="Eliminar"
                                                       onclick="return confirm('¿Eliminar este problema específico?')">🗑️</a>
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

        </div><!-- /mant-grid subtipos -->

    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>lucide.createIcons();</script>
</body>
</html>
