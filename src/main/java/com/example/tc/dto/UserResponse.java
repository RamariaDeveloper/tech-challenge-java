package com.example.tc.dto;

import com.example.tc.domain.Address;
import java.time.Instant;
import java.util.UUID;

public class UserResponse {
  private UUID id;
  private String name;
  private String email;
  private String login;
  private Instant updatedAt;
  private Address address;

  // getters/setters
  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getLogin() { return login; }
  public void setLogin(String login) { this.login = login; }
  public Instant getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
  public Address getAddress() { return address; }
  public void setAddress(Address address) { this.address = address; }
}
