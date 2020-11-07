package com.amongusdev.controller;

import com.amongusdev.exception.GenericResponse;
import com.amongusdev.exception.UnknownIdentifierException;
import com.amongusdev.models.Cliente;
import com.amongusdev.repositories.ClienteRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.amongusdev.utils.Defines.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

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
}
