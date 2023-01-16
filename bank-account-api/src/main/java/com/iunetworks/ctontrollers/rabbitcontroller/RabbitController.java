package com.iunetworks.ctontrollers.rabbitcontroller;

import com.iunetworks.rabbitmqaccount.TransferInformation;
import com.iunetworks.transferservice.model.AmountTransaction;
import com.iunetworks.transferservice.transfer.TransferService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rabmq", produces = MediaType.APPLICATION_JSON_VALUE,
  consumes = MediaType.APPLICATION_JSON_VALUE)


public class RabbitController {
  private final TransferService transfer;
  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public RabbitController(TransferService transfer, RabbitTemplate rabbitTemplate) {
    this.transfer = transfer;
    this.rabbitTemplate = rabbitTemplate;
  }

  @PostMapping("/transfer")
  public TransferInformation transfer(@RequestBody AmountTransaction amountTransaction) {

    TransferInformation transferInformation = transfer.transfer(amountTransaction);
    rabbitTemplate.convertAndSend("", "transfer-reg", transferInformation);
    return transferInformation;
  }
}

