<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Registro</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <jsp:include page="/WEB-INF/vista/menu.jsp"/>

        <div class="contenedor auth">
            <h1>Crear cuenta</h1>
            <p class="subtitulo">Registrate como socio del club</p>

            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>

            <form class="formulario" action="Registro" method="post">
                <label>Nombre</label>
                <input type="text" name="nombre" maxlength="45">

                <label>Correo</label>
                <input type="email" name="correo" maxlength="60">

                <label>Contraseña</label>
                <input type="password" name="clave">

                <input type="submit" value="Registrarme">
            </form>

            <p class="pie-form">¿Ya tenés cuenta? <a href="Login">Iniciá sesión</a></p>
        </div>
    </body>
</html>