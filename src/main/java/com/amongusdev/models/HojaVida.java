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
@Table(name = "hoja_vida")
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
    @Column(name = "curriculum")
    private String curriculum;
    @Column(name = "experiencia")
    private Integer experiencia;
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @JoinColumn(name = "especialista_cedula", referencedColumnName = "cedula", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Especialista especialista;
}
