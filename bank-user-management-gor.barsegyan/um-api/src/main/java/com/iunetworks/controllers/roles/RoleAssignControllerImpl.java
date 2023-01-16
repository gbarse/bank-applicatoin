package com.iunetworks.controllers.roles;

import com.iunetworks.dtos.roledtos.RoleAssignDto;
import com.iunetworks.roleservice.RoleAssignService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role-assign")
public class RoleAssignControllerImpl {

  private final RoleAssignService roleAssignService;

  public RoleAssignControllerImpl(RoleAssignService roleAssignService) {
    this.roleAssignService = roleAssignService;
  }


  @PreAuthorize("hasAnyAuthority('can_assign_role_B')")
  @PostMapping("/banker")
  public String roleAssignBankUser(@RequestBody RoleAssignDto roleAssignDto) {
    return roleAssignService.roleAssignToBankUser(roleAssignDto);
  }
  @PreAuthorize("hasAnyAuthority('can_assign_role_B')")
  @PostMapping("/customer")
  public String roleAssignToCustomer(@RequestBody RoleAssignDto roleAssignDto) {
    return roleAssignService.roleAssignToCustomer(roleAssignDto);
  }
}





