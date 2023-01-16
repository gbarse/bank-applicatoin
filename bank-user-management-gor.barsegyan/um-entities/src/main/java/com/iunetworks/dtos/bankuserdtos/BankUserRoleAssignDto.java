package com.iunetworks.dtos.bankuserdtos;

import java.util.UUID;

public class BankUserRoleAssignDto {

  private UUID bankUserId;

  private UUID roleId;

  public UUID getRoleId() {
    return roleId;
  }

  public void setRoleId(UUID roleId) {
    this.roleId = roleId;
  }

  public BankUserRoleAssignDto() {
  }

  public UUID getBankUserId() {
    return bankUserId;
  }



  public void setBankUserId(UUID bankUserId) {
    this.bankUserId = bankUserId;
  }


}
