package com.iunetworks.ctontrollers.legalcreditcardcontroller;


import com.iunetworks.legalcreditcardservice.LegalCreditCardTransferService;
import com.iunetworks.transactionmodel.CardToAccountTransactionModel;
import com.iunetworks.transactionmodel.CardToCardTransactionModel;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/LtoLCard")
public class LegalCreditCardTransferController {

  public LegalCreditCardTransferController(LegalCreditCardTransferService service) {
    this.service = service;
  }

  private final LegalCreditCardTransferService service;


  @PutMapping("/send-to-account")
  public void LtoLAccount(@RequestBody final CardToAccountTransactionModel model) {
    service.LtoLAccount(model.getFromCard(), model.getToAccount(), model.getAmount());
  }

  @PutMapping("/send-to-card")
  public void LtoLCard(@RequestBody final CardToCardTransactionModel model) {
    service.LtoLCard(model.getFromCard(), model.getToCard(), model.getAmount());
  }
}
