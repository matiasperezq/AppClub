<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Todas las reservas</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <jsp:include page="/WEB-INF/vista/menu.jsp"/>

        <div class="contenedor">
            <h1>Todas las reservas</h1>

            <c:choose>
                <c:when test="${empty reservas}">
                    <p>No hay reservas.</p>
                </c:when>
                <c:otherwise>
                    <table>
                        <tr>
                            <th>Fecha</th>
                            <th>Horario</th>
                            <th>Cancha</th>
                            <th>Deporte</th>
                            <th>Socio</th>
                            <th>Correo</th>
                            <th>Estado</th>
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
                                <td>${r.usuarioIdusuario.nombre}</td>
                                <td>${r.usuarioIdusuario.correo}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${proximas.contains(r.idreserva)}">
                                            <span class="badge proxima">Próxima</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge finalizada">Finalizada</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>