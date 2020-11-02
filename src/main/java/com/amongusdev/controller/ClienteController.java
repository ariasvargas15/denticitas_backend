package com.amongusdev.controller;

import com.amongusdev.models.Cliente;
import com.amongusdev.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping("/cliente")
    public List<Cliente> index() {
        return clienteRepository.findAll();
    }
}
