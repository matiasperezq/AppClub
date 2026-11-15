<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Mis reservas</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <jsp:include page="/WEB-INF/vista/menu.jsp"/>

        <div class="contenedor">
            <h1>Mis reservas</h1>

            <c:choose>
                <c:when test="${empty reservas}">
                    <p>Todavía no tenés reservas.</p>
                    <a class="boton" href="Reservar">Reservar una cancha</a>
                </c:when>
                <c:otherwise>
                    <table>
                        <tr>
                            <th>Fecha</th>
                            <th>Horario</th>
                            <th>Cancha</th>
                            <th>Deporte</th>
                            <th>Estado</th>
                            <th>Acción</th>
                        </tr>
                        <c:forEach var="r" items="${reservas}">
                            <tr>
                                <td><fmt:formatDate value="${r.fecha}" pattern="dd/MM/yyyy"/></td>
                                <td>
                                    <fmt:formatDate value="${r.horaInicio}" pattern="HH:mm"/> a
                                    <fmt:formatDate value="${r.horaFin}" pattern="HH:mm"/>
                                </td>
                                <td>${r.canchaIdcancha.nombre}</td>
                                <td>${r.canchaIdcancha.deporteIddeporte.nombre}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${cancelables.contains(r.idreserva)}">
                                            <span class="badge proxima">Próxima</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge finalizada">Finalizada</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${cancelables.contains(r.idreserva)}">
                                        <form action="CancelarReserva" method="post"
                                              onsubmit="return confirm('¿Cancelar esta reserva?');">
                                            <input type="hidden" name="id" value="${r.idreserva}">
                                            <input type="submit" class="peligro" value="Cancelar"
                                                   style="background:#c62828">
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
