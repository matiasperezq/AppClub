/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.club.sesiones;

import com.club.entidades.Cancha;
import com.club.entidades.Reserva;
import com.club.entidades.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 *
 * @author matia
 */
@Stateless
public class ReservaFacade extends AbstractFacade<Reserva> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservaFacade() {
        super(Reserva.class);
    }

    // Reservas de una cancha en una fecha
    public List<Reserva> findByCanchaYFecha(Cancha cancha, Date fecha) {
        return em.createQuery(
                "SELECT r FROM Reserva r "
                + "WHERE r.canchaIdcancha = :cancha AND r.fecha = :fecha "
                + "ORDER BY r.horaInicio", Reserva.class)
                .setParameter("cancha", cancha)
                .setParameter("fecha", fecha, TemporalType.DATE)
                .getResultList();
    }

    // Comprueba si ese turno ya esta reservado
    public boolean estaOcupada(Cancha cancha, Date fecha, Date horaInicio) {
        Long cantidad = em.createQuery(
                "SELECT COUNT(r) FROM Reserva r "
                + "WHERE r.canchaIdcancha = :cancha AND r.fecha = :fecha AND r.horaInicio = :hora",
                Long.class)
                .setParameter("cancha", cancha)
                .setParameter("fecha", fecha, TemporalType.DATE)
                .setParameter("hora", horaInicio, TemporalType.TIME)
                .getSingleResult();
        return cantidad > 0;
    }

    // Reservas de un usuario
    public List<Reserva> findByUsuario(Usuario usuario) {
        return em.createQuery(
                "SELECT r FROM Reserva r "
                + "JOIN FETCH r.canchaIdcancha c JOIN FETCH c.deporteIddeporte "
                + "WHERE r.usuarioIdusuario = :usuario "
                + "ORDER BY r.fecha DESC, r.horaInicio", Reserva.class)
                .setParameter("usuario", usuario)
                .getResultList();
    }

    // Todas las reservas, con usuario, cancha y deporte en la misma consulta
    public List<Reserva> findTodas() {
        return getEntityManager()
                .createQuery("SELECT r FROM Reserva r "
                        + "JOIN FETCH r.usuarioIdusuario "
                        + "JOIN FETCH r.canchaIdcancha c "
                        + "JOIN FETCH c.deporteIddeporte "
                        + "ORDER BY r.fecha DESC, r.horaInicio", Reserva.class)
                .getResultList();
    }
}
