package com.iunetworks.customerservice;

import com.iunetworks.dtos.customeruserdtos.CustomerUserRegistrationRequestDto;
import com.iunetworks.dtos.customeruserdtos.CustomerUserRegistrationResponseDto;
import com.iunetworks.dtos.customeruserdtos.CustomerUserUpdateDto;

import java.util.UUID;

public interface CustomerUserService  {
   CustomerUserRegistrationResponseDto addCustomerUser(CustomerUserRegistrationRequestDto customerUserRegistrationRequestDto);

   CustomerUserUpdateDto update(UUID id, CustomerUserUpdateDto customerUserUpdateDto);

}
