package com.iunetworks.controllers.accountandcardadmincontroller;


import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin1")

public class AccountAndCardAdminController {

  private final AccountAndCardAdminService service;

  public AccountAndCardAdminController(AccountAndCardAdminService service) {
    this.service = service;
  }

  @GetMapping("/call1")
  public String getInProcessAccounts() {
    return service.getInProcessAccounts();
  }

  @GetMapping("/cards")
  public String getInProcessCreditCards() {
    return service.getInProcessCreditCards();
  }

  @PostMapping("/account/accept/{id}")
  public void accept(@PathVariable UUID id) {
    service.accept(id);
  }

  @PostMapping("/legal-account/accept/{id}")
  public void lAccept(@PathVariable UUID id) {
    service.lAccept(id);
  }

  @PostMapping("/legal-account/reject/{id}")
  public void lReject(@PathVariable UUID id) {
    service.lReject(id);
  }

  @PostMapping("account/reject/{id}")
  public void reject(@PathVariable UUID id) {
    service.reject(id);
  }

  @PostMapping("account/delete/{id}")
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }

  @PostMapping("legal-account/delete/{id}")
  public void lDelete(@PathVariable UUID id) {
    service.lDelete(id);
  }

  @PostMapping("/credit-card/delete/{id}")
  void pdelete(@PathVariable UUID id){
    service.pdelete(id);
  }


  @DeleteMapping("/legal_credit-card/{id}")
  void ldelete(@PathVariable UUID id){
    service.ldelete(id);
  }

  @PostMapping("/accept/{id}")
  void laccept(@PathVariable UUID id){
    service.laccept(id);
  }
  @PostMapping("/credit-card/accept/{id}")
  void paccept(@PathVariable UUID id){
    service.paccept(id);
  }

  @PostMapping("/credit-card/reject/{id}")
  String rejectRequest(@PathVariable UUID id,@RequestBody String message){
    service.rejectRequest(id);
    return message;
  }

  @PostMapping("/legal_credit-card/reject/{id}")
  void lrejectRequest(@PathVariable UUID id){
    service.lrejectRequest(id);
  }

}
