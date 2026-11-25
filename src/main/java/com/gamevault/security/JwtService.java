package com.gamevault.security;

import com.gamevault.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


//  Gera e valida tokens JWT.
//
//  Um JWT tem 3 partes separadas por ponto: HEADER.PAYLOAD.ASSINATURA
//  - payload: dados (quem e o usuario, perfil, quando expira)
//  - assinatura: feita com a nossa chave secreta. Se alguem alterar o payload,
//   a assinatura deixa de bater e o token e rejeitado.

@Service
public class JwtService {

    private final SecretKey chave;
    private final long expiracaoMs;

    public JwtService(@Value("${jwt.secret}") String segredoBase64,
                      @Value("${jwt.expiration}") long expiracaoMs) {
        this.chave = Keys.hmacShaKeyFor(Decoders.BASE64.decode(segredoBase64));
        this.expiracaoMs = expiracaoMs;
    }

    // Gera o token. subject = email do usuario, e um claim extra com o perfil.
    public String gerarToken(Usuario usuario) {
        Date agora = new Date();
        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("perfil", usuario.getPerfil().name())
                .issuedAt(agora)
                .expiration(new Date(agora.getTime() + expiracaoMs))
                .signWith(chave)
                .compact();
    }

    // Le o email (subject) de dentro do token. Lanca JwtException se o token for invalido.
    public String extrairEmail(String token) {
        return extrairClaims(token).getSubject();
    }

//     Token e valido se pertence a esse usuario e nao expirou.
    public boolean tokenValido(String token, UserDetails usuario) {
        Claims claims = extrairClaims(token);
        return claims.getSubject().equals(usuario.getUsername())
                && claims.getExpiration().after(new Date());
    }

//    Confere a assinatura e devolve o conteudo do token.
    private Claims extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(chave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
