package com.aluracursos.desafio.forohub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosRespuestaRespuesta(Long id,
                                      String mensaje,
                                      Long idTopico,
                                      LocalDateTime fechaDeCracion,
                                      Long idAutor,
                                      String solucion) {
}