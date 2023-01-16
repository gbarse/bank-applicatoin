package com.iunetworks.permissionassigntoroleservice;

import com.iunetworks.entities.Permission;

import java.util.Set;
import java.util.UUID;

public interface PermissionAssignToRoleSImpl {


  Set<Permission> privilegesAssign(UUID roleId, Set<UUID> privelegesIdList);

  void deletePermission(UUID PermissionId);
}
