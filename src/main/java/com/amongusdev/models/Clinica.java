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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author bsav157
 */
@Entity
@Table(name = "clinica")
@NamedQueries({
    @NamedQuery(name = "Clinica.findAll", query = "SELECT c FROM Clinica c"),
    @NamedQuery(name = "Clinica.findById", query = "SELECT c FROM Clinica c WHERE c.id = :id"),
    @NamedQuery(name = "Clinica.findByNombre", query = "SELECT c FROM Clinica c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Clinica.findByDescripcion", query = "SELECT c FROM Clinica c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Clinica.findByDireccion", query = "SELECT c FROM Clinica c WHERE c.direccion = :direccion")})
public class Clinica implements Serializable {

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
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clinicaId")
    private List<Administrador> administradorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clinicaId")
    private List<Cliente> clienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clinicaId")
    private List<Especialista> especialistaList;

    public Clinica() {
    }

    public Clinica(Integer id) {
        this.id = id;
    }

    public Clinica(Integer id, String nombre) {
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Administrador> getAdministradorList() {
        return administradorList;
    }

    public void setAdministradorList(List<Administrador> administradorList) {
        this.administradorList = administradorList;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public List<Especialista> getEspecialistaList() {
        return especialistaList;
    }

    public void setEspecialistaList(List<Especialista> especialistaList) {
        this.especialistaList = especialistaList;
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
        if (!(object instanceof Clinica)) {
            return false;
        }
        Clinica other = (Clinica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clinica{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", direccion='" + direccion + '\'' +
                ", administradorList=" + administradorList +
                ", clienteList=" + clienteList +
                ", especialistaList=" + especialistaList +
                '}';
    }
}
