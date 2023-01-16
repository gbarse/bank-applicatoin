package com.iunetworks.dtos.legalaccountdto;

import com.iunetworks.dtos.feiginclientdto.AccountRegistrationResponseDto;
import com.iunetworks.enums.CompanyType;

public class LegalAccountRegistrationResponseDto extends AccountRegistrationResponseDto {

  private String companyName;

  private CompanyType companyType;


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

}


