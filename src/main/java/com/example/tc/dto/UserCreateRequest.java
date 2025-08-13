package com.example.tc.dto;

import com.example.tc.domain.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserCreateRequest {
  @NotBlank private String name;
  @Email @NotBlank private String email;
  @NotBlank private String login;
  @NotBlank private String password;
  private Address address;

  // getters/setters
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getLogin() { return login; }
  public void setLogin(String login) { this.login = login; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
  public Address getAddress() { return address; }
  public void setAddress(Address address) { this.address = address; }
}
