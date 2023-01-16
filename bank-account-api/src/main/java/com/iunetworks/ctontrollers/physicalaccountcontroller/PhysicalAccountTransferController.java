package com.iunetworks.ctontrollers.physicalaccountcontroller;

import com.iunetworks.physicalaccountservice.PhysicalAccountTransferService;
import com.iunetworks.transactionmodel.AccountToCardTransactionModel;
import com.iunetworks.transactionmodel.AccountTransactionModel;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Pto")


public class PhysicalAccountTransferController {

  private final PhysicalAccountTransferService physicalAccountTransferService;

  public PhysicalAccountTransferController(PhysicalAccountTransferService physicalAccountTransferService) {
    this.physicalAccountTransferService = physicalAccountTransferService;
  }


  @PutMapping("/send-to")
  public void PtoPAccount(@RequestBody final AccountTransactionModel model) {
    physicalAccountTransferService.PtoPAccount(model.getFromAccount(), model.getToAccount(), model.getAmount());
  }

  @PutMapping("/send-to-card")
  public void PtoLCard(@RequestBody final AccountToCardTransactionModel model) {
    physicalAccountTransferService.PtoLCard(model.getFromAccount(), model.getToCard(), model.getAmount());
  }
}
