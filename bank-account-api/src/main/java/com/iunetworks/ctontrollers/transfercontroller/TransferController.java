package com.iunetworks.ctontrollers.transfercontroller;


import com.iunetworks.transferservice.model.AmountTransaction;
import com.iunetworks.transferservice.transfer.TransferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {

  private final TransferService transferService;


  public TransferController(TransferService transferService) {
    this.transferService = transferService;
  }


  @PostMapping
  public void transfer(@RequestBody AmountTransaction amountTransaction) {
    transferService.transfer(amountTransaction);
  }
}

