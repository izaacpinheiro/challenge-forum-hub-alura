package com.izaacpinheiro.forumhub.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DadosCriacaoTopico(
        @NotBlank String titulo,
        @NotBlank String mensagem,
        @NotBlank String curso
) {
}
