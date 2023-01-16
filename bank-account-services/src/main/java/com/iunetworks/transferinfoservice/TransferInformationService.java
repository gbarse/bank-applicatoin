package com.iunetworks.transferinfoservice;

import com.iunetworks.rabbitmqaccount.TransferInformation;
import org.springframework.stereotype.Service;


@Service
public class TransferInformationService {

  private final TransferInformation transferInformation;

  public TransferInformationService(TransferInformation transferInformation) {
    this.transferInformation = transferInformation;
  }
}
