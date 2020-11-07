package com.amongusdev.controller;

import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.Servicio;
import com.amongusdev.repositories.ServicioRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.amongusdev.utils.Defines.*;

@RestController
@RequestMapping("/servicio")
public class ServicioController {
    @Autowired
    ServicioRepository servicioRepository;

    @GetMapping
    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getServicio(@PathVariable int id) {
        Servicio servicio = servicioRepository.findOne(id);
        if (servicio != null) {
            return new ResponseEntity<>(servicio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponse(AREA_NOT_FOUND.getSecond(), AREA_NOT_FOUND.getFirst()), HttpStatus.OK);
        }
    }

    private boolean validarDatos(Servicio servicio){
        return servicio.getNombre() != null && servicio.getPrecio() != 0 && servicio.getAreaId() != null;
    }

    @PostMapping()
    @ApiOperation(value = "Crear un servicio", notes = "Se crea un servicio con su respectivo con los campos nombre, precio y area obligatorios")
    public GenericResponse createServicio(@Valid Servicio servicio){

        if (validarDatos(servicio)) {
            servicioRepository.save(servicio);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        } else {
            return new GenericResponse(FAILED.getSecond(), FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Eliminar un servicio", notes = "Se verifica si el servicio existe y si existe se elimina")
    public GenericResponse deleteServicio(@PathVariable int id){
        Servicio servicio = servicioRepository.findOne(id);
        if(servicio != null){
            servicioRepository.delete(id);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        } else {
            return new GenericResponse(FAILED.getSecond(), SERVICE_NOT_FOUND.getSecond(), SERVICE_NOT_FOUND.getFirst());
        }
    }
}
