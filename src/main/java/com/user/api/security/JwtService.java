package com.user.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtService {

    private final SecretKey signingKey;

    public JwtService(
            @Value("${security.jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String gerarToken(UsuarioAutenticado userDetails) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + 1000 * 60 * 15);

        return Jwts.builder()
                .subject(userDetails.getId().toString())
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(signingKey)
                .compact();
    }

    public Integer extrairUsuarioId(String token) {
        return Integer.valueOf(extrairClaims(token).getSubject());
    }

    public boolean tokenValido(
            String token, UsuarioAutenticado userDetails) {

        Integer id = extrairUsuarioId(token);

        return id.equals(userDetails.getId())
                && extrairClaims(token).getExpiration().after(new Date());
    }

    private Claims extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
