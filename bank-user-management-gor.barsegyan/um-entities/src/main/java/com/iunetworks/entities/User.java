package com.iunetworks.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class User {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")

  @Column(name = "id", updatable = false, nullable = false)
  protected UUID id;

  @Column(name = "lastName", nullable = false)
  protected String lastName;

  @Column(unique = true, name = "email", nullable = false)
  protected String email;

  @Column(name = "firstName", nullable = false)
  protected String firstName;

  @Column(name = "address", nullable = false)
  protected String address;

  @Column(name = "phone_number", nullable = false)
  protected String phoneNumber;

  @Column(name = "city", nullable = false)
  protected String city;


  @Column(name = "birthDate", nullable = false)
  @JsonFormat(pattern = "dd-MM-yyyy")
  protected LocalDate birthDate;

  @Column(name = "created", nullable = false)
  protected LocalDateTime created;

  @Column(name = "updated", nullable = false)
  protected LocalDateTime updated;

  @Column(name = "deleted", nullable = false)

  protected LocalDateTime deleted;

  @Column(name = "password", nullable = false)
  protected String password;


  public User(String lastName, String email, String firstName, String address, String phoneNumber, String city, LocalDate birthDate, String password) {

    this.lastName = lastName;
    this.email = email;
    this.firstName = firstName;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.city = city;
    this.birthDate = birthDate;
    this.password = password;
  }


  public User() {

  }


  @PrePersist
  protected void onCreate() {
    this.created = LocalDateTime.now();
    this.updated = this.created;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updated = LocalDateTime.now();
  }

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
