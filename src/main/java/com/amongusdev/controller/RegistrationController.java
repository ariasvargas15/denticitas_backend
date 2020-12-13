package com.amongusdev.controller;

import com.amongusdev.controller.user.UserData;
import com.amongusdev.controller.user.UserService;
import com.amongusdev.exception.GenericResponse;
import com.amongusdev.exception.UserAlreadyExistException;
import com.amongusdev.models.*;
import com.amongusdev.repositories.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.amongusdev.utils.Defines.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH,RequestMethod.PUT})
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EspecialistaRepository especialistaRepository;

    @Autowired
    private HojaVidaRepository hojaVidaRepository;

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Autowired
    private AreaEspecializacionRepository areaEspecializacionRepository;

    @PostMapping("/registro")
    public GenericResponse registerUserAccount(@RequestBody UserData userData) {
        try {
            if (userData.getRol().equals("cliente")) {
                Persona p = userService.register(userData);
                Cliente c = new Cliente();
                BeanUtils.copyProperties(p, c);
                clienteRepository.save(c);
                HistoriaClinica historiaClinica = new HistoriaClinica(p.getCedula());
                historiaClinicaRepository.save(historiaClinica);
            } else if (userData.getRol().equals("especialista")) {
                Persona p = userService.register(userData);
                Especialista e = new Especialista();
                BeanUtils.copyProperties(p, e);
                especialistaRepository.save(e);
                areaEspecializacionRepository.insertarAreaEspecialista(7, e.getCedula());
                HojaVida hojaVida = new HojaVida(p.getCedula());
                hojaVidaRepository.save(hojaVida);
            } else {
                return new GenericResponse(FAILED.getSecond(), INCORRECT_USER_TYPE.getSecond(), INCORRECT_USER_TYPE.getFirst());
            }

        } catch (UserAlreadyExistException e) {
            return new GenericResponse(FAILED.getSecond(), USER_ALREADY_EXISTS.getSecond(), USER_ALREADY_EXISTS.getFirst());
        }

        return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
    }

    @PostMapping("/login")
    public GenericResponse login(@RequestBody UserData userData) {
        boolean res = userService.login(userData);
        if (res) {
            return new GenericResponse(SUCCESS.getSecond(), SUCCESS.getFirst());
        } else {
            return new GenericResponse(FAILED.getSecond(), FAILED.getFirst());
        }
    }
}
