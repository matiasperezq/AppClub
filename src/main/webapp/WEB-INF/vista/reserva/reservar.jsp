<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Reservar</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <jsp:include page="/WEB-INF/vista/menu.jsp"/>

        <div class="contenedor">
            <h1>Reservar una cancha</h1>

            <c:if test="${param.ok == '1'}">
                <p class="ok">¡Reserva realizada!</p>
            </c:if>
            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>

            <%-- Paso 1: elegir el deporte (filtro) --%>
            <h2>1. Elegí el deporte</h2>
            <c:forEach var="d" items="${deportes}">
                <a class="boton ${deporteSel == d.iddeporte ? '' : 'claro'}"
                   href="Reservar?deporte=${d.iddeporte}">${d.nombre}</a>
            </c:forEach>

            <%-- Paso 2: elegir cancha y fecha --%>
            <c:if test="${not empty deporteSel}">
                <h2>2. Elegí la cancha y la fecha</h2>

                <c:if test="${empty canchas}">
                    <p>No hay canchas cargadas para este deporte.</p>
                </c:if>

                <c:if test="${not empty canchas}">
                    <form action="Reservar" method="get">
                        <input type="hidden" name="deporte" value="${deporteSel}">
                        Cancha:
                        <select name="cancha">
                            <c:forEach var="c" items="${canchas}">
                                <option value="${c.idcancha}" ${param.cancha == c.idcancha ? "selected" : ""}>
                                    ${c.nombre}
                                </option>
                            </c:forEach>
                        </select>
                        Fecha: <input type="date" name="fecha" value="${param.fecha}" min="${hoy}" required>
                        <input type="submit" value="Ver turnos">
                    </form>
                </c:if>
            </c:if>

            <%-- Paso 3: grilla de turnos (solo si ya se eligio cancha y fecha) --%>
            <c:if test="${turnos != null}">
                <h2>3. Turnos del ${param.fecha}</h2>

                <c:if test="${empty turnos}">
                    <p>No quedan turnos disponibles para este día.</p>
                </c:if>

                <c:if test="${not empty turnos}">
                    <table>
                        <tr>
                            <th>Turno</th>
                            <th>Estado</th>
                        </tr>
                        <c:forEach var="t" items="${turnos}">
                            <tr>
                                <td>${t[0]} a ${t[1]}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${ocupados.contains(t[0])}">
                                            <span class="badge ocupado">Ocupado</span>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="Reservar" method="post">
                                                <input type="hidden" name="cancha" value="${param.cancha}">
                                                <input type="hidden" name="fecha" value="${param.fecha}">
                                                <input type="hidden" name="hora" value="${t[0]}">
                                                <input type="submit" value="Reservar">
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </c:if>
        </div>
    </body>
</html>