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
import java.util.List;

/**
 * @author bsav157
 */
@Data
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "ocupacion")
    private String ocupacion;
    @Id
    @Basic(optional = false)
    @Column(name = "cedula")
    private String cedula;
    @JoinColumn(name = "cedula", referencedColumnName = "cedula", insertable = false, updatable = false)
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Persona persona;
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteCedula")
    private List<Cita> citaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cliente")
    private HistoriaClinica historiaClinica;

}
