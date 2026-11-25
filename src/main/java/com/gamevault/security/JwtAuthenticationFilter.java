package com.gamevault.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


//  Roda UMA vez em toda requisicao, antes dos controllers.
//
//  1. Procura o header  Authorization: Bearer <token>
//  2. Se tiver e for valido, avisa o Spring Security quem e o usuario (e o perfil dele).
//  3. Se nao tiver ou for invalido, so deixa passar sem autenticar:
//     o SecurityConfig e quem decide responder 401 para rotas protegidas.

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIXO = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith(PREFIXO)
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            String token = header.substring(PREFIXO.length());
            try {
                String email = jwtService.extrairEmail(token);
                UserDetails usuario = userDetailsService.loadUserByUsername(email);

                if (jwtService.tokenValido(token, usuario)) {
                    var autenticacao = new UsernamePasswordAuthenticationToken(
                            usuario, null, usuario.getAuthorities()); // authorities = ROLE_ADMIN / ROLE_COMUM
                    autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(autenticacao);
                }
            } catch (JwtException | IllegalArgumentException | UsernameNotFoundException e) {
                // token expirado, adulterado ou de usuario que nao existe mais: segue sem autenticar
            }
        }

        filterChain.doFilter(request, response);
    }
}
