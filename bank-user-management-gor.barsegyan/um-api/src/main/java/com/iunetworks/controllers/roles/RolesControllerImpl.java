package com.iunetworks.controllers.roles;

import com.iunetworks.dtos.roledtos.RoleDto;
import com.iunetworks.dtos.roledtos.RoleResponseDto;
import com.iunetworks.roleservice.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")

public class RolesControllerImpl {

  private final RoleServiceImpl roleServiceImpl;

  @Autowired
  public RolesControllerImpl(RoleServiceImpl roleServiceImpl) {
    this.roleServiceImpl = roleServiceImpl;

  }

    @PreAuthorize("hasAnyAuthority('can_create_roles_B')")
    @PostMapping("/create")
  public RoleResponseDto createRole(@RequestBody RoleDto roleDto) {
    return roleServiceImpl.createRole(roleDto);
  }

  @PreAuthorize("hasAnyAuthority('can_delete_roles_B')")
  @DeleteMapping("/{id}")
  public void deleteRole(@PathVariable UUID id) {
    roleServiceImpl.deleteRole(id);
  }

  @PreAuthorize("hasAnyAuthority('can_recover_roles_B')")
  @PostMapping("/{id}")
  public void recoverRole(@PathVariable UUID id) {
    roleServiceImpl.recoverRole(id);
  }

  @PreAuthorize("hasAnyAuthority('can_view_roles_B')")
  @GetMapping("/view-all")
  public List<RoleResponseDto> getAll() {
    return roleServiceImpl.getAllRoles();
  }

  @PreAuthorize("hasAnyAuthority('can_update_roles_B')")
  @PatchMapping("/{id}")
  public RoleDto updateRole(@PathVariable(name = "id") UUID id,
                            @RequestBody RoleDto roleDto) {
    return roleServiceImpl.updateRole(id, roleDto);
  }
}
