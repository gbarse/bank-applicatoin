package com.iunetworks.physicalaccountservice;

import com.iunetworks.entities.LegalAccount;
import com.iunetworks.entities.LegalCreditCard;
import com.iunetworks.entities.PhysicalAccount;
import com.iunetworks.entities.PhysicalCreditCard;
import com.iunetworks.enums.AccountAndCardStatus;
import com.iunetworks.enums.Currency;
import com.iunetworks.exceptions.ApplicationException;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.physicalaccountservice.physicalaccountserviceinterfaces.IPhysicalAccountTransferService;
import com.iunetworks.rateservice.TransactionAmountService;
import com.iunetworks.repositories.LegalAccountRepository;
import com.iunetworks.repositories.LegalCreditCardRepository;
import com.iunetworks.repositories.PhCreditCardRepository;
import com.iunetworks.repositories.PhysicalAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
public class PhysicalAccountTransferService implements IPhysicalAccountTransferService {

  private final PhysicalAccountRepository repository;
  private final LegalAccountRepository legalAccountRepository;
  private final LegalCreditCardRepository cardRepository;
  private final PhCreditCardRepository phCreditCardRepository;
  private final TransactionAmountService transactionAmountService;

  public PhysicalAccountTransferService(PhysicalAccountRepository repository, LegalAccountRepository legalAccountRepository,
                                        LegalCreditCardRepository cardRepository, PhCreditCardRepository
                                          phCreditCardRepository, TransactionAmountService transactionAmountService) {
    this.repository = repository;
    this.legalAccountRepository = legalAccountRepository;
    this.cardRepository = cardRepository;
    this.phCreditCardRepository = phCreditCardRepository;
    this.transactionAmountService = transactionAmountService;
  }

  @Override
  @Transactional
  public void send(PhysicalAccount senderAccount, PhysicalAccount receiverAccount, BigDecimal amount) {

    final BigDecimal calculateAmount = this.calculateAmount(senderAccount.getCurrency(), receiverAccount.getCurrency(),
      amount);
    if (senderAccount.getStatus() == AccountAndCardStatus.ACTIVE &&
      (receiverAccount.getStatus() == AccountAndCardStatus.ACTIVE)) {
    } else {
      throw new ResourceNotFoundException ("ACCOUNT IS NOT ACTIVE");
    }
    if (senderAccount.getAmount().compareTo(amount) >= 0) {
      senderAccount.setAmount(senderAccount.getAmount().subtract(amount));
      receiverAccount.setAmount(receiverAccount.getAmount().add(calculateAmount));
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "NOT ENOUGH FUNDS");
    }
  }

  @Override
  @Transactional
  public void PtoPAccount(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {

    if (repository.existsByAccountNumberAndDeletedIsNull(fromAccountNumber)) {
      PhysicalAccount physicalSender = repository.findByAccountNumberAndDeletedIsNull(fromAccountNumber).get();

      if (repository.existsByAccountNumberAndDeletedIsNull(toAccountNumber)) {
        PhysicalAccount physicalReceiver = repository.findByAccountNumberAndDeletedIsNull(toAccountNumber).get();

        send(physicalSender, physicalReceiver, amount);
      }

      if (legalAccountRepository.existsByAccountNumberAndDeletedIsNull(toAccountNumber)) {
        LegalAccount legalReceiver = legalAccountRepository.findByAccountNumberAndDeletedIsNull(toAccountNumber)
          .get();

        sendToLa(physicalSender, legalReceiver, amount);
      }
    }
  }

  @Override
  @Transactional
  public void sendToLa(PhysicalAccount senderAccount, LegalAccount receiverAccount, BigDecimal amount) {

    final BigDecimal calculateAmount = this.calculateAmount(senderAccount.getCurrency(), receiverAccount.getCurrency(),
      amount);
    if (senderAccount.getStatus() == AccountAndCardStatus.ACTIVE &&
      (receiverAccount.getStatus() == AccountAndCardStatus.ACTIVE)) {
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "ACCOUNT IS NOT ACTIVE");
    }

    if (senderAccount.getCurrency() != receiverAccount.getCurrency()) {

    }
    if (senderAccount.getAmount().compareTo(amount) >= 0) {
      senderAccount.setAmount(senderAccount.getAmount().subtract(amount));
      receiverAccount.setAmount(receiverAccount.getAmount().add(calculateAmount));
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "NOT ENOUGH FUNDS");
    }
  }

  @Override
  @Transactional
  public void PtoLCard(String fromAccountNumber, String toCardNumber, BigDecimal amount) {

    if (repository.existsByAccountNumberAndDeletedIsNull(fromAccountNumber)) {
      PhysicalAccount physicalSender = repository.findByAccountNumberAndDeletedIsNull(fromAccountNumber).get();

      if (cardRepository.existsByCardNumberAndDeletedIsNull(toCardNumber)) {
        LegalCreditCard cardReceiver = cardRepository.findByCardNumberAndDeletedIsNull(toCardNumber).get();

        sendToCard(physicalSender, cardReceiver, amount);
      }
      if (phCreditCardRepository.existsByCardNumberAndDeletedIsNull(toCardNumber)) {
        PhysicalCreditCard cardReceiver = phCreditCardRepository.findByCardNumberAndDeletedIsNull(toCardNumber).get();

        sendToPhCard(physicalSender, cardReceiver, amount);
      }
    }
  }

  @Override
  @Transactional
  public void sendToCard(PhysicalAccount senderAccount, LegalCreditCard receiverCard, BigDecimal amount) {
    final BigDecimal calculateAmount = this.calculateAmount(senderAccount.getCurrency(), receiverCard.getCurrency(),
      amount);
    if (senderAccount.getStatus() == AccountAndCardStatus.ACTIVE &&
      (receiverCard.getStatus() == (AccountAndCardStatus.ACTIVE))) {
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "ACCOUNT OR CARD IS NOT ACTIVE");
    }
    if (senderAccount.getAmount().compareTo(amount) >= 0) {
      senderAccount.setAmount(senderAccount.getAmount().subtract(amount));
      receiverCard.setAmount(receiverCard.getAmount().add(calculateAmount));
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "NOT ENOUGH FUNDS");
    }
  }

  @Override
  @Transactional
  public void sendToPhCard(PhysicalAccount senderAccount, PhysicalCreditCard receiverCard, BigDecimal amount) {

    final BigDecimal calculateAmount = this.calculateAmount(senderAccount.getCurrency(), receiverCard.getCurrency(),
      amount);
    if (senderAccount.getStatus() == AccountAndCardStatus.ACTIVE &&
      (receiverCard.getStatus() == (AccountAndCardStatus.ACTIVE))) {
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "ACCOUNT OR CARD IS NOT ACTIVE");
    }
    if (senderAccount.getAmount().compareTo(amount) >= 0) {
      senderAccount.setAmount(senderAccount.getAmount().subtract(amount));
      receiverCard.setAmount(receiverCard.getAmount().add(calculateAmount));
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "NOT ENOUGH FUNDS");
    }
  }

  private BigDecimal calculateAmount(Currency out, Currency in, BigDecimal amount) {
    return out.equals(in) ? amount : this.transactionAmountService.getTransactionAmount(out, in, amount);
  }
}
