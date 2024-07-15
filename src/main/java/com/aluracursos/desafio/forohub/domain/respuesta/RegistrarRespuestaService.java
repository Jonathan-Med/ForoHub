package com.aluracursos.desafio.forohub.domain.respuesta;

import com.aluracursos.desafio.forohub.domain.topico.Topico;
import com.aluracursos.desafio.forohub.domain.topico.TopicoRepository;
import com.aluracursos.desafio.forohub.domain.usuario.Usuario;
import com.aluracursos.desafio.forohub.domain.usuario.UsuarioRepository;
import com.aluracursos.desafio.forohub.infra.errores.TratadorDeErrores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegistrarRespuestaService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository repository;

    @Transactional
    public DatosRespuestaRespuesta agregarRespuesta(Long topicoId, DatosRegistroRespuesta datos) {
        Topico topico = topicoRepository.findByIdAndEstatusTrue(topicoId).orElseThrow(() -> new IllegalArgumentException("Ese topico no existe"));
        Usuario usuario = usuarioRepository.findByIdAndEstatusTrue(datos.idAutor()).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));;

        var respuesta = new Respuesta();
        respuesta.setMensaje(datos.mensaje());
        respuesta.setTopico(topico);
        respuesta.setFechaDeCreacion(LocalDateTime.now());
        respuesta.setAutor(usuario);
        respuesta.setSolucion(datos.solucion());
        respuesta.setEstatus(true);
        repository.save(respuesta);
        return new DatosRespuestaRespuesta(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico().getId(),
                respuesta.getFechaDeCreacion(), respuesta.getAutor().getId(), respuesta.getSolucion());
    }
}