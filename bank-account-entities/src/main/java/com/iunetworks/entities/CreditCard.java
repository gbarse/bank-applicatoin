package com.iunetworks.entities;

import com.iunetworks.enums.AccountAndCardStatus;
import com.iunetworks.enums.CardType;

import com.iunetworks.enums.CardLevel;
import com.iunetworks.enums.Currency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@MappedSuperclass
public abstract class CreditCard {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")

  @Column(name = "id", nullable = false)
  protected UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "currency", nullable = false)
  protected Currency currency;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  protected AccountAndCardStatus status;

  @Column(name = "cardNumber", nullable = false)
  protected String cardNumber;


  @Column(name = "cardType", nullable = false)
  @Enumerated(EnumType.STRING)
  protected CardType cardType;

  @Column(name = "cardLevel", nullable = false)
  @Enumerated(EnumType.STRING)
  protected CardLevel cardLevel;

  @Column(name = "created", nullable = false)
  protected LocalDateTime created;

  @Column(name = "updated", nullable = false)
  protected LocalDateTime updated;

  @Column(name = "deleted")
  protected LocalDateTime deleted;

  @Column(name = "exp_date")
  protected LocalDate expDate;

  @Column(name = "cvv", nullable = false)
  protected String cvv;

  @Column(name = "amount")
  protected BigDecimal amount;

  @PrePersist
  private void onCreate() {
    this.created = LocalDateTime.now();
    this.updated = this.created;
    status = status.IN_PROCESS;
  }

  @PreUpdate
  private void onUpdate() {
    this.updated = LocalDateTime.now();
  }

  public CreditCard() {
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }


  public CardType getCardType() {
    return cardType;
  }

  public void setCardType(CardType cardType) {
    this.cardType = cardType;
  }

  public CardLevel getCardLevel() {
    return cardLevel;
  }

  public void setCardLevel(CardLevel cardLevel) {
    this.cardLevel = cardLevel;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updated = updated;
  }

  public LocalDateTime getDeleted() {
    return deleted;
  }

  public void setDeleted(LocalDateTime deleted) {
    this.deleted = deleted;
  }

  public String getCvv() {
    return cvv;
  }

  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  public AccountAndCardStatus getStatus() {
    return status;
  }

  public void setStatus(AccountAndCardStatus status) {
    this.status = status;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDate getExpDate() {
    return expDate;
  }

  public void setExpDate(LocalDate expDate) {
    this.expDate = expDate;
  }

  public CreditCard(UUID id, String cardNumber, CardType cardType, CardLevel cardLevel, LocalDateTime created,
                    LocalDateTime updated, LocalDateTime deleted, LocalDate expDate, String cvv, AccountAndCardStatus status,
                    BigDecimal amount) {
    this.id = id;
    this.cardNumber = cardNumber;
    this.cardType = cardType;
    this.cardLevel = cardLevel;
    this.created = created;
    this.updated = updated;
    this.deleted = deleted;
    this.expDate = expDate;
    this.cvv = cvv;
    this.status = status;
    this.amount = amount;
  }
}
