package com.amongusdev.models.user;

import com.amongusdev.exception.UnknownIdentifierException;
import com.amongusdev.exception.UserAlreadyExistException;
import com.amongusdev.models.Persona;

public interface UserService {
    Persona register(final UserData user) throws UserAlreadyExistException;

    boolean checkIfUserExist(final String cedula) ;

    void sendRegistrationConfirmationEmail(final Persona user);

    Persona getUserById(final String id) throws UnknownIdentifierException;
}
