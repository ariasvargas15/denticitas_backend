package com.amongusdev.controller;

import com.amongusdev.exception.GenericResponse;
import com.amongusdev.models.Cliente;
import com.amongusdev.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.amongusdev.utils.Defines.CUSTOMER_NOT_FOUND;

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
            return new ResponseEntity<>(new GenericResponse(CUSTOMER_NOT_FOUND.getSecond(), CUSTOMER_NOT_FOUND.getFirst()), HttpStatus.OK);
        }
    }

}
