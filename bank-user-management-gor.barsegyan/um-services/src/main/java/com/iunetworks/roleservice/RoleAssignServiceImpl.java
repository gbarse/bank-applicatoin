package com.iunetworks.roleservice;

import com.iunetworks.dtos.roledtos.RoleAssignDto;

public interface RoleAssignServiceImpl {
  public String roleAssignToBankUser(RoleAssignDto roleAssignDto);
  public String roleAssignToCustomer(RoleAssignDto roleAssignDto);
}
