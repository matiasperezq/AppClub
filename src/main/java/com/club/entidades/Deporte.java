/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.club.entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author matia
 */
@Entity
@Table(name = "deporte", catalog = "bdclub", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deporte.findAll", query = "SELECT d FROM Deporte d"),
    @NamedQuery(name = "Deporte.findByIddeporte", query = "SELECT d FROM Deporte d WHERE d.iddeporte = :iddeporte"),
    @NamedQuery(name = "Deporte.findByNombre", query = "SELECT d FROM Deporte d WHERE d.nombre = :nombre")})
public class Deporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddeporte", nullable = false)
    private Integer iddeporte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deporteIddeporte")
    private Collection<Cancha> canchaCollection;

    public Deporte() {
    }

    public Deporte(Integer iddeporte) {
        this.iddeporte = iddeporte;
    }

    public Deporte(Integer iddeporte, String nombre) {
        this.iddeporte = iddeporte;
        this.nombre = nombre;
    }

    public Integer getIddeporte() {
        return iddeporte;
    }

    public void setIddeporte(Integer iddeporte) {
        this.iddeporte = iddeporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Cancha> getCanchaCollection() {
        return canchaCollection;
    }

    public void setCanchaCollection(Collection<Cancha> canchaCollection) {
        this.canchaCollection = canchaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddeporte != null ? iddeporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deporte)) {
            return false;
        }
        Deporte other = (Deporte) object;
        if ((this.iddeporte == null && other.iddeporte != null) || (this.iddeporte != null && !this.iddeporte.equals(other.iddeporte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.club.entidades.Deporte[ iddeporte=" + iddeporte + " ]";
    }
    
}
