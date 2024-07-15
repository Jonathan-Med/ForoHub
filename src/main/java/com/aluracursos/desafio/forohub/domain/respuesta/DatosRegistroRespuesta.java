package com.aluracursos.desafio.forohub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DatosRegistroRespuesta(@NotBlank
                                     String mensaje,
                                     @NotNull
                                     Long idTopico,
                                     @NotNull
                                     Long idAutor,
                                     @NotBlank
                                     String solucion) {
}