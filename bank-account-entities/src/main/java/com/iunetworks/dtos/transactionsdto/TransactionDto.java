package com.iunetworks.dtos.transactionsdto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransactionDto {

  private String result;
  private String documentation;
  private String termsOfUse;
  private long timeLastUpdateUnix;
  private String timeLastUpdateUtc;
  private long timeNextUpdateUnix;
  private String timeNextUpdateUtc;
  private String baseCode;
  private String targetCode;
  private double conversionRate;

  @JsonProperty("conversion_result")
  private BigDecimal conversionResult;

  public TransactionDto() {
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getDocumentation() {
    return documentation;
  }

  public void setDocumentation(String documentation) {
    this.documentation = documentation;
  }

  public String getTermsOfUse() {
    return termsOfUse;
  }

  public void setTermsOfUse(String termsOfUse) {
    this.termsOfUse = termsOfUse;
  }

  public long getTimeLastUpdateUnix() {
    return timeLastUpdateUnix;
  }

  public void setTimeLastUpdateUnix(long timeLastUpdateUnix) {
    this.timeLastUpdateUnix = timeLastUpdateUnix;
  }

  public String getTimeLastUpdateUtc() {
    return timeLastUpdateUtc;
  }

  public void setTimeLastUpdateUtc(String timeLastUpdateUtc) {
    this.timeLastUpdateUtc = timeLastUpdateUtc;
  }

  public long getTimeNextUpdateUnix() {
    return timeNextUpdateUnix;
  }

  public void setTimeNextUpdateUnix(long timeNextUpdateUnix) {
    this.timeNextUpdateUnix = timeNextUpdateUnix;
  }

  public String getTimeNextUpdateUtc() {
    return timeNextUpdateUtc;
  }

  public void setTimeNextUpdateUtc(String timeNextUpdateUtc) {
    this.timeNextUpdateUtc = timeNextUpdateUtc;
  }

  public String getBaseCode() {
    return baseCode;
  }

  public void setBaseCode(String baseCode) {
    this.baseCode = baseCode;
  }

  public String getTargetCode() {
    return targetCode;
  }

  public void setTargetCode(String targetCode) {
    this.targetCode = targetCode;
  }

  public double getConversionRate() {
    return conversionRate;
  }

  public void setConversionRate(double conversionRate) {
    this.conversionRate = conversionRate;
  }

  public BigDecimal getConversionResult() {
    return conversionResult;
  }

  public void setConversionResult(BigDecimal conversionResult) {
    this.conversionResult = conversionResult;
  }
}

