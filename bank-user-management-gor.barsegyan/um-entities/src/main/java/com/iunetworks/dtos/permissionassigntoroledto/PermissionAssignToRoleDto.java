package com.iunetworks.dtos.permissionassigntoroledto;

import java.util.Set;
import java.util.UUID;

public class PermissionAssignToRoleDto {

  private UUID roleId;
  private Set<UUID> permissionIdList;

  public PermissionAssignToRoleDto() {
  }

  public UUID getRoleId() {
    return roleId;
  }

  public void setRoleId(UUID roleId) {
    this.roleId = roleId;
  }

  public Set<UUID> getPermissionIdList() {
    return permissionIdList;
  }

  public void setPermissionIdList(Set<UUID> permissionIdList) {
    this.permissionIdList = permissionIdList;
  }
}
