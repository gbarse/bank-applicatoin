package com.iunetworks.transferservice.transfer;

import com.iunetworks.rabbitmqaccount.TransferInformation;
import com.iunetworks.transferservice.model.AmountTransaction;

public interface TransferService {

  TransferInformation transfer(AmountTransaction amountTransaction);
}

