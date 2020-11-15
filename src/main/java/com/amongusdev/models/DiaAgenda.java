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
@Table(name = "dia_agenda")
public class DiaAgenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dia")
    private Integer dia;
    @JoinColumn(name = "agenda_id", referencedColumnName = "id")
    @ManyToOne
    private Agenda agendaId;
    @OneToMany(mappedBy = "diaAgendaId")
    private List<Turno> turnoList;

    public DiaAgenda(){

    }

    public DiaAgenda(int dia, Agenda agendaId){
        this.dia = dia;
        this.agendaId = agendaId;
    }

}
