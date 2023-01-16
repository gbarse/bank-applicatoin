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
@Table(name = "t_physical_credit_card")
public class PhysicalCreditCard extends CreditCard {

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "physical_account_id")
  private PhysicalAccount physicalAccount;

  public PhysicalAccount getPhysicalAccount() {
    return physicalAccount;
  }

  public void setPhysicalAccount(PhysicalAccount physicalAccount) {
    this.physicalAccount = physicalAccount;
  }

  public PhysicalCreditCard(PhysicalAccount physicalAccount) {
    this.physicalAccount = physicalAccount;
  }

  public PhysicalCreditCard() {
  }

  public PhysicalCreditCard(UUID id, String cardNumber, CardType cardType, CardLevel cardLevel, LocalDateTime created, LocalDateTime updated, LocalDateTime deleted,
                            LocalDate expDate, String cvv, AccountAndCardStatus status, BigDecimal amount,
                            PhysicalAccount physicalAccount) {
    super(id, cardNumber, cardType, cardLevel, created, updated, deleted, expDate, cvv, status, amount);
    this.physicalAccount = physicalAccount;
  }
}
