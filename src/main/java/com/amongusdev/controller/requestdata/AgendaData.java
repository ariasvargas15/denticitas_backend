package com.amongusdev.controller.requestdata;

import com.amongusdev.models.Especialista;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class AgendaData implements Serializable {

    private Integer id;
    private Integer mes;
    private Integer anio;
    private List<DiaAgendaData> diaAgendaList;
    private Especialista especialistaCedula;

}
