package com.iunetworks.ctontrollers.phcreditcardcontroller;


import com.iunetworks.physicalcreditcardservice.PhysicalCreditCardTransferService;
import com.iunetworks.transactionmodel.CardToAccountTransactionModel;
import com.iunetworks.transactionmodel.CardToCardTransactionModel;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cToc")

public class PhCreditCardTransferController {

  private final PhysicalCreditCardTransferService service;

  public PhCreditCardTransferController(PhysicalCreditCardTransferService service) {
    this.service = service;
  }


  @PutMapping("/send-to-card")
  public void PtoLCard(@RequestBody final CardToCardTransactionModel model) {
    service.PtoLCard(model.getFromCard(), model.getToCard(), model.getAmount());
  }

  @PutMapping("/send-to-account")
  public void LtoLCard(@RequestBody final CardToAccountTransactionModel model) {
    service.PtoLAccount(model.getFromCard(), model.getToAccount(), model.getAmount());
  }
}
