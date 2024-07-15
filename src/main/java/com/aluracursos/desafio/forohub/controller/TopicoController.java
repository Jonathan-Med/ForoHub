package com.aluracursos.desafio.forohub.controller;

import com.aluracursos.desafio.forohub.domain.respuesta.Respuesta;
import com.aluracursos.desafio.forohub.domain.topico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private RegistrarTopicoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> crearTopico(@RequestBody @Valid DatosRegistroTopico datos) {
        DatosRespuestaTopico response = service.crear(datos);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopicos>> listar(@PageableDefault(size = 10) Pageable paginacion) {
        var page = repository.findByEstatusTrue(paginacion).map(DatosListadoTopicos::new);
        return ResponseEntity.ok(page);
    }

//    @GetMapping("/{id}/respuestas")
//    public ResponseEntity<List<Respuesta>> retornarRespuestasPorTopico(@PathVariable Long id) {
//        List<Respuesta> respuestas = service.getRespuestasByTopicoId(id);
//        return ResponseEntity.ok(respuestas);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> retornarDatosCurso(@PathVariable Long id) {
        Topico topico = repository.getReferenceById(id);
        var datosCurso = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFechaDeCreacion(), topico.getAutor().getId(), topico.getCurso().getId());
        return ResponseEntity.ok(datosCurso);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datos) {
        Topico topico = repository.getReferenceById(datos.id());
        topico.actualizarTopico(datos);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFechaDeCreacion(), topico.getAutor().getId(), topico.getCurso().getId()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        Topico topico = repository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }
}