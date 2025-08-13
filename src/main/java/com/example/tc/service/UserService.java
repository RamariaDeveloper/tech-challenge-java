package com.example.tc.service;

import com.example.tc.domain.User;
import com.example.tc.dto.UserCreateRequest;
import com.example.tc.dto.UserResponse;
import com.example.tc.dto.UserUpdateRequest;
import com.example.tc.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
  private final UserRepository repo;
  private final PasswordEncoder encoder;

  public UserService(UserRepository repo, PasswordEncoder encoder) {
    this.repo = repo;
    this.encoder = encoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repo.findByLogin(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
  }

  @Transactional
  public UserResponse create(UserCreateRequest req) {
    if (repo.existsByEmail(req.getEmail())) throw new IllegalArgumentException("Email já cadastrado");
    if (repo.existsByLogin(req.getLogin())) throw new IllegalArgumentException("Login já cadastrado");

    User u = new User();
    u.setName(req.getName());
    u.setEmail(req.getEmail());
    u.setLogin(req.getLogin());
    u.setPassword(encoder.encode(req.getPassword()));
    u.setAddress(req.getAddress());
    u = repo.save(u);
    return toDto(u);
  }

  @Transactional(readOnly = true)
  public List<UserResponse> list() {
    return repo.findAll().stream().map(this::toDto).toList();
  }

  @Transactional(readOnly = true)
  public UserResponse get(UUID id) {
    return repo.findById(id).map(this::toDto).orElseThrow();
  }

  @Transactional
  public UserResponse update(UUID id, UserUpdateRequest req) {
    User u = repo.findById(id).orElseThrow();
    u.setName(req.getName());
    u.setEmail(req.getEmail());
    u.setAddress(req.getAddress());
    return toDto(repo.save(u));
  }

  @Transactional
  public void delete(UUID id) {
    repo.deleteById(id);
  }

  @Transactional
  public void changePassword(String login, String oldPassword, String newPassword) {
  var user = repo.findByLogin(login).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    if (!encoder.matches(oldPassword, user.getPassword())) {
    throw new IllegalArgumentException("Senha atual incorreta");
    }
  user.setPassword(encoder.encode(newPassword));
  repo.save(user);
}


  private UserResponse toDto(User u) {
    UserResponse dto = new UserResponse();
    dto.setId(u.getId());
    dto.setName(u.getName());
    dto.setEmail(u.getEmail());
    dto.setLogin(u.getLogin());
    dto.setUpdatedAt(u.getUpdatedAt());
    dto.setAddress(u.getAddress());
    return dto;
  }
}
