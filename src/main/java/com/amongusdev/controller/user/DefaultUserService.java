package com.amongusdev.controller.user;

import com.amongusdev.controller.requestdata.PersonaData;
import com.amongusdev.exception.UnknownIdentifierException;
import com.amongusdev.exception.UserAlreadyExistException;
import com.amongusdev.models.Administrador;
import com.amongusdev.models.Cliente;
import com.amongusdev.models.Especialista;
import com.amongusdev.models.Persona;
import com.amongusdev.repositories.PersonaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service("userService")
public class DefaultUserService implements UserService {

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

    public boolean partialUpdateUser(String cedula, PersonaData personaData){
        Persona persona = personaRepository.findOne(cedula);

        if(personaData.getNombre() != null)
            persona.setNombre(personaData.getNombre());

        if(personaData.getApellido() != null)
            persona.setApellido(personaData.getApellido());

        if(personaData.getFechaNacimiento() != null){
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            try{
                persona.setFechaNacimiento(formato.parse(personaData.getFechaNacimiento()));
            } catch(ParseException e){
                return false;
            }
        }

        if(personaData.getTelefono() != null)
            persona.setTelefono(personaData.getTelefono());

        if(personaData.getDireccion() != null)
            persona.setDireccion(personaData.getDireccion());

        if(personaData.getEmail() != null)
            persona.setEmail(personaData.getEmail());

        if(personaData.getPassword() != null)
            persona.setPassword(passwordEncoder().encode(personaData.getPassword()));

        if(personaData.getCedula() != null){
            persona.setCedula(personaData.getCedula());
            personaRepository.delete(cedula);
        }
        personaRepository.save(persona);
        return true;
    }

    private boolean validarDatosPut(PersonaData personaData){
        return personaData.getNombre() != null && personaData.getApellido() != null &&
                personaData.getFechaNacimiento() != null && personaData.getTelefono() != null &&
                personaData.getDireccion() != null && personaData.getEmail() != null && personaData.getPassword() != null;
    }

    public boolean updateUser(String cedula, PersonaData personaData){
        if(validarDatosPut(personaData)){
            Persona persona = personaRepository.findOne(cedula);
            BeanUtils.copyProperties(personaData, persona);
            persona.setCedula(cedula);
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            try{
                persona.setFechaNacimiento(formato.parse(personaData.getFechaNacimiento()));
            } catch(ParseException e){
                return false;
            }
            persona.setPassword(passwordEncoder().encode(personaData.getPassword()));
            personaRepository.save(persona);
            return true;
        } else{
            return false;
        }
    }
}
