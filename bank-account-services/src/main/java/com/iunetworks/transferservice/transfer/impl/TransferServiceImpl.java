package com.iunetworks.transferservice.transfer.impl;


import com.iunetworks.exceptions.ApplicationException;
import com.iunetworks.rabbitmqaccount.TransferInformation;
import com.iunetworks.transferservice.model.AmountTransaction;
import com.iunetworks.transferservice.strategy.TransferStrategyRegister;
import com.iunetworks.transferservice.transfer.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransferServiceImpl implements TransferService {

  private final TransferStrategyRegister transferStrategyRegister;

  public TransferServiceImpl(final TransferStrategyRegister transferStrategyRegister) {
    this.transferStrategyRegister = transferStrategyRegister;
  }

  @Override
  @Transactional
  public TransferInformation transfer(final AmountTransaction amountTransaction) {
    if (amountTransaction.getAmount().signum() != 1) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid amount");
    }
    transferStrategyRegister.byStrategy(amountTransaction.getTransferType())
      .transfer(amountTransaction);
    return null;
  }
}
