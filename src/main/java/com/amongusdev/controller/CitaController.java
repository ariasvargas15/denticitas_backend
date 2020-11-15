package com.amongusdev.controller;

import com.amongusdev.controller.requestdata.CitaData;
import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.Cita;
import com.amongusdev.repositories.CitaRepository;
import com.amongusdev.repositories.ClienteRepository;
import com.amongusdev.repositories.ServicioRepository;
import com.amongusdev.repositories.TurnoRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

import static com.amongusdev.utils.Defines.*;

@RestController
@RequestMapping("/cita")
public class CitaController {
    @Autowired
    CitaRepository citaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    TurnoRepository turnoRepository;

    @Autowired
    ServicioRepository servicioRepository;

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

    @GetMapping("/cliente/{cedula}")
    @ApiOperation(value = "Consultar citas de un cliente", notes = "Consulta todas las citas asociadas a un cliente")
    public List<Cita> getCitaCliente(@PathVariable String cedula){
        return citaRepository.buscarCitaCliente(cedula);
    }

    @PostMapping()
    @ApiOperation(value = "Crear una cita", notes = "Se crea una cita con el respectivo ciente y turno")
    public GenericResponse createCita(@RequestBody CitaData citaData){
        Cita cita = new Cita(clienteRepository.findOne(citaData.getClienteCedula()), turnoRepository.findOne(citaData.getTurnoId()), Calendar.getInstance().getTime(), servicioRepository.findOne(citaData.getServicioId()));
        GenericResponse respuesta;

        if(cita.getClienteCedula() == null){
            respuesta = new GenericResponse(FAILED.getSecond(), CUSTOMER_NOT_FOUND.getSecond(), CUSTOMER_NOT_FOUND.getFirst());
        } else if(cita.getTurnoId() == null){
            respuesta =  new GenericResponse(FAILED.getSecond(), TURNO_NOT_FOUND.getSecond(), TURNO_NOT_FOUND.getFirst());
        } else if(cita.getServicioId() == null){
            respuesta =  new GenericResponse(FAILED.getSecond(), SERVICE_NOT_FOUND.getSecond(), SERVICE_NOT_FOUND.getFirst());
        } else{
            if(citaRepository.verificarExistenciaCita(citaData.getClienteCedula(), citaData.getTurnoId()) == null){
                if(citaRepository.buscarCitaPorTurno(citaData.getTurnoId()) == null){
                    citaRepository.save(cita);
                    respuesta = new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
                } else{
                    respuesta = new GenericResponse(FAILED.getSecond(), TURNO_ASIGNADO.getSecond(), TURNO_ASIGNADO.getFirst());
                }
            } else{
                respuesta = new GenericResponse(FAILED.getSecond(), CITA_ALREADY_EXISTS.getSecond(), CITA_ALREADY_EXISTS.getFirst());
            }
        }

        return respuesta;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Eliminar una cita", notes = "Se verifica si existe la cita y si existe la elimina")
    public GenericResponse deleteCita(@PathVariable int id){
        Cita cita = citaRepository.findOne(id);
        if (cita != null) {
            citaRepository.delete(id);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        } else{
            return new GenericResponse(FAILED.getSecond(), CITA_NOT_FOUND.getSecond(), CITA_NOT_FOUND.getFirst());
        }
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Actualizar parcialmente una cita", notes = "Actualiza algunos campos especificados de una cita")
    public GenericResponse partialUpdateCita(@PathVariable int id, @RequestBody CitaData citaData){
        Cita cita = citaRepository.findOne(id);

        if(cita != null){
            if(citaData.getClienteCedula() != null)
                if(clienteRepository.findOne(citaData.getClienteCedula()) != null)
                    cita.setClienteCedula(clienteRepository.findOne(citaData.getClienteCedula()));

            if(citaData.getTurnoId() != 0 && citaData.getTurnoId() != cita.getTurnoId().getId() && turnoRepository.findOne(citaData.getTurnoId()) != null){
                if(citaRepository.buscarCitaPorTurno(citaData.getTurnoId()) == null){
                    cita.setTurnoId(turnoRepository.findOne(citaData.getTurnoId()));
                } else{
                    return new GenericResponse(FAILED.getSecond(), TURNO_ASIGNADO.getSecond(), TURNO_ASIGNADO.getFirst());
                }
            }

            if(citaData.getServicioId() != 0 && servicioRepository.findOne(citaData.getServicioId()) != null)
                cita.setServicioId(servicioRepository.findOne(citaData.getServicioId()));

            citaRepository.save(cita);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        } else{
            return new GenericResponse(FAILED.getSecond(), CITA_NOT_FOUND.getSecond(), CITA_NOT_FOUND.getFirst());
        }
    }

    private boolean validarDatos(CitaData citaData){
        return citaData.getClienteCedula() != null && citaData.getTurnoId() != 0 && citaData.getServicioId() != 0;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Actualizar una cita", notes = "Actualizar todos los campos de una cita")
    public GenericResponse updateCita(@PathVariable int id, @RequestBody CitaData citaData){
        GenericResponse respuesta;
        if(validarDatos(citaData)){
            Cita cita = citaRepository.findOne(id);
            if(cita != null){
                cita.setClienteCedula(clienteRepository.findOne(citaData.getClienteCedula()));
                if(cita.getClienteCedula() == null){
                    respuesta = new GenericResponse(FAILED.getSecond(), CUSTOMER_NOT_FOUND.getSecond(), CUSTOMER_NOT_FOUND.getFirst());
                } else {
                    cita.setServicioId(servicioRepository.findOne(citaData.getServicioId()));

                    if(cita.getServicioId() == null){
                        respuesta = new GenericResponse(FAILED.getSecond(), SERVICE_NOT_FOUND.getSecond(), SERVICE_NOT_FOUND.getFirst());
                    } else{
                        cita.setTurnoId(turnoRepository.findOne(citaData.getTurnoId()));

                        if(cita.getTurnoId() == null){
                            respuesta = new GenericResponse(FAILED.getSecond(), TURNO_NOT_FOUND.getSecond(), TURNO_NOT_FOUND.getFirst());
                        } else{
                            if(citaRepository.buscarCitaPorTurno(citaData.getTurnoId()) == null){
                                citaRepository.save(cita);
                                respuesta = new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
                            } else{
                                respuesta = new GenericResponse(FAILED.getSecond(), TURNO_ASIGNADO.getSecond(), TURNO_ASIGNADO.getFirst());
                            }
                        }
                    }
                }
            } else{
                respuesta = new GenericResponse(FAILED.getSecond(), CITA_NOT_FOUND.getSecond(), CITA_NOT_FOUND.getFirst());
            }
        } else{
            respuesta = new GenericResponse(FAILED.getSecond(), FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
        }

        return respuesta;
    }
}
