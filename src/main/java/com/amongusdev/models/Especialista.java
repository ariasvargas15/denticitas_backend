/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amongusdev.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author bsav157
 */
@Entity
@Table(name = "especialista")
@NamedQueries({
    @NamedQuery(name = "Especialista.findAll", query = "SELECT e FROM Especialista e"),
    @NamedQuery(name = "Especialista.findByCedula", query = "SELECT e FROM Especialista e WHERE e.cedula = :cedula")})
public class Especialista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cedula")
    private String cedula;
    @ManyToMany(mappedBy = "especialistaList")
    private List<AreaEspecializacion> areaEspecializacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "especialistaCedula")
    private List<Agenda> agendaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "especialista")
    private HojaVida hojaVida;
    @JoinColumn(name = "clinica_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Clinica clinicaId;
    @JoinColumn(name = "cedula", referencedColumnName = "cedula", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;

    public Especialista() {
    }

    public Especialista(String cedula) {
        this.cedula = cedula;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public List<AreaEspecializacion> getAreaEspecializacionList() {
        return areaEspecializacionList;
    }

    public void setAreaEspecializacionList(List<AreaEspecializacion> areaEspecializacionList) {
        this.areaEspecializacionList = areaEspecializacionList;
    }

    public List<Agenda> getAgendaList() {
        return agendaList;
    }

    public void setAgendaList(List<Agenda> agendaList) {
        this.agendaList = agendaList;
    }

    public HojaVida getHojaVida() {
        return hojaVida;
    }

    public void setHojaVida(HojaVida hojaVida) {
        this.hojaVida = hojaVida;
    }

    public Clinica getClinicaId() {
        return clinicaId;
    }

    public void setClinicaId(Clinica clinicaId) {
        this.clinicaId = clinicaId;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedula != null ? cedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Especialista)) {
            return false;
        }
        Especialista other = (Especialista) object;
        if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Especialista{" +
                "cedula='" + cedula + '\'' +
                ", areaEspecializacionList=" + areaEspecializacionList +
                ", agendaList=" + agendaList +
                ", hojaVida=" + hojaVida +
                ", clinicaId=" + clinicaId +
                ", persona=" + persona +
                '}';
    }
}
