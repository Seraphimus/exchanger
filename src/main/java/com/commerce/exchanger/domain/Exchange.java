package com.commerce.exchanger.domain;

import com.commerce.exchanger.app.exception.ExchangerGeneralException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class Exchange {

  CurrencyPair pair;
  BigDecimal amount;
  boolean isExchangingToBase;

  public static Exchange buildExchange(CurrencyPair pair, String baseCurrency, BigDecimal amount) {
    return Exchange.builder()
        .pair(pair)
        .isExchangingToBase(baseCurrency.equalsIgnoreCase(pair.getToCurrency()))
        .amount(amount)
        .build();
  }

  public Client performExchangeForClientWithRate(Client client, Rate rate) {
    if (client == null) {
      throw new ExchangerGeneralException("Client unknown");
    }
    if (!client.canExchange(pair.getFromCurrency(), amount)) {
      throw new ExchangerGeneralException("Insufficient funds");
    }
    client.performExchange(pair, amount, calculateExchangedAmount(amount, rate));
    return client;
  }

  private BigDecimal calculateExchangedAmount(BigDecimal amount, Rate rate) {
    if (isExchangingToBase) {
      return calculateForeignToBase(amount, rate.getValue());
    }
    return calculateBaseToForeign(amount, rate.getValue());
  }

  private BigDecimal calculateBaseToForeign(BigDecimal amount, BigDecimal rate) {
    return amount.setScale(4, RoundingMode.HALF_UP)
        .divide(rate, RoundingMode.HALF_UP)
        .setScale(4, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateForeignToBase(BigDecimal amount, BigDecimal rate) {
    return amount.setScale(4, RoundingMode.HALF_UP).multiply(rate)
        .setScale(2, RoundingMode.HALF_UP);
  }
}
