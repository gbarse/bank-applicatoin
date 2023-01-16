package com.iunetworks.customeruserbankservice;

import com.iunetworks.RoleRepository;
import com.iunetworks.customerservice.CustomerUserServiceImpl;
import com.iunetworks.CustomerUserRepository;
import com.iunetworks.dtos.customeruserdtos.CustomerUserRoleAssignDto;
import com.iunetworks.entities.Role;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.dtos.customeruserdtos.CustomerUserRegistrationResponseDto;
import com.iunetworks.entities.CustomerUser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


  @Service
public class CustomerUserBankServiceImpl implements CustomerUserBankService {

  private final ModelMapper modelMapper;

  private final CustomerUserRepository customerUserRepository;

  private static final Logger log = LoggerFactory.getLogger(CustomerUserServiceImpl.class);

  private final RoleRepository roleRepository;

  public CustomerUserBankServiceImpl(ModelMapper modelMapper,
                                     CustomerUserRepository customerUserRepository, RoleRepository roleRepository) {
    this.modelMapper = modelMapper;
    this.customerUserRepository = customerUserRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public void deleteCustomerUser(UUID id) {

    CustomerUser user = customerUserRepository.findById(id).get();
    log.info("The customer has been found by ID");
    if (user.getDeleted() != null) {
      throw new ResourceNotFoundException(id);
    }
    user.setDeleted(LocalDateTime.now());
    customerUserRepository.save(user);
  }

  @Override
  public void recoverCustomerUser(UUID id){
    CustomerUser user = customerUserRepository.findById(id).get();
    if (user.getDeleted() == null){
      throw new ResourceNotFoundException(id);
    }
    user.setDeleted(null);
    customerUserRepository.save(user);
  }

  @Override
  public List<CustomerUserRegistrationResponseDto> getAllCustomerUsers() {
    List<CustomerUser> all = customerUserRepository.findAllByDeletedIsNull();
    log.info("The list with customer users has been created");
    List<CustomerUserRegistrationResponseDto> allUserDto = new ArrayList<>();
    for (CustomerUser user :
      all){
      CustomerUserRegistrationResponseDto userDto = modelMapper.map(user,CustomerUserRegistrationResponseDto.class);
      allUserDto.add(userDto);
    }
    return allUserDto;

  }

  @Override
  public CustomerUserRegistrationResponseDto getCustomerById(UUID id) {
    log.info("Bank User has tried to get an information about a customer");
    if (!customerUserRepository.existsByIdAndDeletedIsNull(id)) {
      throw new ResourceNotFoundException(id);
    }
    return modelMapper
      .map((customerUserRepository.findByIdAndDeletedIsNull(id)
        .orElseThrow(() -> new ResourceNotFoundException(id))), CustomerUserRegistrationResponseDto.class);
  }
//    @Override
//    public CustomerUserRoleAssignDto assignCustomerRole(CustomerUserRoleAssignDto dto) {
//      CustomerUser user = customerUserRepository.findById(dto.getCustomerUserId()).
//        orElseThrow(() -> new ResourceNotFoundException("Wrong  user id."));
//      Role role = roleRepository.findById(dto.getRoleId()).
//        orElseThrow(() -> new ResourceNotFoundException("Wrong  user role"));
//      user.setRoles(Set.of(role));
//      return dto;
//    }


}
