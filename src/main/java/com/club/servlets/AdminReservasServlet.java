package com.club.servlets;

import com.club.entidades.Reserva;
import com.club.sesiones.CanchaFacade;
import com.club.sesiones.DeporteFacade;
import com.club.sesiones.ReservaFacade;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "AdminReservasServlet", urlPatterns = {"/TodasLasReservas"})
public class AdminReservasServlet extends HttpServlet {

    @EJB
    private ReservaFacade reservaFacade;

    @EJB
    private DeporteFacade deporteFacade;

    @EJB
    private CanchaFacade canchaFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Filtros opcionales: si no llegan (o vienen vacios), quedan en null
        String fechaStr = request.getParameter("fecha");
        String deporteStr = request.getParameter("deporte");
        String canchaStr = request.getParameter("cancha");

        java.sql.Date fecha = null;
        if (fechaStr != null && !fechaStr.isEmpty()) {
            fecha = java.sql.Date.valueOf(LocalDate.parse(fechaStr));
        }
        Integer deporteId = null;
        if (deporteStr != null && !deporteStr.isEmpty()) {
            deporteId = Integer.valueOf(deporteStr);
        }
        Integer canchaId = null;
        if (canchaStr != null && !canchaStr.isEmpty()) {
            canchaId = Integer.valueOf(canchaStr);
        }

        List<Reserva> reservas = reservaFacade.findFiltradas(fecha, deporteId, canchaId);

        // ids de las reservas que todavia no empezaron (las "proximas")
        Set<Integer> proximas = new HashSet<>();
        for (Reserva r : reservas) {
            if (esFutura(r)) {
                proximas.add(r.getIdreserva());
            }
        }

        request.setAttribute("reservas", reservas);
        request.setAttribute("proximas", proximas);
        request.setAttribute("deportes", deporteFacade.findAll());   // para el filtro de deporte
        request.setAttribute("canchas", canchaFacade.findAll());     // para el filtro de cancha
        request.getRequestDispatcher("/WEB-INF/vista/reserva/todas.jsp").forward(request, response);
    }

    // true si la reserva todavia no empezo
    private boolean esFutura(Reserva r) {
        LocalDate fecha = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(r.getFecha()));
        LocalTime hora = LocalTime.parse(new SimpleDateFormat("HH:mm").format(r.getHoraInicio()));
        return LocalDateTime.of(fecha, hora).isAfter(LocalDateTime.now());
    }
}
