package com.iunetworks.dtos.physicalaccountdto;


import com.iunetworks.enums.Currency;

import java.util.UUID;

public class PhysicalAccountRegistrationRequestDto {

  private UUID id;

  private String ssn;

  private Currency currency;

  public PhysicalAccountRegistrationRequestDto() {
  }

  public String getSsn() {
    return ssn;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }


}
