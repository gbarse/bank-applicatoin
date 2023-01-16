package com.iunetworks.dtos.customeruserdtos;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CustomerUserRegistrationRequestDto {
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
  private String password;
  @NotNull
  private String matchingPassword;


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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMatchingPassword() {
    return matchingPassword;
  }

  public void setMatchingPassword(String matchingPassword) {
    this.matchingPassword = matchingPassword;
  }

  public CustomerUserRegistrationRequestDto(String lastName, String email, String firstName, String address, String phoneNumber, String city, LocalDate birthDate, LocalDateTime created, LocalDateTime updated, LocalDateTime deleted, String password, String matchingPassword) {

    this.lastName = lastName;
    this.email = email;
    this.firstName = firstName;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.city = city;
    this.birthDate = birthDate;
    this.password = password;
    this.matchingPassword = matchingPassword;
  }
}





