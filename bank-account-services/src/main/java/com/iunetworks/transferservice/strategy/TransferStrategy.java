package com.iunetworks.transferservice.strategy;

import com.iunetworks.transferservice.model.AmountTransaction;
import com.iunetworks.transferservice.model.AmountTransferType;

public interface TransferStrategy {

  void transfer(AmountTransaction amountTransaction);

  AmountTransferType byType();

}

