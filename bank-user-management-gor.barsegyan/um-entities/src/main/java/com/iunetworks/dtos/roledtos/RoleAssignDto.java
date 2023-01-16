package com.iunetworks.dtos.roledtos;

import java.util.Collection;
import java.util.UUID;

public class RoleAssignDto {
  private UUID userId;
  private Collection<UUID> roleId;

  public RoleAssignDto() {
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public Collection<UUID> getRoleId() {
    return roleId;
  }

  public void setRoleId(Collection<UUID> roleId) {
    this.roleId = roleId;
  }
}



