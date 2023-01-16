package com.iunetworks.ctontrollers.legalaccountcontroller;

import com.iunetworks.dtos.legalaccountdto.LegalAccountRegistrationRequestDto;
import com.iunetworks.dtos.legalaccountdto.LegalAccountRegistrationResponseDto;
import com.iunetworks.legalaccountservice.LegalAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/legal-account")
public class LegalAccountController {

  private final LegalAccountService legalAccountService;

  public LegalAccountController(LegalAccountService legalAccountService) {
    this.legalAccountService = legalAccountService;
  }

  @PostMapping("/create")
  public LegalAccountRegistrationResponseDto create(
    @RequestBody LegalAccountRegistrationRequestDto
      requestDto) {
    return legalAccountService.create(requestDto);
  }

  @PostMapping("/accept/{id}")
  public void accept(@PathVariable UUID id) {
    legalAccountService.accept(id);
  }


  @PostMapping("/delete/{id}")
  public void delete(@PathVariable UUID id) {
    legalAccountService.delete(id);
  }

  @PostMapping("/reject/{id}")
  public String rejectRequest(@PathVariable UUID id, @RequestBody String message) {
    legalAccountService.rejectRequest(id);
    return message;
  }

  @GetMapping("/view-all")
  public List<LegalAccountRegistrationResponseDto> getAll() {
    return legalAccountService.getAllAccounts();
  }


  @GetMapping("/{id}")
  public LegalAccountRegistrationResponseDto getBankUserById(@PathVariable UUID id) {
    return legalAccountService.getAccountById(id);
  }
}
