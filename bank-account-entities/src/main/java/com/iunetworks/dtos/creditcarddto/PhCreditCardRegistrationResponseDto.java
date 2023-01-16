package com.iunetworks.dtos.creditcarddto;

import com.iunetworks.dtos.feiginclientdto.CardRegistrationResponseDto;
import com.iunetworks.enums.AccountAndCardStatus;
import com.iunetworks.enums.CardLevel;
import com.iunetworks.enums.CardType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PhCreditCardRegistrationResponseDto extends CardRegistrationResponseDto {

  private CardType cardType;

  private CardLevel cardLevel;

  private LocalDateTime created;

  private LocalDateTime updated;

  private LocalDateTime deleted;

  private Long cardNumber;

  private LocalDate expDate;

  private String cvv;

  private AccountAndCardStatus status;

  public PhCreditCardRegistrationResponseDto() {
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updated = updated;
  }

  public LocalDateTime getDeleted() {
    return deleted;
  }

  public void setDeleted(LocalDateTime deleted) {
    this.deleted = deleted;
  }

  public CardType getCardType() {
    return cardType;
  }

  public void setCardType(CardType cardType) {
    this.cardType = cardType;
  }

  public CardLevel getCardLevel() {
    return cardLevel;
  }

  public void setCardLevel(CardLevel cardLevel) {
    this.cardLevel = cardLevel;
  }

  public Long getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(Long cardNumber) {
    this.cardNumber = cardNumber;
  }

  public LocalDate getExpDate() {
    return expDate;
  }

  public void setExpDate(LocalDate expDate) {
    this.expDate = expDate;
  }

  public String getCvv() {
    return cvv;
  }

  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  public AccountAndCardStatus getStatus() {
    return status;
  }

  public void setStatus(AccountAndCardStatus status) {
    this.status = status;
  }
}
