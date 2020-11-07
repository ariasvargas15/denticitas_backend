package com.amongusdev.controller.user;

import com.amongusdev.exception.UnknownIdentifierException;
import com.amongusdev.exception.UserAlreadyExistException;
import com.amongusdev.models.Administrador;
import com.amongusdev.models.Cliente;
import com.amongusdev.models.Especialista;
import com.amongusdev.models.Persona;
import com.amongusdev.repositories.ClienteRepository;
import com.amongusdev.repositories.PersonaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service("userService")
public class DefaultUserService implements UserService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    PersonaRepository personaRepository;

    @Override
    public Persona register(UserData user) throws UserAlreadyExistException {
        if (checkIfUserExist(user.getCedula())) {
            throw new UserAlreadyExistException();
        }
        Persona persona = new Persona();
        BeanUtils.copyProperties(user, persona);
        persona.setCreateTime(Calendar.getInstance().getTime());
        encodePassword(persona, user);
        return personaRepository.save(persona);
    }

    @Override
    public boolean checkIfUserExist(String cedula) {
        return personaRepository.findOne(cedula) != null;
    }

    @Override
    public void sendRegistrationConfirmationEmail(Persona user) {

    }

    @Override
    public Persona getUserById(String id) throws UnknownIdentifierException {
        return null;
    }

    @Override
    public boolean login(final UserData user) {
        Persona p = personaRepository.findOne(user.getCedula());
        if (p != null) {
            return validarTipoUsuario(user.getTipo(), p) && passwordEncoder().matches(user.getPassword(), p.getPassword());
        } else {
            return false;
        }
    }

    private boolean validarTipoUsuario(String tipo, Persona p) {
        boolean res = true;
        switch (tipo) {
            case "cliente":
                if (!(p.obtenerTipoPersona() instanceof Cliente)) {
                    res = false;
                }
                break;
            case "especialista":
                if (!(p.obtenerTipoPersona() instanceof Especialista)) {
                    res = false;
                }
                break;
            case "admin":
                if (!(p.obtenerTipoPersona() instanceof Administrador)) {
                    res = false;
                }
                break;
            default:
                res = false;
        }
        return res;
    }

    private void encodePassword(Persona persona, UserData user) {
        persona.setPassword(passwordEncoder().encode(user.getPassword()));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
