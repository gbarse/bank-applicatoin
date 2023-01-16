package com.iunetworks.dtos.physicalaccountdto;


import com.iunetworks.dtos.feiginclientdto.AccountRegistrationResponseDto;

public class PhysicalAccountRegistrationResponseDto extends AccountRegistrationResponseDto {

  private String ssn;

  public String getSsn() {
    return ssn;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }
}
