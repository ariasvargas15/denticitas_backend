package com.amongusdev.controller.requestdata;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServicioData implements Serializable {

    private String nombre;
    private String descripcion;
    private String imagen;
    private int areaId;
    private double precio;

}
