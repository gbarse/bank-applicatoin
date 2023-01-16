package com.iunetworks.dtos.userdto;

import com.sun.istack.NotNull;

import java.util.UUID;

public class UserRegistrationRequestDto {

  @NotNull
  private String passportNumber;

  private UUID loggedId;


  public UserRegistrationRequestDto() {
  }

  public UUID getLoggedId() {
    return loggedId;
  }

  public void setLoggedId(UUID loggedId) {
    this.loggedId = loggedId;
  }

  public String getPassportNumber() {
    return passportNumber;
  }

  public void setPassportNumber(String passportNumber) {
    this.passportNumber = passportNumber;

  }
}


