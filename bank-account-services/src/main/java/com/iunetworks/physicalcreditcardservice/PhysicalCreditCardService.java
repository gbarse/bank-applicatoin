package com.iunetworks.physicalcreditcardservice;


import com.iunetworks.dtos.creditcarddto.PhCreditCardRegistrationRequestDto;
import com.iunetworks.dtos.creditcarddto.PhCreditCardRegistrationResponseDto;
import com.iunetworks.entities.PhysicalAccount;
import com.iunetworks.entities.PhysicalCreditCard;
import com.iunetworks.enums.AccountAndCardStatus;
import com.iunetworks.exceptions.ApplicationException;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.physicalcreditcardservice.physixalcreditcardserviceinterfaces.IPhysicalCreditCardService;
import com.iunetworks.repositories.PhCreditCardRepository;
import com.iunetworks.repositories.PhysicalAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service

public class PhysicalCreditCardService implements IPhysicalCreditCardService {

  private final PhCreditCardRepository phCreditCardRepository;

  private final ModelMapper modelMapper;
  private final PhysicalAccountRepository physicalAccountRepository;


  public PhysicalCreditCardService(PhCreditCardRepository phCreditCardRepository, ModelMapper modelMapper, PhysicalAccountRepository physicalAccountRepository) {
    this.phCreditCardRepository = phCreditCardRepository;
    this.modelMapper = modelMapper;
    this.physicalAccountRepository = physicalAccountRepository;
  }

  @Override
  public PhCreditCardRegistrationResponseDto create(UUID id, PhCreditCardRegistrationRequestDto requestDto) {
    if (phCreditCardRepository.existsByPhysicalAccountIdAndDeletedIsNull(id)) {
      throw new ApplicationException(HttpStatus.ALREADY_REPORTED, "Already exists");
    }

    AccountAndCardStatus accountAndCardStatus = physicalAccountRepository.getReferenceById(id).getStatus();
    if (accountAndCardStatus.equals(AccountAndCardStatus.IN_PROCESS)) {
      throw new ApplicationException(HttpStatus.ALREADY_REPORTED, "THE ACCOUNT IS NOT ACTIVE");

    }

    PhysicalCreditCard physicalCreditCard = modelMapper.map(requestDto, PhysicalCreditCard.class);
    physicalCreditCard.setPhysicalAccount((PhysicalAccount) physicalAccountRepository.findByIdAndDeletedIsNull(id)
      .orElseThrow(() -> new ResourceNotFoundException("Invalid account id")));
    int randomNumber = (int) (0 + Math.random() * 99999999);
    String accountNumber = "0001122" + randomNumber;
    physicalCreditCard.setCardNumber(accountNumber);
    physicalCreditCard.setCvv((int) (Math.random() * 999) + "");
    physicalCreditCard.setExpDate(LocalDate.from(LocalDateTime.now().plusYears(3)));
    physicalCreditCard.setStatus(AccountAndCardStatus.IN_PROCESS);
    physicalCreditCard.setAmount(BigDecimal.valueOf(12321321));
    return modelMapper.map(phCreditCardRepository.save(physicalCreditCard), PhCreditCardRegistrationResponseDto.class);

  }

  @Override
  public void pdelete(UUID id) {

    PhysicalCreditCard physicalCreditCard = phCreditCardRepository.findById(id).get();
    if (physicalCreditCard.getDeleted() != null) {
      throw new ApplicationContextException("THE CARD IS DELETED");
    }
    physicalCreditCard.setDeleted(LocalDateTime.now());
    physicalCreditCard.setStatus(AccountAndCardStatus.DISABLED);
    phCreditCardRepository.save(physicalCreditCard);
  }

  @Override
  public void accept(UUID id) {

    PhysicalCreditCard physicalCreditCard = phCreditCardRepository.findById(id).get();
    if (physicalCreditCard.getStatus().equals(AccountAndCardStatus.ACTIVE)) {
      throw new ApplicationContextException("THE ACCOUNT IS ACTIVE");
    }
    physicalCreditCard.setStatus(AccountAndCardStatus.ACTIVE);
    phCreditCardRepository.save(physicalCreditCard);
  }

  @Override
  public void rejectRequest(UUID id) {

    PhysicalCreditCard physicalCreditCard = phCreditCardRepository.findById(id).get();
    if (physicalCreditCard.getStatus().equals(AccountAndCardStatus.REJECTED)) {
      throw new ApplicationContextException("THE ACCOUNT IS ALREADY REJECTED");
    }
    physicalCreditCard.setStatus(AccountAndCardStatus.REJECTED);
    physicalCreditCard.setDeleted(LocalDateTime.now());
    phCreditCardRepository.save(physicalCreditCard);

  }

  @Override
  public List<PhCreditCardRegistrationResponseDto> getAllCards() {
    List<PhysicalCreditCard> all = phCreditCardRepository.findAllByDeletedIsNull();
    List<PhCreditCardRegistrationResponseDto> allCardsDto = new ArrayList<>();
    for (PhysicalCreditCard card :
      all) {
      PhCreditCardRegistrationResponseDto cardDto = modelMapper.map(card, PhCreditCardRegistrationResponseDto.class);
      allCardsDto.add(cardDto);
    }
    return allCardsDto;
  }

  @Override
  public PhCreditCardRegistrationResponseDto getCardById(UUID id) {

//    if (!phCreditCardRepository.existsByIdAndDeletedIsNull(id)) {
//      throw new ApplicationContextException("WRONG ID");
//    }
    return modelMapper
      .map((phCreditCardRepository.findByIdAndDeletedIsNull(id)
        .orElseThrow(() -> new ApplicationContextException("WRONG ID"))), PhCreditCardRegistrationResponseDto.class);
  }
}




