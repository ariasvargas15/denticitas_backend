package com.amongusdev.controller;

import com.amongusdev.controller.requestdata.ServicioData;
import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.AreaEspecializacion;
import com.amongusdev.models.Servicio;
import com.amongusdev.repositories.AreaEspecializacionRepository;
import com.amongusdev.repositories.ServicioRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    AreaEspecializacionRepository areaEspecializacionRepository;

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

    private boolean validarDatosPost(Servicio servicio){
        return servicio.getNombre() != null && servicio.getPrecio() != 0 && servicio.getAreaId() != null;
    }

    @PostMapping()
    @ApiOperation(value = "Crear un servicio", notes = "Se crea un servicio especificando los respectivos campos del mismo")
    public GenericResponse createServicio(@Valid ServicioData servicioData){
        Servicio servicio = new Servicio();
        BeanUtils.copyProperties(servicioData, servicio);
        servicio.setAreaId(areaEspecializacionRepository.findOne(servicioData.getAreaId()));

        if (validarDatosPost(servicio)) {
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

    @PatchMapping("{id}")
    @ApiOperation(value = "Actualizar un servicio parcialmente", notes = "Actualiza algunos campos especificados de un servicio")
    public GenericResponse partialUpdateServicio(@PathVariable int id, @Valid ServicioData servicioData){
        Servicio servicio = servicioRepository.findOne(id);

        if(servicio == null){
            return new GenericResponse(FAILED.getSecond(), SERVICE_NOT_FOUND.getSecond(), SERVICE_NOT_FOUND.getFirst());
        } else{
            if(servicioData.getNombre() != null)
                servicio.setNombre(servicioData.getNombre());

            if(servicioData.getDescripcion() != null)
                servicio.setDescripcion(servicioData.getDescripcion());

            if(servicioData.getImagen() != null)
                servicio.setImagen(servicioData.getImagen());

            if(servicioData.getAreaId() != 0)
                servicio.setAreaId(areaEspecializacionRepository.findOne(servicioData.getAreaId()));

            if(servicioData.getPrecio() != 0)
                servicio.setPrecio(servicioData.getPrecio());

            servicioRepository.save(servicio);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        }
    }

    private boolean validarDatosPut(ServicioData servicio){
        return servicio.getNombre() != null && servicio.getDescripcion() != null && servicio.getImagen() != null && servicio.getAreaId() != 0 && servicio.getPrecio() != 0;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Actualizar un servicio", notes = "Actualiza todos los campos de un servicio")
    public GenericResponse updateServicio(@PathVariable int id, @Valid ServicioData servicioData){
        if(!validarDatosPut(servicioData)){
            return new GenericResponse(FAILED.getSecond(),FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
        }else{
            Servicio servicio = servicioRepository.findOne(id);
            if(servicio == null){
                return new GenericResponse(FAILED.getSecond(), SERVICE_NOT_FOUND.getSecond(), SERVICE_NOT_FOUND.getFirst());
            } else{
                BeanUtils.copyProperties(servicioData, servicio);
                AreaEspecializacion area = areaEspecializacionRepository.findOne(servicioData.getAreaId());
                if(area == null){
                    return new GenericResponse(FAILED.getSecond(), AREA_NOT_FOUND.getSecond(), AREA_NOT_FOUND.getFirst());
                }else{
                    servicio.setAreaId(area);
                    servicioRepository.save(servicio);
                    return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
                }
            }
        }
    }
}
