package com.amongusdev.controller;

import com.amongusdev.controller.requestdata.AgendaData;
import com.amongusdev.controller.requestdata.DiaAgendaData;
import com.amongusdev.controller.requestdata.TurnoData;
import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.Agenda;
import com.amongusdev.models.Cita;
import com.amongusdev.models.DiaAgenda;
import com.amongusdev.models.Turno;
import com.amongusdev.repositories.AgendaRepository;
import com.amongusdev.repositories.DiaAgendaRepository;
import com.amongusdev.repositories.EspecialistaRepository;
import com.amongusdev.repositories.TurnoRepository;
import com.amongusdev.repositories.CitaRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.amongusdev.utils.Defines.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH,RequestMethod.PUT})
public class AgendaController {
    @Autowired
    AgendaRepository agendaRepository;

    @Autowired
    EspecialistaRepository especialistaRepository;

    @Autowired
    DiaAgendaRepository diaAgendaRepository;

    @Autowired
    TurnoRepository turnoRepository;

    @Autowired
    CitaRepository citaRepository;

    private Turno createTurno(String horaInicio, DiaAgenda diaAgenda) {
        return turnoRepository.save(new Turno(horaInicio, diaAgenda));
    }

    private DiaAgenda createDiaAgenda(int dia, Agenda agenda) {
        return diaAgendaRepository.save(new DiaAgenda(dia, agenda));
    }

    private Agenda createAgenda(int mes, int anio, String cedula) {
        return agendaRepository.save(new Agenda(mes, anio, especialistaRepository.findOne(cedula)));
    }

