package com.gamevault.repository;

import com.gamevault.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// findByEmail e existsByEmail sao usados pelo login/JWT (parte do Danilo).
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
