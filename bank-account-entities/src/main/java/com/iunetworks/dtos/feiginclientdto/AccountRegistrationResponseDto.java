package com.iunetworks.dtos.feiginclientdto;


import com.iunetworks.enums.AccountAndCardStatus;
import com.iunetworks.enums.Currency;
import com.sun.istack.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountRegistrationResponseDto {

  @NotNull
  private Currency currency;

  @NotNull
  private LocalDateTime created;

  @NotNull
  private LocalDateTime updated;

  private LocalDateTime deleted;

  private AccountAndCardStatus status;

  private BigDecimal amount;

  private long accountNumber;

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
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

  public AccountAndCardStatus getStatus() {
    return status;
  }

  public void setStatus(AccountAndCardStatus status) {
    this.status = status;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }
}
