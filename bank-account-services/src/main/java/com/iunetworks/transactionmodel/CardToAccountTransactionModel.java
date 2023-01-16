package com.iunetworks.transactionmodel;

import java.math.BigDecimal;

public class CardToAccountTransactionModel {

  private String fromCard;

  private String toAccount;

  private BigDecimal amount;

  public String getFromCard() {
    return fromCard;
  }

  public void setFromCard(String fromCard) {
    this.fromCard = fromCard;
  }

  public String getToAccount() {
    return toAccount;
  }

  public void setToAccount(String toAccount) {
    this.toAccount = toAccount;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
