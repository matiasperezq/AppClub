<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>AppClub</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <jsp:include page="/WEB-INF/vista/menu.jsp"/>

        <div class="contenedor">
            <c:choose>
                <c:when test="${empty sessionScope.usuario}">
                    <h1>Bienvenido a AppClub</h1>
                    <p>Reservá tu cancha en pocos pasos.</p>
                    <a class="boton" href="Login">Iniciar sesión</a>
                    <a class="boton" href="Registro">Registrarse</a>
                </c:when>
                <c:otherwise>
                    <h1>Hola, ${sessionScope.usuario.nombre}</h1>
                    <div class="tarjetas">
                        <c:if test="${sessionScope.usuario.rol == 'SOCIO'}">
                            <a class="tarjeta" href="Reservar"><span>📅</span>Reservar una cancha</a>
                            <a class="tarjeta" href="MisReservas"><span>📋</span>Mis reservas</a>
                        </c:if>
                        <c:if test="${sessionScope.usuario.rol == 'ADMIN'}">
                            <a class="tarjeta" href="ListarDeportes"><span>🏅</span>Deportes</a>
                            <a class="tarjeta" href="ListarCanchas"><span>🏟️</span>Canchas</a>
                            <a class="tarjeta" href="TodasLasReservas"><span>📋</span>Reservas</a>
                        </c:if>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <p class="pie">AppClub · Desarrollo Backend III</p>
    </body>
</html>