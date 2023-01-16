package com.iunetworks.transactionmodel;

import java.math.BigDecimal;

public class AccountToCardTransactionModel {


  private String fromAccount;

  private String toCard;

  private BigDecimal amount;

  public String getFromAccount() {
    return fromAccount;
  }

  public void setFromAccount(String fromAccount) {
    this.fromAccount = fromAccount;
  }

  public String getToCard() {
    return toCard;
  }

  public void setToCard(String toCard) {
    this.toCard = toCard;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
