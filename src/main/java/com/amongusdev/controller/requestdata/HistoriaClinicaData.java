package com.amongusdev.controller.requestdata;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class HistoriaClinicaData implements Serializable {

    private String clienteCedula;
    private Date createTime;
    private String estadoSalud;
    private Boolean toleranciaAnestesicos;
    private String alergiaMedicamentos;
    private String anomaliasDentales;
    private String higieneOral;

}
