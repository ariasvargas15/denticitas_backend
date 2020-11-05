package com.amongusdev.controller.user;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
public class UserData implements Serializable {

    @NotEmpty(message = "{registration.validation.cedula}")
    private String cedula;

    @NotEmpty(message = "{registration.validation.password}")
    private String password;

    @NotEmpty(message = "{registration.validation.tipo}")
    private String tipo;

}
