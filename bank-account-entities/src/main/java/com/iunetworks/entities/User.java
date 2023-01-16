package com.iunetworks.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_users")

public class User {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")

  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "logged_id")
  private UUID loggedId;

  @Column(name = "passport_number", nullable = false)
  private String passportNumber;

  @Column(name = "created", nullable = false)
  private LocalDateTime created;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  @Column(name = "deleted")
  private LocalDateTime deleted;

  public User() {
  }

  public User(UUID id, UUID loggedId, String passportNumber, LocalDateTime created, LocalDateTime updated,
              LocalDateTime deleted) {
    this.id = id;
    this.loggedId = loggedId;
    this.passportNumber = passportNumber;
    this.created = created;
    this.updated = updated;
    this.deleted = deleted;

  }

  @PrePersist
  private void onCreate() {
    this.created = LocalDateTime.now();
    this.updated = this.created;
  }

  @PreUpdate
  private void onUpdate() {
    this.updated = LocalDateTime.now();
  }

  public UUID getId() {
    return id;
  }


  public void setId(UUID id) {
    this.id = id;
  }

  public String getPassportNumber() {
    return passportNumber;
  }

  public void setPassportNumber(String passportNumber) {
    this.passportNumber = passportNumber;
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

  public UUID getLoggedId() {
    return loggedId;
  }

  public void setLoggedId(UUID loggedId) {
    this.loggedId = loggedId;
  }
}
