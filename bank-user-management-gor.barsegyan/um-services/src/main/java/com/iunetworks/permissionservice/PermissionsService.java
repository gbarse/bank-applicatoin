package com.iunetworks.permissionservice;

import com.iunetworks.dtos.permissionsdto.PermissionsDto;

import java.util.List;
import java.util.UUID;

public interface PermissionsService {

  public List<PermissionsDto> getAll();
  public PermissionsDto getById(final UUID id);

}
