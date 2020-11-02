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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author bsav157
 */
@Entity
@Table(name = "area_especializacion")
@NamedQueries({
    @NamedQuery(name = "AreaEspecializacion.findAll", query = "SELECT a FROM AreaEspecializacion a"),
    @NamedQuery(name = "AreaEspecializacion.findById", query = "SELECT a FROM AreaEspecializacion a WHERE a.id = :id"),
    @NamedQuery(name = "AreaEspecializacion.findByNombre", query = "SELECT a FROM AreaEspecializacion a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AreaEspecializacion.findByDescripcion", query = "SELECT a FROM AreaEspecializacion a WHERE a.descripcion = :descripcion")})
public class AreaEspecializacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @JoinTable(name = "especialista_has_area_especializacion", joinColumns = {
        @JoinColumn(name = "area_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "especialista_cedula", referencedColumnName = "cedula")})
    @ManyToMany
    private List<Especialista> especialistaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "areaId")
    private List<Servicio> servicioList;

    public AreaEspecializacion() {
    }

    public AreaEspecializacion(Integer id) {
        this.id = id;
    }

    public AreaEspecializacion(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Especialista> getEspecialistaList() {
        return especialistaList;
    }

    public void setEspecialistaList(List<Especialista> especialistaList) {
        this.especialistaList = especialistaList;
    }

    public List<Servicio> getServicioList() {
        return servicioList;
    }

    public void setServicioList(List<Servicio> servicioList) {
        this.servicioList = servicioList;
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
        if (!(object instanceof AreaEspecializacion)) {
            return false;
        }
        AreaEspecializacion other = (AreaEspecializacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AreaEspecializacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", especialistaList=" + especialistaList +
                ", servicioList=" + servicioList +
                '}';
    }
}
