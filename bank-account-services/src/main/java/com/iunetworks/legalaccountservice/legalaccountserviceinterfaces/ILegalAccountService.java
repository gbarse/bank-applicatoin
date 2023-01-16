package com.iunetworks.legalaccountservice.legalaccountserviceinterfaces;

import com.iunetworks.dtos.legalaccountdto.LegalAccountRegistrationRequestDto;
import com.iunetworks.dtos.legalaccountdto.LegalAccountRegistrationResponseDto;

import java.util.List;
import java.util.UUID;

public interface ILegalAccountService {
  LegalAccountRegistrationResponseDto create(LegalAccountRegistrationRequestDto
                                               requestDto);

  void accept(UUID id);

  void delete(UUID id);

  void rejectRequest(UUID id);

  List<LegalAccountRegistrationResponseDto> getAllAccounts();

  LegalAccountRegistrationResponseDto getAccountById(UUID id);
}
