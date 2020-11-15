package com.amongusdev.controller.requestdata;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CitaData implements Serializable {

    private Date createTime;
    private String clienteCedula;
    private int turnoId;
    private int servicioId;

}
