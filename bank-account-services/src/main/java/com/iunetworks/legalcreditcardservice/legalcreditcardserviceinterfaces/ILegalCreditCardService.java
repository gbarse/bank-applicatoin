package com.iunetworks.legalcreditcardservice.legalcreditcardserviceinterfaces;

import com.iunetworks.dtos.creditcarddto.LegalCreditCardRegistrationRequestDto;
import com.iunetworks.dtos.creditcarddto.LegalCreditCardRegistrationResponseDto;

import java.util.List;
import java.util.UUID;

public interface ILegalCreditCardService {
  LegalCreditCardRegistrationResponseDto create(UUID id, LegalCreditCardRegistrationRequestDto requestDto);



    void ldelete(UUID id);

    void accept(UUID id);

  void rejectRequest(UUID id);

  List<LegalCreditCardRegistrationResponseDto> getAllCards();

  LegalCreditCardRegistrationResponseDto getCardById(UUID id);
}
