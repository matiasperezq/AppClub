<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cancha</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <jsp:include page="/WEB-INF/vista/menu.jsp"/>

        <div class="contenedor">
            <h1>${empty cancha ? 'Nueva cancha' : 'Editar cancha'}</h1>

            <form class="formulario" action="AgregarCancha" method="post">
                <input type="hidden" name="id" value="${cancha.idcancha}">

                <label>Nombre</label>
                <input type="text" name="nombre" value="${cancha.nombre}" maxlength="45">

                <label>Deporte</label>
                <select name="deporte">
                    <c:forEach var="d" items="${deportes}">
                        <option value="${d.iddeporte}"
                                ${cancha.deporteIddeporte.iddeporte == d.iddeporte ? "selected" : ""}>
                            ${d.nombre}
                        </option>
                    </c:forEach>
                </select>

                <input type="submit" value="Guardar">
            </form>

            <p><a href="ListarCanchas">← Volver a la lista</a></p>
        </div>
    </body>
</html>