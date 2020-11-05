package com.amongusdev.controller;

import com.amongusdev.exception.GenericResponse;
import com.amongusdev.exception.UserAlreadyExistException;
import com.amongusdev.models.Cliente;
import com.amongusdev.models.Especialista;
import com.amongusdev.models.Persona;
import com.amongusdev.models.user.UserData;
import com.amongusdev.models.user.UserService;
import com.amongusdev.repositories.ClienteRepository;
import com.amongusdev.repositories.EspecialistaRepository;
import static com.amongusdev.utils.Defines.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/registro")
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EspecialistaRepository especialistaRepository;

    @PostMapping
    public GenericResponse registerUserAccount(@Valid UserData userData) {
        try {
            if (userData.getTipo().equals("cliente")) {
                Persona p = userService.register(userData);
                Cliente c = new Cliente();
                BeanUtils.copyProperties(p, c);
                clienteRepository.save(c);
            } else if (userData.getTipo().equals("especialista")) {
                Persona p = userService.register(userData);
                Especialista e = new Especialista();
                BeanUtils.copyProperties(p, e);
                especialistaRepository.save(e);
            } else {
                return new GenericResponse(FAILED.getSecond(), INCORRECT_USER_TYPE.getSecond(), INCORRECT_USER_TYPE.getFirst());
            }

        } catch (UserAlreadyExistException e) {
            return new GenericResponse(FAILED.getSecond(), USER_ALREADY_EXISTS.getSecond(), USER_ALREADY_EXISTS.getFirst());
        }

        return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
    }
}
