package com.amongusdev.controller;

import com.amongusdev.controller.requestdata.EspecialistaData;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.amongusdev.utils.Defines.*;

@RestController
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
        } else{

            if(especialistaData.getAreaEspecializacionList() != null){
                AreaEspecializacion area;
                for(int i = 0; i < especialistaData.getAreaEspecializacionList().size(); i++){
                    area = especialistaData.getAreaEspecializacionList().get(i);
                    if(area == null){
                        return new GenericResponse(FAILED.getSecond(), AREA_NOT_FOUND.getSecond(), AREA_NOT_FOUND.getFirst());
                    }

                    //if(areaEspecializacionRepository.encontrarAreaConEspecialista(area.getId(), cedula) != null)
                      //  especialistaRepository.deleteAreasEspecialista(area.getId(), cedula);

                    especialistaRepository.createAreasEspecialista(area.getId(), cedula);
                }
            }

            if(especialistaData.getHojaVida() != null){
                hojaVidaRepository.save(especialistaData.getHojaVida());
            }

            userService.partialUpdateUser(cedula, especialistaData.getPersona());

            if(especialistaData.getPersona().getCedula() != null){
                especialista.setCedula(especialistaData.getPersona().getCedula());
            }

            especialistaRepository.save(especialista);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        }
    }

    @PutMapping("/{cedula}")
    @ApiOperation(value = "Actualizar un especialista", notes = "Actualiza todos los campos de un especialista")
    public GenericResponse updateEspecialista(@PathVariable String cedula, @RequestBody EspecialistaData especialistaData){
        Especialista especialista = especialistaRepository.findOne(cedula);
        if(especialista == null){
            return new GenericResponse(FAILED.getSecond(), ESPECIALIST_NOT_FOUND.getSecond(), ESPECIALIST_NOT_FOUND.getFirst());
        } else{
            if(userService.updateUser(cedula, especialistaData.getPersona())){
                hojaVidaRepository.save(especialistaData.getHojaVida());
                return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
            } else{
                return new GenericResponse(FAILED.getSecond(), FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
            }
        }
    }
}
