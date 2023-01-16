package com.iunetworks.roleservice;

import com.iunetworks.dtos.roledtos.RoleDto;
import com.iunetworks.dtos.roledtos.RoleResponseDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    RoleResponseDto createRole(RoleDto roleDto);

  void deleteRole(UUID id);

  void recoverRole(UUID id);

  List<RoleResponseDto> getAllRoles();

  RoleDto updateRole(UUID id, RoleDto roleDto);
}
