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
@Table(name = "turno")
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "hora_inicio")
    private String horaInicio;
    @Column(name = "duracion")
    private Integer duracion;
    @Column(name = "disponible")
    private boolean disponible;
    @JoinColumn(name = "dia_agenda_id", referencedColumnName = "id")
    @ManyToOne
    private DiaAgenda diaAgendaId;
    @Column(name = "tiempo_disponible")
    private Integer tiempoDisponible;
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "turnoId")
    private List<Cita> citaList;

    public Turno(){

    }

    public Turno(String horaInicio, int duracion, DiaAgenda diaAgenda){
        this.horaInicio = horaInicio;
        this.duracion = duracion;
        this.diaAgendaId = diaAgenda;
        this.disponible = false;
    }
}
