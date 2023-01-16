package com.iunetworks.entities;

import com.iunetworks.enums.AccountAndCardStatus;
import com.iunetworks.enums.Currency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class Account {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")

  @Column(name = "id", nullable = false)
  protected UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  protected AccountAndCardStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "currency")
  protected Currency currency;

  @Column(name = "amount")
  protected BigDecimal amount;

  @Column(name = "created", nullable = false)
  protected LocalDateTime created;

  @Column(name = "updated", nullable = false)
  protected LocalDateTime updated;

  @Column(name = "deleted")
  protected LocalDateTime deleted;

  @Column(name = "account_number")
  protected String accountNumber;


  @PreUpdate
  protected void onUpdate() {
    this.updated = LocalDateTime.now();
  }

  @PrePersist
  private void prePersist() {
    created= LocalDateTime.now();
    status = AccountAndCardStatus.IN_PROCESS;
  }

  public Account() {
  }

  public Account(UUID id, AccountAndCardStatus status, Currency currency, BigDecimal amount, LocalDateTime created,
                 LocalDateTime updated, LocalDateTime deleted, String accountNumber) {
    this.id = id;
    this.status = status;
    this.currency = currency;
    this.amount = amount;
    this.created = created;
    this.updated = updated;
    this.deleted = deleted;
    this.accountNumber = accountNumber;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public AccountAndCardStatus getStatus() {
    return status;
  }

  public void setStatus(AccountAndCardStatus status) {
    this.status = status;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
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

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }
}
