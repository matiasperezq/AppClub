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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "cancha", catalog = "bdclub", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cancha.findAll", query = "SELECT c FROM Cancha c"),
    @NamedQuery(name = "Cancha.findByIdcancha", query = "SELECT c FROM Cancha c WHERE c.idcancha = :idcancha"),
    @NamedQuery(name = "Cancha.findByNombre", query = "SELECT c FROM Cancha c WHERE c.nombre = :nombre")})
public class Cancha implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcancha", nullable = false)
    private Integer idcancha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;
    @JoinColumn(name = "deporte_iddeporte", referencedColumnName = "iddeporte", nullable = false)
    @ManyToOne(optional = false)
    private Deporte deporteIddeporte;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "canchaIdcancha")
    private Collection<Reserva> reservaCollection;

    public Cancha() {
    }

    public Cancha(Integer idcancha) {
        this.idcancha = idcancha;
    }

    public Cancha(Integer idcancha, String nombre) {
        this.idcancha = idcancha;
        this.nombre = nombre;
    }

    public Integer getIdcancha() {
        return idcancha;
    }

    public void setIdcancha(Integer idcancha) {
        this.idcancha = idcancha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Deporte getDeporteIddeporte() {
        return deporteIddeporte;
    }

    public void setDeporteIddeporte(Deporte deporteIddeporte) {
        this.deporteIddeporte = deporteIddeporte;
    }

    @XmlTransient
    public Collection<Reserva> getReservaCollection() {
        return reservaCollection;
    }

    public void setReservaCollection(Collection<Reserva> reservaCollection) {
        this.reservaCollection = reservaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcancha != null ? idcancha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cancha)) {
            return false;
        }
        Cancha other = (Cancha) object;
        if ((this.idcancha == null && other.idcancha != null) || (this.idcancha != null && !this.idcancha.equals(other.idcancha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.club.entidades.Cancha[ idcancha=" + idcancha + " ]";
    }
    
}
