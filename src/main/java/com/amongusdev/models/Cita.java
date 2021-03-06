/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amongusdev.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author bsav157
 */
@Data
@Entity
@Table(name = "cita")
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "servicio_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Servicio servicioId;
    @OneToMany(mappedBy = "cita")
    private List<Evolucion> evolucionList;

    public Cita(){

    }

    public Cita(Cliente cliente, Turno turno, Date createTime, Servicio servicio){
        this.clienteCedula = cliente;
        this.turnoId = turno;
        this.createTime = createTime;
        this.servicioId = servicio;
    }
}
