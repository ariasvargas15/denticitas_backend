package com.amongusdev.controller;

import com.amongusdev.controller.requestdata.EspecialistaData;
import com.amongusdev.controller.requestdata.HojaVidaData;
import com.amongusdev.controller.requestdata.PersonaData;
import com.amongusdev.controller.user.UserService;
import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.AreaEspecializacion;
import com.amongusdev.models.Especialista;
import com.amongusdev.models.HojaVida;
import com.amongusdev.repositories.AreaEspecializacionRepository;
import com.amongusdev.repositories.EspecialistaRepository;
import com.amongusdev.repositories.HojaVidaRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.amongusdev.utils.Defines.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH,RequestMethod.PUT})
@RequestMapping("/especialista")
public class EspecialistaController {
    @Autowired
    EspecialistaRepository especialistaRepository;

    @Autowired
    UserService userService;

    @Autowired
    AreaEspecializacionRepository areaEspecializacionRepository;

    @Autowired
    HojaVidaRepository hojaVidaRepository;

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
    public GenericResponse deleteEspecialista(@PathVariable String cedula) {
        Especialista especialista = especialistaRepository.findOne(cedula);
        if (especialista != null) {
            especialistaRepository.delete(cedula);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        } else {
            return new GenericResponse(FAILED.getSecond(), ESPECIALIST_NOT_FOUND.getSecond(), ESPECIALIST_NOT_FOUND.getFirst());
        }
    }

    @PatchMapping("{cedula}")
    @ApiOperation(value = "Actualizar parcialmente un especialista", notes = "Actualiza algunos campos especificados de un especialista")
    public GenericResponse partialUpdateEspecialista(@PathVariable String cedula, @RequestBody EspecialistaData especialistaData){
        Especialista especialista = especialistaRepository.findOne(cedula);

        if(especialista == null){
            return new GenericResponse(FAILED.getSecond(), ESPECIALIST_NOT_FOUND.getSecond(), ESPECIALIST_NOT_FOUND.getFirst());
        }

        if(especialistaData.getHojaVidaData() != null){
            HojaVida hojaVida = new HojaVida();
            BeanUtils.copyProperties(especialistaData.getHojaVidaData(), hojaVida);
            hojaVidaRepository.save(hojaVida);
        }

        userService.partialUpdateUser(cedula, especialistaData.getPersona());

        if(especialistaData.getPersona().getCedula() != null){
            especialista.setCedula(especialistaData.getPersona().getCedula());
        }

        especialistaRepository.save(especialista);
        return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
    }

    @PostMapping("/{cedulaEspecialista}/area/{areaId}")
    @ApiOperation(value = "Agregar un especialista a un area", notes = "Agrega un especialista a un area")
    public GenericResponse createAreaEspecialista(@PathVariable String cedulaEspecialista, @PathVariable int areaId){
        areaEspecializacionRepository.insertarAreaEspecialista(areaId, cedulaEspecialista);
        return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
    }

    @DeleteMapping("/{cedulaEspecialista}/area/{areaId}")
    @ApiOperation(value = "Eliminar un especialista a un area", notes = "Elimina un especialista a un area")
    public GenericResponse deleteAreaEspecialista(@PathVariable String cedulaEspecialista, @PathVariable int areaId){
        areaEspecializacionRepository.deleteAreaEspecialista(areaId, cedulaEspecialista);
        return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
    }

    @PatchMapping("/{cedula}/hojavida")
    @ApiOperation(value = "Actualizar parcialmente la hoja de vida de un especialista", notes = "Actualiza algunos datos de la hoja de vida de un especialista")
    public GenericResponse partialUpdateHojaVida(@PathVariable String cedula, @RequestBody HojaVidaData hojaVidaData){
        HojaVida hojaVida = hojaVidaRepository.findOne(cedula);

        if(hojaVida == null) {
            return new GenericResponse(FAILED.getSecond(), HOJA_VIDA_NOT_FOUND.getSecond(), HOJA_VIDA_NOT_FOUND.getFirst());
        }

        if(hojaVidaData.getTituloPregrado() != null){
            hojaVida.setTituloPregrado(hojaVidaData.getTituloPregrado());
        }

        if(hojaVidaData.getUniversidadPregrado() != null){
            hojaVida.setUniversidadPregrado(hojaVidaData.getUniversidadPregrado());
        }

        if(hojaVidaData.getLicencia() != null){
            hojaVida.setLicencia(hojaVidaData.getLicencia());
        }

        if(hojaVidaData.getCurriculum() != null){
            hojaVida.setCurriculum(hojaVidaData.getCurriculum());
        }

        if(hojaVidaData.getExperiencia() != null){
            hojaVida.setExperiencia(hojaVidaData.getExperiencia());
        }

        hojaVidaRepository.save(hojaVida);
        return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
    }

    @PutMapping("/{cedula}")
    @ApiOperation(value = "Actualizar un especialista", notes = "Actualiza todos los campos de un especialista")
    public GenericResponse updateEspecialista(@PathVariable String cedula, @RequestBody EspecialistaData especialistaData){
        Especialista especialista = especialistaRepository.findOne(cedula);
        if(especialista == null){
            return new GenericResponse(FAILED.getSecond(), ESPECIALIST_NOT_FOUND.getSecond(), ESPECIALIST_NOT_FOUND.getFirst());
        } else{
            if(userService.updateUser(cedula, especialistaData.getPersona())){
                HojaVida hojaVida = new HojaVida();
                BeanUtils.copyProperties(especialistaData.getHojaVidaData(), hojaVida);
                hojaVidaRepository.save(hojaVida);
                return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
            } else{
                return new GenericResponse(FAILED.getSecond(), FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
            }
        }
    }
}
