package com.aluracursos.desafio.forohub.domain.topico;

import com.aluracursos.desafio.forohub.domain.curso.Curso;

import java.time.LocalDateTime;

public record DatosListadoTopicos(Long id,
                                  String titulo,
                                  String mensaje,
                                  LocalDateTime fechaDeCreacion,
                                  Long autorId,
                                  Long cursoId) {
    public DatosListadoTopicos(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaDeCreacion()
        , topico.getAutor().getId(), topico.getCurso().getId());
    }
}
