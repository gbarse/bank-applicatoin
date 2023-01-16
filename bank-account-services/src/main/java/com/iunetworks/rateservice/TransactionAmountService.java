package com.iunetworks.rateservice;

import com.iunetworks.dtos.transactionsdto.TransactionDto;
import com.iunetworks.enums.Currency;
import com.iunetworks.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class TransactionAmountService {

  private final WebClient.Builder webClientBuilder;

  @Autowired
  public TransactionAmountService(@Qualifier("webClientBean")WebClient.Builder builder) {
    this.webClientBuilder = builder;
  }

  public BigDecimal getTransactionAmount(Currency out, Currency in, BigDecimal amount){
    try {
      TransactionDto block = webClientBuilder
        .baseUrl("https://v6.exchangerate-api.com/")
        .build()
        .get()
        .uri(String.format("/v6/4872c6a1a861554e8dc39e05/pair/%s/%s/%f", out.name(), in.name(), amount))
        .retrieve()
        .bodyToMono(TransactionDto.class)
        .block();
      if(block == null){
        throw new ApplicationException(HttpStatus.SERVICE_UNAVAILABLE, "API call for conversion failed");
      }
      return block.getConversionResult();
    }
    catch (Exception ignored){
      throw new ApplicationException(HttpStatus.SERVICE_UNAVAILABLE, "API call for conversion failed");    }
  }


}
