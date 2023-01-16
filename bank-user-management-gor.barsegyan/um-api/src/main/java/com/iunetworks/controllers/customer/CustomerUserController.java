package com.iunetworks.controllers.customer;

import com.iunetworks.customerservice.CustomerUserServiceImpl;
import com.iunetworks.dtos.customeruserdtos.CustomerUserRegistrationRequestDto;
import com.iunetworks.dtos.customeruserdtos.CustomerUserRegistrationResponseDto;
import com.iunetworks.dtos.customeruserdtos.CustomerUserUpdateDto;
import com.iunetworks.dtos.logindto.LoginRequest;
import com.iunetworks.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CustomerUserController {
  private final CustomerUserServiceImpl customerUserServiceImpl;

  @Autowired
  public CustomerUserController(CustomerUserServiceImpl customerUserServiceImpl) {
    this.customerUserServiceImpl = customerUserServiceImpl;
  }

  @PostMapping("/create")

  public CustomerUserRegistrationResponseDto createUser(@RequestBody @Valid CustomerUserRegistrationRequestDto customerUserRegistrationRequestDto) {
    return customerUserServiceImpl.addCustomerUser(customerUserRegistrationRequestDto);
  }

  @PreAuthorize("hasAnyAuthority('can_update_customer_C')")
  @PatchMapping("/{id}")
  public CustomerUserUpdateDto updateCustomer(@PathVariable(name = "id") UUID id, @RequestBody CustomerUserUpdateDto userDto) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    CustomerUserRegistrationResponseDto customerByUserDetails = customerUserServiceImpl.getByEmail(userDetails.getUsername());
    if (!customerByUserDetails.getId().equals(id)) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "you cannot edit other customer");
    }
    return customerUserServiceImpl.update(id, userDto);
  }

  @PostMapping("/login")
  public ResponseEntity<?> signIn(@RequestBody LoginRequest request) {
    return customerUserServiceImpl.signIn(request);
  }
}








