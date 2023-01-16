package com.iunetworks.physicalaccountservice;

import com.iunetworks.dtos.physicalaccountdto.PhysicalAccountRegistrationRequestDto;
import com.iunetworks.dtos.physicalaccountdto.PhysicalAccountRegistrationResponseDto;
import com.iunetworks.entities.PhysicalAccount;
import com.iunetworks.entities.User;
import com.iunetworks.enums.AccountAndCardStatus;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.physicalaccountservice.physicalaccountserviceinterfaces.IPhysicalAccountService;
import com.iunetworks.repositories.PhysicalAccountRepository;
import com.iunetworks.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PhysicalAccountService implements IPhysicalAccountService {
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final PhysicalAccountRepository physicalAccountRepository;
  private final WebClient.Builder webClientBuilder;
  private final GetAccountAddressClient client;


  @Autowired
  public PhysicalAccountService(UserRepository userRepository, ModelMapper modelMapper,
                                PhysicalAccountRepository physicalAccountRepository,
                                WebClient.Builder webClientBuilder, GetAccountAddressClient client) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.physicalAccountRepository = physicalAccountRepository;

    this.webClientBuilder = webClientBuilder;
    this.client = client;
  }

  @Override
  public PhysicalAccountRegistrationResponseDto create(PhysicalAccountRegistrationRequestDto
                                                         physicalAccountRegistrationRequestDto) {
    if (physicalAccountRepository.existsByUserIdAndDeletedIsNull(physicalAccountRegistrationRequestDto.getId())) {
      throw new ApplicationContextException("account already exists");
    }
    PhysicalAccount savedPhysicalAccount = modelMapper.map(physicalAccountRegistrationRequestDto, PhysicalAccount.class);

    int randomNumber = (int) (0 + Math.random() * 99999999);
    String accountNumber = "0000001" + randomNumber;
    savedPhysicalAccount.setUpdated(LocalDateTime.now());
    savedPhysicalAccount.setAccountNumber(accountNumber);
    savedPhysicalAccount.setAmount(BigDecimal.valueOf(44444));
    savedPhysicalAccount.setUser(userRepository.findByIdAndDeletedIsNull(
      physicalAccountRegistrationRequestDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Invalid user id")));
    return modelMapper.map(physicalAccountRepository.save(savedPhysicalAccount), PhysicalAccountRegistrationResponseDto.class);
  }

  @Override
  public void accept(UUID id) {
    PhysicalAccount physicalAccount = physicalAccountRepository.findById(id).get();
    if (physicalAccount.getStatus().equals(AccountAndCardStatus.ACTIVE)) {
      throw new ApplicationContextException("THE ACCOUNT IS ACTIVE");
    }
    physicalAccount.setStatus(AccountAndCardStatus.ACTIVE);
    physicalAccountRepository.save(physicalAccount);
  }

  @Override
  public void delete(UUID id) {
    PhysicalAccount physicalAccount = physicalAccountRepository.findById(id).get();
    if (physicalAccount.getDeleted() != null) {
      throw new ApplicationContextException("THE ACCOUNT IS DELETED");
    }
    physicalAccount.setDeleted(LocalDateTime.now());
    physicalAccount.setStatus(AccountAndCardStatus.DISABLED);
    physicalAccountRepository.save(physicalAccount);
  }

  @Override
  public void rejectRequest(UUID id) {
    PhysicalAccount physicalAccount = physicalAccountRepository.findById(id).get();
    if (physicalAccount.getStatus().equals(AccountAndCardStatus.REJECTED)) {
      throw new ApplicationContextException("THE ACCOUNT IS ALREADY REJECTED");
    }
    physicalAccount.setStatus(AccountAndCardStatus.REJECTED);
    physicalAccount.setDeleted(LocalDateTime.now());
    physicalAccountRepository.save(physicalAccount);
  }

  @Override
  public List<PhysicalAccountRegistrationResponseDto> getAllAccounts() {
    List<PhysicalAccount> all = physicalAccountRepository.findAllByDeletedIsNull();
    List<PhysicalAccountRegistrationResponseDto> allAccountsDto = new ArrayList<>();
    for (PhysicalAccount user :
      all) {
      PhysicalAccountRegistrationResponseDto accountDto = modelMapper.map(user, PhysicalAccountRegistrationResponseDto.class);
      allAccountsDto.add(accountDto);
    }
    return allAccountsDto;
  }

  @Override
  public PhysicalAccountRegistrationResponseDto getAccountById(UUID id) {

    if (!physicalAccountRepository.existsByIdAndDeletedIsNull(id)) {
      throw new ApplicationContextException("WRONG ID");
    }
    return modelMapper
      .map((physicalAccountRepository.findByIdAndDeletedIsNull(id)
        .orElseThrow(() -> new ApplicationContextException("WRONG ID 2"))), PhysicalAccountRegistrationResponseDto.class);
  }

  @Override
  public String getCustomerInformation(UUID id) {
    PhysicalAccount account = physicalAccountRepository.findById(id).get();
    User user = account.getUser();
    UUID loggedUserId = user.getLoggedId();
    return client.getCustomerInfo(loggedUserId);
  }
}
