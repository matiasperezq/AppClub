<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Iniciar sesión</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <jsp:include page="/WEB-INF/vista/menu.jsp"/>

        <div class="contenedor">
            <h1>Iniciar sesión</h1>

            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>

            <form class="formulario" action="Login" method="post">
                <label>Correo</label>
                <input type="email" name="correo">

                <label>Contraseña</label>
                <input type="password" name="clave">

                <input type="submit" value="Entrar">
            </form>

            <p>¿No tenés cuenta? <a href="Registro">Registrate</a></p>
        </div>
    </body>
</html>