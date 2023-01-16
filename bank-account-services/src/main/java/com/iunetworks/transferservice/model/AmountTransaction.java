package com.iunetworks.transferservice.model;

import java.math.BigDecimal;

public class AmountTransaction {

  private String from;

  private String to;

  private BigDecimal amount;

  private AmountTransferType transferType;

  public AmountTransaction() {
  }

  public AmountTransferType getTransferType() {
    return transferType;
  }

  public void setTransferType(AmountTransferType transferType) {
    this.transferType = transferType;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}

