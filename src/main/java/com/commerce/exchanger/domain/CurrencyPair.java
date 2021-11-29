package com.commerce.exchanger.domain;

import com.commerce.exchanger.app.exception.ExchangeErrorException;
import lombok.Value;

@Value
public class CurrencyPair {

  String fromCurrency;
  String toCurrency;

  private CurrencyPair(String fromCurrency, String toCurrency) {
    this.fromCurrency = fromCurrency;
    this.toCurrency = toCurrency;
  }

  public static CurrencyPair of(String fromCurrency, String toCurrency) {
    if(fromCurrency.equalsIgnoreCase(toCurrency)) {
      throw new ExchangeErrorException("Invalid currency combination");
    }
    return new CurrencyPair(fromCurrency, toCurrency);
  }
}
