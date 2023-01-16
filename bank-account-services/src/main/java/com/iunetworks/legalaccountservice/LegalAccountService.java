package com.iunetworks.legalaccountservice;

import com.iunetworks.dtos.legalaccountdto.LegalAccountRegistrationRequestDto;
import com.iunetworks.dtos.legalaccountdto.LegalAccountRegistrationResponseDto;
import com.iunetworks.entities.LegalAccount;
import com.iunetworks.enums.AccountAndCardStatus;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.legalaccountservice.legalaccountserviceinterfaces.ILegalAccountService;
import com.iunetworks.repositories.LegalAccountRepository;
import com.iunetworks.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service

public class LegalAccountService implements ILegalAccountService {


  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final LegalAccountRepository legalAccountRepository;


  @Autowired
  public LegalAccountService(UserRepository userRepository, ModelMapper modelMapper, LegalAccountRepository legalAccountRepository) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.legalAccountRepository = legalAccountRepository;

  }

  @Override
  public LegalAccountRegistrationResponseDto create(LegalAccountRegistrationRequestDto
                                                      requestDto) {

    if (legalAccountRepository.existsByIdAndDeletedIsNull(requestDto.getId())) {
      throw new ApplicationContextException("account already exists");
    }
    LegalAccount savedLegalAccount = modelMapper.map(requestDto, LegalAccount.class);

    int randomNumber = (int) (0 + Math.random() * 99999999);
    String accountNumber = "0001122" + randomNumber;

    savedLegalAccount.setUpdated(LocalDateTime.now());
    savedLegalAccount.setAccountNumber(accountNumber);
    savedLegalAccount.setAmount(BigDecimal.valueOf(123123));

    savedLegalAccount.setUser(userRepository.findByIdAndDeletedIsNull(
      requestDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Invalid user id")));


    return modelMapper.map(legalAccountRepository.save(savedLegalAccount), LegalAccountRegistrationResponseDto.class);
  }

  @Override
  public void accept(UUID id) {

    LegalAccount legalAccount = legalAccountRepository.findById(id).get();
    if (legalAccount.getStatus().equals(AccountAndCardStatus.ACTIVE)) {
      throw new ApplicationContextException("THE ACCOUNT IS ACTIVE");
    }
    legalAccount.setStatus(AccountAndCardStatus.ACTIVE);
    legalAccountRepository.save(legalAccount);
  }

  @Override
  public void delete(UUID id) {

    LegalAccount legalAccount = legalAccountRepository.findById(id).get();
    if (legalAccount.getDeleted() != null) {
      throw new ApplicationContextException("THE ACCOUNT IS DELETED");
    }
    legalAccount.setDeleted(LocalDateTime.now());
    legalAccount.setStatus(AccountAndCardStatus.DISABLED);
    legalAccountRepository.save(legalAccount);
  }

  @Override
  public void rejectRequest(UUID id) {

    LegalAccount legalAccount = legalAccountRepository.findById(id).get();
    if (legalAccount.getStatus().equals(AccountAndCardStatus.REJECTED)) {
      throw new ApplicationContextException("THE ACCOUNT IS ALREADY REJECTED");
    }
    legalAccount.setStatus(AccountAndCardStatus.REJECTED);
    legalAccount.setDeleted(LocalDateTime.now());
    legalAccountRepository.save(legalAccount);

  }

  @Override
  public List<LegalAccountRegistrationResponseDto> getAllAccounts() {
    List<LegalAccount> all = legalAccountRepository.findAllByDeletedIsNull();
    List<LegalAccountRegistrationResponseDto> allAccountsDto = new ArrayList<>();
    for (LegalAccount user :
      all) {
      LegalAccountRegistrationResponseDto accountDto = modelMapper.map(user, LegalAccountRegistrationResponseDto.class);
      allAccountsDto.add(accountDto);
    }
    return allAccountsDto;
  }

  @Override
  public LegalAccountRegistrationResponseDto getAccountById(UUID id) {

//    if (!legalAccountRepository.existsByIdAndDeletedIsNull(id)) {
//      throw new ApplicationContextException("WRONG ACCOUNT ID");
//    }
    return modelMapper
      .map((legalAccountRepository.findByIdAndDeletedIsNull(id)
        .orElseThrow(() -> new ApplicationContextException("WRONG ACCOUNT ID"))), LegalAccountRegistrationResponseDto.class);
  }
}

