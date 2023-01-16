package com.iunetworks.controllers.permissionassigntorole;

import com.iunetworks.dtos.permissionassigntoroledto.PermissionAssignToRoleDto;
import com.iunetworks.entities.Permission;
import com.iunetworks.permissionassigntoroleservice.PermissionAssignToRoleS;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;

@RestController
@RequestMapping("/assign-perm")

public class PermissionAssignToRoleControllerImpl {
  private final PermissionAssignToRoleS permissionAssignToRoleS;

  public PermissionAssignToRoleControllerImpl(PermissionAssignToRoleS permissionAssignToRoleS) {
    this.permissionAssignToRoleS = permissionAssignToRoleS;
  }

@PreAuthorize("hasAnyAuthority('can_assign_permissions_B')")
  @PostMapping("assign")
  public Set<Permission> privilegesAssign(@RequestBody PermissionAssignToRoleDto request) {
    return permissionAssignToRoleS.privilegesAssign(request.getRoleId(), request.getPermissionIdList());
  }
}