    @GetMapping("/especialista/{cedula}/agenda")
    @ApiOperation(value = "Consultar las agendas de un especialista", notes = "Consulta las agendas asociadas a un especialista desde el dia actual")
    public ResponseEntity<Object> getAgendaEspecialista(@PathVariable String cedula){
        List<Agenda> agendas = agendaRepository.findByEspecialista(cedula);
        List<DiaAgenda> diasAgenda;
        List<Turno> turnos;
        DiaAgendaData diaAgendaData;
        TurnoData turnoData;
        List<AgendaData> agendaDataList = new ArrayList<>();

        if(agendas.size() != 0){
            Calendar calendario = Calendar.getInstance();

            for(int i = 0; i < agendas.size(); i++){
                if((agendas.get(i).getAnio() == calendario.get(Calendar.YEAR) && agendas.get(i).getMes() >= calendario.get(Calendar.MONTH)+1) || agendas.get(i).getAnio() > calendario.get(Calendar.YEAR)){
                    AgendaData agendasData = new AgendaData();
                    BeanUtils.copyProperties(agendas.get(i), agendasData);
                    agendasData.setDiaAgendaList(new ArrayList<>());
                    agendaDataList.add(agendasData);

                    diasAgenda = diaAgendaRepository.findByAgenda(agendas.get(i).getId());
                    for (int j = 0; j < diasAgenda.size(); j++){
                        if(diasAgenda.get(j).getDia() >= calendario.get(Calendar.DATE)){
                            diaAgendaData = new DiaAgendaData();
                            BeanUtils.copyProperties(diasAgenda.get(j), diaAgendaData);
                            diaAgendaData.setTurnoList(new ArrayList<>());

                            turnos = turnoRepository.findByDiaAgenda(diasAgenda.get(j).getId());
                            for(int k = 0; k < turnos.size(); k++){
                                turnoData = new TurnoData();
                                BeanUtils.copyProperties(turnos.get(k), turnoData);
                                diaAgendaData.getTurnoList().add(turnoData);
                            }
                            agendaDataList.get(agendaDataList.size()-1).getDiaAgendaList().add(diaAgendaData);
                        }
                    }
                }
            }

            return new ResponseEntity<>(agendaDataList, HttpStatus.OK);
        }

        return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), NO_AGENDAS.getSecond(), NO_AGENDAS.getFirst()), HttpStatus.OK);
    }

    @PostMapping("especialista/{cedula}/agenda")
    @ApiOperation(value = "Crear un horario de especialista", notes = "Se crea un horario del especialista correspondiente.")
    public void createHorario(@PathVariable String cedula, @RequestParam() int anio, @RequestParam() int mes, @RequestParam() String[] turnos) {
        Agenda agenda = agendaRepository.verificarExistenciaAgenda(mes, anio, cedula);
        DiaAgenda diaAgenda;
        Turno turno;

        if (agenda == null) {
            agenda = createAgenda(mes, anio, cedula);
        }

        for (int i = 0; i < turnos.length; i++) {
            diaAgenda = diaAgendaRepository.verificarExistenciaDiaAgenda(agenda.getId(), Integer.parseInt(turnos[i].substring(0, 2)));
            if (diaAgenda == null) {
                diaAgenda = createDiaAgenda(Integer.parseInt(turnos[i].substring(0, 2)), agenda);
            }

            turno = turnoRepository.verificarExistenciaTurno(turnos[i].substring(2, 6), diaAgenda.getId());
            if (turno == null) {
                turno = createTurno(turnos[i].substring(2, 6), diaAgenda);
            }
        }
    }

    @DeleteMapping("agenda/{cedulaEspecialista}")
    @ApiOperation(value = "Eliminar una agenda", notes = "Elimina una agenda completa de un especialista dado.")
    public GenericResponse deleteAgenda(@PathVariable String cedulaEspecialista){
        List<Agenda> agendas = agendaRepository.findByEspecialista(cedulaEspecialista);

        for(int i = 0; i < agendas.size(); i++){
            Calendar calendario = Calendar.getInstance();
            if((agendas.get(i).getAnio() == calendario.get(Calendar.YEAR) && agendas.get(i).getMes() >= calendario.get(Calendar.MONTH)+1) || agendas.get(i).getAnio() > calendario.get(Calendar.YEAR)){
                List<DiaAgenda> diasAgenda = diaAgendaRepository.findByAgenda(agendas.get(i).getId());

                for(int j = 0; j < diasAgenda.size(); j++){
                    List<Turno> turnos = turnoRepository.findByDiaAgenda(diasAgenda.get(j).getId());

                    for(int k = 0; k < turnos.size(); k++){
                        citaRepository.deleteByTurno(turnos.get(k).getId());
                    }
                }
                agendaRepository.delete(agendas.get(i).getId());
            }
        }

        return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
    }

    @DeleteMapping("agenda/dia/{diaAgendaId}")
    @ApiOperation(value = "Eliminar un dia de una agenda", notes = "Elimina un dia con todos sus turnos")
    public GenericResponse deleteDiaAgenda(@PathVariable int diaAgendaId){
        DiaAgenda diaAgenda = diaAgendaRepository.findOne(diaAgendaId);

        if(diaAgenda == null){
            return new GenericResponse(FAILED.getSecond(), DIA_AGENDA_NOT_FOUND.getSecond(), DIA_AGENDA_NOT_FOUND.getFirst());
        }

        List<Turno> turnos = turnoRepository.findByDiaAgenda(diaAgendaId);

        for(int i = 0; i < turnos.size(); i++){
            citaRepository.deleteByTurno(turnos.get(i).getId());
            turnoRepository.delete(turnos.get(i).getId());
        }

        diaAgendaRepository.delete(diaAgendaId);
        return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
    }

    @DeleteMapping("agenda/turno/{turnoId}")
    @ApiOperation(value = "Eliminar un turno", notes = "Elimina un turno asociado a un id dado")
    public GenericResponse deleteTurno(@PathVariable int turnoId){
        Turno turno = turnoRepository.findOne(turnoId);

        if(turno == null){
            return new GenericResponse(FAILED.getSecond(), TURNO_NOT_FOUND.getSecond(), TURNO_ALREADY_EXISTS.getFirst());
        }

        citaRepository.deleteByTurno(turnoId);

        turnoRepository.delete(turnoId);
        return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
    }
}
