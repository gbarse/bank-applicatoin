package com.iunetworks.permissionassigntoroleservice;

import com.iunetworks.PermissionsRepository;
import com.iunetworks.RoleRepository;
import com.iunetworks.entities.Permission;
import com.iunetworks.entities.Role;
import com.iunetworks.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class PermissionAssignToRoleS implements PermissionAssignToRoleSImpl {
  private final PermissionsRepository permissionsRepository;
  private final RoleRepository roleRepository;

  @Autowired
  public PermissionAssignToRoleS(PermissionsRepository permissionsRepository, RoleRepository roleRepository) {
    this.permissionsRepository = permissionsRepository;
    this.roleRepository = roleRepository;
  }




  @Override
  public Set<Permission> privilegesAssign(UUID roleId, Set<UUID> permissionIdList) {
    Role role= roleRepository.findByIdAndDeletedIsNull(roleId).orElseThrow(()->
      new ResourceNotFoundException("Wrong role id"));
    role.setPermission(permissionsRepository.findAllByIdInAndDeletedIsNull(permissionIdList));
    roleRepository.save(role);
    return permissionsRepository.findAllByIdInAndDeletedIsNull(permissionIdList);
  }
  @Override
  public void deletePermission(UUID PermissionId){
    Permission permission = permissionsRepository.findByIdAndDeletedIsNull(PermissionId).orElseThrow(() ->
      new ResourceNotFoundException("Wrong permission id"));
    permission.setDeleted(LocalDateTime.now());
    permissionsRepository.save(permission);
  }

}

