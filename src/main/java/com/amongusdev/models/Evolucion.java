package com.amongusdev.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "evolucion")
public class Evolucion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "id_historia")
    private Integer idHistoria;
    @Column(name = "id_cita")
    private Integer idCita;
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @JoinColumn(name = "id_historia", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private HistoriaClinica historiaClinica;
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @JoinColumn(name = "id_cita", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cita cita;

}
