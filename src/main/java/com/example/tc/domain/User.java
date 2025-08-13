package com.example.tc.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements org.springframework.security.core.userdetails.UserDetails {

  @Id
  @GeneratedValue
  private UUID id;

  @NotBlank
  private String name;

  @Email
  @Column(unique = true, nullable = false)
  private String email;

  @Column(unique = true, nullable = false)
  private String login;

  @Column(nullable = false)
  private String password;

  private Instant updatedAt;

  @Embedded
  private Address address;

  private String role = "USER"; // simples: USER/ADMIN

  @PrePersist @PreUpdate
  public void touch() {
    this.updatedAt = Instant.now();
  }

  // getters/setters
  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getLogin() { return login; }
  public void setLogin(String login) { this.login = login; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
  public Instant getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
  public Address getAddress() { return address; }
  public void setAddress(Address address) { this.address = address; }
  public String getRole() { return role; }
  public void setRole(String role) { this.role = role; }

  @Override public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
    return java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + role));
  }
  @Override public String getUsername() { return login; }
  @Override public boolean isAccountNonExpired() { return true; }
  @Override public boolean isAccountNonLocked() { return true; }
  @Override public boolean isCredentialsNonExpired() { return true; }
  @Override public boolean isEnabled() { return true; }
}

