package com.amongusdev.controller;

import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.Servicio;
import com.amongusdev.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.amongusdev.utils.Defines.AREA_NOT_FOUND;

@RestController
@RequestMapping("/servicio")
public class ServicioContoller {
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
}
