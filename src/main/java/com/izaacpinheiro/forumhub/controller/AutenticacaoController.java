package com.izaacpinheiro.forumhub.controller;

import com.izaacpinheiro.forumhub.domain.usuario.DadosAutenticacao;
import com.izaacpinheiro.forumhub.domain.usuario.RegistroDTO;
import com.izaacpinheiro.forumhub.domain.usuario.Usuario;
import com.izaacpinheiro.forumhub.infra.security.TokenJWTResponseDTO;
import com.izaacpinheiro.forumhub.infra.security.TokenService;
import com.izaacpinheiro.forumhub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid DadosAutenticacao dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var auth = authenticationManager.authenticate(authToken);
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new TokenJWTResponseDTO(token));
    }

    @PostMapping("/registro")
    public ResponseEntity registro(@RequestBody @Valid RegistroDTO dados) {
        if (this.usuarioRepository.findByEmail(dados.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.senha());
        var novoUsuario = new Usuario(dados.nome(), dados.email(), senhaCriptografada);
        this.usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }
}
