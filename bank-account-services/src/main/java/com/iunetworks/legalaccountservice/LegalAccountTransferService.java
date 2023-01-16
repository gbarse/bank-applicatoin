package com.iunetworks.legalaccountservice;


import com.iunetworks.entities.LegalAccount;
import com.iunetworks.entities.LegalCreditCard;
import com.iunetworks.entities.PhysicalAccount;
import com.iunetworks.entities.PhysicalCreditCard;
import com.iunetworks.enums.AccountAndCardStatus;
import com.iunetworks.enums.Currency;
import com.iunetworks.exceptions.ApplicationException;
import com.iunetworks.legalaccountservice.legalaccountserviceinterfaces.ILegalAccountTransferService;
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
public class LegalAccountTransferService implements ILegalAccountTransferService {

  private final LegalAccountRepository repository;
  private final LegalCreditCardRepository cardRepository;
  private final PhysicalAccountRepository physicalAccountRepository;
  private final PhCreditCardRepository phCreditCardRepository;
  private final TransactionAmountService transactionAmountService;

  public LegalAccountTransferService(LegalAccountRepository repository, LegalCreditCardRepository cardRepository, PhysicalAccountRepository physicalAccountRepository, PhCreditCardRepository phCreditCardRepository, TransactionAmountService transactionAmountService) {
    this.repository = repository;

    this.cardRepository = cardRepository;
    this.physicalAccountRepository = physicalAccountRepository;
    this.phCreditCardRepository = phCreditCardRepository;
    this.transactionAmountService = transactionAmountService;
  }

  @Override
  @Transactional
  public void send(LegalAccount senderAccount, LegalAccount receiverAccount, BigDecimal amount) {

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
  public void LtoLAccount(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {

    if (repository.existsByAccountNumberAndDeletedIsNull(fromAccountNumber)) {
      LegalAccount legalSender = repository.findByAccountNumberAndDeletedIsNull(fromAccountNumber).get();

      if (repository.existsByAccountNumberAndDeletedIsNull(toAccountNumber)) {
        LegalAccount legalReceiver = repository.findByAccountNumberAndDeletedIsNull(toAccountNumber).get();

        send(legalSender, legalReceiver, amount);
      }

      if (physicalAccountRepository.existsByAccountNumberAndDeletedIsNull(toAccountNumber)) {
        PhysicalAccount physicalReceiver = physicalAccountRepository.findByAccountNumberAndDeletedIsNull(toAccountNumber)
          .get();

        sendToPh(legalSender, physicalReceiver, amount);
      }
    }
  }

  @Override
  @Transactional
  public void sendToPh(LegalAccount senderAccount, PhysicalAccount receiverAccount, BigDecimal amount) {

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
  public void LtoLCard(String fromAccountNumber, String toCardNumber, BigDecimal amount) {

    if (repository.existsByAccountNumberAndDeletedIsNull(fromAccountNumber)) {
      LegalAccount legalSender = repository.findByAccountNumberAndDeletedIsNull(fromAccountNumber).get();

      if (cardRepository.existsByCardNumberAndDeletedIsNull(toCardNumber)) {
        LegalCreditCard cardReceiver = cardRepository.findByCardNumberAndDeletedIsNull(toCardNumber).get();

        sendToCard(legalSender, cardReceiver, amount);
      }
      if (phCreditCardRepository.existsByCardNumberAndDeletedIsNull(toCardNumber)) {
        PhysicalCreditCard cardReceiver = phCreditCardRepository.findByCardNumberAndDeletedIsNull(toCardNumber).get();

        sendToPhCard(legalSender, cardReceiver, amount);
      }
    }
  }

  @Override
  @Transactional
  public void sendToCard(LegalAccount senderAccount, LegalCreditCard receiverCard, BigDecimal amount) {

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
  public void sendToPhCard(LegalAccount senderAccount, PhysicalCreditCard receiverCard, BigDecimal amount) {


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

