package com.iunetworks.controllers.accountandcardadmincontroller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "bank-account-management-application")
public interface AccountAndCardClient {

  @GetMapping("/admin/accounts")
  String getInProcessAccounts();

  @GetMapping("/admin/cards")
  String getInProcessCreditCards();

  @PostMapping("/account/accept/{id}")
  void accept(@PathVariable UUID id);

  @PostMapping("/legal-account/accept/{id}")
  void lAccept(@PathVariable UUID id);

  @PostMapping("/legal-account/reject/{id}")
  void lReject(@PathVariable UUID id);

  @PostMapping("/account/reject/{id}")
  void reject(@PathVariable UUID id);

  @PostMapping("/account/delete/{id}")
  void delete(@PathVariable UUID id);

  @PostMapping("/legal-account/delete/{id}")
  void lDelete(@PathVariable UUID id);

  @PostMapping("/credit-card/delete/{id}")
   void pdelete(@PathVariable UUID id);

  @DeleteMapping("/legal_credit-card/{id}")
  void ldelete(@PathVariable UUID id);

  @PostMapping("/legal_credit-card/accept/{id}")
  void laccept(@PathVariable UUID id);

  @PostMapping("/credit-card/accept/{id}")
  void paccept(@PathVariable UUID id);

  @PostMapping("/credit-card/reject/{id}")
   String rejectRequest(@PathVariable UUID id);

  @PostMapping("/legal_credit-card/reject/{id}")
   void lrejectRequest(@PathVariable UUID id);
}
