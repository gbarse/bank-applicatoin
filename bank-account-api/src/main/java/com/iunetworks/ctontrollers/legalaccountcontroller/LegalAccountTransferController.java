package com.iunetworks.ctontrollers.legalaccountcontroller;


import com.iunetworks.transactionmodel.AccountToCardTransactionModel;
import com.iunetworks.transactionmodel.AccountTransactionModel;
import com.iunetworks.legalaccountservice.LegalAccountTransferService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/LtoL")
public class LegalAccountTransferController {

  private final LegalAccountTransferService legalAccountTransferService;

  public LegalAccountTransferController(LegalAccountTransferService legalAccountTransferService) {
    this.legalAccountTransferService = legalAccountTransferService;
  }

  @PutMapping("/send-to")
  public void LtoLAccount(@RequestBody final AccountTransactionModel model) {
    legalAccountTransferService.LtoLAccount(model.getFromAccount(), model.getToAccount(), model.getAmount());
  }
  @PutMapping("/send-to-card")
  public void LtoLCard(@RequestBody final AccountToCardTransactionModel model)  {
    legalAccountTransferService.LtoLCard(model.getFromAccount(),model.getToCard(), model.getAmount());
  }
}
