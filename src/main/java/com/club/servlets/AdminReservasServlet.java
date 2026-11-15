package com.club.servlets;

import com.club.entidades.Reserva;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Reserva> reservas = reservaFacade.findTodas();

        // ids de las reservas que todavia no empezaron
        Set<Integer> proximas = new HashSet<>();
        for (Reserva r : reservas) {
            if (esFutura(r)) {
                proximas.add(r.getIdreserva());
            }
        }

        request.setAttribute("reservas", reservas);
        request.setAttribute("proximas", proximas);
        request.getRequestDispatcher("/WEB-INF/vista/reserva/todas.jsp").forward(request, response);
    }

    // true si la reserva todavia no empezo
    private boolean esFutura(Reserva r) {
        LocalDate fecha = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(r.getFecha()));
        LocalTime hora = LocalTime.parse(new SimpleDateFormat("HH:mm").format(r.getHoraInicio()));
        return LocalDateTime.of(fecha, hora).isAfter(LocalDateTime.now());
    }
}
