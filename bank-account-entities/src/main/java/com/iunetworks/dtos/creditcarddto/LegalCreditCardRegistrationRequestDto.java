package com.iunetworks.dtos.creditcarddto;

import com.iunetworks.enums.CardLevel;
import com.iunetworks.enums.CardType;
import com.iunetworks.enums.Currency;

public class LegalCreditCardRegistrationRequestDto {
  private CardType cardType;

  private CardLevel cardLevel;

  private Currency currency;


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

  public LegalCreditCardRegistrationRequestDto() {
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
}
