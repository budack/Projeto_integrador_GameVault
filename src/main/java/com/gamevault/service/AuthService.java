package com.gamevault.service;

import com.gamevault.dto.request.LoginRequest;
import com.gamevault.dto.request.UsuarioRequest;
import com.gamevault.dto.response.LoginResponse;
import com.gamevault.exception.RegraNegocioException;
import com.gamevault.model.Perfil;
import com.gamevault.model.Usuario;
import com.gamevault.repository.UsuarioRepository;
import com.gamevault.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


//      1. O AuthenticationManager busca o usuario pelo email (UserDetailsService)
//         e compara a senha digitada com o hash BCrypt do banco.
//      2. Se estiver errado, lanca BadCredentialsException -> GlobalExceptionHandler responde 401.
//      3. Se estiver certo, gera o JWT.

    public LoginResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha()));

        Usuario usuario = (Usuario) auth.getPrincipal();
        return new LoginResponse(jwtService.gerarToken(usuario), "Bearer");
    }

    // Cadastro publico: sempre cria com perfil COMUM (ninguem se cadastra como ADMIN sozinho).
    @Transactional
    public LoginResponse registrar(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new RegraNegocioException("Email ja cadastrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        usuario.setPerfil(Perfil.COMUM);
        usuarioRepository.save(usuario);

        return new LoginResponse(jwtService.gerarToken(usuario), "Bearer");
    }
}
