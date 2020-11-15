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
@Table(name = "area_especializacion")
public class AreaEspecializacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @JoinTable(name = "especialista_has_area_especializacion", joinColumns = {
            @JoinColumn(name = "area_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "especialista_cedula", referencedColumnName = "cedula")})
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @ManyToMany
    private List<Especialista> especialistaList;
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "areaId")
    private List<Servicio> servicioList;


}
