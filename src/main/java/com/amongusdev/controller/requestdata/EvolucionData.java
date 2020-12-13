package com.amongusdev.controller.requestdata;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class EvolucionData implements Serializable {

    private String fecha;
    private String descripcion;
    private String imagen;
    private Integer idHistoria;
    private Integer idCita;

}
