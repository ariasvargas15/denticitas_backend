package com.amongusdev.controller;

import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.Especialista;
import com.amongusdev.repositories.EspecialistaRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.amongusdev.utils.Defines.*;

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
            return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), ESPECIALIST_NOT_FOUND.getSecond(), ESPECIALIST_NOT_FOUND.getFirst()), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{cedula}")
    @ApiOperation(value = "Eliminar especialista", notes = "Verifica si existe el especialista y si es dado el caso lo elimina.")
    public GenericResponse deleteEspecialista(@PathVariable String cedula){
        Especialista especialista = especialistaRepository.findOne(cedula);
        if(especialista != null){
            especialistaRepository.delete(cedula);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        } else{
            return new GenericResponse(FAILED.getSecond(), ESPECIALIST_NOT_FOUND.getSecond(), ESPECIALIST_NOT_FOUND.getFirst());
        }
    }
}
