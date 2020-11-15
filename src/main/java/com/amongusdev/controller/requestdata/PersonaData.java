package com.amongusdev.controller.requestdata;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PersonaData implements Serializable {

    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String telefono;
    private String direccion;
    private String email;
    private String cedula;
    private String password;

}
