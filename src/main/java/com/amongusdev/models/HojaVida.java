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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author bsav157
 */
@Entity
@Table(name = "hoja_vida")
@NamedQueries({
    @NamedQuery(name = "HojaVida.findAll", query = "SELECT h FROM HojaVida h"),
    @NamedQuery(name = "HojaVida.findByEspecialistaCedula", query = "SELECT h FROM HojaVida h WHERE h.especialistaCedula = :especialistaCedula"),
    @NamedQuery(name = "HojaVida.findByTituloPregrado", query = "SELECT h FROM HojaVida h WHERE h.tituloPregrado = :tituloPregrado"),
    @NamedQuery(name = "HojaVida.findByUniversidadPregrado", query = "SELECT h FROM HojaVida h WHERE h.universidadPregrado = :universidadPregrado"),
    @NamedQuery(name = "HojaVida.findByLicencia", query = "SELECT h FROM HojaVida h WHERE h.licencia = :licencia")})
public class HojaVida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "especialista_cedula")
    private String especialistaCedula;
    @Column(name = "titulo_pregrado")
    private String tituloPregrado;
    @Column(name = "universidad_pregrado")
    private String universidadPregrado;
    @Column(name = "licencia")
    private String licencia;
    @ManyToMany(mappedBy = "hojaVidaList")
    private List<Estudios> estudiosList;
    @JoinColumn(name = "especialista_cedula", referencedColumnName = "cedula", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Especialista especialista;

    public HojaVida() {
    }

    public HojaVida(String especialistaCedula) {
        this.especialistaCedula = especialistaCedula;
    }

    public String getEspecialistaCedula() {
        return especialistaCedula;
    }

    public void setEspecialistaCedula(String especialistaCedula) {
        this.especialistaCedula = especialistaCedula;
    }

    public String getTituloPregrado() {
        return tituloPregrado;
    }

    public void setTituloPregrado(String tituloPregrado) {
        this.tituloPregrado = tituloPregrado;
    }

    public String getUniversidadPregrado() {
        return universidadPregrado;
    }

    public void setUniversidadPregrado(String universidadPregrado) {
        this.universidadPregrado = universidadPregrado;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public List<Estudios> getEstudiosList() {
        return estudiosList;
    }

    public void setEstudiosList(List<Estudios> estudiosList) {
        this.estudiosList = estudiosList;
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (especialistaCedula != null ? especialistaCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HojaVida)) {
            return false;
        }
        HojaVida other = (HojaVida) object;
        if ((this.especialistaCedula == null && other.especialistaCedula != null) || (this.especialistaCedula != null && !this.especialistaCedula.equals(other.especialistaCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HojaVida{" +
                "especialistaCedula='" + especialistaCedula + '\'' +
                ", tituloPregrado='" + tituloPregrado + '\'' +
                ", universidadPregrado='" + universidadPregrado + '\'' +
                ", licencia='" + licencia + '\'' +
                ", estudiosList=" + estudiosList +
                ", especialista=" + especialista +
                '}';
    }
}
