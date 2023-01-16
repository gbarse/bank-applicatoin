package com.iunetworks.dtos.transferinfodto;

import com.iunetworks.rabbitmqaccount.TransferInformation;

import java.math.BigDecimal;

public class TransferInfoDto {
  BigDecimal finalAmount;
  TransferInformation transferInformation;

  public BigDecimal getFinalAmount() {
    return finalAmount;
  }

  public void setFinalAmount(BigDecimal finalAmount) {
    this.finalAmount = finalAmount;
  }

  public TransferInformation getTransferInformation() {
    return transferInformation;
  }

  public void setTransferInformation(TransferInformation transferInformation) {
    this.transferInformation = transferInformation;
  }
}

