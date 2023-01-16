package com.iunetworks.customeruserbankservice;

import com.iunetworks.dtos.customeruserdtos.CustomerUserRegistrationResponseDto;
import com.iunetworks.dtos.customeruserdtos.CustomerUserRoleAssignDto;

import java.util.List;
import java.util.UUID;

public interface CustomerUserBankService {

  public void deleteCustomerUser(UUID id);
  public void recoverCustomerUser(UUID id);
  public List<CustomerUserRegistrationResponseDto> getAllCustomerUsers();
  public CustomerUserRegistrationResponseDto getCustomerById(UUID id);


//  CustomerUserRoleAssignDto assignCustomerRole(CustomerUserRoleAssignDto dto);
}
