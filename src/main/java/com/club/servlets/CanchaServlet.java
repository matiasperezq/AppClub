/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.club.servlets;

import com.club.entidades.Cancha;
import com.club.sesiones.CanchaFacade;
import com.club.sesiones.DeporteFacade;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author matia
 */
@WebServlet(name = "CanchaServlet",
            urlPatterns = {"/ListarCanchas", "/AgregarCancha", "/EditarCancha", "/EliminarCancha"})
public class CanchaServlet extends HttpServlet {

    @EJB
    private CanchaFacade canchaFacade;

    @EJB
    private DeporteFacade deporteFacade;

    protected void procesar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String url = "/index.jsp";

        switch (request.getServletPath()) {

            case "/ListarCanchas":
                // Trae todas las canchas y las manda a la vista
                request.setAttribute("lista", canchaFacade.findAll());
                url = "/WEB-INF/vista/cancha/lista.jsp";
                break;

            case "/AgregarCancha":
                String nombre = request.getParameter("nombre");
                if (nombre == null) {
                    // Todavia no se envio el formulario: lo mostramos con la lista de deportes
                    request.setAttribute("deportes", deporteFacade.findAll());
                    url = "/WEB-INF/vista/cancha/form.jsp";
                } else {
                    // Se envio el formulario: guardamos
                    String id = request.getParameter("id");
                    Cancha c;
                    if (id == null || id.isEmpty()) {
                        c = new Cancha();                                   // nueva
                    } else {
                        c = canchaFacade.find(Integer.parseInt(id));        // editar
                    }
                    c.setNombre(nombre);
                    c.setDeporteIddeporte(deporteFacade.find(Integer.parseInt(request.getParameter("deporte"))));
                    if (c.getIdcancha() == null) {
                        canchaFacade.create(c);
                    } else {
                        canchaFacade.edit(c);
                    }
                    response.sendRedirect("ListarCanchas");
                    return;
                }
                break;

            case "/EditarCancha":
                // Busca la cancha y muestra el mismo formulario con sus datos
                request.setAttribute("cancha", canchaFacade.find(Integer.parseInt(request.getParameter("id"))));
                request.setAttribute("deportes", deporteFacade.findAll());
                url = "/WEB-INF/vista/cancha/form.jsp";
                break;

            case "/EliminarCancha":
                Cancha borrar = canchaFacade.find(Integer.parseInt(request.getParameter("id")));
                canchaFacade.remove(borrar);
                response.sendRedirect("ListarCanchas");
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