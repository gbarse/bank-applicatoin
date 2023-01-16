package com.iunetworks.physicalaccountservice.physicalaccountserviceinterfaces;

import com.iunetworks.entities.LegalAccount;
import com.iunetworks.entities.LegalCreditCard;
import com.iunetworks.entities.PhysicalAccount;
import com.iunetworks.entities.PhysicalCreditCard;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface IPhysicalAccountTransferService {
  @Transactional
  void send(PhysicalAccount senderAccount, PhysicalAccount receiverAccount, BigDecimal amount);

  @Transactional
  void PtoPAccount(String fromAccountNumber, String toAccountNumber, BigDecimal amount);

  @Transactional
  void sendToLa(PhysicalAccount senderAccount, LegalAccount receiverAccount, BigDecimal amount);

  @Transactional
  void PtoLCard(String fromAccountNumber, String toCardNumber, BigDecimal amount);

  @Transactional
  void sendToCard(PhysicalAccount senderAccount, LegalCreditCard receiverCard, BigDecimal amount);

  @Transactional
  void sendToPhCard(PhysicalAccount senderAccount, PhysicalCreditCard receiverCard, BigDecimal amount);
}
