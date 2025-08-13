package com.example.tc.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

  private static final Logger log = LoggerFactory.getLogger(JwtService.class);

  @Value("${app.jwt.secret}")
  private String secret;

  @Value("${app.jwt.exp-minutes}")
  private long expMinutes;

  private SecretKey key;

  @PostConstruct
  void initKey() {
    byte[] bytes;
    try {
      bytes = Decoders.BASE64.decode(secret);
    } catch (IllegalArgumentException e) {
      bytes = secret.getBytes(StandardCharsets.UTF_8);
    }
    if (bytes.length < 32) {
      throw new IllegalArgumentException("JWT secret precisa ter >= 256 bits (32 bytes).");
    }
    this.key = Keys.hmacShaKeyFor(bytes);
  }

  public String generateToken(String username) {
    Instant now = Instant.now();
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(now.plus(expMinutes, ChronoUnit.MINUTES)))
        .signWith(key)
        .compact();
  }

  public String extractUsername(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }

  public OncePerRequestFilter jwtFilter(UserDetailsService uds) {
    return new OncePerRequestFilter() {
      @Override
      protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
          String token = auth.substring(7);
          try {
            String username = extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
              UserDetails userDetails = uds.loadUserByUsername(username);
              var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
              authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authToken);
            }
          } catch (Exception e) {
            log.debug("JWT inválido: {}", e.getMessage());
          }
        }
        filterChain.doFilter(request, response);
      }
    };
  }
}
