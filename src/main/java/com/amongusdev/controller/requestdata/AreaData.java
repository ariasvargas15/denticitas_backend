package com.amongusdev.controller.requestdata;

import lombok.Data;

import java.io.Serializable;

@Data
public class AreaData implements Serializable {

    private String nombre;
    private String descripcion;

}
