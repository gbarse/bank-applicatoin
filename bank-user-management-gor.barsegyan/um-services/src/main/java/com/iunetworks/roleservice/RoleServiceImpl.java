package com.iunetworks.roleservice;

import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.RoleRepository;
import com.iunetworks.dtos.roledtos.RoleDto;
import com.iunetworks.dtos.roledtos.RoleResponseDto;
import com.iunetworks.entities.Role;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class RoleServiceImpl implements RoleService {


  private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

  private final RoleRepository roleRepository;
  private final ModelMapper modelMapper;


  public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
    this.roleRepository = roleRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public RoleResponseDto createRole(RoleDto roleDto) {
    Optional<Role> roleName = roleRepository.findByName(roleDto.getName());
    log.info("The bank user has tried to create a role");
    if (roleName.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "role already exists!");
    }
    return modelMapper.map(roleRepository.save(modelMapper.map(roleDto, Role.class)), RoleResponseDto.class);
  }


  @Override
  public void deleteRole(UUID id) {

    Role role = roleRepository.findById(id).get();
    if (role.getDeleted() != null) {
      throw new ResourceNotFoundException(id);
    }
    role.setDeleted(LocalDateTime.now());
    roleRepository.save(role);
  }

  @Override
  public void recoverRole(UUID id) {
    Role role = roleRepository.findById(id).get();
    log.info("Bank user is trying to recover another bank user");
    if (role.getDeleted() == null) {
      throw new ResourceNotFoundException(id);
    }
    role.setDeleted(null);
    roleRepository.save(role);
  }

  @Override
  public List<RoleResponseDto> getAllRoles() {
    List<Role> all = roleRepository.findAll();
    List<RoleResponseDto> allRolesDto = new ArrayList<>();
    for (Role role :
      all) {
      RoleResponseDto roleDto = modelMapper.map(role, RoleResponseDto.class);
      allRolesDto.add(roleDto);
    }
    return allRolesDto;

  }

  @Override
  public RoleDto updateRole(UUID id, RoleDto roleDto) {
    log.info("Bank User has tried to update information about the roles");
    if (!roleRepository.existsByIdAndDeletedIsNull(id)) {
      throw new ResourceNotFoundException(id);
    }
    Role role = roleRepository.findById(id).get();
    Optional.ofNullable(roleDto.getName()).ifPresent(role::setName);
    roleRepository.save(role);
    log.info("saved");
    return modelMapper.map(role, RoleDto.class);
  }



}
