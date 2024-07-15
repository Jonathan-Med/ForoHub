package com.aluracursos.desafio.forohub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosListadoRespuestas(Long id,
                                     String mensaje,
                                     Long idTopico,
                                     LocalDateTime fechaDeCracion,
                                     Long idAutor,
                                     String solucion) {
    public DatosListadoRespuestas(Respuesta respuesta){
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico().getId(),
                respuesta.getFechaDeCreacion(), respuesta.getAutor().getId(), respuesta.getSolucion());
    }
}
