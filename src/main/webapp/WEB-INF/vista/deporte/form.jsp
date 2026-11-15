<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Deporte</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <jsp:include page="/WEB-INF/vista/menu.jsp"/>

        <div class="contenedor">
            <h1>${empty deporte ? 'Nuevo deporte' : 'Editar deporte'}</h1>

            <form class="formulario" action="AgregarDeporte" method="post">
                <input type="hidden" name="id" value="${deporte.iddeporte}">

                <label>Nombre</label>
                <input type="text" name="nombre" value="${deporte.nombre}" maxlength="30">

                <input type="submit" value="Guardar">
            </form>

            <p><a href="ListarDeportes">← Volver a la lista</a></p>
        </div>
    </body>
</html>