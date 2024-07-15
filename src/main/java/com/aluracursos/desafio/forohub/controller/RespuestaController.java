package com.aluracursos.desafio.forohub.controller;

import com.aluracursos.desafio.forohub.domain.respuesta.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos/{topicoId}/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {
    @Autowired
    private RespuestaRepository repository;

    @Autowired
    private RegistrarRespuestaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaRespuesta> agregarRespuesta(@PathVariable Long topicoId, @RequestBody @Valid DatosRegistroRespuesta datos) {
        DatosRespuestaRespuesta respuesta = service.agregarRespuesta(topicoId, datos);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoRespuestas>> listarRespuestas(@PathVariable Long topicoId, @PageableDefault(size = 10) Pageable paginacion) {
        var page = repository.findByTopicoIdAndEstatusTrue(topicoId, paginacion).map(DatosListadoRespuestas::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datos){
        Respuesta respuesta = repository.getReferenceById(datos.id());
        respuesta.actualizarRespuesta(datos);
        return ResponseEntity.ok(new DatosRespuestaRespuesta(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico().getId(),
                respuesta.getFechaDeCreacion(), respuesta.getAutor().getId(), respuesta.getSolucion()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id){
        Respuesta respuesta = repository.getReferenceById(id);
        respuesta.desactivarRespuesta();
        return ResponseEntity.noContent().build();
    }
}