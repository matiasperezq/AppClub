<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Canchas</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <jsp:include page="/WEB-INF/vista/menu.jsp"/>

        <div class="contenedor">
            <h1>Canchas</h1>
            <a class="boton" href="AgregarCancha">+ Nueva cancha</a>

            <table>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Deporte</th>
                    <th>Acciones</th>
                </tr>
                <c:forEach var="c" items="${lista}">
                    <tr>
                        <td>${c.idcancha}</td>
                        <td>${c.nombre}</td>
                        <td>${c.deporteIddeporte.nombre}</td>
                        <td>
                            <a class="boton chico" href="EditarCancha?id=${c.idcancha}">Editar</a>
                            <a class="boton chico peligro" href="EliminarCancha?id=${c.idcancha}"
                               onclick="return confirm('¿Eliminar? Se borrarán también sus reservas.')">Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>