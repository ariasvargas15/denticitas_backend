package com.amongusdev.controller;

import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.Cita;
import com.amongusdev.repositories.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.amongusdev.utils.Defines.CITA_NOT_FOUND;

@RestController
@RequestMapping("/cita")
public class CitaController {
    @Autowired
    CitaRepository citaRepository;

    @GetMapping
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCita(@PathVariable int id) {
        Cita cita = citaRepository.findOne(id);
        if (cita != null) {
            return new ResponseEntity<>(cita, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponse(CITA_NOT_FOUND.getSecond(), CITA_NOT_FOUND.getFirst()), HttpStatus.OK);
        }
    }
    //Cristian
    @GetMapping("/{cedula}")
    public ResponseEntity<Object> getCitaByCedula(@PathVariable String cedula){
        List<Cita> cita = citaRepository.findByCedula(cedula);
        if (cita != null) {
            return new ResponseEntity<>(cita, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponse(CITA_NOT_FOUND.getSecond(), CITA_NOT_FOUND.getFirst()), HttpStatus.OK);
        }
    }
}
