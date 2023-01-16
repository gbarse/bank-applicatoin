package com.iunetworks.ctontrollers.phcreditcardcontroller;


import com.iunetworks.dtos.creditcarddto.PhCreditCardRegistrationRequestDto;
import com.iunetworks.dtos.creditcarddto.PhCreditCardRegistrationResponseDto;
import com.iunetworks.physicalcreditcardservice.PhysicalCreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/credit-card")
public class PhCreditCardController {

  private final PhysicalCreditCardService physicalCreditCardService;

  @Autowired
  public PhCreditCardController(PhysicalCreditCardService physicalCreditCardService) {
    this.physicalCreditCardService = physicalCreditCardService;
  }


  @PostMapping("/{id}")
  public PhCreditCardRegistrationResponseDto create(@PathVariable(name = "id") UUID id,
                                                    @RequestBody PhCreditCardRegistrationRequestDto
                                                      phCreditCardRegistrationRequestDto) {
    return physicalCreditCardService.create(id,phCreditCardRegistrationRequestDto);
  }

  @DeleteMapping("/{id}")
  public void pdelete(@PathVariable(name = "id") UUID id){
    physicalCreditCardService.pdelete(id);
  }

  //  @PreAuthorize("hasAnyAuthority('can_view_all_customer_B')")
  @PostMapping("/accept/{id}")
  public void accept(@PathVariable UUID id) {
    physicalCreditCardService.accept(id);
  }


  @PostMapping("/reject/{id}")
  public String rejectRequest(@PathVariable UUID id,@RequestBody String message){
    physicalCreditCardService.rejectRequest(id);
    return message;

  }


  @GetMapping("/view-all")
  public List<PhCreditCardRegistrationResponseDto> getAll() {
    return physicalCreditCardService.getAllCards();
  }


  @GetMapping("/{id}")
  public PhCreditCardRegistrationResponseDto getCardById(@PathVariable UUID id) {
    return physicalCreditCardService.getCardById(id);
  }
}
