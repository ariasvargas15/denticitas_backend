package com.amongusdev.utils;

import org.springframework.data.util.Pair;

public class Defines {

    //RESPONSE MESSAGES AND CODES
    public static final Pair<Integer, String> SUCCESS                   = Pair.of(0, "success");
    public static final Pair<Integer, String> FAILED                    = Pair.of(1, "failed");
    public static final Pair<Integer, String> USER_ALREADY_EXISTS       = Pair.of(2, "Esta cedula ya se encuentra registrada");
    public static final Pair<Integer, String> EMAIL_ALREADY_EXISTS      = Pair.of(3, "Este email ya se encuentra registrado");
    public static final Pair<Integer, String> INCORRECT_USER_TYPE       = Pair.of(4, "Tipo de usuario incorrecto");

}
