package com.club.filtros;

import com.club.util.Acceso;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {
    "/ListarDeportes", "/AgregarDeporte", "/EditarDeporte", "/EliminarDeporte",
    "/ListarCanchas", "/AgregarCancha", "/EditarCancha", "/EliminarCancha",
    "/TodasLasReservas"})
public class FiltroAdmin implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (Acceso.esAdmin(request)) {
            chain.doFilter(req, resp);
        } else if (Acceso.usuario(request) == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
