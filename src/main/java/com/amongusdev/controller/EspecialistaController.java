package com.amongusdev.controller;

import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.Especialista;
import com.amongusdev.repositories.EspecialistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.amongusdev.utils.Defines.ESPECIALIST_NOT_FOUND;

@RestController
@RequestMapping("/especialista")
public class EspecialistaController {
    @Autowired
    EspecialistaRepository especialistaRepository;

    @GetMapping
    public List<Especialista> listEspecialistas() {
        return especialistaRepository.findAll();
    }

    @GetMapping("/{cedula}")
    public ResponseEntity<Object> getEspecialista(@PathVariable String cedula) {
        Especialista especialista = especialistaRepository.findOne(cedula);
        if (especialista != null) {
            return new ResponseEntity<>(especialista, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponse(ESPECIALIST_NOT_FOUND.getSecond(), ESPECIALIST_NOT_FOUND.getFirst()), HttpStatus.OK);
        }
    }

}
