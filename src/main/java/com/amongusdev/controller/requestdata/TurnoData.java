package com.amongusdev.controller.requestdata;

import lombok.Data;

import java.io.Serializable;

@Data
public class TurnoData implements Serializable {

    private int id;
    private String horaInicio;
    private Integer duracion;
    private boolean estado;

}
