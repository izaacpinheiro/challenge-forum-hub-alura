package com.izaacpinheiro.forumhub.controller;

import com.izaacpinheiro.forumhub.domain.topico.*;
import com.izaacpinheiro.forumhub.domain.usuario.Usuario;
import com.izaacpinheiro.forumhub.repository.TopicoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> criar(@RequestBody @Valid @NotNull DadosCriacaoTopico dados, @AuthenticationPrincipal Usuario usuario, UriComponentsBuilder uriBuilder) {
        var topico = new Topico(dados, usuario);
        topicoRepository.save(topico);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopicos>> listar(@PageableDefault(size = 10, sort = {"dataCriacao"})Pageable pageable) {
        var page = topicoRepository.findAll(pageable).map(DadosListagemTopicos::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosTopicoAtualizado> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dados) {
        var topicoOpt = topicoRepository.findById(id);
        if (topicoOpt.isPresent()) {
            var topico = topicoOpt.get();
            topico.atualizarInfos(dados);
            return ResponseEntity.ok(new DadosTopicoAtualizado(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> excluir(@PathVariable Long id) {
        var topicoOpt = topicoRepository.findById(id);
        if (topicoOpt.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }



}
