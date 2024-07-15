package com.aluracursos.desafio.forohub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DatosRegistroUsuario(@NotBlank
                                   String nombre,
                                   @Email
                                   String email,
                                   @NotBlank
                                   @NotBlank
                                   @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
                                   @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                                           message = "La contraseña debe contener al menos un número, una letra, un carácter especial y no debe tener espacios en blanco")
                                   String contrasena
                                   ) {
}
