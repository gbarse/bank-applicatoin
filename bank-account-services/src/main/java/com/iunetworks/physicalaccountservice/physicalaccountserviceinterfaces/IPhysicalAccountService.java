package com.iunetworks.physicalaccountservice.physicalaccountserviceinterfaces;

import com.iunetworks.dtos.physicalaccountdto.PhysicalAccountRegistrationRequestDto;
import com.iunetworks.dtos.physicalaccountdto.PhysicalAccountRegistrationResponseDto;

import java.util.List;
import java.util.UUID;

public interface IPhysicalAccountService {
  PhysicalAccountRegistrationResponseDto create(PhysicalAccountRegistrationRequestDto
                                                  physicalAccountRegistrationRequestDto);

  void accept(UUID id);

  void delete(UUID id);

  void rejectRequest(UUID id);

  List<PhysicalAccountRegistrationResponseDto> getAllAccounts();

  PhysicalAccountRegistrationResponseDto getAccountById(UUID id);

  String getCustomerInformation(UUID id);
}
