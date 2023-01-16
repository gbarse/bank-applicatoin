package com.iunetworks.bankservice;

import com.iunetworks.dtos.bankuserdtos.BankUserRegistrationRequestDto;
import com.iunetworks.dtos.bankuserdtos.BankUserRegistrationResponseDto;
import com.iunetworks.dtos.bankuserdtos.BankUserUpdateDto;
import com.iunetworks.dtos.logindto.LoginRequest;
import com.iunetworks.entities.BankUser;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;


public interface BankUserService {

  BankUserRegistrationResponseDto addBankUser(BankUserRegistrationRequestDto bankUserRegistrationRequestDto);

    BankUser saveUser(BankUser bankUser);

    void deleteBankUser(UUID id);

  void recoverBankUser(UUID id);

  List<BankUserRegistrationResponseDto> getAllBankUsers();


  BankUserRegistrationResponseDto getBankUserById(UUID id);

  BankUserUpdateDto updateBankUser(UUID id, BankUserUpdateDto bankUserUpdateDto);


  ResponseEntity<?> signIn(LoginRequest source);
}
