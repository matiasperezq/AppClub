<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<nav class="menu">
    <a class="marca" href="${ctx}/index.jsp">⚽ AppClub</a>

    <div class="enlaces">
        <c:if test="${sessionScope.usuario.rol == 'SOCIO'}">
            <a href="${ctx}/Reservar">Reservar</a>
            <a href="${ctx}/MisReservas">Mis reservas</a>
        </c:if>
        <c:if test="${sessionScope.usuario.rol == 'ADMIN'}">
            <a href="${ctx}/ListarDeportes">Deportes</a>
            <a href="${ctx}/ListarCanchas">Canchas</a>
            <a href="${ctx}/TodasLasReservas">Reservas</a>
        </c:if>
    </div>

    <div class="sesion">
        <c:choose>
            <c:when test="${empty sessionScope.usuario}">
                <a href="${ctx}/Login">Ingresar</a>
                <a href="${ctx}/Registro">Registrarse</a>
            </c:when>
            <c:otherwise>
                <span>${sessionScope.usuario.nombre} (${sessionScope.usuario.rol})</span>
                <a href="${ctx}/Logout">Salir</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>