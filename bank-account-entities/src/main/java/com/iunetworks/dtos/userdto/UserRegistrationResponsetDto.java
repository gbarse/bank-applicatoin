package com.iunetworks.dtos.userdto;

import com.iunetworks.entities.LegalAccount;
import com.iunetworks.entities.PhysicalAccount;
import com.sun.istack.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserRegistrationResponsetDto {


  private String passportNumber;


  protected LocalDateTime created;


  protected LocalDateTime updated;


  private UUID loggedId;


  private UUID id;




  protected LocalDateTime deleted;

  public UserRegistrationResponsetDto() {
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

  public String getPassportNumber() {
    return passportNumber;
  }

  public void setPassportNumber(String passportNumber) {
    this.passportNumber = passportNumber;
  }

  public UUID getLoggedId() {
    return loggedId;
  }

  public void setLoggedId(UUID loggedId) {
    this.loggedId = loggedId;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
