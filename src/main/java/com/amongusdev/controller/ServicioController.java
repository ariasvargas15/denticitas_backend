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

import java.util.List;

import static com.amongusdev.utils.Defines.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH,RequestMethod.PUT})
@RequestMapping("/servicio")
public class ServicioController {
    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    AreaEspecializacionRepository areaEspecializacionRepository;

    @GetMapping
    public ResponseEntity<Object> listarServicios() {
        List<Servicio> servicios = servicioRepository.findAll();

        if(servicios.size() != 0)
            return new ResponseEntity<>(servicios, HttpStatus.OK);

        return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), NO_SERVICES.getSecond(), NO_SERVICES.getFirst()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getServicio(@PathVariable int id) {
        Servicio servicio = servicioRepository.findOne(id);
        if (servicio != null) {
            return new ResponseEntity<>(servicio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), SERVICE_NOT_FOUND.getSecond(), SERVICE_NOT_FOUND.getFirst()), HttpStatus.OK);
        }
    }

    private boolean validarDatosPost(ServicioData servicioData) {
        return servicioData.getNombre() != null && servicioData.getPrecio() != 0 && servicioData.getAreaId() != 0;
    }

    @PostMapping()
    @ApiOperation(value = "Crear un servicio", notes = "Se crea un servicio especificando los respectivos campos del mismo")
    public GenericResponse createServicio(@RequestBody ServicioData servicioData) {
        Servicio servicio = new Servicio();

        if (validarDatosPost(servicioData)) {
            BeanUtils.copyProperties(servicioData, servicio);
            servicio.setEstado(true);
            servicio.setAreaId(areaEspecializacionRepository.findOne(servicioData.getAreaId()));
            if(servicio.getAreaId() == null) {
                return new GenericResponse(FAILED.getSecond(), AREA_NOT_FOUND.getSecond(), AREA_NOT_FOUND.getFirst());
            }

            servicioRepository.save(servicio);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        }

        return new GenericResponse(FAILED.getSecond(), FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Eliminar un servicio", notes = "Se verifica si el servicio existe y si existe se elimina")
    public GenericResponse deleteServicio(@PathVariable int id) {
        Servicio servicio = servicioRepository.findOne(id);
        if (servicio != null) {
            servicioRepository.delete(id);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        } else {
            return new GenericResponse(FAILED.getSecond(), SERVICE_NOT_FOUND.getSecond(), SERVICE_NOT_FOUND.getFirst());
        }
    }

    @PatchMapping("{id}")
    @ApiOperation(value = "Actualizar un servicio parcialmente", notes = "Actualiza algunos campos especificados de un servicio")
    public GenericResponse partialUpdateServicio(@PathVariable int id, @RequestBody ServicioData servicioData) {
        Servicio servicio = servicioRepository.findOne(id);

        if (servicio == null)
            return new GenericResponse(FAILED.getSecond(), SERVICE_NOT_FOUND.getSecond(), SERVICE_NOT_FOUND.getFirst());

        if (servicioData.getNombre() != null)
            servicio.setNombre(servicioData.getNombre());

        if (servicioData.getDescripcion() != null)
            servicio.setDescripcion(servicioData.getDescripcion());

        if (servicioData.getImagen() != null)
            servicio.setImagen(servicioData.getImagen());

        if (servicioData.getAreaId() != 0){
            servicio.setAreaId(areaEspecializacionRepository.findOne(servicioData.getAreaId()));
            if(servicio.getAreaId() == null)
                return new GenericResponse(FAILED.getSecond(), AREA_NOT_FOUND.getSecond(), AREA_NOT_FOUND.getFirst());
        }

        if (servicioData.getPrecio() != 0)
            servicio.setPrecio(servicioData.getPrecio());

        if(servicioData.getDuracion() != 0)
            servicio.setDuracion(servicioData.getDuracion());

        if(servicioData.getEstado() != null)
            servicio.setEstado(servicioData.getEstado());

        servicioRepository.save(servicio);
        return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
    }

    private boolean validarDatosPut(ServicioData servicio) {
        return servicio.getNombre() != null && servicio.getDescripcion() != null && servicio.getImagen() != null &&
                servicio.getAreaId() != 0 && servicio.getPrecio() != 0 && servicio.getDuracion() != 0 && servicio.getEstado() != null;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Actualizar un servicio", notes = "Actualiza todos los campos de un servicio")
    public GenericResponse updateServicio(@PathVariable int id, @RequestBody ServicioData servicioData) {
        if (!validarDatosPut(servicioData)){
            return new GenericResponse(FAILED.getSecond(), FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
        }

        Servicio servicio = servicioRepository.findOne(id);

        if (servicio == null) {
            return new GenericResponse(FAILED.getSecond(), SERVICE_NOT_FOUND.getSecond(), SERVICE_NOT_FOUND.getFirst());
        }

        BeanUtils.copyProperties(servicioData, servicio);
        AreaEspecializacion area = areaEspecializacionRepository.findOne(servicioData.getAreaId());
        if (area == null) {
            return new GenericResponse(FAILED.getSecond(), AREA_NOT_FOUND.getSecond(), AREA_NOT_FOUND.getFirst());
        }

        servicio.setAreaId(area);
        servicioRepository.save(servicio);
        return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
    }
}
