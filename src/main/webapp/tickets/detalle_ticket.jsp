<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>HelpDesk | Ticket #${ticket.id_ticket}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ver_detalle.css">
</head>
<body>
<div class="app-layout">
    <jsp:include page="../sidebar.jsp" />

    <main class="main-content">

        <div class="ticket-topbar">
            <div>
                <div class="ticket-id">TK-<c:out value="${ticket.id_ticket}"/></div>
                <div class="ticket-subtitle"><c:out value="${ticket.titulo}"/></div>
            </div>
<a href="${pageContext.request.contextPath}/TicketServlet?tipo=list" class="btn-back">← Volver</a>        </div>

        <div class="detail-grid">
          
            <div style="display:flex;flex-direction:column;gap:20px;">               
                <div class="card-panel card-desc">
                    <div class="panel-title">📋 Descripción
                        <span class="estado-badge estado-${ticket.id_estado}" style="margin-left:auto;margin-right:0;">
                            <c:out value="${ticket.nom_estado}"/>
                        </span>
                    </div>
                    <div class="desc-box"><c:out value="${ticket.descripcion}"/></div>
                </div>
                
                <div class="card-panel card-chat">
                    <div class="panel-title">💬 Chat &amp; Comentarios</div>
                    
                    <c:set var="fueReabierto" value="false"/>
                    <c:forEach var="com" items="${comentarios}">
                        <c:if test="${fn:startsWith(com.texto_comentario, 'CASO REABIERTO')}">
                            <c:set var="fueReabierto" value="true"/>
                        </c:if>
                    </c:forEach>

                    
                    <c:set var="solCount" value="0"/>
                    <c:forEach var="com" items="${comentarios}">
                        <c:if test="${com.es_solucion == 1}">
                            <c:set var="solCount" value="${solCount + 1}"/>
                        </c:if>
                    </c:forEach>

                    
                    <div class="chat-box" id="chatBox">
                        <c:forEach var="com" items="${comentarios}">
                            <div class="msg-row ${com.id_usuario == sessionScope.objUsuario.id_usuario ? 'mine' : 'theirs'}">
                                <c:if test="${com.es_solucion == 1}">
                                    <span class="sol-tag">✔ Solución registrada</span>
                                </c:if>
                                <div class="msg-sender"><c:out value="${com.nom_usuario}"/></div>
                                <div class="msg-bubble ${com.es_solucion == 1 ? 'es-solucion' : ''}">
                                    <c:out value="${com.texto_comentario}"/>
                                    <span class="msg-time"><c:out value="${com.fecha_reg}"/></span>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <hr class="hd-divider">

                    
                    <c:if test="${sessionScope.objUsuario.id_rol == 2 && ticket.id_estado == 2}">
                        <div class="panel-title" style="margin-bottom:12px;">
                            <c:choose>
                                <c:when test="${solCount >= 1}">🔒 Registrar respuesta definitiva</c:when>
                                <c:otherwise>📝 Registrar solución</c:otherwise>
                            </c:choose>
                        </div>
                        <form action="${pageContext.request.contextPath}/TicketServlet" method="POST" accept-charset="UTF-8">
                            <input type="hidden" name="tipo"     value="resolver">
                            <input type="hidden" name="idTicket" value="${ticket.id_ticket}">
                            <input type="hidden" name="solCount" value="${solCount}">
                            <textarea name="txtSolucion" class="hd-input" rows="4"
                                      placeholder="Describe la solución aplicada..." required></textarea>
                            <div style="margin-top:13px;">
                                <c:choose>
                                    
                                    <c:when test="${solCount >= 1}">
                                        <button type="submit" class="btn-danger-hd">
                                            🔒 Cerrar ticket definitivamente
                                        </button>
                                    </c:when>
                                    
                                    <c:otherwise>
                                        <button type="submit" class="btn-success-hd">
                                            ✔ Marcar resuelto
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </form>
                        <c:if test="${solCount >= 1}">
                            <div class="aviso-box aviso-warning" style="margin-top:12px;">
                                ⚠️ El usuario rechazó la solución anterior. Al enviar esta respuesta el ticket se
                                <strong>cerrará definitivamente</strong>.
                            </div>
                        </c:if>
                    </c:if>

                    <c:if test="${sessionScope.objUsuario.id_rol == 2 && ticket.id_estado == 3 && fueReabierto == false}">
                        <div class="aviso-box">✅ Ticket marcado como resuelto. Esperando confirmación del usuario.</div>
                    </c:if>

                  <c:if test="${sessionScope.objUsuario.id_rol == 2 && ticket.id_estado == 3 && fueReabierto == true}">
                        <div class="aviso-box">🔒 Respuesta definitiva enviada. El ticket ha sido cerrado.</div>
                    </c:if>

                    <c:if test="${sessionScope.objUsuario.id_rol == 2 && (ticket.id_estado == 4 || ticket.id_estado == 5)}">
                        <div class="aviso-box">🔒 Ticket cerrado definitivamente.</div>
                    </c:if>

                    <c:if test="${sessionScope.objUsuario.id_rol == 3 && ticket.id_estado != 3 && ticket.id_estado != 4 && ticket.id_estado != 5}">
                        <form action="${pageContext.request.contextPath}/TicketServlet" method="POST" accept-charset="UTF-8">
                            <input type="hidden" name="tipo"     value="comentar">
                            <input type="hidden" name="idTicket" value="${ticket.id_ticket}">
                            <div style="display:flex;gap:10px;align-items:flex-end;">
                                <textarea name="txtComentario" class="hd-input" rows="2"
                                          placeholder="Escribe un comentario..." required></textarea>
                                <button type="submit" class="btn-primary-hd" style="white-space:nowrap;">Enviar</button>
                            </div>
                        </form>
                    </c:if>

                    <c:if test="${sessionScope.objUsuario.id_rol == 3 && ticket.id_estado == 3 && fueReabierto == false}">
                        <div class="conformidad-box">
                            <p>✅ Tu ticket ha sido marcado como <strong>Resuelto</strong>.<br>
                               ¿Estás conforme con la solución aplicada?</p>
                            <div style="display:flex;gap:12px;flex-wrap:wrap;">
                                <form action="${pageContext.request.contextPath}/TicketServlet" method="POST" accept-charset="UTF-8">
                                    <input type="hidden" name="tipo"     value="finalizar">
                                    <input type="hidden" name="idTicket" value="${ticket.id_ticket}">
                                    <button type="submit" class="btn-success-hd">👍 Sí, cerrar ticket</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/TicketServlet" method="POST" accept-charset="UTF-8">
                                    <input type="hidden" name="tipo"     value="reabrir">
                                    <input type="hidden" name="idTicket" value="${ticket.id_ticket}">
                                    <button type="submit" class="btn-danger-hd">👎 No, reabrir caso</button>
                                </form>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${sessionScope.objUsuario.id_rol == 3 && ticket.id_estado == 3 && fueReabierto == true}">
                        <div class="aviso-box">🔒 El técnico ha enviado la respuesta definitiva. El ticket ha sido cerrado.</div>
                    </c:if>

                    <c:if test="${sessionScope.objUsuario.id_rol == 3 && (ticket.id_estado == 4 || ticket.id_estado == 5)}">
                        <div class="aviso-box">🔒 Este ticket ha sido cerrado definitivamente.</div>
                    </c:if>

                </div>
            </div>

            
            <div style="display:flex;flex-direction:column;gap:20px;">

                <div class="card-panel card-info">
                    <div class="panel-title">ℹ️ Información</div>
                    <div class="info-row">
                        <span class="info-label">Estado</span>
                        <c:choose>
                            <c:when test="${ticket.id_estado == 3 && fueReabierto == true}">
                                <span class="estado-badge estado-4">Cerrado</span>
                            </c:when>
                            <c:otherwise>
                                <span class="estado-badge estado-${ticket.id_estado}">
                                    <c:out value="${ticket.nom_estado}"/>
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Tipo</span>
                        <span class="info-value"><c:out value="${ticket.nom_tipo}"/></span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Prioridad</span>
                        <span class="info-value ${ticket.id_prioridad == 1 ? 'prio-baja' :
                                                   ticket.id_prioridad == 2 ? 'prio-media' :
                                                   ticket.id_prioridad == 3 ? 'prio-alta'  : 'prio-critica'}">
                            <c:out value="${ticket.nom_prioridad}"/>
                        </span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Fecha</span>
                        <span class="info-value" style="font-size:.77rem;color:var(--text-muted);">
                            <c:out value="${ticket.fecha_reg}"/>
                        </span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Reportado por</span>
                        <span class="info-value"><c:out value="${ticket.nom_usuario_reporta}"/></span>
                    </div>
                    <div class="info-row">
                        <span class="info-label">Técnico</span>
                        <span class="info-value" style="color:var(--accent-blue);">
                            <c:choose>
                                <c:when test="${not empty ticket.nom_tecnico_asignado}">
                                    <c:out value="${ticket.nom_tecnico_asignado}"/>
                                </c:when>
                                <c:otherwise>—</c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                    <c:if test="${sessionScope.objUsuario.id_rol == 1}">
                    <div class="card-panel card-hist" style="border: 1px solid #ffc107; background-color: #fffdf5;">
                        <div class="panel-title" style="color: #856404;">🔄 Reasignar Responsable</div>
                        <form action="${pageContext.request.contextPath}/TicketServlet" method="POST">
                            <input type="hidden" name="tipo" value="asignar">
                            <input type="hidden" name="idTicket" value="${ticket.id_ticket}">
                            
                            <select name="idTecnico" class="hd-input" style="margin-bottom: 10px; font-size: 0.85rem;" required>
                                <option value="" disabled selected>Seleccione técnico...</option>
                                <c:forEach var="tec" items="${tecnicos}">
                                    <option value="${tec.id_usuario}" ${tec.id_usuario == ticket.id_tecnico_asignado ? 'selected' : ''}>
                                        <c:out value="${tec.nombre}"/>
                                    </option>
                                </c:forEach>
                            </select>
                            
                            <button type="submit" class="btn-primary-hd" style="width: 100%; background-color: #ffc107; color: #212529; border: none; font-size: 0.8rem; font-weight: bold;">
                                ACTUALIZAR RESPONSABLE
                            </button>
                        </form>
                    </div>
                </c:if>
                </div>

                <div class="card-panel card-hist">
                    <div class="panel-title">📅 Historial</div>
                    <div class="hist-row">
                        <div class="hist-dot"></div>
                        <span>Creado: <strong><c:out value="${ticket.fecha_reg}"/></strong></span>
                    </div>
                    <c:if test="${not empty ticket.fecha_cierre}">
                        <div class="hist-row">
                            <div class="hist-dot" style="background:var(--accent-green);"></div>
                            <span>Cerrado: <strong><c:out value="${ticket.fecha_cierre}"/></strong></span>
                        </div>
                    </c:if>
                    <div class="hist-row" style="margin-top:4px;">
                        <div class="hist-dot" style="background:var(--accent-amber);"></div>
                        <span>💬 <strong>${fn:length(comentarios)}</strong> comentario(s)</span>
                    </div>
                </div>

            </div>
        </div>
    </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    (function(){ const b = document.getElementById('chatBox'); if(b) b.scrollTop = b.scrollHeight; })();
</script>
</body>
</html>
