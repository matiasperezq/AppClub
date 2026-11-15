/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.club.servlets;

import com.club.entidades.Cancha;
import com.club.entidades.Reserva;
import com.club.sesiones.CanchaFacade;
import com.club.sesiones.DeporteFacade;
import com.club.sesiones.ReservaFacade;
import com.club.sesiones.UsuarioFacade;
import com.club.util.Acceso;
import com.club.util.Turnos;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author matia
 */
@WebServlet(name = "ReservaServlet", urlPatterns = {"/Reservar", "/MisReservas", "/CancelarReserva"})
public class ReservaServlet extends HttpServlet {

    @EJB
    private ReservaFacade reservaFacade;

    @EJB
    private CanchaFacade canchaFacade;

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private DeporteFacade deporteFacade;

    protected void reservar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String canchaStr = request.getParameter("cancha");
        String fechaStr = request.getParameter("fecha");
        String horaStr = request.getParameter("hora");

        // Deporte elegido: viene en el parametro, o se deduce de la cancha
        Integer deporteId = null;
        if (request.getParameter("deporte") != null && !request.getParameter("deporte").isEmpty()) {
            deporteId = Integer.parseInt(request.getParameter("deporte"));
        } else if (canchaStr != null && !canchaStr.isEmpty()) {
            deporteId = canchaFacade.find(Integer.parseInt(canchaStr)).getDeporteIddeporte().getIddeporte();
        }

        // Solo las canchas de ese deporte
        List<Cancha> canchas = new ArrayList<>();
        if (deporteId != null) {
            for (Cancha c : canchaFacade.findAll()) {
                if (c.getDeporteIddeporte().getIddeporte().equals(deporteId)) {
                    canchas.add(c);
                }
            }
        }

        request.setAttribute("deportes", deporteFacade.findAll());
        request.setAttribute("deporteSel", deporteId);
        request.setAttribute("canchas", canchas);

        request.setAttribute("hoy", LocalDate.now().toString());

        if (canchaStr != null && !canchaStr.isEmpty() && fechaStr != null && !fechaStr.isEmpty()) {
            Cancha cancha = canchaFacade.find(Integer.parseInt(canchaStr));
            LocalDate fecha = LocalDate.parse(fechaStr);
            java.sql.Date fechaSql = java.sql.Date.valueOf(fecha);

            // Apreto reservar
            if (horaStr != null) {
                LocalTime inicio = LocalTime.parse(horaStr);
                java.sql.Time horaSql = java.sql.Time.valueOf(inicio);

                if (LocalDateTime.of(fecha, inicio).isBefore(LocalDateTime.now())) {
                    request.setAttribute("error", "No se puede reservar un turno que ya pasó.");
                } else if (reservaFacade.estaOcupada(cancha, fechaSql, horaSql)) {
                    request.setAttribute("error", "Ese turno ya está reservado.");
                } else {
                    try {
                        Reserva r = new Reserva();
                        r.setFecha(fechaSql);
                        r.setHoraInicio(horaSql);
                        r.setHoraFin(java.sql.Time.valueOf(Turnos.fin(inicio)));
                        r.setCanchaIdcancha(cancha);
                        r.setUsuarioIdusuario(usuarioFacade.find(Acceso.usuario(request).getIdusuario()));
                        reservaFacade.create(r);
                        response.sendRedirect("Reservar?cancha=" + canchaStr + "&fecha=" + fechaStr + "&ok=1");
                        return;
                    } catch (Exception ex) {
                        request.setAttribute("error", "Ese turno ya está reservado.");
                    }
                }
            }

            // Turnos
            // Las reservas que ya existen ese dia en esa cancha
            Set<String> ocupados = new HashSet<>();
            SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
            for (Reserva r : reservaFacade.findByCanchaYFecha(cancha, fechaSql)) {
                ocupados.add(formato.format(r.getHoraInicio()));
            }

            // Los turnos del dia (menos los que ya pasaron)
            List<String[]> turnos = new ArrayList<>();
            LocalDateTime ahora = LocalDateTime.now();
            for (LocalTime inicio : Turnos.inicios()) {
                if (LocalDateTime.of(fecha, inicio).isBefore(ahora)) {
                    continue;
                }
                turnos.add(new String[]{inicio.toString(), Turnos.fin(inicio).toString()});
            }

            request.setAttribute("turnos", turnos);
            request.setAttribute("ocupados", ocupados);
        }

        request.getRequestDispatcher("/WEB-INF/vista/reserva/reservar.jsp").forward(request, response);
    }

    // Mis reservas
    protected void misReservas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idUsuario = Acceso.usuario(request).getIdusuario();
        List<Reserva> reservas = reservaFacade.findByUsuario(usuarioFacade.find(idUsuario));

        // ids de las reservas futuras (solo esas se pueden cancelar)
        Set<Integer> cancelables = new HashSet<>();
        for (Reserva r : reservas) {
            if (esFutura(r)) {
                cancelables.add(r.getIdreserva());
            }
        }

        request.setAttribute("reservas", reservas);
        request.setAttribute("cancelables", cancelables);
        request.getRequestDispatcher("/WEB-INF/vista/reserva/mis_reservas.jsp").forward(request, response);
    }

    // Cancelar: solo si la reserva es del socio logueado y todavia no empezo
    protected void cancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            Reserva r = reservaFacade.find(Integer.parseInt(idStr));
            Integer idUsuario = Acceso.usuario(request).getIdusuario();

            if (r != null
                    && r.getUsuarioIdusuario().getIdusuario().equals(idUsuario)
                    && esFutura(r)) {
                reservaFacade.remove(r);
            }
        }
        response.sendRedirect("MisReservas");
    }

    // true si la reserva todavia no empezo
    private boolean esFutura(Reserva r) {
        LocalDate fecha = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(r.getFecha()));
        LocalTime hora = LocalTime.parse(new SimpleDateFormat("HH:mm").format(r.getHoraInicio()));
        return LocalDateTime.of(fecha, hora).isAfter(LocalDateTime.now());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ruta = request.getServletPath();
        if (ruta.equals("/MisReservas")) {
            misReservas(request, response);
        } else if (ruta.equals("/CancelarReserva")) {
            response.sendRedirect("MisReservas");
        } else {
            reservar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ruta = request.getServletPath();
        if (ruta.equals("/CancelarReserva")) {
            cancelar(request, response);
        } else {
            reservar(request, response);
        }
    }
}
