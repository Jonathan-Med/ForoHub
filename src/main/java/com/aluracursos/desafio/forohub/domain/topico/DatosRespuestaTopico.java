package com.aluracursos.desafio.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(Long id,
                                   String titulo,
                                   String mensaje,
                                   LocalDateTime fechaDeCreacion,
                                   Long idAutor,
                                   Long idCurso) {
}
