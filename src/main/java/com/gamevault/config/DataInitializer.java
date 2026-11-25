package com.gamevault.config;

import com.gamevault.model.Perfil;
import com.gamevault.model.Usuario;
import com.gamevault.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Roda uma vez quando a aplicacao sobe e cria dois usuarios de teste
 * (se ainda nao existirem), para conseguir testar o login no Postman:
 *
 *   admin@gamevault.com / 123456  -> perfil ADMIN
 *   comum@gamevault.com / 123456  -> perfil COMUM
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        criarSeNaoExistir("Administrador", "admin@gamevault.com", "123456", Perfil.ADMIN);
        criarSeNaoExistir("Usuario Comum", "comum@gamevault.com", "123456", Perfil.COMUM);
    }

    private void criarSeNaoExistir(String nome, String email, String senha, Perfil perfil) {
        if (usuarioRepository.existsByEmail(email)) {
            return;
        }
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha)); // senha salva criptografada (BCrypt)
        usuario.setPerfil(perfil);
        usuarioRepository.save(usuario);
    }
}
