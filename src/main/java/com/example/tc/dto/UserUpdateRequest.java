package com.example.tc.dto;

import com.example.tc.domain.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserUpdateRequest {
  @NotBlank private String name;
  @Email @NotBlank private String email;
  private Address address;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public Address getAddress() { return address; }
  public void setAddress(Address address) { this.address = address; }
}
