package com.example.tc.web;

import com.example.tc.dto.LoginRequest;
import com.example.tc.dto.LoginResponse;
import com.example.tc.dto.UserCreateRequest;
import com.example.tc.dto.UserResponse;
import com.example.tc.service.JwtService;
import com.example.tc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthenticationManager authManager;
  private final JwtService jwt;
  private final UserService users;

  public AuthController(AuthenticationManager authManager, JwtService jwt, UserService users) {
    this.authManager = authManager;
    this.jwt = jwt;
    this.users = users;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest req) {
    try {
      authManager.authenticate(
          new UsernamePasswordAuthenticationToken(req.getLogin(), req.getPassword()));
      String token = jwt.generateToken(req.getLogin());
      return ResponseEntity.ok(new LoginResponse(token));
    } catch (org.springframework.security.core.AuthenticationException ex) {
      return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
    }
  }

  @PostMapping("/register")
  public ResponseEntity<UserResponse> register(@RequestBody @Valid UserCreateRequest req) {
    return ResponseEntity.ok(users.create(req));
  }
}
