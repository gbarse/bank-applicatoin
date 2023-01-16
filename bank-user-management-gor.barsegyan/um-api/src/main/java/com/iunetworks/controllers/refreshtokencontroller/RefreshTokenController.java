package com.iunetworks.controllers.refreshtokencontroller;

import com.iunetworks.BankUserRepository;
import com.iunetworks.CustomerUserRepository;
import com.iunetworks.securityconfig.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/refreshtoken")
public class RefreshTokenController {

  private final JwtTokenUtil jwtTokenUtil;
  private final BankUserRepository bankUserRepository;

  private final CustomerUserRepository customerUserRepository;

  @Autowired
  public RefreshTokenController(JwtTokenUtil jwtTokenUtil, BankUserRepository bankUserRepository, CustomerUserRepository customerUserRepository) {
    this.jwtTokenUtil = jwtTokenUtil;
    this.bankUserRepository = bankUserRepository;

    this.customerUserRepository = customerUserRepository;
  }

  @GetMapping("/bank-user")
  public List<String> addingExpirationToAccessesTokenB() {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


    return bankUserRepository.findByEmailAndDeletedIsNull(userDetails.getUsername())
      .map(b -> generateBTokens(userDetails.getUsername()))

      .orElseThrow(() -> new RuntimeException("exc"));

  }

  private List<String> generateBTokens(String email) {

    return jwtTokenUtil.doGenerateBToken(bankUserRepository.findByEmailAndDeletedIsNull(email).get());
  }

  @GetMapping("/user")
  public List<String> addingExpirationToAccessesTokenC() {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


    return customerUserRepository.findByEmailAndDeletedIsNull(userDetails.getUsername())
      .map(C -> generateCTokens(userDetails.getUsername()))

      .orElseThrow(() -> new RuntimeException("exc"));

  }

  private List<String> generateCTokens(String email) {

    return jwtTokenUtil.doGenerateCToken(customerUserRepository.findByEmailAndDeletedIsNull(email).get());
  }


}



