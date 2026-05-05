<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TechFlow — Acceso al Sistema</title>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;600;700&family=Space+Mono:wght@400;700&display=swap" rel="stylesheet">
    <script src="https://unpkg.com/lucide@latest"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>

    <!-- Fondo con rejilla y luces ambientales -->
    <div class="bg-grid"></div>
    <div class="bg-glow bg-glow-1"></div>
    <div class="bg-glow bg-glow-2"></div>

    <div class="login-wrapper">

        <!-- Panel izquierdo: branding -->
        <div class="brand-panel">
            <div class="brand-content">
                <div class="brand-logo">
                    <i data-lucide="shield-check"></i>
                </div>
                <h1 class="brand-name">TechFlow</h1>
                <p class="brand-tagline">Sistema de Gestión<br>de Tickets de Soporte</p>

                <div class="brand-stats">
                    <div class="stat-item">
                        <span class="stat-num">99%</span>
                        <span class="stat-txt">Uptime</span>
                    </div>
                    <div class="stat-divider"></div>
                    <div class="stat-item">
                        <span class="stat-num">24/7</span>
                        <span class="stat-txt">Soporte</span>
                    </div>
                    <div class="stat-divider"></div>
                    <div class="stat-item">
                        <span class="stat-num">SLA</span>
                        <span class="stat-txt">Garantizado</span>
                    </div>
                </div>

                <div class="brand-features">
                    <div class="feature-row">
                        <i data-lucide="check-circle"></i>
                        <span>Gestión de incidencias en tiempo real</span>
                    </div>
                    <div class="feature-row">
                        <i data-lucide="check-circle"></i>
                        <span>Asignación automática de técnicos</span>
                    </div>
                    <div class="feature-row">
                        <i data-lucide="check-circle"></i>
                        <span>Seguimiento completo del flujo</span>
                    </div>
                </div>
            </div>
            <div class="brand-footer">© 2026 TechFlow System</div>
        </div>

        <!-- Panel derecho: formulario -->
        <div class="form-panel">
            <div class="form-card">

                <div class="form-header">
                    <h2 class="form-title">Bienvenido</h2>
                    <p class="form-subtitle">Ingresa tus credenciales para continuar</p>
                </div>

                <!-- Error message -->
                <c:if test="${not empty mensaje}">
                    <div class="alert-error">
                        <i data-lucide="alert-triangle"></i>
                        <span>${mensaje}</span>
                    </div>
                </c:if>

                <form action="LoginServlet" method="POST" autocomplete="off">
                    <input type="hidden" name="opc" value="login">

                    <div class="field-group">
                        <label class="field-label" for="usuario">Usuario</label>
                        <div class="field-wrap">
                            <span class="field-icon"><i data-lucide="user"></i></span>
                            <input type="text" id="usuario" name="txtUsuario"
                                   class="field-input" placeholder="Ej: jsmith" required>
                        </div>
                    </div>

                    <div class="field-group">
                        <label class="field-label" for="password">Contraseña</label>
                        <div class="field-wrap">
                            <span class="field-icon"><i data-lucide="lock"></i></span>
                            <input type="password" id="password" name="txtClave"
                                   class="field-input" placeholder="••••••••" required>
                            <button type="button" class="toggle-pass" onclick="togglePass()" tabindex="-1">
                                <i data-lucide="eye" id="eyeIcon"></i>
                            </button>
                        </div>
                    </div>

                    <button type="submit" class="btn-submit">
                        <i data-lucide="log-in"></i>
                        <span>Acceder al Sistema</span>
                    </button>
                </form>

                <div class="form-hint">
                    <i data-lucide="info"></i>
                    <span>Acceso restringido a personal autorizado</span>
                </div>

            </div>
        </div>

    </div>

<script>
    lucide.createIcons();

    function togglePass() {
        const input = document.getElementById('password');
        const icon  = document.getElementById('eyeIcon');
        if (input.type === 'password') {
            input.type = 'text';
            icon.setAttribute('data-lucide', 'eye-off');
        } else {
            input.type = 'password';
            icon.setAttribute('data-lucide', 'eye');
        }
        lucide.createIcons();
    }
</script>
</body>
</html>
