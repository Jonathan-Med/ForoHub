package com.aluracursos.desafio.forohub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByEstatusTrue(Pageable paginacion);

    Optional<Topico> findByIdAndEstatusTrue(Long id);
}
