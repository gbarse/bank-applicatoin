package com.iunetworks.ctontrollers.legalcreditcardcontroller;

import com.iunetworks.dtos.creditcarddto.LegalCreditCardRegistrationRequestDto;
import com.iunetworks.dtos.creditcarddto.LegalCreditCardRegistrationResponseDto;
import com.iunetworks.legalcreditcardservice.LegalCreditCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/legal_credit-card")
public class LegalCreditCardController {


  private final LegalCreditCardService legalCreditCardService;

  public LegalCreditCardController(LegalCreditCardService legalCreditCardService) {
    this.legalCreditCardService = legalCreditCardService;
  }


  @PostMapping("/{id}")
  public LegalCreditCardRegistrationResponseDto create(@PathVariable(name = "id") UUID id,
                                                       @RequestBody LegalCreditCardRegistrationRequestDto
                                                         legalCreditCardRegistrationRequestDto) {
    return legalCreditCardService.create(id, legalCreditCardRegistrationRequestDto);
  }

  @DeleteMapping("/{id}")
  public void ldelete(@PathVariable(name = "id") UUID id){
    legalCreditCardService.ldelete(id);
  }

  @PostMapping("/accept/{id}")
  public void accept(@PathVariable(name = "id") UUID id) {
    legalCreditCardService.accept(id);
  }

  @PostMapping("/reject/{id}")
  public void rejectRequest(@PathVariable(name = "id") UUID id) {
    legalCreditCardService.rejectRequest(id);
  }

  @GetMapping("/getAll")
  public List<LegalCreditCardRegistrationResponseDto> getAllCards(){
   return legalCreditCardService.getAllCards();
  }

  @GetMapping("/getById/{id}")
  public LegalCreditCardRegistrationResponseDto getCardById(@PathVariable(name = "id") UUID id) {
    return legalCreditCardService.getCardById(id);
  }

}
