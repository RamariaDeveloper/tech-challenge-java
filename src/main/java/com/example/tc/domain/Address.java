package com.example.tc.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Address {
  @NotBlank
  private String street;
  private String number;
  @NotBlank
  private String city;
  @NotBlank
  private String state;
  @NotBlank
  private String zip;
  private String complement;

  // getters/setters
  public String getStreet() { return street; }
  public void setStreet(String street) { this.street = street; }
  public String getNumber() { return number; }
  public void setNumber(String number) { this.number = number; }
  public String getCity() { return city; }
  public void setCity(String city) { this.city = city; }
  public String getState() { return state; }
  public void setState(String state) { this.state = state; }
  public String getZip() { return zip; }
  public void setZip(String zip) { this.zip = zip; }
  public String getComplement() { return complement; }
  public void setComplement(String complement) { this.complement = complement; }
}
