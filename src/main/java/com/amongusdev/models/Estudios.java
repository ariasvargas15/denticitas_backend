/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amongusdev.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author bsav157
 */
@Entity
@Table(name = "estudios")
@NamedQueries({
    @NamedQuery(name = "Estudios.findAll", query = "SELECT e FROM Estudios e"),
    @NamedQuery(name = "Estudios.findById", query = "SELECT e FROM Estudios e WHERE e.id = :id"),
    @NamedQuery(name = "Estudios.findByTitulo", query = "SELECT e FROM Estudios e WHERE e.titulo = :titulo")})
public class Estudios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "titulo")
    private String titulo;
    @JoinTable(name = "hoja_vida_has_estudios", joinColumns = {
        @JoinColumn(name = "estudios_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "especialista_cedula", referencedColumnName = "especialista_cedula")})
    @ManyToMany
    private List<HojaVida> hojaVidaList;

    public Estudios() {
    }

    public Estudios(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<HojaVida> getHojaVidaList() {
        return hojaVidaList;
    }

    public void setHojaVidaList(List<HojaVida> hojaVidaList) {
        this.hojaVidaList = hojaVidaList;
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
        if (!(object instanceof Estudios)) {
            return false;
        }
        Estudios other = (Estudios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Estudios{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", hojaVidaList=" + hojaVidaList +
                '}';
    }
}
