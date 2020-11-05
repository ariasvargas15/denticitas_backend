package com.amongusdev.controller;

import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.AreaEspecializacion;
import com.amongusdev.repositories.AreaEspecializacionRepository;
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
}
