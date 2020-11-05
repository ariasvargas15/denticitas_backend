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

/**
 * @author bsav157
 */
@Data
@Entity
@Table(name = "persona")
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "email")
    private String email;
    @Id
    @Basic(optional = false)
    @Column(name = "cedula")
    private String cedula;
    @Column(name = "password")
    private String password;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Cliente cliente;
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Administrador administrador;
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Especialista especialista;


}
