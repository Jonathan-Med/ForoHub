package com.aluracursos.desafio.forohub.domain.topico;

import com.aluracursos.desafio.forohub.domain.curso.Curso;
import com.aluracursos.desafio.forohub.domain.curso.CursoRepository;
import com.aluracursos.desafio.forohub.domain.respuesta.Respuesta;
import com.aluracursos.desafio.forohub.domain.usuario.Usuario;
import com.aluracursos.desafio.forohub.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrarTopicoService {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public DatosRespuestaTopico crear(DatosRegistroTopico datos) {
        Usuario autor = usuarioRepository.findByIdAndEstatusTrue(datos.autorId()).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Curso curso = cursoRepository.findById(datos.cursoId()).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
        Topico topico = new Topico();
        topico.setTitulo(datos.titulo());
        topico.setMensaje(datos.mensaje());
        topico.setFechaDeCreacion(LocalDateTime.now());
        topico.setEstatus(true);
        topico.setAutor(autor);
        topico.setCurso(curso);

        repository.save(topico);

        return new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaDeCreacion(), autor.getId(), curso.getId());
    }

    public List<Respuesta> getRespuestasByTopicoId(Long id) {
        Topico topico = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tópico no encontrado"));
        return topico.getRespuestas();
    }
}
