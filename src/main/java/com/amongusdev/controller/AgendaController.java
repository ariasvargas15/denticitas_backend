package com.amongusdev.controller;

import com.amongusdev.models.Agenda;
import com.amongusdev.models.DiaAgenda;
import com.amongusdev.models.Turno;
import com.amongusdev.repositories.AgendaRepository;
import com.amongusdev.repositories.DiaAgendaRepository;
import com.amongusdev.repositories.EspecialistaRepository;
import com.amongusdev.repositories.TurnoRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgendaController {
    @Autowired
    AgendaRepository agendaRepository;

    @Autowired
    EspecialistaRepository especialistaRepository;

    @Autowired
    DiaAgendaRepository diaAgendaRepository;

    @Autowired
    TurnoRepository turnoRepository;

    private Turno createTurno(String horaInicio, int duracion, DiaAgenda diaAgenda) {
        return turnoRepository.save(new Turno(horaInicio, duracion, diaAgenda));
    }

    private DiaAgenda createDiaAgenda(int dia, Agenda agenda) {
        return diaAgendaRepository.save(new DiaAgenda(dia, agenda));
    }

    private Agenda createAgenda(int mes, int anio, String cedula) {
        return agendaRepository.save(new Agenda(mes, anio, especialistaRepository.findOne(cedula)));
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
                turno = createTurno(turnos[i].substring(2, 6), Integer.parseInt(turnos[i].substring(6, 8)), diaAgenda);
            }
        }
    }
}
