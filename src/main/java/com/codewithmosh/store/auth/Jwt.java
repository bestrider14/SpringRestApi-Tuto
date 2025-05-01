package com.codewithmosh.store.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;

public class Jwt {

    private final Claims claims;
    private final SecretKey key;

    public Jwt(Claims claims, SecretKey key) {
        this.claims = claims;
        this.key = key;
    }

    public boolean isExpired() {
        return claims.getExpiration().before(new Date());
    }

    public String getEmail() {
        return claims.get("email", String.class);
    }

    public String getName() {
        return claims.get("name", String.class);
    }

    public Role getRole() {
        return Role.valueOf(claims.get("role", String.class));
    }

    public Long getUserId() {
        return Long.parseLong(claims.getSubject());
    }

    public String toString() {
        return Jwts.builder().claims(claims).signWith(key).compact();
    }
}
