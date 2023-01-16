package com.iunetworks.controllers.accountandcardadmincontroller;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Service
public class AccountAndCardAdminService {

  private final AccountAndCardClient client;


  public AccountAndCardAdminService(AccountAndCardClient client) {
    this.client = client;
  }

  public String getInProcessAccounts() {
    return client.getInProcessAccounts();
  }

  public String getInProcessCreditCards() {
    return client.getInProcessCreditCards();
  }

  public void accept(@PathVariable UUID id) {
    client.accept(id);
  }

  public void lAccept(@PathVariable UUID id) {
    client.lAccept(id);
  }

  public void lReject(@PathVariable UUID id) {
    client.lReject(id);
  }

  public void reject(@PathVariable UUID id) {
    client.reject(id);
  }

  public void delete(@PathVariable UUID id) {
    client.delete(id);
  }

  public void lDelete(@PathVariable UUID id) {
    client.lDelete(id);
  }

  public void pdelete(@PathVariable UUID id) {
    client.pdelete(id);
  }

  public void ldelete(@PathVariable UUID id) {
    client.ldelete(id);
  }

  public void laccept(@PathVariable UUID id){
    client.laccept(id);
  }

  public void paccept(@PathVariable UUID id){
    client.paccept(id);
  }

  public String rejectRequest(@PathVariable UUID id){
    return client.rejectRequest(id);
  }
  public void lrejectRequest(@PathVariable UUID id){
    client.lrejectRequest(id);
  }
}



