package com.amongusdev.controller;

import com.amongusdev.controller.requestdata.CitaData;
import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.*;
import com.amongusdev.repositories.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.amongusdev.utils.Defines.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH,RequestMethod.PUT})
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

    @Autowired
    AgendaRepository agendaRepository;

    @Autowired
    EspecialistaRepository especialistaRepository;

    @Autowired
    DiaAgendaRepository diaAgendaRepository;

    @GetMapping
    public ResponseEntity<Object> listarCitas() {
        List<Cita> citas = citaRepository.findAll();

        if(citas.size() != 0)
            return new ResponseEntity<>(citas, HttpStatus.OK);

        return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), NO_CITAS.getSecond(), NO_CITAS.getFirst()), HttpStatus.OK);
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

    @GetMapping("{citaId}/turno")
    @ApiOperation(value = "Consultar turno y hora aproximada de un cliente", notes = "Consulta turno y hora aproximada de la cita de un cliente")
    public ResponseEntity<Object> getTurnoCliente(@PathVariable int citaId){
        Cita cita = citaRepository.findOne(citaId);

        if(cita == null){
            return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), NO_CITAS.getSecond(), NO_CITAS.getFirst()), HttpStatus.OK);
        }

        List<Cita> citas = citaRepository.findByTurno(cita.getTurnoId().getId());
        List<Cita> citasEmergencia = citaRepository.findByTurnoAndAndServicioEmergencia(cita.getTurnoId().getId());
        String turno = "Turno No. ";
        int contador = citasEmergencia.size();

        for(int i = 0; i < citas.size(); i++){
            if(citas.get(i).getServicioId().getId() > 26 || citas.get(i).getServicioId().getId() < 26){
                contador++;
            }
            if(citas.get(i).getId() == cita.getId()){
                turno += (contador);
                i = citas.size();
            }
        }

        return new ResponseEntity<>(turno, HttpStatus.OK);
    }

    @GetMapping("/cliente/{cedula}")
    @ApiOperation(value = "Consultar citas de un cliente", notes = "Consulta todas las citas asociadas a un cliente")
    public ResponseEntity<Object> getCitaCliente(@PathVariable String cedula){
        List<Cita> citasCliente = citaRepository.findByCliente(cedula);

        if(citasCliente.size() != 0)
            return new ResponseEntity<>(citasCliente, HttpStatus.OK);

        return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), NO_CITAS.getSecond(), NO_CITAS.getFirst()), HttpStatus.OK);
    }

    @GetMapping("/especialista/{cedulaEspecialista}")
    @ApiOperation(value = "Consultar citas de un especialista", notes = "Consulta todas las citas asociadas a un especialista")
    public ResponseEntity<Object> getCitaEspecialista(@PathVariable String cedulaEspecialista){
        List<Agenda> agendas = agendaRepository.findByEspecialista(cedulaEspecialista);
        List<Cita> citas = new ArrayList<>();

        if(agendas.size() == 0)
            return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), NO_CITAS.getSecond(), NO_CITAS.getFirst()), HttpStatus.OK);

        for(int i = 0; i < agendas.size(); i++){
            List<DiaAgenda> diasAgenda = diaAgendaRepository.findByAgenda(agendas.get(i).getId());

            for(int j = 0; j < diasAgenda.size(); j++){
                List<Turno> turnos = turnoRepository.findByDiaAgenda(diasAgenda.get(j).getId());
                for(int k = 0; k < turnos.size(); k++){
                    citas.addAll(citaRepository.findByTurno(turnos.get(k).getId()));
                }
            }
        }

        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    private boolean validarDatos(CitaData citaData){
        return citaData.getClienteCedula() != null && citaData.getTurnoId() != 0 && citaData.getServicioId() != 0;
    }

    @PostMapping()
    @ApiOperation(value = "Crear una cita", notes = "Se crea una cita con el respectivo ciente y turno")
    public GenericResponse createCita(@RequestBody CitaData citaData){
        if(validarDatos(citaData)){
            Cita cita = new Cita(clienteRepository.findOne(citaData.getClienteCedula()), turnoRepository.findOne(citaData.getTurnoId()), Calendar.getInstance().getTime(), servicioRepository.findOne(citaData.getServicioId()));

            if(cita.getClienteCedula() == null){
                return new GenericResponse(FAILED.getSecond(), CUSTOMER_NOT_FOUND.getSecond(), CUSTOMER_NOT_FOUND.getFirst());
            }

            if(cita.getTurnoId() == null){
                return  new GenericResponse(FAILED.getSecond(), TURNO_NOT_FOUND.getSecond(), TURNO_NOT_FOUND.getFirst());
            }

            if(cita.getServicioId() == null){
                return  new GenericResponse(FAILED.getSecond(), SERVICE_NOT_FOUND.getSecond(), SERVICE_NOT_FOUND.getFirst());
            }

            if(citaRepository.verificarExistenciaCita(citaData.getClienteCedula(), citaData.getTurnoId(), citaData.getServicioId()) == null){
                Turno turno = cita.getTurnoId();
                Servicio servicio = cita.getServicioId();

                if(servicio.getId() == 26){
                    citaRepository.save(cita);
                    return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
                }

                if(turno.isDisponible() && turno.getTiempoDisponible() - servicio.getDuracion() >= 0){
                    citaRepository.save(cita);
                    turno.setTiempoDisponible(turno.getTiempoDisponible() - servicio.getDuracion());
                    if(turno.getTiempoDisponible() == 0)
                        turno.setDisponible(false);
                    turnoRepository.save(turno);
                    return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
                }

                return new GenericResponse(FAILED.getSecond(), TURNO_ASIGNADO.getSecond(), TURNO_ASIGNADO.getFirst());
            }

            return new GenericResponse(FAILED.getSecond(), CITA_ALREADY_EXISTS.getSecond(), CITA_ALREADY_EXISTS.getFirst());
        }

        return new GenericResponse(FAILED.getSecond(), FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Eliminar una cita", notes = "Se verifica si existe la cita y si existe la elimina")
    public GenericResponse deleteCita(@PathVariable int id){
        Cita cita = citaRepository.findOne(id);
        if (cita != null) {
            Turno turno = cita.getTurnoId();
            Servicio servicio = cita.getServicioId();
            turno.setTiempoDisponible(turno.getTiempoDisponible() + servicio.getDuracion());
            turno.setDisponible(true);
            turnoRepository.save(turno);
            citaRepository.delete(id);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        }

        return new GenericResponse(FAILED.getSecond(), CITA_NOT_FOUND.getSecond(), CITA_NOT_FOUND.getFirst());
    }
}
