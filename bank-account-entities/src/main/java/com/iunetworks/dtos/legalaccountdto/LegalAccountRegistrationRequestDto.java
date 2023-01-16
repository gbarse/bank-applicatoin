package com.iunetworks.dtos.legalaccountdto;

import com.iunetworks.enums.CompanyType;
import com.iunetworks.enums.Currency;
import com.sun.istack.NotNull;

import java.util.UUID;

public class LegalAccountRegistrationRequestDto {


  @NotNull
  private String companyName;
  @NotNull
  private CompanyType companyType;
  @NotNull
  private Currency currency;

  private String ssh;

  private UUID id;


  public LegalAccountRegistrationRequestDto() {
  }

  public UUID getId() {
    return id;
  }

  public String getSsh() {
    return ssh;
  }

  public void setSsh(String ssh) {
    this.ssh = ssh;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public CompanyType getCompanyType() {
    return companyType;
  }

  public void setCompanyType(CompanyType companyType) {
    this.companyType = companyType;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }


}
