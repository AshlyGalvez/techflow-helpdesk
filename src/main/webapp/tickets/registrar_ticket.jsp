<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nuevo Ticket | TechFlow</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registrar.css">
</head>
<body>
<div class="rt-wrap">
    <jsp:include page="../sidebar.jsp" />

    <main class="rt-main">

        <div class="rt-header">
            <div>
                <h1 class="rt-title">
                    <span class="rt-title-icon">🎫</span>
                    Nuevo Ticket
                </h1>
                <p class="rt-subtitle">Reporta un problema y nuestro equipo técnico te ayudará.</p>
            </div>
            <a href="${pageContext.request.contextPath}/TicketServlet?tipo=list" class="rt-btn-volver">← Volver</a>
        </div>

        <c:if test="${not empty mensaje}">
            <div class="rt-alert">⚠️ &nbsp;${mensaje}</div>
        </c:if>

        <div class="rt-card">
            <form action="${pageContext.request.contextPath}/TicketServlet?tipo=registrar" method="POST">
>
                <p class="rt-section">Clasificación del problema</p>

                <div class="rt-grid-2">

                    <div class="rt-field">
                        <label class="rt-label" for="selTipo">
                            Tipo de problema <span class="req">*</span>
                        </label>
                        <select name="idTipo" id="selTipo" class="rt-select" required>
                            <option value="" disabled selected>Seleccionar categoría...</option>
                            <c:forEach var="tp" items="${tipos}">
                                <option value="${tp.id_tipo}">${tp.nom_tipo}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="rt-field">
                        <label class="rt-label" for="selSubtipo">
                            Problema específico <span class="req">*</span>
                        </label>
                        <select name="idSubtipo" id="selSubtipo" class="rt-select" required disabled>
                            <option value="" disabled selected>— Primero elige un tipo —</option>
                        </select>
                        <p class="rt-hint" id="subHint">
                            ↑ Selecciona un tipo para ver los problemas disponibles
                        </p>
                    </div>

                </div>

                <div class="rt-field">
                    <label class="rt-label">Prioridad <span class="req">*</span></label>
                    <div class="rt-prio-group">
                        <c:forEach var="p" items="${prioridades}">
                            <input type="radio"
                                   name="idPrioridad"
                                   id="prio_${p.id_prioridad}"
                                   value="${p.id_prioridad}"
                                   class="rt-prio-radio" required />
                            <label for="prio_${p.id_prioridad}" class="rt-prio-pill">
                                <span class="rt-prio-dot prio-dot-${p.id_prioridad}"></span>
                                ${p.nom_prioridad}
                            </label>
                        </c:forEach>
                    </div>
                </div>

                <hr class="rt-divider">

                <p class="rt-section">Detalle del problema</p>

                <div class="rt-field">
                    <label class="rt-label" for="txtTitulo">
                        Título <span class="req">*</span>
                    </label>
                    <input type="text" name="txtTitulo" id="txtTitulo" class="rt-input" required
                           placeholder="Ej: No puedo acceder al sistema de nómina" />
                </div>

                <div class="rt-field">
                    <label class="rt-label" for="txtDescripcion">
                        Descripción detallada <span class="req">*</span>
                    </label>
                    <textarea name="txtDescripcion" id="txtDescripcion"
                              class="rt-textarea" required
                              placeholder="Describe paso a paso qué ocurrió, cuándo empezó y qué estabas haciendo..."></textarea>
                    <div class="rt-tip">
                        <span class="rt-tip-icon">💡</span>
                        <p class="rt-tip-text">
                            <strong>Tip:</strong> Entre más detalle proporciones, más rápido podremos ayudarte.
                            Incluye mensajes de error, pasos para reproducir el problema y cualquier información relevante.
                        </p>
                    </div>
                </div>

                <input type="hidden" name="idUsuario" value="${sessionScope.objUsuario.id_usuario}">

                <div class="rt-actions">
                    <button type="submit" class="rt-btn-submit">✉ Enviar Ticket</button>
                    <a href="${pageContext.request.contextPath}/TicketServlet?tipo=list"
                       class="rt-btn-cancel">Cancelar</a>
                </div>

            </form>
        </div>
    </main>
</div>


<script>
    var subtiposMap = {};
    <c:forEach var="s" items="${subtipos}">
    if (!subtiposMap["${s.id_tipo}"]) subtiposMap["${s.id_tipo}"] = [];
    subtiposMap["${s.id_tipo}"].push({ id: "${s.id_subtipo}", nom: "${fn:escapeXml(s.nom_subtipo)}" });
    </c:forEach>

    document.getElementById('selTipo').addEventListener('change', function () {
        var idTipo = this.value;
        var sel    = document.getElementById('selSubtipo');
        var hint   = document.getElementById('subHint');
        var lista  = subtiposMap[idTipo];

        sel.innerHTML = '<option value="" disabled selected>Seleccionar problema específico...</option>';

        if (lista && lista.length > 0) {
            lista.forEach(function (s) {
                var opt = document.createElement('option');
                opt.value       = s.id;
                opt.textContent = s.nom;
                sel.appendChild(opt);
            });
            sel.disabled      = false;
            hint.style.display = 'none';
        } else {
            sel.disabled       = true;
            hint.style.display = 'flex';
            hint.textContent   = 'No hay problemas registrados para este tipo.';
        }
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
