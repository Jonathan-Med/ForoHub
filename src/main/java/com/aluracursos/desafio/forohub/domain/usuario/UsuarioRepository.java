package com.aluracursos.desafio.forohub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreAndEstatusTrue(String nombre);
    boolean existsByNombre(String nombre);

    Optional<Usuario> findByIdAndEstatusTrue(Long id);
}
