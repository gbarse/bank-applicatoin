package com.iunetworks.rabbitmqaccount;


import com.iunetworks.enums.Currency;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TransferInformation {

  private String fromAccountNumber;
  private String toAccountNumber;
  private BigDecimal amount;
  private BigDecimal fromNewAmount;
  private BigDecimal fromOldAmount;
  private Currency fromCurrency;
  private BigDecimal toNewAmount;
  private BigDecimal toOldAmount;
  private Currency toCurrency;
  private LocalDateTime transferDate;
  private BigDecimal finalAmount;

  public String getFromAccountNumber() {
    return fromAccountNumber;
  }

  public void setFromAccountNumber(String fromAccountNumber) {
    this.fromAccountNumber = fromAccountNumber;
  }

  public String getToAccountNumber() {
    return toAccountNumber;
  }

  public void setToAccountNumber(String toAccountNumber) {
    this.toAccountNumber = toAccountNumber;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getFromNewAmount() {
    return fromNewAmount;
  }

  public void setFromNewAmount(BigDecimal fromNewAmount) {
    this.fromNewAmount = fromNewAmount;
  }

  public BigDecimal getFromOldAmount() {
    return fromOldAmount;
  }

  public void setFromOldAmount(BigDecimal fromOldAmount) {
    this.fromOldAmount = fromOldAmount;
  }

  public Currency getFromCurrency() {
    return fromCurrency;
  }

  public void setFromCurrency(Currency fromCurrency) {
    this.fromCurrency = fromCurrency;
  }

  public BigDecimal getToNewAmount() {
    return toNewAmount;
  }

  public void setToNewAmount(BigDecimal toNewAmount) {
    this.toNewAmount = toNewAmount;
  }

  public BigDecimal getToOldAmount() {
    return toOldAmount;
  }

  public void setToOldAmount(BigDecimal toOldAmount) {
    this.toOldAmount = toOldAmount;
  }

  public Currency getToCurrency() {
    return toCurrency;
  }

  public void setToCurrency(Currency toCurrency) {
    this.toCurrency = toCurrency;
  }

  public LocalDateTime getTransferDate() {
    return transferDate;
  }

  public void setTransferDate(LocalDateTime transferDate) {
    this.transferDate = transferDate;
  }

  public BigDecimal getFinalAmount() {
    return finalAmount;
  }

  public void setFinalAmount(BigDecimal finalAmount) {
    this.finalAmount = finalAmount;
  }

  public TransferInformation() {
  }

  public TransferInformation(String fromAccountNumber, String toAccountNumber, BigDecimal amount, BigDecimal fromNewAmount,
                             BigDecimal fromOldAmount, Currency fromCurrency, BigDecimal toNewAmount, BigDecimal toOldAmount,
                             Currency toCurrency, LocalDateTime transferDate, BigDecimal finalAmount) {
    this.fromAccountNumber = fromAccountNumber;
    this.toAccountNumber = toAccountNumber;
    this.amount = amount;
    this.fromNewAmount = fromNewAmount;
    this.fromOldAmount = fromOldAmount;
    this.fromCurrency = fromCurrency;
    this.toNewAmount = toNewAmount;
    this.toOldAmount = toOldAmount;
    this.toCurrency = toCurrency;
    this.transferDate = transferDate;
    this.finalAmount = finalAmount;
  }
}






