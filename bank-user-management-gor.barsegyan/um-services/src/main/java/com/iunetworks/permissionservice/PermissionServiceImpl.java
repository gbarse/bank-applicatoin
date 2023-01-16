package com.iunetworks.permissionservice;

import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.PermissionsRepository;
import com.iunetworks.dtos.permissionsdto.PermissionsDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionsService{

  final Logger log =  LoggerFactory.getLogger(PermissionServiceImpl.class);

  private final PermissionsRepository permissionsRepository;
  private final ModelMapper modelMapper;

  public PermissionServiceImpl(PermissionsRepository permissionsRepository, ModelMapper modelMapper) {
    this.permissionsRepository = permissionsRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<PermissionsDto> getAll() {
    log.info("User has tried to get all the permissions");
    return permissionsRepository.findAll().stream()
      .map(prm -> modelMapper.map(prm, PermissionsDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public PermissionsDto getById(final UUID id) {
    log.info("User has tried to get a permission by id");
    return modelMapper
      .map((permissionsRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id))), PermissionsDto.class);
  }
}


