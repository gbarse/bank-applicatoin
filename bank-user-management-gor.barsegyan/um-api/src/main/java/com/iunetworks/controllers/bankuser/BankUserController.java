package com.iunetworks.controllers.bankuser;


import com.iunetworks.CustomerUserRepository;
import com.iunetworks.bankservice.BankUserServiceImpl;
import com.iunetworks.customerservice.CustomerUserServiceImpl;
import com.iunetworks.dtos.bankuserdtos.BankUserRegistrationRequestDto;
import com.iunetworks.dtos.bankuserdtos.BankUserRegistrationResponseDto;
import com.iunetworks.dtos.bankuserdtos.BankUserUpdateDto;
import com.iunetworks.dtos.logindto.LoginRequest;
import com.iunetworks.securityconfig.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/bank")

public class BankUserController {

  private final BankUserServiceImpl bankUserServiceImpl;
  private final JwtTokenUtil jwtTokenUtil;


  @Autowired
  public BankUserController(BankUserServiceImpl bankUserServiceImpl, CustomerUserServiceImpl customerUserServiceImpl, CustomerUserRepository customerUserRepository, JwtTokenUtil jwtTokenUtil) {
    this.bankUserServiceImpl = bankUserServiceImpl;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @PreAuthorize("hasAnyAuthority('can_view_all_admin_B')")
  @GetMapping("/view-all")
  public List<BankUserRegistrationResponseDto> getAll() {
    return bankUserServiceImpl.getAllBankUsers();
  }

//  @PreAuthorize("hasAnyAuthority('can_create_admin_B')")
  @PostMapping("/create")
  public BankUserRegistrationResponseDto createUser(@RequestBody @Valid BankUserRegistrationRequestDto bankUserRegistrationRequestDto) {
    return bankUserServiceImpl.addBankUser(bankUserRegistrationRequestDto);
  }

  @PreAuthorize("hasAnyAuthority('can_delete_admin_B')")
  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable UUID id) {
    bankUserServiceImpl.deleteBankUser(id);
  }

  @PreAuthorize("hasAnyAuthority('can_recover_admin_B')")
  @PostMapping("/{id}")
  public void recoverUser(@PathVariable UUID id) {
    bankUserServiceImpl.recoverBankUser(id);
  }

  @PreAuthorize("hasAnyAuthority('can_view_all_admin_by_id_B')")
  @GetMapping("/{id}")
  public BankUserRegistrationResponseDto getBankUserById(@PathVariable UUID id) {
    return bankUserServiceImpl.getBankUserById(id);
  }

  @PreAuthorize("hasAnyAuthority('can_update_admin_B')")
  @PatchMapping("/{id}")
  public BankUserUpdateDto bankUserUpdate(@PathVariable(name = "id") UUID id,
                                          @RequestBody @Valid BankUserUpdateDto bankUserUpdateDto) {
    return bankUserServiceImpl.updateBankUser(id, bankUserUpdateDto);
  }


  @PostMapping("/login")
  public ResponseEntity<?> signIn(@RequestBody LoginRequest request) {
    return bankUserServiceImpl.signIn(request);
  }
}


