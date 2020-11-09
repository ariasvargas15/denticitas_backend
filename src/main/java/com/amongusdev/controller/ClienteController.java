package com.amongusdev.controller;

import com.amongusdev.controller.requestdata.ClienteData;
import com.amongusdev.controller.user.UserService;
import com.amongusdev.exception.GenericResponse;
import com.amongusdev.exception.UnknownIdentifierException;
import com.amongusdev.models.Cliente;
import com.amongusdev.repositories.ClienteRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.amongusdev.utils.Defines.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    UserService userService;

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{cedula}")
    public ResponseEntity<Object> getCliente(@PathVariable String cedula) {
        Cliente cliente = clienteRepository.findOne(cedula);
        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponse(FAILED.getSecond(), CUSTOMER_NOT_FOUND.getSecond(), CUSTOMER_NOT_FOUND.getFirst()), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{cedula}")
    @ApiOperation(value = "Eliminar Cliente", notes = "Se verifica si el cliente existe y si es el caso lo elimina.")
    public GenericResponse deleteCliente(@PathVariable String cedula){
        Cliente cliente = clienteRepository.findOne(cedula);
        if(cliente != null){
            clienteRepository.delete(cedula);
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        } else{
            return new GenericResponse(FAILED.getSecond(), CUSTOMER_NOT_FOUND.getSecond(), CUSTOMER_NOT_FOUND.getFirst());
        }
    }

    @PatchMapping("/{cedula}")
    @ApiOperation(value = "Actualizar parcialmente un cliente", notes = "Actualiza algunos campos especificados de un cliente")
    public GenericResponse partialUpdateCliente(@PathVariable String cedula, @Valid ClienteData clienteData){
        Cliente cliente = clienteRepository.findOne(cedula);
        if(cliente == null){
            return new GenericResponse(FAILED.getSecond(), CUSTOMER_NOT_FOUND.getSecond(), CUSTOMER_NOT_FOUND.getFirst());
        } else{
            if(clienteData.getOcupacion() != null)
                cliente.setOcupacion(clienteData.getOcupacion());

            if(clienteData.getPersona().getCedula() != null)
                cliente.setCedula(clienteData.getPersona().getCedula());

            userService.partialUpdateUser(cedula, clienteData.getPersona());

            clienteRepository.save(cliente);

            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        }
    }

    private boolean verificarDatosPut(ClienteData clienteData){
        return clienteData.getOcupacion() != null && clienteData.getPersona() != null;
    }

    @PutMapping("/{cedula}")
    @ApiOperation(value = "Actualizar un cliente", notes = "Actualiza todos los campos de un cliente")
    public GenericResponse updateCliente(@PathVariable String cedula, @Valid ClienteData clienteData){
        Cliente cliente = clienteRepository.findOne(cedula);
        if(cliente == null){
            return new GenericResponse(FAILED.getSecond(), CUSTOMER_NOT_FOUND.getSecond(), CUSTOMER_NOT_FOUND.getFirst());
        } else{
            if(verificarDatosPut(clienteData)){
                cliente.setOcupacion(clienteData.getOcupacion());
                if(userService.updateUser(cedula, clienteData.getPersona()))
                    return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
                else
                    return new GenericResponse(FAILED.getSecond(), FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
            } else{
                return new GenericResponse(FAILED.getSecond(), FALTAN_DATOS.getSecond(), FALTAN_DATOS.getFirst());
            }
        }
    }
}
