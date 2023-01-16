package com.iunetworks.physicalcreditcardservice;

import com.iunetworks.entities.LegalAccount;
import com.iunetworks.entities.LegalCreditCard;
import com.iunetworks.entities.PhysicalAccount;
import com.iunetworks.entities.PhysicalCreditCard;
import com.iunetworks.enums.AccountAndCardStatus;
import com.iunetworks.enums.Currency;
import com.iunetworks.exceptions.ApplicationException;
import com.iunetworks.exceptions.ResourceNotFoundException;
import com.iunetworks.physicalcreditcardservice.physixalcreditcardserviceinterfaces.IPhysicalCreditCardTransferService;
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
public class PhysicalCreditCardTransferService implements IPhysicalCreditCardTransferService {

  private final PhCreditCardRepository repository;
  private final LegalCreditCardRepository legalCreditCardRepository;
  private final LegalAccountRepository legalAccountRepository;
  private final PhysicalAccountRepository physicalAccountRepository;
  private final TransactionAmountService transactionAmountService;

  public PhysicalCreditCardTransferService(PhCreditCardRepository repository, LegalCreditCardRepository legalCreditCardRepository, LegalAccountRepository legalAccountRepository, PhysicalAccountRepository physicalAccountRepository, TransactionAmountService transactionAmountService) {
    this.repository = repository;
    this.legalCreditCardRepository = legalCreditCardRepository;
    this.legalAccountRepository = legalAccountRepository;
    this.physicalAccountRepository = physicalAccountRepository;
    this.transactionAmountService = transactionAmountService;
  }

  @Override
  @Transactional
  public void send(PhysicalCreditCard senderCard, PhysicalCreditCard receiverCard, BigDecimal amount) {

    final BigDecimal calculateAmount = this.calculateAmount(senderCard.getCurrency(), receiverCard.getCurrency(),
      amount);
    if (senderCard.getStatus() == AccountAndCardStatus.ACTIVE &&
      (receiverCard.getStatus() == AccountAndCardStatus.ACTIVE)) {
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "CARD IS NOT ACTIVE");
    }

    if (senderCard.getAmount().compareTo(amount) >= 0) {
      senderCard.setAmount(senderCard.getAmount().subtract(amount));
      receiverCard.setAmount(receiverCard.getAmount().add(calculateAmount));
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "NOT ENOUGH FUNDS");
    }
  }

  @Override
  @Transactional
  public void PtoLCard(String fromCardNumber, String toCardNumber, BigDecimal amount) {

    if (repository.existsByCardNumberAndDeletedIsNull(fromCardNumber)) {
      PhysicalCreditCard phSender = repository.findByCardNumberAndDeletedIsNull(fromCardNumber).orElseThrow(
        () -> new ResourceNotFoundException("Invalid from cardNumber")
      );

      if (repository.existsByCardNumberAndDeletedIsNull(toCardNumber)) {
        PhysicalCreditCard phReceiver = repository.findByCardNumberAndDeletedIsNull(toCardNumber).get();

        send(phSender, phReceiver, amount);
      }
      if (legalCreditCardRepository.existsByCardNumberAndDeletedIsNull(toCardNumber)) {
        LegalCreditCard legalReceiver = legalCreditCardRepository.findByCardNumberAndDeletedIsNull(toCardNumber)
          .get();

        sendToPh(phSender, legalReceiver, amount);
      }
    }
  }

  @Override
  @Transactional
  public void sendToPh(PhysicalCreditCard senderCard, LegalCreditCard receiverCard, BigDecimal amount) {

    final BigDecimal calculateAmount = this.calculateAmount(senderCard.getCurrency(), receiverCard.getCurrency(),
      amount);
    if (senderCard.getStatus() == AccountAndCardStatus.ACTIVE &&
      (receiverCard.getStatus() == AccountAndCardStatus.ACTIVE)) {
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "ACCOUNT IS NOT ACTIVE");
    }
    if (senderCard.getAmount().compareTo(amount) >= 0) {
      senderCard.setAmount(senderCard.getAmount().subtract(amount));
      receiverCard.setAmount(receiverCard.getAmount().add(calculateAmount));
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "NOT ENOUGH FUNDS");
    }
  }

  @Override
  @Transactional
  public void PtoLAccount(String fromCardNumber, String toAccountNumber, BigDecimal amount) {

    if (repository.existsByCardNumberAndDeletedIsNull(fromCardNumber)) {
      PhysicalCreditCard cardSender = repository.findByCardNumberAndDeletedIsNull(fromCardNumber).get();

      if (legalAccountRepository.existsByAccountNumberAndDeletedIsNull(toAccountNumber)) {
        LegalAccount legalReceiver = legalAccountRepository.findByAccountNumberAndDeletedIsNull(toAccountNumber).get();

        sendToL(cardSender, legalReceiver, amount);
      }
      if (physicalAccountRepository.existsByAccountNumberAndDeletedIsNull(toAccountNumber)) {
        PhysicalAccount accountReceiver = physicalAccountRepository.findByAccountNumberAndDeletedIsNull
          (toAccountNumber).get();

        sendToPhCard(cardSender, accountReceiver, amount);
      }
    }
  }

  @Override
  @Transactional
  public void sendToL(PhysicalCreditCard senderAccount, LegalAccount receiverAccount, BigDecimal amount) {

    final BigDecimal calculateAmount = this.calculateAmount(senderAccount.getCurrency(), receiverAccount.getCurrency(),
      amount);
    if (senderAccount.getStatus() == AccountAndCardStatus.ACTIVE &&
      (receiverAccount.getStatus() == (AccountAndCardStatus.ACTIVE))) {
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "ACCOUNT OR CARD IS NOT ACTIVE");
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
  public void sendToPhCard(PhysicalCreditCard senderCard, PhysicalAccount receiverAccount, BigDecimal amount) {
    if (senderCard.getStatus() == AccountAndCardStatus.ACTIVE &&
      (receiverAccount.getStatus() == (AccountAndCardStatus.ACTIVE))) {
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "ACCOUNT OR CARD IS NOT ACTIVE");
    }
    if (senderCard.getAmount().compareTo(amount) >= 0) {
      senderCard.setAmount(senderCard.getAmount().subtract(amount));
      receiverAccount.setAmount(receiverAccount.getAmount().add(amount));
    } else {
      throw new ApplicationException(HttpStatus.BAD_GATEWAY, "NOT ENOUGH FUNDS");
    }
  }

  private BigDecimal calculateAmount(Currency out, Currency in, BigDecimal amount) {
    return out.equals(in) ? amount : this.transactionAmountService.getTransactionAmount(out, in, amount);
  }
}
