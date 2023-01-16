package com.iunetworks.controllers.permissionscontroller;

import com.iunetworks.dtos.permissionsdto.PermissionsDto;
import com.iunetworks.permissionservice.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/permissions")

public class PermissionController {
  private final PermissionsService permissionsService;

  @Autowired

  public PermissionController(PermissionsService permissionsService) {
    this.permissionsService = permissionsService;

  }

  // AUTHORIZATION
  @GetMapping("/{id}")
  public PermissionsDto getById(@PathVariable("id") UUID id) {
    return permissionsService.getById(id);

  }

  // AUTHORIZATION
  @GetMapping
  public List<PermissionsDto> getAll() {
    return permissionsService.getAll();
  }
}

