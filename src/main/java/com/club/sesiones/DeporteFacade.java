/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.club.sesiones;

import com.club.entidades.Deporte;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author matia
 */
@Stateless
public class DeporteFacade extends AbstractFacade<Deporte> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DeporteFacade() {
        super(Deporte.class);
    }

    public List<Deporte> findAllOrdenado() {
        return em.createQuery("SELECT d FROM Deporte d ORDER BY d.nombre", Deporte.class).getResultList();
    }
}
