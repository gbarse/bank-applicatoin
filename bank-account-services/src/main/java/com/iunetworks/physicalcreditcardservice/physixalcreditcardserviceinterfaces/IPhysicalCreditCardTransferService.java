package com.iunetworks.physicalcreditcardservice.physixalcreditcardserviceinterfaces;

import com.iunetworks.entities.LegalAccount;
import com.iunetworks.entities.LegalCreditCard;
import com.iunetworks.entities.PhysicalAccount;
import com.iunetworks.entities.PhysicalCreditCard;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface IPhysicalCreditCardTransferService {
  @Transactional
  void send(PhysicalCreditCard senderCard, PhysicalCreditCard receiverCard, BigDecimal amount);

  @Transactional
  void PtoLCard(String fromCardNumber, String toCardNumber, BigDecimal amount);

  @Transactional
  void sendToPh(PhysicalCreditCard senderCard, LegalCreditCard receiverCard, BigDecimal amount);

  @Transactional
  void PtoLAccount(String fromCardNumber, String toAccountNumber, BigDecimal amount);

  @Transactional
  void sendToL(PhysicalCreditCard senderAccount, LegalAccount receiverAccount, BigDecimal amount);

  @Transactional
  void sendToPhCard(PhysicalCreditCard senderCard, PhysicalAccount receiverAccount, BigDecimal amount);
}
