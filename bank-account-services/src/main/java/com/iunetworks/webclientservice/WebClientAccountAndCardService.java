package com.iunetworks.webclientservice;

import com.iunetworks.dtos.creditcarddto.LegalCreditCardRegistrationResponseDto;
import com.iunetworks.dtos.creditcarddto.PhCreditCardRegistrationResponseDto;
import com.iunetworks.dtos.feiginclientdto.AccountRegistrationResponseDto;
import com.iunetworks.dtos.feiginclientdto.CardRegistrationResponseDto;
import com.iunetworks.dtos.legalaccountdto.LegalAccountRegistrationResponseDto;
import com.iunetworks.dtos.physicalaccountdto.PhysicalAccountRegistrationResponseDto;
import com.iunetworks.entities.LegalAccount;
import com.iunetworks.entities.LegalCreditCard;
import com.iunetworks.entities.PhysicalAccount;
import com.iunetworks.entities.PhysicalCreditCard;
import com.iunetworks.repositories.LegalAccountRepository;
import com.iunetworks.repositories.LegalCreditCardRepository;
import com.iunetworks.repositories.PhCreditCardRepository;
import com.iunetworks.repositories.PhysicalAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebClientAccountAndCardService {

  private final PhysicalAccountRepository repository;
  private final ModelMapper modelMapper;
  private final LegalAccountRepository legalAccountRepository;
  private final PhCreditCardRepository phCreditCardRepository;
  private final LegalCreditCardRepository legalCreditCardRepository;

  @Autowired
  public WebClientAccountAndCardService(PhysicalAccountRepository repository, ModelMapper modelMapper, LegalAccountRepository legalAccountRepository, PhCreditCardRepository phCreditCardRepository, LegalCreditCardRepository legalCreditCardRepository) {
    this.repository = repository;
    this.modelMapper = modelMapper;
    this.legalAccountRepository = legalAccountRepository;
    this.phCreditCardRepository = phCreditCardRepository;
    this.legalCreditCardRepository = legalCreditCardRepository;
  }

  public List<AccountRegistrationResponseDto> getInProcessAccounts() {
    List<PhysicalAccount> physicalAccounts = repository.getAllByInProcessAccount();
    List<LegalAccount> legalAccounts = legalAccountRepository.getAllByInProcessAccount();

    List<AccountRegistrationResponseDto> collect = physicalAccounts.stream().map(a -> modelMapper.map
      (a, PhysicalAccountRegistrationResponseDto.class)).collect(Collectors.toList());
    collect.addAll(legalAccounts.stream().map(a -> modelMapper.map
      (a, LegalAccountRegistrationResponseDto.class)).collect(Collectors.toList()));
    return collect;
  }


  public List<CardRegistrationResponseDto> getInProcessCards() {
    List<PhysicalCreditCard> physicalCreditCards = phCreditCardRepository.getAllByInProcessPhCards();
    List<LegalCreditCard> legalCreditCards = legalCreditCardRepository.getAllByInProcessLeCards();

    List<CardRegistrationResponseDto> collect = physicalCreditCards.stream().map(a -> modelMapper.map
      (a, PhCreditCardRegistrationResponseDto.class)).collect(Collectors.toList());
    collect.addAll(legalCreditCards.stream().map(a -> modelMapper.map(a, LegalCreditCardRegistrationResponseDto.class)).
      collect(Collectors.toList()));
    return collect;
  }
}

//  public String getCardsBtId(UUID id){
//    LegalCreditCard card = legalCreditCardRepository.findByIdAndDeletedIsNull(id).get();
//    PhysicalCreditCard phcard = phCreditCardRepository.findByIdAndDeletedIsNull(id).get();
//
//
//  }
//}


