package com.iunetworks.entities;


import com.iunetworks.enums.AccountAndCardStatus;
import com.iunetworks.enums.CardLevel;
import com.iunetworks.enums.CardType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_legal_credit_card")
public class LegalCreditCard extends CreditCard {
  @OneToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "legal_account_id", nullable = false)
  private LegalAccount legalAccount;

  public LegalAccount getLegalAccount() {
    return legalAccount;
  }

  public void setLegalAccount(LegalAccount legalAccount) {
    this.legalAccount = legalAccount;
  }

  public LegalCreditCard(LegalAccount legalAccount) {
    this.legalAccount = legalAccount;
  }


  public LegalCreditCard(UUID id, String cardNumber, CardType cardType, CardLevel cardLevel, LocalDateTime created, LocalDateTime updated, LocalDateTime deleted,
                         LocalDate expDate, String cvv, AccountAndCardStatus status, BigDecimal amount, LegalAccount legalAccount) {
    super(id, cardNumber, cardType, cardLevel, created, updated, deleted, expDate, cvv, status, amount);
    this.legalAccount = legalAccount;
  }

  public LegalCreditCard() {
  }
}
