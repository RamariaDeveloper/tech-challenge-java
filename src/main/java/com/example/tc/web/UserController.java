package com.example.tc.web;

import com.example.tc.dto.PasswordChangeRequest;
import com.example.tc.dto.UserResponse;
import com.example.tc.dto.UserUpdateRequest;
import com.example.tc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService users;
  private final PasswordEncoder encoder;

  public UserController(UserService users, PasswordEncoder encoder) {
    this.users = users;
    this.encoder = encoder;
  }

  @GetMapping
  public ResponseEntity<List<UserResponse>> list() {
    return ResponseEntity.ok(users.list());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> get(@PathVariable UUID id) {
    return ResponseEntity.ok(users.get(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(@PathVariable UUID id, @RequestBody @Valid UserUpdateRequest req) {
    return ResponseEntity.ok(users.update(id, req));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    users.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/change-password")
  public ResponseEntity<Void> changePassword(@RequestBody @Valid PasswordChangeRequest req, Authentication auth) {
    String login = ((UserDetails) auth.getPrincipal()).getUsername();
    users.changePassword(login, req.getOldPassword(), req.getNewPassword());
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<UserResponse> create(@RequestBody @Valid com.example.tc.dto.UserCreateRequest req) {
    return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(users.create(req));
  }

  private UserUpdateRequest toUpdate(com.example.tc.domain.User u) {
    var r = new UserUpdateRequest();
    r.setName(u.getName());
    r.setEmail(u.getEmail());
    r.setAddress(u.getAddress());
    return r;
  }
}
