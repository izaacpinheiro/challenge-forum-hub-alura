package com.izaacpinheiro.forumhub.repository;

import com.izaacpinheiro.forumhub.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
}
