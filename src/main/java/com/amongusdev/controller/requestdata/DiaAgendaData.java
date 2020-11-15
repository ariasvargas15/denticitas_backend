package com.amongusdev.controller.requestdata;

import com.amongusdev.models.Turno;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DiaAgendaData implements Serializable{

    private Integer id;
    private Integer dia;
    private List<TurnoData> turnoList;

}
