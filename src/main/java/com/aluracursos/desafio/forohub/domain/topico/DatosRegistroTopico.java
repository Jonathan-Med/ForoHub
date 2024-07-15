package com.aluracursos.desafio.forohub.domain.topico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DatosRegistroTopico(@NotBlank
                                  String titulo,
                                  @NotBlank
                                  String mensaje,
                                  @Valid
                                  Long autorId,
                                  @Valid
                                  Long cursoId) {
}
