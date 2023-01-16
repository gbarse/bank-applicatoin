package com.iunetworks.ctontrollers.physicalaccountcontroller;

import com.iunetworks.dtos.physicalaccountdto.PhysicalAccountRegistrationRequestDto;
import com.iunetworks.dtos.physicalaccountdto.PhysicalAccountRegistrationResponseDto;
import com.iunetworks.physicalaccountservice.PhysicalAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class PhysicalAccountController {

  private final PhysicalAccountService physicalAccountService;



  public PhysicalAccountController(PhysicalAccountService physicalAccountService) {
    this.physicalAccountService = physicalAccountService;

  }


  @PostMapping("/create")
  public PhysicalAccountRegistrationResponseDto create(
    @RequestBody PhysicalAccountRegistrationRequestDto
      physicalAccountRegistrationRequestDto) {
    return physicalAccountService.create(physicalAccountRegistrationRequestDto);
  }

  //  @PreAuthorize("hasAnyAuthority('can_view_all_customer_B')")
  @PostMapping("/accept/{id}")
  public void accept(@PathVariable UUID id) {
    physicalAccountService.accept(id);
  }

  //  @PreAuthorize("hasAnyAuthority('can_view_all_customer_B')")
  @PostMapping("/delete/{id}")
  public void delete(@PathVariable UUID id) {
    physicalAccountService.delete(id);
  }

  @PostMapping("/reject/{id}")
  public String rejectRequest(@PathVariable UUID id, @RequestBody String message) {
    physicalAccountService.rejectRequest(id);
    return message;

  }

  @GetMapping("/view-all")
  public List<PhysicalAccountRegistrationResponseDto> getAll() {
    return physicalAccountService.getAllAccounts();
  }


  @GetMapping("/{id}")
  public PhysicalAccountRegistrationResponseDto getBankUserById(@PathVariable UUID id) {
    return physicalAccountService.getAccountById(id);
  }


  // testing permission->
  //@PreAuthorize("hasAnyAuthority('can_view_all_customer_by_id_B')")
  @GetMapping("/find-address/{id}")
  public String getCustomerInfo(@PathVariable(name = "id") UUID id) {
    return physicalAccountService.getCustomerInformation(id);
  }
}
