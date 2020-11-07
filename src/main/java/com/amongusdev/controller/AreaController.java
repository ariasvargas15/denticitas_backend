package com.amongusdev.controller;

import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.AreaEspecializacion;
import com.amongusdev.repositories.AreaEspecializacionRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.amongusdev.utils.Defines.*;

@RestController
@RequestMapping("/area")
public class AreaController {
    @Autowired
    AreaEspecializacionRepository areaEspecializacionRepository;

    @GetMapping
    public List<AreaEspecializacion> listarAreas() {
        return areaEspecializacionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getArea(@PathVariable int id) {
        AreaEspecializacion area = areaEspecializacionRepository.findOne(id);
        if (area != null) {
            return new ResponseEntity<>(area, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponse(AREA_NOT_FOUND.getSecond(), AREA_NOT_FOUND.getFirst()), HttpStatus.OK);
        }
    }

    private boolean validarDatos(AreaEspecializacion area){
        return area.getNombre() != null;
    }

    @PostMapping()
    @ApiOperation(value = "Agregar un area de especializacion", notes = "Se agrega un area de especializacion especificando con el campo nombre obligatorio")
    public GenericResponse createArea(@Valid AreaEspecializacion area){
        if(validarDatos(area)) {
            areaEspecializacionRepository.save(area);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        } else{
            return new GenericResponse(FAILED.getSecond(), FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Eliminar un area de especializacion", notes = "Se verifica si existe el area de especializacion y si existe se elimina")
    public GenericResponse deleteArea(@PathVariable int id){
        AreaEspecializacion area = areaEspecializacionRepository.findOne(id);
        if(area != null) {
            areaEspecializacionRepository.delete(id);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        } else{
            return new GenericResponse(FAILED.getSecond(), AREA_NOT_FOUND.getSecond(), AREA_NOT_FOUND.getFirst());
        }
    }
}
