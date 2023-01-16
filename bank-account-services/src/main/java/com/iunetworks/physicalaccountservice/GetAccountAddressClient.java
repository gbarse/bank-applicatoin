package com.iunetworks.physicalaccountservice;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;


@FeignClient(name = "bank-user-management-application")

public interface GetAccountAddressClient {

  @GetMapping("/api/user_bank/find-address/{id}")
  String getCustomerInfo(@PathVariable(name = "id") UUID id);
}
