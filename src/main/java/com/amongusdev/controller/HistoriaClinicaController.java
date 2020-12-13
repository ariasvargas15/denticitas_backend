package com.amongusdev.controller;

import com.amongusdev.controller.requestdata.EvolucionData;
import com.amongusdev.controller.requestdata.HistoriaClinicaData;
import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.Cliente;
import com.amongusdev.models.Evolucion;
import com.amongusdev.models.HistoriaClinica;
import com.amongusdev.repositories.CitaRepository;
import com.amongusdev.repositories.ClienteRepository;
import com.amongusdev.repositories.EvolucionRepository;
import com.amongusdev.repositories.HistoriaClinicaRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.amongusdev.utils.Defines.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH,RequestMethod.PUT})
@RequestMapping()
public class HistoriaClinicaController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    HistoriaClinicaRepository historiaClinicaRepository;

    @Autowired
    EvolucionRepository evolucionRepository;

    @Autowired
    CitaRepository citaRepository;

    @GetMapping("/historiaclinica")
    public List<HistoriaClinica> listarHistoriaClinica() {
        return historiaClinicaRepository.findAll();
    }

    @GetMapping("/historiaclinica/{cedula}")
    public ResponseEntity<Object> getHistoriaClinica(@PathVariable String cedula){
        Cliente cliente = clienteRepository.findOne(cedula);

        if(cliente == null){
            return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), CUSTOMER_NOT_FOUND.getSecond(), CUSTOMER_NOT_FOUND.getFirst()), HttpStatus.OK);
        }

        return new ResponseEntity<>(historiaClinicaRepository.findByCliente(cedula), HttpStatus.OK);
    }

    @PatchMapping("/historiaclinica/{cedula}")
    @ApiOperation(value = "Actualizar parcialmente la historia clinica de un cliente", notes = "Actualiza algunos campos especificados de la historia clinica de un cliente")
    public GenericResponse partialUpdateCliente(@PathVariable String cedula, @RequestBody HistoriaClinicaData historiaClinicaData) {
        Cliente cliente = clienteRepository.findOne(cedula);

        if(cliente == null){
            return new GenericResponse(FAILED.getSecond(), CUSTOMER_NOT_FOUND.getSecond(), CUSTOMER_NOT_FOUND.getFirst());
        }

        HistoriaClinica historiaClinica = historiaClinicaRepository.findByCliente(cedula);

        if(historiaClinicaData.getEstadoSalud() != null){
            historiaClinica.setEstadoSalud(historiaClinicaData.getEstadoSalud());
        }

        if(historiaClinicaData.getToleranciaAnestesicos() != null){
            historiaClinica.setToleranciaAnestesicos(historiaClinicaData.getToleranciaAnestesicos());
        }

        if(historiaClinicaData.getAlergiaMedicamentos() != null){
            historiaClinica.setAlergiaMedicamentos(historiaClinicaData.getAlergiaMedicamentos());
        }

        if(historiaClinicaData.getAnomaliasDentales() != null){
            historiaClinica.setAnomaliasDentales(historiaClinicaData.getAnomaliasDentales());
        }

        if(historiaClinicaData.getHigieneOral() != null){
            historiaClinica.setHigieneOral(historiaClinicaData.getHigieneOral());
        }

        historiaClinicaRepository.save(historiaClinica);
        return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
    }

    @GetMapping("/historiaclinica/evolucion")
    public List<Evolucion> listarEvolucion() {
        return evolucionRepository.findAll();
    }

    @GetMapping("/historiaclinica/{cedulaCliente}/evolucion")
    public ResponseEntity<Object> listarEvolucionCliente (@PathVariable String cedulaCliente){
        Cliente cliente = clienteRepository.findOne(cedulaCliente);

        if(cliente == null){
            return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), CUSTOMER_NOT_FOUND.getSecond(), CUSTOMER_NOT_FOUND.getFirst()), HttpStatus.OK);
        }

        HistoriaClinica historiaClinica = historiaClinicaRepository.findByCliente(cedulaCliente);

        return new ResponseEntity<>(evolucionRepository.findByHistoriaClinica(historiaClinica.getId()), HttpStatus.OK);
    }

    @GetMapping("/historiaclinica/evolucion/{idEvolucion}")
    public ResponseEntity<Object> getEvolucion(@PathVariable int idEvolucion){
        Evolucion evolucion = evolucionRepository.findOne(idEvolucion);

        if(evolucion == null){
            return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), EVOLUCION_NOT_FOUND.getSecond(), EVOLUCION_NOT_FOUND.getFirst()), HttpStatus.OK);
        }

        return new ResponseEntity<>(evolucion, HttpStatus.OK);
    }

    public boolean validarDatos(EvolucionData evolucionData){
        return evolucionData.getIdCita() != null && evolucionData.getFecha() != null;
    }

    @PostMapping("/historiaclinica/{cedulaCliente}/evolucion")
    @ApiOperation(value = "Crear una evolucion de la historia clinica", notes = "Crea una evolucion de la historia clinica")
    public GenericResponse createEvolucion(@PathVariable String cedulaCliente, @RequestBody EvolucionData evolucionData){
        Evolucion evolucion = new Evolucion();

        if(!validarDatos(evolucionData)){
            return new GenericResponse(FAILED.getSecond(), FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
        }

        if(clienteRepository.findOne(cedulaCliente) == null){
            return new GenericResponse(FAILED.getSecond(), CUSTOMER_NOT_FOUND.getSecond(), CUSTOMER_NOT_FOUND.getFirst());
        }

        if(citaRepository.findOne(evolucionData.getIdCita()) == null){
            return new GenericResponse(FAILED.getSecond(), CITA_NOT_FOUND.getSecond(), CITA_NOT_FOUND.getFirst());
        }

        BeanUtils.copyProperties(evolucionData, evolucion);
        evolucion.setIdHistoria(historiaClinicaRepository.findByCliente(cedulaCliente).getId());

        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        try{
            evolucion.setFecha(formato.parse(evolucionData.getFecha()));
        } catch(ParseException e){
            return new GenericResponse(FAILED.getSecond(), FAILED.getSecond(), FAILED.getFirst());
        }

        evolucionRepository.save(evolucion);
        return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
    }
}