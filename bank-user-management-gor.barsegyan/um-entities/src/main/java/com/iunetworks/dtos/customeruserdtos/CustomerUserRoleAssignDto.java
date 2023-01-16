package com.iunetworks.dtos.customeruserdtos;

import java.util.UUID;

public class CustomerUserRoleAssignDto {

  private UUID customerUserId;

  private UUID roleId;

  public CustomerUserRoleAssignDto() {
  }

  public UUID getCustomerUserId() {
    return customerUserId;
  }

  public void setCustomerUserId(UUID customerUserId) {
    this.customerUserId = customerUserId;
  }

  public UUID getRoleId() {
    return roleId;
  }

  public void setRoleId(UUID roleId) {
    this.roleId = roleId;
  }
}
