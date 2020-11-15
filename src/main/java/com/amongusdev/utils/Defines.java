package com.amongusdev.utils;

import org.springframework.data.util.Pair;

public class Defines {

    //RESPONSE MESSAGES AND CODES
    public static final Pair<Integer, String> SUCCESS                   = Pair.of(0, "success");
    public static final Pair<Integer, String> FAILED                    = Pair.of(1, "failed");
    public static final Pair<Integer, String> USER_ALREADY_EXISTS       = Pair.of(2, "Esta cedula ya se encuentra registrada");
    public static final Pair<Integer, String> EMAIL_ALREADY_EXISTS      = Pair.of(3, "Este email ya se encuentra registrado");
    public static final Pair<Integer, String> INCORRECT_USER_TYPE       = Pair.of(4, "Tipo de usuario incorrecto");
    public static final Pair<Integer, String> CUSTOMER_NOT_FOUND        = Pair.of(5, "Este cliente no se encuentra registrado");
    public static final Pair<Integer, String> ESPECIALIST_NOT_FOUND     = Pair.of(6, "Este especialista no se encuentra registrado");
    public static final Pair<Integer, String> AREA_NOT_FOUND            = Pair.of(7, "Esta area de especialización no se encuentra registrada");
    public static final Pair<Integer, String> CITA_NOT_FOUND            = Pair.of(8, "Esta cita no se encuentra registrada");
    public static final Pair<Integer, String> SERVICE_NOT_FOUND         = Pair.of(9, "Este servicio no se encuentra registrado");
    public static final Pair<Integer, String> FALTAN_DATOS              = Pair.of(10, "Faltan datos");
    public static final Pair<Integer, String> DATOS_ASOCIADOS_AREA      = Pair.of(11, "Existen servicios o especialistas asociados al area");
    public static final Pair<Integer, String> TURNO_ALREADY_EXISTS      = Pair.of(12, "El turno ya existe");
    public static final Pair<Integer, String> TURNO_NOT_FOUND           = Pair.of(13, "El turno no se encuentra registrado");
    public static final Pair<Integer, String> CITA_ALREADY_EXISTS       = Pair.of(14, "La cita ya se encuentra registrada");
    public static final Pair<Integer, String> TURNO_ASIGNADO            = Pair.of(15, "El turno ya se encuentra asignado");
}
