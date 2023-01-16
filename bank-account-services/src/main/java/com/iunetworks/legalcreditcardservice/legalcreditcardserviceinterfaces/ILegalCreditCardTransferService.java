package com.iunetworks.legalcreditcardservice.legalcreditcardserviceinterfaces;

import com.iunetworks.entities.LegalAccount;
import com.iunetworks.entities.LegalCreditCard;
import com.iunetworks.entities.PhysicalAccount;
import com.iunetworks.entities.PhysicalCreditCard;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface ILegalCreditCardTransferService {
  @Transactional
  void send(LegalCreditCard senderCard, LegalCreditCard receiverCard, BigDecimal amount);

  @Transactional
  void LtoLCard(String fromCardNumber, String toCardNumber, BigDecimal amount);

  @Transactional
  void sendToPh(LegalCreditCard senderCard, PhysicalCreditCard receiverCard, BigDecimal amount);

  @Transactional
  void LtoLAccount(String fromCardNumber, String toAccountNumber, BigDecimal amount);

  @Transactional
  void sendToL(LegalCreditCard senderAccount, LegalAccount receiverAccount, BigDecimal amount);

  @Transactional
  void sendToPhCard(LegalCreditCard senderCard, PhysicalAccount receiverAccount, BigDecimal amount);
}
