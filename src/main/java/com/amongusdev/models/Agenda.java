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
@Table(name = "agenda")
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "anio")
    private Integer anio;
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @OneToMany(mappedBy = "agendaId")
    private List<DiaAgenda> diaAgendaList;
    @JoinColumn(name = "especialista_cedula", referencedColumnName = "cedula")
    @ManyToOne
    private Especialista especialistaCedula;

    public Agenda(){

    }

    public Agenda(int mes, int anio, Especialista especialista){
        this.mes = mes;
        this.anio = anio;
        this.especialistaCedula = especialista;
    }
}
