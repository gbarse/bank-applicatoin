package com.iunetworks.controllers.bankuser;

import com.iunetworks.customeruserbankservice.CustomerUserBankServiceImpl;
import com.iunetworks.dtos.customeruserdtos.CustomerUserRegistrationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user_bank")
public class CustomerUserBankServiceController {
  private final CustomerUserBankServiceImpl customerUserBankServiceImpl;

  @Autowired
  public CustomerUserBankServiceController(CustomerUserBankServiceImpl customerUserBankServiceImpl) {
    this.customerUserBankServiceImpl = customerUserBankServiceImpl;
  }

  @PreAuthorize("hasAnyAuthority('can_delete_customer_B')")
  @DeleteMapping("/{id}")
  public void deleteCustomerUser(@PathVariable UUID id) {
    customerUserBankServiceImpl.deleteCustomerUser(id);
  }

  @PreAuthorize("hasAnyAuthority('can_recover_customer_B')")
  @PostMapping("/{id}")
  public void recoverCustomerUser(@PathVariable UUID id) {
    customerUserBankServiceImpl.recoverCustomerUser(id);
  }

  @PreAuthorize("hasAnyAuthority('can_view_all_customer_B')")
  @GetMapping("/view-all-customers")
  public List<CustomerUserRegistrationResponseDto> getAllCustomerUsers() {
    return customerUserBankServiceImpl.getAllCustomerUsers();
  }

  //@PreAuthorize("hasAnyAuthority('can_view_all_customer_by_id_B')")
  @GetMapping("/{id}")
  public CustomerUserRegistrationResponseDto getCustomerById(@PathVariable("id") UUID id) {
    return customerUserBankServiceImpl.getCustomerById(id);
  }
//
//  @PostMapping("/assignRole")
//  public CustomerUserRoleAssignDto assignCustomerRole(@RequestBody CustomerUserRoleAssignDto dto) {
//    return customerUserBankService.assignCustomerRole(dto);
//  }


}
