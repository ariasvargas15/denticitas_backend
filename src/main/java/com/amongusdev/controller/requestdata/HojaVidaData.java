package com.amongusdev.controller.requestdata;

import lombok.Data;

import java.io.Serializable;

@Data
public class HojaVidaData implements Serializable {

    private String tituloPregrado;
    private String universidadPregrado;
    private String licencia;
    private String curriculum;
    private Integer experiencia;

}
