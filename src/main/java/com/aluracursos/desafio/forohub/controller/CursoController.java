package com.aluracursos.desafio.forohub.controller;

import com.aluracursos.desafio.forohub.domain.curso.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@ResponseBody
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity registrarCurso(@RequestBody @Valid DatosRegistroCurso datos,
                                         UriComponentsBuilder uriComponentsBuilder){
        Curso curso = repository.save(new Curso(datos));

        DatosRespuestaCurso datosRespuestaCurso = new DatosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria().toString());
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaCurso);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoCursos>> listar(@PageableDefault(size = 10) Pageable paginacion) {
        var page = repository.findAll(paginacion).map(DatosListadoCursos::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity retornarDatosCurso(@PathVariable Long id){
        Curso curso = repository.getReferenceById(id);
        var datosCurso = new DatosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria().toString());
        return ResponseEntity.ok(datosCurso);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarCurso(@RequestBody @Valid DatosActualizarCurso datos){
        Curso curso = repository.getReferenceById(datos.id());
        curso.actualizarCurso(datos);
        return ResponseEntity.ok(new DatosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria().toString()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarCurso(@PathVariable Long id){
        Curso curso = repository.getReferenceById(id);
        if(curso != null){
            repository.delete(curso);
        }
    }
}
