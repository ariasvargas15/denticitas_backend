package com.amongusdev.controller;

import com.amongusdev.controller.requestdata.AreaData;
import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.AreaEspecializacion;
import com.amongusdev.models.Especialista;
import com.amongusdev.repositories.AreaEspecializacionRepository;
import com.amongusdev.repositories.EspecialistaRepository;
import com.amongusdev.repositories.ServicioRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

import static com.amongusdev.utils.Defines.*;

@RestController
@RequestMapping("/area")
public class AreaController {
    @Autowired
    AreaEspecializacionRepository areaEspecializacionRepository;

    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    EspecialistaRepository especialistaRepository;

    @GetMapping
    public ResponseEntity<Object> listarAreas() {
        List<AreaEspecializacion> areas = areaEspecializacionRepository.findAll();

        if(areas.size() != 0)
            return new ResponseEntity<>(areas, HttpStatus.OK);

        return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), NO_AREAS.getSecond(), NO_AREAS.getFirst()), HttpStatus.OK);
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

    @GetMapping("/{id}/especialista")
    @ApiOperation(value = "Buscar un especialistas de un area", notes = "Busca los especialistas asociados a un area")
    public ResponseEntity<Object> getAreaEspecialista(@PathVariable int id){
        List<String> listaEspecialistas = areaEspecializacionRepository.findEspecialistasByArea(id);
        List<Especialista> especialistas = new ArrayList<>();

        for(int i = 0; i < listaEspecialistas.size(); i++){
            especialistas.add(especialistaRepository.findOne(listaEspecialistas.get(i)));
        }

        if(listaEspecialistas.size() != 0)
            return new ResponseEntity<>(especialistas, HttpStatus.OK);

        return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), NO_AREAS.getSecond(), NO_AREAS.getFirst()), HttpStatus.OK);
    }

    private boolean validarDatosPost(AreaEspecializacion area) {
        return area.getNombre() != null;
    }

    @PostMapping()
    @ApiOperation(value = "Agregar un area de especializacion", notes = "Se agrega un area de especializacion especificando los respectivo")
    public GenericResponse createArea(@RequestBody AreaData areaData) {
        AreaEspecializacion area = new AreaEspecializacion();
        BeanUtils.copyProperties(areaData, area);

        if (validarDatosPost(area)) {
            areaEspecializacionRepository.save(area);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        } else {
            return new GenericResponse(FAILED.getSecond(), FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Eliminar un area de especializacion", notes = "Se verifica si existe el area de especializacion y si existe se elimina")
    public GenericResponse deleteArea(@PathVariable int id) {
        AreaEspecializacion area = areaEspecializacionRepository.findOne(id);
        if (area != null) {
            List<Integer> listaServicios = servicioRepository.findByArea(area.getId());
            List<Integer> listaEspecialistas = especialistaRepository.encontrarEspecialistasPorArea(area.getId());
            if (listaServicios.size() == 0 && listaEspecialistas.size() == 0) {
                areaEspecializacionRepository.delete(id);
                return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
            } else {
                return new GenericResponse(FAILED.getSecond(), DATOS_ASOCIADOS_AREA.getSecond(), DATOS_ASOCIADOS_AREA.getFirst());
            }
        } else {
            return new GenericResponse(FAILED.getSecond(), AREA_NOT_FOUND.getSecond(), AREA_NOT_FOUND.getFirst());
        }
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Actualiza parcialmente un area de especializacion", notes = "Actualiza algunos campos especificados de un area")
    public GenericResponse partialUpdateArea(@PathVariable int id, @RequestBody AreaData areaData) {
        AreaEspecializacion area = areaEspecializacionRepository.findOne(id);

        if (area == null) {
            return new GenericResponse(FAILED.getSecond(), AREA_NOT_FOUND.getSecond(), AREA_NOT_FOUND.getFirst());
        } else {
            if (areaData.getNombre() != null)
                area.setNombre(areaData.getNombre());

            if (areaData.getDescripcion() != null)
                area.setDescripcion(areaData.getDescripcion());

            areaEspecializacionRepository.save(area);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        }
    }

    private boolean validarDatosPut(AreaData area) {
        return area.getNombre() != null && area.getDescripcion() != null;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Actualizar un area", notes = "Actualiza todos los campos de un area")
    public GenericResponse updateArea(@PathVariable int id, @RequestBody AreaData areaData) {
        if (validarDatosPut(areaData)) {
            AreaEspecializacion area = areaEspecializacionRepository.findOne(id);
            if (area == null) {
                return new GenericResponse(FAILED.getSecond(), AREA_NOT_FOUND.getSecond(), AREA_NOT_FOUND.getFirst());
            } else {
                BeanUtils.copyProperties(areaData, area);
                areaEspecializacionRepository.save(area);
                return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
            }
        } else {
            return new GenericResponse(FAILED.getSecond(), FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
        }
    }
}
