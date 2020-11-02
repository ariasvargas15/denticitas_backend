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
@Table(name = "agenda")
@NamedQueries({
    @NamedQuery(name = "Agenda.findAll", query = "SELECT a FROM Agenda a"),
    @NamedQuery(name = "Agenda.findById", query = "SELECT a FROM Agenda a WHERE a.id = :id"),
    @NamedQuery(name = "Agenda.findByMes", query = "SELECT a FROM Agenda a WHERE a.mes = :mes"),
    @NamedQuery(name = "Agenda.findByAnio", query = "SELECT a FROM Agenda a WHERE a.anio = :anio")})
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "mes")
    private int mes;
    @Basic(optional = false)
    @Column(name = "anio")
    private int anio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agendaId")
    private List<DiaAgenda> diaAgendaList;
    @JoinColumn(name = "especialista_cedula", referencedColumnName = "cedula")
    @ManyToOne(optional = false)
    private Especialista especialistaCedula;

    public Agenda() {
    }

    public Agenda(Integer id) {
        this.id = id;
    }

    public Agenda(Integer id, int mes, int anio) {
        this.id = id;
        this.mes = mes;
        this.anio = anio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public List<DiaAgenda> getDiaAgendaList() {
        return diaAgendaList;
    }

    public void setDiaAgendaList(List<DiaAgenda> diaAgendaList) {
        this.diaAgendaList = diaAgendaList;
    }

    public Especialista getEspecialistaCedula() {
        return especialistaCedula;
    }

    public void setEspecialistaCedula(Especialista especialistaCedula) {
        this.especialistaCedula = especialistaCedula;
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
        if (!(object instanceof Agenda)) {
            return false;
        }
        Agenda other = (Agenda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Agenda{" +
                "id=" + id +
                ", mes=" + mes +
                ", anio=" + anio +
                ", diaAgendaList=" + diaAgendaList +
                ", especialistaCedula=" + especialistaCedula +
                '}';
    }
}
