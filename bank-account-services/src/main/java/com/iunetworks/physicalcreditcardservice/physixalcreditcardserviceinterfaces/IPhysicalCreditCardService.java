package com.iunetworks.physicalcreditcardservice.physixalcreditcardserviceinterfaces;

import com.iunetworks.dtos.creditcarddto.PhCreditCardRegistrationRequestDto;
import com.iunetworks.dtos.creditcarddto.PhCreditCardRegistrationResponseDto;

import java.util.List;
import java.util.UUID;

public interface IPhysicalCreditCardService {
  PhCreditCardRegistrationResponseDto create(UUID id, PhCreditCardRegistrationRequestDto requestDto);


  void pdelete(UUID id);

  void accept(UUID id);

  void rejectRequest(UUID id);

  List<PhCreditCardRegistrationResponseDto> getAllCards();

  PhCreditCardRegistrationResponseDto getCardById(UUID id);
}
