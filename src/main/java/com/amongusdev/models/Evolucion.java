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
    private Date fecha;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "imagen")
    private String imagen;
    @JoinColumn(name = "id_historia", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private HistoriaClinica historiaClinica;
    @JoinColumn(name = "id_cita", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cita cita;

}
