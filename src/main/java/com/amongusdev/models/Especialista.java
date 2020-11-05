/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amongusdev.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author bsav157
 */
@Data
@Entity
@Table(name = "especialista")
public class Especialista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cedula")
    private String cedula;
    @ManyToMany(mappedBy = "especialistaList")
    private List<AreaEspecializacion> areaEspecializacionList;
    @JoinColumn(name = "cedula", referencedColumnName = "cedula", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;
    @OneToMany(mappedBy = "especialistaCedula")
    private List<Agenda> agendaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "especialista")
    private HojaVida hojaVida;


}
