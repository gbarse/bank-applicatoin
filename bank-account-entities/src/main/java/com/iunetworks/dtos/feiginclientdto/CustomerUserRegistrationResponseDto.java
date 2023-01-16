package com.iunetworks.dtos.feiginclientdto;

import com.sun.istack.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerUserRegistrationResponseDto {

  private UUID id;
  @NotNull
  private String lastName;
  @NotNull
  private String email;

  @NotNull
  private String firstName;
  @NotNull

  private String address;
  @NotNull

  private String phoneNumber;

  @NotNull
  private String city;

  @NotNull
  private LocalDate birthDate;

  @NotNull
  private LocalDateTime created;

  @NotNull
  private LocalDateTime updated;


  private LocalDateTime deleted;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }



  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updated = updated;
  }

  public LocalDateTime getDeleted() {
    return deleted;
  }

  public void setDeleted(LocalDateTime deleted) {
    this.deleted = deleted;
  }
}



