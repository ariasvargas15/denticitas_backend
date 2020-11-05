package com.amongusdev.models.user;

import com.amongusdev.exception.UnknownIdentifierException;
import com.amongusdev.exception.UserAlreadyExistException;
import com.amongusdev.models.Persona;
import com.amongusdev.repositories.ClienteRepository;
import com.amongusdev.repositories.PersonaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private void encodePassword(Persona persona, UserData user) {
        persona.setPassword(passwordEncoder().encode(user.getPassword()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
