package com.amongusdev.controller.requestdata;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClienteData implements Serializable {

    private String ocupacion;
    private PersonaData persona;
}
