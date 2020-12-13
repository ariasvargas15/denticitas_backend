package com.amongusdev.controller.requestdata;

import com.amongusdev.models.AreaEspecializacion;
import com.amongusdev.models.HojaVida;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EspecialistaData implements Serializable {

    private List<AreaData> areaEspecializacionList;
    private PersonaData persona;
    private HojaVidaData hojaVidaData;

}
