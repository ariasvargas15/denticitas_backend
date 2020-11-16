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

/**
 * @author bsav157
 */
@Data
@Entity
@Table(name = "estudios")
public class Estudios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "titulo")
    private String titulo;
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @JoinColumn(name = "especialista_cedula", referencedColumnName = "especialista_cedula")
    @ManyToOne
    private HojaVida especialistaCedula;


}
