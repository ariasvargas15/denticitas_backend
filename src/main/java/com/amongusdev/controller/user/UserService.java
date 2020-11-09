package com.amongusdev.controller.user;

import com.amongusdev.controller.requestdata.PersonaData;
import com.amongusdev.exception.UnknownIdentifierException;
import com.amongusdev.exception.UserAlreadyExistException;
import com.amongusdev.models.Persona;

public interface UserService {
    Persona register(final UserData user) throws UserAlreadyExistException;

    boolean checkIfUserExist(final String cedula) ;

    void sendRegistrationConfirmationEmail(final Persona user);

    Persona getUserById(final String id) throws UnknownIdentifierException;

    boolean login(final UserData user);

    boolean partialUpdateUser(String cedula, PersonaData personaData);

    boolean updateUser(String cedula, PersonaData personaData);
}
