package com.gamevault.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


//  Usuario do sistema. Implementa UserDetails para o Spring Security
//  conseguir usar esta classe direto no login.

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    // String (e nao Long) porque guardamos o HASH BCrypt, ex: "$2a$10$..."
    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING) // salva "ADMIN"/"COMUM" no banco, e nao 0/1
    @Column(nullable = false, length = 10)
    private Perfil perfil;

    // ===== Metodos exigidos pelo UserDetails =====

    // O Spring Security usa o prefixo ROLE_ para o hasRole('ADMIN') funcionar.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + perfil.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    // O "username" do nosso sistema e o email.
    @Override
    public String getUsername() {
        return email;
    }
}
