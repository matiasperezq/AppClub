/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.club.servlets;

import com.club.entidades.Deporte;
import com.club.sesiones.DeporteFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author matia
 */
@WebServlet(name = "DeporteServlet",
            urlPatterns = {"/ListarDeportes", "/AgregarDeporte", "/EditarDeporte", "/EliminarDeporte"})
public class DeporteServlet extends HttpServlet {

    @EJB
    private DeporteFacade deporteFacade;

    protected void procesar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String url = "/index.jsp";

        switch (request.getServletPath()) {

            case "/ListarDeportes":
                // Trae todos los deportes y los manda a la vista
                request.setAttribute("lista", deporteFacade.findAll());
                url = "/WEB-INF/vista/deporte/lista.jsp";
                break;

            case "/AgregarDeporte":
                String nombre = request.getParameter("nombre");
                if (nombre == null) {
                    // Todavia no se envio el formulario: lo mostramos vacio
                    url = "/WEB-INF/vista/deporte/form.jsp";
                } else {
                    // Se envio el formulario: se guarda
                    String id = request.getParameter("id");
                    Deporte d;
                    if (id == null || id.isEmpty()) {
                        d = new Deporte();                                 // nuevo
                    } else {
                        d = deporteFacade.find(Integer.parseInt(id));      // editar
                    }
                    d.setNombre(nombre);
                    if (d.getIddeporte() == null) {
                        deporteFacade.create(d);
                    } else {
                        deporteFacade.edit(d);
                    }
                    response.sendRedirect("ListarDeportes");
                    return;
                }
                break;

            case "/EditarDeporte":
                request.setAttribute("deporte", deporteFacade.find(Integer.parseInt(request.getParameter("id"))));
                url = "/WEB-INF/vista/deporte/form.jsp";
                break;

            case "/EliminarDeporte":
                Deporte borrar = deporteFacade.find(Integer.parseInt(request.getParameter("id")));
                deporteFacade.remove(borrar);
                response.sendRedirect("ListarDeportes");
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