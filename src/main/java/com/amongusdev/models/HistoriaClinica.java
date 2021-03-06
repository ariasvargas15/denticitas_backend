package com.amongusdev.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cliente_cedula")
    private String clienteCedula;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "estado_salud")
    private String estadoSalud;
    @Column(name = "tolerancia_anestesicos")
    private Boolean toleranciaAnestesicos;
    @Column(name = "alergia_medicamentos")
    private String alergiaMedicamentos;
    @Column(name = "anomalias_dentales")
    private String anomaliasDentales;
    @Column(name = "higiene_oral")
    private String higieneOral;
    @OneToMany(mappedBy = "historiaClinica")
    private List<Evolucion> evolucionList;
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    @JoinColumn(name = "cliente_cedula", referencedColumnName = "cedula", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Cliente cliente;

    public HistoriaClinica(){

    }

    public HistoriaClinica(String clienteCedula){
        this.clienteCedula = clienteCedula;
    }

}
