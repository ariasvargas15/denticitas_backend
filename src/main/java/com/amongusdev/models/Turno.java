/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amongusdev.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author bsav157
 */
@Entity
@Table(name = "turno")
@NamedQueries({
    @NamedQuery(name = "Turno.findAll", query = "SELECT t FROM Turno t"),
    @NamedQuery(name = "Turno.findById", query = "SELECT t FROM Turno t WHERE t.id = :id"),
    @NamedQuery(name = "Turno.findByHoraInicio", query = "SELECT t FROM Turno t WHERE t.horaInicio = :horaInicio"),
    @NamedQuery(name = "Turno.findByDuracion", query = "SELECT t FROM Turno t WHERE t.duracion = :duracion")})
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Basic(optional = false)
    @Column(name = "duracion")
    private int duracion;
    @JoinColumn(name = "dia_agenda_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DiaAgenda diaAgendaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "turnoId")
    private List<Cita> citaList;

    public Turno() {
    }

    public Turno(Integer id) {
        this.id = id;
    }

    public Turno(Integer id, Date horaInicio, int duracion) {
        this.id = id;
        this.horaInicio = horaInicio;
        this.duracion = duracion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public DiaAgenda getDiaAgendaId() {
        return diaAgendaId;
    }

    public void setDiaAgendaId(DiaAgenda diaAgendaId) {
        this.diaAgendaId = diaAgendaId;
    }

    public List<Cita> getCitaList() {
        return citaList;
    }

    public void setCitaList(List<Cita> citaList) {
        this.citaList = citaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turno)) {
            return false;
        }
        Turno other = (Turno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", horaInicio=" + horaInicio +
                ", duracion=" + duracion +
                ", diaAgendaId=" + diaAgendaId +
                ", citaList=" + citaList +
                '}';
    }
}
