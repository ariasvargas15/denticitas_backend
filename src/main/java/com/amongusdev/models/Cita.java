/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amongusdev.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author bsav157
 */
@Entity
@Table(name = "cita")
@NamedQueries({
    @NamedQuery(name = "Cita.findAll", query = "SELECT c FROM Cita c"),
    @NamedQuery(name = "Cita.findById", query = "SELECT c FROM Cita c WHERE c.id = :id"),
    @NamedQuery(name = "Cita.findByCreateTime", query = "SELECT c FROM Cita c WHERE c.createTime = :createTime")})
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @JoinColumn(name = "cliente_cedula", referencedColumnName = "cedula")
    @ManyToOne(optional = false)
    private Cliente clienteCedula;
    @JoinColumn(name = "turno_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Turno turnoId;

    public Cita() {
    }

    public Cita(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Cliente getClienteCedula() {
        return clienteCedula;
    }

    public void setClienteCedula(Cliente clienteCedula) {
        this.clienteCedula = clienteCedula;
    }

    public Turno getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(Turno turnoId) {
        this.turnoId = turnoId;
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
        if (!(object instanceof Cita)) {
            return false;
        }
        Cita other = (Cita) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", clienteCedula=" + clienteCedula +
                ", turnoId=" + turnoId +
                '}';
    }
}
