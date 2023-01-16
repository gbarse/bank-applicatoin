package com.iunetworks.legalcreditcardservice;

import com.iunetworks.dtos.creditcarddto.LegalCreditCardRegistrationRequestDto;
import com.iunetworks.dtos.creditcarddto.LegalCreditCardRegistrationResponseDto;
import com.iunetworks.entities.LegalAccount;
import com.iunetworks.entities.LegalCreditCard;
import com.iunetworks.enums.AccountAndCardStatus;
import com.iunetworks.exceptions.ApplicationException;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.legalcreditcardservice.legalcreditcardserviceinterfaces.ILegalCreditCardService;
import com.iunetworks.repositories.LegalAccountRepository;
import com.iunetworks.repositories.LegalCreditCardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LegalCreditCardService implements ILegalCreditCardService {

  private final LegalCreditCardRepository legalCreditCardRepository;
  private final ModelMapper modelMapper;
  private final LegalAccountRepository legalAccountRepository;


  @Autowired
  public LegalCreditCardService(LegalCreditCardRepository legalCreditCardRepository, ModelMapper modelMapper, LegalAccountRepository legalAccountRepository) {
    this.legalCreditCardRepository = legalCreditCardRepository;
    this.modelMapper = modelMapper;
    this.legalAccountRepository = legalAccountRepository;

  }

  @Override
  public LegalCreditCardRegistrationResponseDto create(UUID id, LegalCreditCardRegistrationRequestDto requestDto) {
    if (legalCreditCardRepository.existsByLegalAccountIdAndDeletedIsNull(id)) {
      throw new ApplicationException(HttpStatus.ALREADY_REPORTED, "Already exists");
    }

    AccountAndCardStatus accountAndCardStatus = legalAccountRepository.getReferenceById(id).getStatus();
    if (accountAndCardStatus.equals(AccountAndCardStatus.IN_PROCESS)) {
      throw new ApplicationException(HttpStatus.ALREADY_REPORTED, "THE ACCOUNT IS NOT ACTIVE");
    }

    LegalCreditCard legalCreditCard = modelMapper.map(requestDto, LegalCreditCard.class);
    legalCreditCard.setLegalAccount((LegalAccount) legalAccountRepository.findByIdAndDeletedIsNull(id)
      .orElseThrow(() -> new ResourceNotFoundException("Invalid account id")));
    int randomNumber = (int) (0 + Math.random() * 99999999);
    String accountNumber = "0001122" + randomNumber;
    legalCreditCard.setCardNumber(accountNumber);
    legalCreditCard.setCvv((int) (Math.random() * 999) + "");
    legalCreditCard.setExpDate(LocalDate.from(LocalDateTime.now().plusYears(3)));
    legalCreditCard.setStatus(AccountAndCardStatus.IN_PROCESS);
    legalCreditCard.setAmount(BigDecimal.valueOf(12321321));
    legalCreditCardRepository.save(legalCreditCard);
    return modelMapper.map(legalCreditCardRepository.save(legalCreditCard), LegalCreditCardRegistrationResponseDto.class);

  }

  @Override
  public void ldelete(UUID id) {

    LegalCreditCard legalCreditCard = legalCreditCardRepository.findById(id).get();
    if (legalCreditCard.getDeleted() != null) {
      throw new ApplicationContextException("THE CARD IS DELETED");
    }
    legalCreditCard.setDeleted(LocalDateTime.now());
    legalCreditCard.setStatus(AccountAndCardStatus.DISABLED);
    legalCreditCardRepository.save(legalCreditCard);
  }
  @Override
  public void accept(UUID id) {

    LegalCreditCard legalCreditCard = legalCreditCardRepository.findById(id).get();
    if (legalCreditCard.getStatus().equals(AccountAndCardStatus.ACTIVE)) {
      throw new ApplicationContextException("THE ACCOUNT IS ACTIVE");
    }
    legalCreditCard.setStatus(AccountAndCardStatus.ACTIVE);
    legalCreditCardRepository.save(legalCreditCard);
  }
  @Override
  public void rejectRequest(UUID id) {

    LegalCreditCard legalCreditCard = legalCreditCardRepository.findById(id).get();
    if (legalCreditCard.getStatus().equals(AccountAndCardStatus.REJECTED)) {
      throw new ApplicationContextException("THE ACCOUNT IS ALREADY REJECTED");
    }
    legalCreditCard.setStatus(AccountAndCardStatus.REJECTED);
    legalCreditCard.setDeleted(LocalDateTime.now());
    legalCreditCardRepository.save(legalCreditCard);

  }
  @Override
  public List<LegalCreditCardRegistrationResponseDto> getAllCards() {
    List<LegalCreditCard> all = legalCreditCardRepository.findAllByDeletedIsNull();
    List<LegalCreditCardRegistrationResponseDto> allCardsDto = new ArrayList<>();
    for (LegalCreditCard card :
      all) {
      LegalCreditCardRegistrationResponseDto cardDto = modelMapper.map(card, LegalCreditCardRegistrationResponseDto.class);
      allCardsDto.add(cardDto);
    }
    return allCardsDto;
  }
  @Override
  public LegalCreditCardRegistrationResponseDto getCardById(UUID id) {

//    if (!phCreditCardRepository.existsByIdAndDeletedIsNull(id)) {
//      throw new ApplicationContextException("WRONG ID");
//    }
    return modelMapper
      .map((legalCreditCardRepository.findByIdAndDeletedIsNull(id)
        .orElseThrow(() -> new ApplicationContextException("WRONG ID "))), LegalCreditCardRegistrationResponseDto.class);
  }

}
