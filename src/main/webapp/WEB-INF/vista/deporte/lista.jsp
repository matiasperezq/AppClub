<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Deportes</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <jsp:include page="/WEB-INF/vista/menu.jsp"/>

        <div class="contenedor">
            <h1>Deportes</h1>
            <a class="boton" href="AgregarDeporte">+ Nuevo deporte</a>

            <table>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Acciones</th>
                </tr>
                <c:forEach var="d" items="${lista}">
                    <tr>
                        <td>${d.iddeporte}</td>
                        <td>${d.nombre}</td>
                        <td>
                            <a class="boton chico" href="EditarDeporte?id=${d.iddeporte}">Editar</a>
                            <a class="boton chico peligro" href="EliminarDeporte?id=${d.iddeporte}"
                               onclick="return confirm('¿Eliminar? Se borrarán también sus canchas y reservas.')">Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>