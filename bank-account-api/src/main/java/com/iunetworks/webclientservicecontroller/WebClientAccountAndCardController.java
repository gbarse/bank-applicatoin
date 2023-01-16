package com.iunetworks.webclientservicecontroller;


import com.iunetworks.dtos.feiginclientdto.AccountRegistrationResponseDto;
import com.iunetworks.dtos.feiginclientdto.CardRegistrationResponseDto;
import com.iunetworks.webclientservice.WebClientAccountAndCardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class WebClientAccountAndCardController {

  private final WebClientAccountAndCardService service;

  public WebClientAccountAndCardController(WebClientAccountAndCardService service) {
    this.service = service;
  }

  @GetMapping("/accounts")
//  @PreAuthorize("hasAnyAuthority('can_update_admin_B')")
  public List<AccountRegistrationResponseDto> getInProcessAccounts() {
    return service.getInProcessAccounts();
  }


  @GetMapping("/cards")
  public List<CardRegistrationResponseDto> getInProcessCreditCards() {
    return service.getInProcessCards();
  }


}
