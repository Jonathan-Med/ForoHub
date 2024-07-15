package com.aluracursos.desafio.forohub.domain.curso;

public record DatosListadoCursos(Long id,
                                 String nombre,
                                 Categoria categoria) {
    public DatosListadoCursos(Curso curso){
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }
}
