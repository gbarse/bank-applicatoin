package com.iunetworks.userservice.userserviceinterface;

import com.iunetworks.dtos.userdto.UserRegistrationRequestDto;
import com.iunetworks.dtos.userdto.UserRegistrationResponsetDto;

import java.util.List;
import java.util.UUID;

public interface IUserService {
  UserRegistrationResponsetDto create(UserRegistrationRequestDto userRegistrationRequestDto);

  void delete(UUID id);

  List<UserRegistrationResponsetDto> getAllAccounts();

  UserRegistrationResponsetDto getAccountById(UUID id);
}
