/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amongusdev.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Column(name = "duracion")
    private Integer duracion;
    @JoinColumn(name = "dia_agenda_id", referencedColumnName = "id")
    @ManyToOne
    private DiaAgenda diaAgendaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "turnoId")
    private List<Cita> citaList;
}
