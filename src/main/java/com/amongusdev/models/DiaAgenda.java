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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author bsav157
 */
@Entity
@Table(name = "dia_agenda")
@NamedQueries({
    @NamedQuery(name = "DiaAgenda.findAll", query = "SELECT d FROM DiaAgenda d"),
    @NamedQuery(name = "DiaAgenda.findById", query = "SELECT d FROM DiaAgenda d WHERE d.id = :id"),
    @NamedQuery(name = "DiaAgenda.findByDia", query = "SELECT d FROM DiaAgenda d WHERE d.dia = :dia")})
public class DiaAgenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "dia")
    private int dia;
    @JoinColumn(name = "agenda_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Agenda agendaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "diaAgendaId")
    private List<Turno> turnoList;

    public DiaAgenda() {
    }

    public DiaAgenda(Integer id) {
        this.id = id;
    }

    public DiaAgenda(Integer id, int dia) {
        this.id = id;
        this.dia = dia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public Agenda getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Agenda agendaId) {
        this.agendaId = agendaId;
    }

    public List<Turno> getTurnoList() {
        return turnoList;
    }

    public void setTurnoList(List<Turno> turnoList) {
        this.turnoList = turnoList;
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
        if (!(object instanceof DiaAgenda)) {
            return false;
        }
        DiaAgenda other = (DiaAgenda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DiaAgenda{" +
                "id=" + id +
                ", dia=" + dia +
                ", agendaId=" + agendaId +
                ", turnoList=" + turnoList +
                '}';
    }
}
