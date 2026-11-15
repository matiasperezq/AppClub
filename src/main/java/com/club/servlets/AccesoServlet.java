/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.club.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.club.entidades.Usuario;
import com.club.sesiones.UsuarioFacade;
import jakarta.ejb.EJB;
import com.club.util.Clave;

/**
 *
 * @author matia
 */
@WebServlet(name = "AccesoServlet", urlPatterns = {"/Registro", "/Login", "/Logout"})
public class AccesoServlet extends HttpServlet {

    @EJB
    private UsuarioFacade usuarioFacade;

    protected void procesar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String url = "/index.jsp";

        switch (request.getServletPath()) {

            case "/Registro":
                String nombre = request.getParameter("nombre");
                if (nombre == null) {
                    url = "/WEB-INF/vista/acceso/registro.jsp";
                } else {
                    String correo = request.getParameter("correo");
                    if (usuarioFacade.buscarPorCorreo(correo) != null) {
                        request.setAttribute("error", "Ese correo ya está registrado.");
                        url = "/WEB-INF/vista/acceso/registro.jsp";
                    } else {
                        Usuario u = new Usuario();
                        u.setNombre(nombre);
                        u.setCorreo(correo);
                        u.setClave(Clave.hash(request.getParameter("clave")));  // se guarda el hash
                        u.setRol("SOCIO");                                     // todos se registran como socio
                        usuarioFacade.create(u);
                        response.sendRedirect("Login");
                        return;
                    }
                }
                break;

            case "/Login":
                String correoLogin = request.getParameter("correo");
                if (correoLogin == null) {
                    url = "/WEB-INF/vista/acceso/login.jsp";
                } else {
                    Usuario u = usuarioFacade.buscarPorCorreo(correoLogin);
                    if (u != null && Clave.verificar(request.getParameter("clave"), u.getClave())) {
                        request.getSession().setAttribute("usuario", u);
                        response.sendRedirect("index.jsp");
                        return;
                    }
                    request.setAttribute("error", "Correo o contraseña incorrectos.");
                    url = "/WEB-INF/vista/acceso/login.jsp";
                }
                break;

            case "/Logout":
                request.getSession().invalidate();
                response.sendRedirect("Login");
                return;
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesar(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesar(request, response);
    }
}