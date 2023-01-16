package com.iunetworks.legalaccountservice.legalaccountserviceinterfaces;

import com.iunetworks.entities.LegalAccount;
import com.iunetworks.entities.LegalCreditCard;
import com.iunetworks.entities.PhysicalAccount;
import com.iunetworks.entities.PhysicalCreditCard;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface ILegalAccountTransferService {
  @Transactional
  void send(LegalAccount senderAccount, LegalAccount receiverAccount, BigDecimal amount);

  @Transactional
  void LtoLAccount(String fromAccountNumber, String toAccountNumber, BigDecimal amount);

  @Transactional
  void sendToPh(LegalAccount senderAccount, PhysicalAccount receiverAccount, BigDecimal amount);

  @Transactional
  void LtoLCard(String fromAccountNumber, String toCardNumber, BigDecimal amount);

  @Transactional
  void sendToCard(LegalAccount senderAccount, LegalCreditCard receiverCard, BigDecimal amount);

  @Transactional
  void sendToPhCard(LegalAccount senderAccount, PhysicalCreditCard receiverCard, BigDecimal amount);
}
